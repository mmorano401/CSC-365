import java.io.IOException;
import java.util.ArrayList;

import javax.json.JsonArray;
import readers.getUrlArray;
import readers.getWords;

public class startup{

    public static void main(String[] args) throws IOException{
        JsonArray array = getUrlArray.pages();
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < array.size(); ++i) {
            String text = getWords.jsoup(array.getJsonObject(i));
            words.add(text);
        }
    }
}