package readers;

import java.util.*;
import tablesAndSuch.*;

public class TFIDF{
   
    public static ArrayList<String> getTFIDFValue(String starting){
        List<String> token1 = tokenize(starting);

        HT tfOne = getTF(token1);

        Map<String, Integer> numDocsWord = getNumDocsWord(token1);

        Map<String, Double> idf = getIDF(1, numDocsWord);

        Map<String, Double> tfidf = getTFIDF(tfOne, idf, token1);

        ArrayList<String> uncommon = removeCommonTerms(tfidf);

        return uncommon;
    }

    private static List<String> tokenize(String doc){
        String[] words = doc.toLowerCase().split("//s+");
        return Arrays.asList(words);
    }

    private static HT getTF(List<String> doc){
        HT freq = new HT();
        int wordCount = doc.size();
        for (String word : doc){
            // puts word and adds 1 each time the word is used
            freq.add(word, (freq.getOrDefault(word, 0))/wordCount);
        }
        return freq;
    }

    private static Map<String, Integer> getNumDocsWord(List<String> docs){
        Map<String, Integer> numOfDocs = new HashMap<>();
        for(String word : docs)
        {
            for(Map.Entry<String, Integer> entry : numOfDocs.entrySet())
            {
                if (word.equals(entry.getKey()))
                {
                    numOfDocs.put(word, numOfDocs.getOrDefault(word, 0)+1);
                }
            }
        }
        return numOfDocs;
    }

    private static Map<String, Double> getIDF(int numDocs, Map<String, Integer> termFreqDocs){
        Map<String, Double> idf = new HashMap<>();
        for(Map.Entry<String, Integer> count: termFreqDocs.entrySet()){
            idf.put(count.getKey(), Math.log(numDocs/count.getValue()));
        }
        return idf;
    }

    private static Map<String, Double> getTFIDF(HT tf, Map<String, Double> idf, List<String> doc){
        Map<String, Double> tfidf = new HashMap<>();

        for(String word: doc){
            while(tf != null){
                if(tf.contains(word)){
                   for(Map.Entry<String, Double> inverse : idf.entrySet())
                   {
                    tfidf.put(word, inverse.getValue()*tf.getOrDefault(word, 0));
                   }
                   tf.remove(word);
                }
            }
        }
        return tfidf;
    }

    private static ArrayList<String> removeCommonTerms(Map<String, Double> tfidf){
        ArrayList<String> uncommon = new ArrayList<>();
        for(Map.Entry<String, Double> entry : tfidf.entrySet()){
            if(entry.getValue() > .1){
                uncommon.add(entry.getKey());
            }
        }
        return uncommon;
    }
}
