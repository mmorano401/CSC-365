import java.io.IOException;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import readers.getUrlArray;
import readers.getWords;
import java.util.*;
import readers.TFIDF;
import tablesAndSuch.*;

public class startup{
    

    public static void main(String[] args) throws IOException{
        // get array of URLs of wikipedia pages
        JsonArray array = getUrlArray.pages();
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject temp = array.getJsonObject(i);
            String url = temp.getString("url");
            urls.add(url);
            System.out.println(url);
        }
        try{
            // use JSoup to parse through wikipedia pages, loop to go through all pages
            System.out.println("Getting words...");
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < array.size(); ++i) {
            String text = getWords.jsoup(array.getJsonObject(i));
            words.add(text);
        }
        System.out.println("complete");
        System.out.println();

        // get list of docs in order of relevance
        HT similarity = TFIDF.getTFIDFValue(words);
        urls.remove(TFIDF.userSelection);
        Map<String, String> wikiTitles = new HashMap<>();
        for (String url : urls) {
            getWords.getTitles(wikiTitles, url);
        }
        

        // displayArticles(similarity, titles);
        

        }
        catch(Exception e){
            System.out.println("Error : " + e);
        }
        
    }
}