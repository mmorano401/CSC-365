package readers;

import java.util.*;
import tablesAndSuch.HT;

public class TFIDF{
   
    public static HT getTFIDFValue(ArrayList<String> docs, int indexChoice){
        ArrayList<List<String>> tokenizedList = new ArrayList<>();
        for (String words : docs) {
            tokenizedList.add(tokenize(words));
        }

        ArrayList<Map<String, Double>> tfAll = new ArrayList<>();
        for (List<String> terms : tokenizedList){
            tfAll.add(getTF(terms));
        }
        
        Map<String, Double> idfAll = getIDF(tokenizedList);

        ArrayList<Map<String, Double>> tfidf = getTFIDF(tfAll, idfAll);

        // user makes selection

        
        Map<String, Double> selected = tfidf.get(indexChoice);

        Map<Integer, Double> cosine = new HashMap<>();
        for (int i = 0; i < tfidf.size(); i++) {
            if(i != indexChoice){
                double similarity = cosineVector(selected, tfidf.get(i));
                cosine.put(i,similarity);
            }
        }

        HT similarityFull = sortTheSimilarity(cosine);

        return similarityFull;
        
    }

    private static HT sortTheSimilarity(Map<Integer, Double> cosine) {
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(cosine.entrySet());
        Collections.sort(entryList, Map.Entry.comparingByValue());
        LinkedHashMap<Integer, Double> sorted = new LinkedHashMap<>();
        for(Map.Entry<Integer, Double> entry : entryList){
            sorted.put(entry.getKey(), entry.getValue());
        }

        HT sortedSimilarities = new HT();
        int i = 0;
        for(Map.Entry<Integer, Double> entry : sorted.entrySet()){
            sortedSimilarities.add(entry.getKey(), i);
            ++i;
        }
        return sortedSimilarities;
    }

    private static ArrayList<Map<String, Double>> getTFIDF(ArrayList<Map<String, Double>> tfAll, Map<String, Double> idfAll) {
        for (Map<String,Double> map : tfAll) {
            for (Map.Entry<String,Double> entry : map.entrySet()) {
                if(idfAll.containsKey(entry.getKey())){
                    double tfidf = entry.getValue() * idfAll.get(entry.getKey());
                    map.put(entry.getKey(), tfidf);
                }
            }
        }
        return tfAll;
    }

    private static List<String> tokenize(String doc){
        doc = doc.replaceAll("[^\\sa-zA-Z0-9]", "");
        String[] words = doc.toLowerCase().split(" ");
        for (int i = 0; i < words.length; ++i) {
            words[i] = words[i].trim().toLowerCase();
        }
        return Arrays.asList(words);
    }

    private static Map<String, Double> getTF(List<String> doc){
        Map<String, Double> frequency = new HashMap<>();
        for (String word : doc){
            // puts word and adds 1 each time the word is used
            frequency.put(word, (frequency.getOrDefault(word, 0.0)+1)/doc.size());
        }
        return frequency;
    }

    private static Map<String, Double> getIDF(ArrayList<List<String>> tokenWords){
        Map<String, Double> idf = new HashMap<>();
        for (List<String> list : tokenWords) {
            for (String word : list) {
                idf.put(word, idf.getOrDefault(word, 0.0)+1);
            }
        }

        for(Map.Entry<String, Double> entry : idf.entrySet()){
            double value = Math.log(10 / entry.getValue());
            idf.put(entry.getKey(), value);
        }
        return idf;
    }


    private static double cosineVector(Map<String, Double> selected, Map<String, Double> comparing){
        double dot = 0.0;
        double A = 0.0;
        double B = 0.0;

        for(String word : selected.keySet()){
            if(comparing.containsKey(word)){
                dot += selected.get(word) * comparing.get(word);
            }
            A += Math.pow(selected.get(word), 2);
        }

        for (double tfidf : comparing.values()){
            B += Math.pow(tfidf, 2);
        }

        if(A == 0 || B == 0){
            return 0.0;
        }

        return dot / (Math.sqrt(A) * Math.sqrt(B));
    }


}
