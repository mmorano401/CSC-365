package readers;

import java.io.IOException;
import java.util.Map;

import javax.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class getWords {
    public static String jsoup(JsonObject url) throws IOException{
        Document doc = Jsoup.connect(url.getString("url")).get();
        String text = getText(doc);
        return text;
    }

    private static String getText(Document doc){
        Element content = doc.select("#content").first();
        content.select(".infobox").remove();
        content.select(".reflist").remove();
        content.select(".toc").remove();

        Elements paragraphs = content.select("p");
        StringBuilder text = new StringBuilder();
        for (Element paragraph : paragraphs) {
            text.append(paragraph.text()).append("\n").trimToSize();
        }
        
        return text.toString();
    }

    public static Map<String, String> getTitles(Map<String, String> list, String url) throws IOException{
        try{
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            String parsedUrl = url;
            list.put(title, parsedUrl);
            return list;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
        
        
        
        
    }
}
