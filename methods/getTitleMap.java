package methods;

import readers.getWords;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class getTitleMap {

    public static Map<String, String> getTitles(){
        try{
            ArrayList<String> urls = getUrls.getURLS();
            Map<String, String> wikiTitles = new HashMap<>();
            for (String url : urls) {
                getWords.getTitles(wikiTitles, url);
            }
        return wikiTitles;
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
    }
}
