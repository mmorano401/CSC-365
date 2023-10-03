package methods;

import java.util.ArrayList;
import java.util.Map;
import readers.getUrlArray;
import javax.json.JsonArray;

public class AppSettings {
    private static AppSettings instance;

    private JsonArray jsonArray;
    private ArrayList<String> urls;
    private Map<String, String> titles;

    private AppSettings(){
        jsonArray = getUrlArray.pages();
        urls = getUrls.getURLS();
        titles = getTitleMap.getTitles();
    }

    public static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
        }
        return instance;
    }

    public JsonArray getArray(){
        return jsonArray;
    }

    public ArrayList<String> getUrlList(){
        return urls;
    }

    public Map<String, String> getAllTitles(){
        return titles;
    }
}
