import javax.json.JsonArray;
import readers.readPages;

public class startup{

    public static void main(String[] args){
        JsonArray array = readPages.pages();
        System.out.println(array);
    }
}