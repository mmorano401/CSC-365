package readers;

import java.util.*;

public class TFIDF{
    public static double similarity(String one, String two){
        // break document strings into tokens
        List<String> oneTokens = tokenize(one);
        List<String> twoTokens = tokenize(two);

        // calculate TF/IDF from the two pages comparing
        double similarity = getTFIDF(oneTokens, twoTokens);

        return similarity;
    }

    private static List<String> tokenize(String doc){
        String[] words = doc.toLowerCase().split("//s+");
        return Arrays.asList(words);
    }

    private static double getTFIDF(List<String> one, List<String> two){
        // use Hash Map to put frequency of term and the term itself together in order to compare
        Map<String, Integer> freq1 = termFreq(one);
        Map<String, Integer> freq2 = termFreq(two);

        // get denomenator of TF/IDF equation
        Map<String, Double> inverseFreq = getIDF(Arrays.asList(one, two));

        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;

        for (String term : freq1.keySet()) {
            double tfidf1 = freq1.get(term) * inverseFreq.getOrDefault(term, 0.0);
            double tfidf2 = freq2.getOrDefault(term, 0) * inverseFreq.getOrDefault(term, 0.0);

            dotProduct += tfidf1 * tfidf2;
            magnitude1 += tfidf1 * tfidf1;
            magnitude2 += tfidf2 * tfidf2;
        }

        // Calculate the cosine similarity
        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0; // Handle the case of empty documents or zero-length vectors
        } else {
            return dotProduct / (Math.sqrt(magnitude1) * Math.sqrt(magnitude2));
        }

    }

    private static Map<String, Integer> termFreq(List<String> text){
        Map<String, Integer> freq = new HashMap<>();
        for (String word: text){
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return freq;
    }

    private static Map<String, Double> getIDF(List<List<String>> docs){
        Map<String, Integer> docFreq = new HashMap<>();


        for (List<String> doc : docs){
            Set<String> uniqueness = new HashSet<>(doc);
            for(String word : uniqueness){
                docFreq.put(word, docFreq.getOrDefault(word, 0)+1);
            }
        }

        Map<String, Double> idf = new HashMap<>();
        int number = docs.size();
        for (String word : docFreq.keySet()){
            int docFreqNum = docFreq.get(word);
            idf.put(word, Math.log((double) number / (1 + docFreqNum)));
        }
        return idf;
    }
}
