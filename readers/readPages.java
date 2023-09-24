package readers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.FileReader;
import java.io.IOException;

public class readPages {
    public static JsonArray pages() {
        // Path path = Paths.get(System.getProperty("user.home")+ "/data/webpages.json");
        String path = System.getProperty("user.home")+ "\\source\\repos\\CSC365\\data\\webpages.json";
        System.out.println(path);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        
        try (FileReader fileReader = new FileReader(path);
        JsonReader reader = factory.createReader(fileReader);){
            JsonObject object = reader.readObject();
            JsonArrayBuilder builder = Json.createArrayBuilder();
            JsonArray webpages = object.getJsonArray("webpages");
            builder.add(webpages);
            builder.build();
            reader.close();
            return webpages;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
