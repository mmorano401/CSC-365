import java.io.IOException;
import java.util.ArrayList;
import javax.json.JsonArray;
import readers.getUrlArray;
import readers.getWords;
import java.util.*;
import readers.TFIDF;

public class startup{

    public static void main(String[] args) throws IOException{
        // get array of URLs of wikipedia pages
        JsonArray array = getUrlArray.pages();

        // use JSoup to parse through wikipedia pages, loop to go through all pages
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < array.size(); ++i) {
            String text = getWords.jsoup(array.getJsonObject(i));
            words.add(text);
        }

        // get tfidf
        // user input to set starting index
        ArrayList<Map<String, Double>> similarity = new ArrayList<>();
        int starting = 0;
        for (int x = 0 ; x < array.size() - 1; ++x){
            if(x != starting){
                Map<String, Double> tfidf = TFIDF.getSimilarity(words.get(starting), words.get(x));
                similarity.add(tfidf);
            }
        }
    }
}