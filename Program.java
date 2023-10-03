
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import readers.getUrlArray;
import readers.getWords;
import java.util.*;
import readers.TFIDF;
import tablesAndSuch.*;

public class Program{

    public static String userChoice;

    public static int indexChoice;

    public static ArrayList<String> urls = getURLS();

    public static Map<String, String> titlesUrlsMap = getTitlesUrls();

    public static JsonArray jsonArray = getUrlArray.pages();

    public static ArrayList<String> titles = getTitles();

    public static JFrame frame = new JFrame("Wikipedia Recommendations");


    public static void main(String[] args) throws IOException{
        theProgram();
    }

    private static void theProgram(){
        frame.dispose();
        frame.setSize(750, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int x = (int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth())) / 2;
        int y = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight())) / 2;
        frame.setLocation(x, y);
        JLabel label = new JLabel("Pick which article you are most interested in to see others that would best suit your interests!");
        JComboBox<String> pageList = new JComboBox<>();
        label.setLabelFor(pageList);
        frame.setAlwaysOnTop(true);
        JButton results = new JButton("Similar Results");
        results.setVisible(true);
        for(String name : titles){
            pageList.addItem(name);;
        }
        frame.add(pageList);
        pageList.setSelectedIndex(titles.size()-1);
        JPanel panel = new JPanel();
        panel.removeAll();
        panel.add(label); 
        panel.add(pageList);
        panel.add(results);
        results.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                frame.dispose();
                JFrame frame = new JFrame("Wikipedia Recommendations");
                frame.setSize(750, 150);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                int x = (int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - frame.getWidth())) / 2;
                int y = (int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - frame.getHeight())) / 2;
                frame.setLocation(x, y);

                userChoice = (String) pageList.getSelectedItem();
                indexChoice = getChoiceIndex();

                Map<String, String> recs = getRec();
                if (recs == null){
                    System.exit(0);
                }
                ArrayList<String> urlRec = getUrlRec(recs);
                String[] recTitles = new String[2];
                try{
                    recTitles[0] = getWords.getTitle(urlRec.get(0));
                    recTitles[1] = getWords.getTitle(urlRec.get(1));  
                }
                catch(Exception ex){
                    System.out.println(ex);
                }

                JLabel bestResults = new JLabel();
                bestResults.setText("Best Results : " + recTitles[0] + " || " + recTitles[1]);
                JButton reset = new JButton();
                reset.setText("Reset");
                bestResults.setLabelFor(reset);
                reset.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        panel.removeAll();
                        frame.dispose();
                        resetVariables();
                        theProgram();
                    }
                });
                panel.add(bestResults);
                panel.add(reset);
                frame.add(panel);
                frame.setVisible(true);
            }

           
        });
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void resetVariables(){
        userChoice = null;

        indexChoice = 0;

        urls = getURLS();

        titlesUrlsMap = getTitlesUrls();

        jsonArray = getUrlArray.pages();

        titles = getTitles();

        frame = new JFrame("Wikipedia Recommendations");
    }
    


    private static ArrayList<String> getUrlRec(Map<String, String> recs) throws NullPointerException{
        ArrayList<String> urlRec = new ArrayList<>();
        for(Map.Entry<String, String> value : recs.entrySet()){
            urlRec.add(value.getValue());
        }
        return urlRec;
    }

    // private static int getIndex() {
    //     int i = 0;
    //     int index = 0;
    //     for(String name : titles){
    //         if(name.equals(userChoice)){
    //             index = i;
    //         }
    //         ++i;
    //     }
    //     return index;
    // }

    // private static String[] getRecTitles(Map<String, String> recs){
    //     String[] recTitle = new String[2];
    //     int x = 0;
    //     for (Map.Entry<String, String> value : recs.entrySet()) {
    //         recTitle[x] = value.getKey();
    //         x++;
    //     }
    //     return recTitle;
    // }
   
    

    private static ArrayList<String> getTitles() {
        ArrayList<String> aloneTitle = new ArrayList<>();
        for(Map.Entry<String, String> value : titlesUrlsMap.entrySet()){
            aloneTitle.add(value.getKey());
        }
        return aloneTitle;
    }



    private static ArrayList<String> getURLS() {
        JsonArray array = getUrlArray.pages();
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JsonObject temp = array.getJsonObject(i);
            String url = temp.getString("url");
            urls.add(url);
        }
        return urls;
    }


    private static Map<String, String> getTitlesUrls(){
        try{
            ArrayList<String> urls = getURLS();
            Map<String, String> wikiTitles = new HashMap<>();
            for (int i = 0 ; i < urls.size(); ++i) {
                String heading = getWords.getTitle(urls.get(i));
                wikiTitles.put(heading, urls.get(i));
            }
        return wikiTitles;
    }
    catch(Exception e){
        System.out.println(e);
        return null;
    }
    }

    private static Map<String, String> getRec(){
        try{
            // use JSoup to parse through wikipedia pages, loop to go through all pages
            ArrayList<String> words = new ArrayList<>();
            for (int i = 0; i < urls.size(); ++i) {
                String text = getWords.jsoup(urls.get(i));
                words.add(text);
            }

            // get list of docs in order of relevance
            HT similarity = TFIDF.getTFIDFValue(words, indexChoice);
            urls.remove(indexChoice);
            ArrayList<String> title = getTitles();
            titlesUrlsMap.clear();
            int i = 0;
            for (String link : urls) {
                titlesUrlsMap.put(title.get(i), link);
                ++i;
            }

            titlesUrlsMap = getTitlesUrls();
            double first = similarity.getValue(1);
            double second = similarity.getValue(2);

            int realFirst = (int) first;
            int realSecond = (int) second;

            Map<String, String> recs = new HashMap<>();

            String one = getURLS().get(realFirst);
            String two = getURLS().get(realSecond);

            String rec1 = null;
            String rec2 = null;

            titlesUrlsMap = getTitlesUrls();
            
            for ( Map.Entry<String, String> entry : titlesUrlsMap.entrySet()) {
                if(entry.getValue().trim().equals(one.trim())){
                    rec1 = entry.getKey();
                }
                if(entry.getValue().trim().equals(two.trim())){
                    rec2 = entry.getKey();
                }
            }

            recs.put(rec1, one);
            recs.put(rec2, two);
            return recs;
        }
        catch(Exception e){
            System.out.println("Error : " + e);
            return null;
        }
            
        
    }

    private static String getChoiceURL(){
        String choiceurl = null;
        for (Map.Entry<String, String> value : titlesUrlsMap.entrySet()) {
            if(value.getKey().trim().equals(userChoice.trim())){
                choiceurl = value.getValue();
            }
        }
        return choiceurl;
    }

    public static int getChoiceIndex(){
        String choiceURL = getChoiceURL();
        int index = 0;
        int i = 0;
        for(String url : urls){
            if(url.trim().equals(choiceURL.trim())){
                index = i;
            }
            ++i;
        }
        return index;
    }
}
