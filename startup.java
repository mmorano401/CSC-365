import javax.json.JsonArray;
import readers.getUrlArray;
import java.util.*;
import methods.getUrls;
import methods.getTitleMap;


public class startup{

    

    private JsonArray jsonArray = getUrlArray.pages();

    private String userChoice;

    private ArrayList<String> urls = getUrls.getURLS();

    private Map<String, String> titles = getTitleMap.getTitles();


    

    // public static void main(String[] args) throws IOException{
    //     String title = "CSC 365";
    //     String header = "Wikipedia Recommendations";
    //     String instructions = "Pick an article that interests you to see similar topics!";
    //     String htmlHeading = "<h2 ><center>" + title + "</center></h2><h1><center>"+header+"</center></h1><body><center>"+instructions+"</center></body>";

    //     String pickMessage = "Pick Article";
    //     String htmlOptions = "<select><option selected><center>"+pickMessage+"</center></option>";
    //     StringBuilder selector = new StringBuilder();
    //     for (Map.Entry<String, String> heading : titles.entrySet()) {
    //         String temp = heading.getKey();
    //         selector.append("<option>"+temp+"</option>");
    //     }
    //     String selectorFinish = selector.toString();
    //     String selectorWhole = htmlOptions+selectorFinish;
    // }
   
    

    // private static ArrayList<String> getURLS() {
    //     JsonArray array = getUrlArray.pages();
    //     ArrayList<String> urls = new ArrayList<>();
    //     for (int i = 0; i < array.size(); i++) {
    //         JsonObject temp = array.getJsonObject(i);
    //         String url = temp.getString("url");
    //         urls.add(url);
    //     }
    //     return urls;
    // }

    
    // private static Map<String, String> getTitles(){
    //     try{
    //         ArrayList<String> urls = getURLS();
    //         Map<String, String> wikiTitles = new HashMap<>();
    //         for (String url : urls) {
    //             getWords.getTitles(wikiTitles, url);
    //         }
    //     return wikiTitles;
    // }
    // catch(Exception e){
    //     System.out.println(e);
    //     return null;
    // }
    // }

    // private static Map<String, String> getRec(){
    //     try{
    //         // use JSoup to parse through wikipedia pages, loop to go through all pages
    //         ArrayList<String> words = new ArrayList<>();
    //         for (int i = 0; i < jsonArray.size(); ++i) {
    //             String text = getWords.jsoup(jsonArray.getJsonObject(i));
    //             words.add(text);
    //         }

    //         // get list of docs in order of relevance
    //         HT similarity = TFIDF.getTFIDFValue(words);
    //         urls.remove(TFIDF.userSelection);
    //         titles = null;
    //         for (String url : urls) {
    //             getWords.getTitles(titles, url);
    //         }

    //         titles = getTitles();
    //         double first = similarity.getValue(1);
    //         double second = similarity.getValue(2);

    //         int realFirst = (int) first;
    //         int realSecond = (int) second;

    //         Map<String, String> recs = new HashMap<>();

    //         String one = urls.get(realFirst);
    //         String two = urls.get(realSecond);

    //         String rec1 = null;
    //         String rec2 = null;
            
    //         for ( Map.Entry<String, String> entry : titles.entrySet()) {
    //             if(entry.getValue() == one){
    //                 rec1 = entry.getKey();
    //             }
    //             if(entry.getValue() == two){
    //                 rec2 = entry.getKey();
    //             }
    //         }

    //         recs.put(rec1, one);
    //         recs.put(rec2, two);
    //         return recs;
    //     }
    //     catch(Exception e){
    //         System.out.println(e);
    //         return null;
    //     }
        
    // }
} 
