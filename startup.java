import java.io.IOException;
import java.util.ArrayList;
import javax.json.JsonArray;
import readers.getUrlArray;
import readers.getWords;

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

        
    }
}