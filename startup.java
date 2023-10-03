import java.io.IOException;
import java.util.ArrayList;
import javax.json.JsonArray;
import readers.getUrlArray;
import readers.getWords;
import java.util.*;
import readers.TFIDF;
import readers.ComparingDocuments;

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

        // remove common terms
        ArrayList<ArrayList<String>> rareWordsList = new ArrayList<>();
        for (int x = 0 ; x < array.size() - 1; ++x){
            ArrayList<String> uncommon = TFIDF.getTFIDFValue(words.get(x));
            rareWordsList.add(uncommon);
        }

        // get similarity
        Map<Integer, Integer> similarityList = ComparingDocuments.similarity(rareWordsList);

        for (Map.Entry<Integer,Integer> entry : similarityList.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}