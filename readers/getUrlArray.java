package readers;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.FileReader;
import java.io.IOException;

public class getUrlArray {

    public static JsonArray pages() {
        // Path to JSON doc with list of URLs contained within a JSON array
        String path = System.getProperty("user.home")+ "\\source\\repos\\CSC-365\\data\\webpages.json";
        
        // Create JSON Reader in order to read the URL list 
        JsonReaderFactory factory = Json.createReaderFactory(null);
        
        // try/catch needed in order to ensure a document is found in the path specified
        try (FileReader fileReader = new FileReader(path);
        JsonReader reader = factory.createReader(fileReader);){
            // Read JSON doc and create array with URLs
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
