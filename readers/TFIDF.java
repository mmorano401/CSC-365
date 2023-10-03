package readers;

import java.util.*;
import structures.HT;

public class TFIDF{
   
    public static Map<String, Double> getSimilarity(String starting, String comparing){
        List<String> token1 = tokenize(starting);
        List<String> token2 = tokenize(comparing);

        Map<String, Double> tfOne = getTF(token1);

        Map<String, Integer> numDocsWord = getNumDocsWord(token1);
        numDocsWord = getNumDocsWord(token2);

        Map<String, Double> idf = getIDF(2, numDocsWord);

        Map<String, Double> similarity = getTFIDF(tfOne, idf);

        return similarity;
    }

    private static List<String> tokenize(String doc){
        String[] words = doc.toLowerCase().split("//s+");
        return Arrays.asList(words);
    }

    private static Map<String, Double> getTF(List<String> doc){
        Map<String, Double> frequency = new HashMap<>();
        HT freq = new HT();
        for (String word : doc){
            // puts word and adds 1 each time the word is used
            frequency.put(word, frequency.getOrDefault(word,0.0)+1);
            freq.add(word, );
        }
        int count = doc.size();
        for(Map.Entry<String, Double> info : frequency.entrySet())
        {
            double intFreq = info.getValue();
            info.setValue(intFreq/count);
        }
        return frequency;
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

    private static Map<String, Double> getTFIDF(Map<String, Double> tf, Map<String, Double> idf){
        Map<String, Double> tfidf = new HashMap<>();
        for(Map.Entry<String, Double> term : tf.entrySet()){
            for(Map.Entry<String, Double> inverse : idf.entrySet()){
                if(term.getKey().equals(inverse.getKey())){
                    tfidf.put(term.getKey(), term.getValue()*inverse.getValue());
                }
            }
        }
        return tfidf;
    }
}
