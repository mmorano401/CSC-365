package methods;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.springframework.stereotype.*;
import readers.getUrlArray;


@Component
public class getUrls {

    public static ArrayList<String> getURLS() {
        JsonArray array = getUrlArray.pages();
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject temp = array.getJsonObject(i);
            String url = temp.getString("url");
            urls.add(url);
        }
        return urls;
    }
}
