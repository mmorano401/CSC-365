package readers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class getWords {
    public static String jsoup(String url) throws IOException{
        
        try{
            Document doc = Jsoup.connect(url).get();
            return getText(doc);
        }
        catch (Exception e){
            return null;
        }
        
    }

    private static String getText(Document doc) throws NullPointerException{
        try{Element content = doc.select("#content").first();
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
    catch(Exception e){
        System.out.println(e);
        return null;
    }
    }

    public static Map<String, String> getTitles(String url) throws IOException{
        try{
            Map<String, String> list = new HashMap<>(); 
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

    public static String getTitle(String url) throws IOException{
        try{
            String list = null;
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            list = title;
            return list;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
        
        
        
        
    }
}
