package methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonArray;
import org.springframework.stereotype.Component;
import readers.TFIDF;
import tablesAndSuch.HT;
import readers.getWords;


@Component
public class getRec {
    public static Map<String, String> getRec(){
        try{
            // use JSoup to parse through wikipedia pages, loop to go through all pages
            ArrayList<String> words = new ArrayList<>();
            JsonArray jsonArray = AppSettings.getInstance().getArray();
            for (int i = 0; i < jsonArray.size(); ++i) {
                String text = getWords.jsoup(jsonArray.getJsonObject(i));
                words.add(text);
            }
            
            // get list of docs in order of relevance
            HT similarity = TFIDF.getTFIDFValue(words);
            ArrayList<String> urls = AppSettings.getInstance().getUrlList();
            AppSettings.getInstance().getUrlList().remove(TFIDF.userSelection);
            Map<String, String> titles = AppSettings.getInstance().getAllTitles();
            titles = null;
            for (String url : urls) {
                getWords.getTitles(titles, url);
            }

            titles = AppSettings.getInstance().getAllTitles();
            double first = similarity.getValue(1);
            double second = similarity.getValue(2);

            int realFirst = (int) first;
            int realSecond = (int) second;

            Map<String, String> recs = new HashMap<>();

            String one = urls.get(realFirst);
            String two = urls.get(realSecond);

            String rec1 = null;
            String rec2 = null;
            
            for ( Map.Entry<String, String> entry : titles.entrySet()) {
                if(entry.getValue() == one){
                    rec1 = entry.getKey();
                }
                if(entry.getValue() == two){
                    rec2 = entry.getKey();
                }
            }

            recs.put(rec1, one);
            recs.put(rec2, two);
            return recs;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
        
    }
}
