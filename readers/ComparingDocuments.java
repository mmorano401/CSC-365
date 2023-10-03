package readers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComparingDocuments {
    
    public static Map<Integer, Integer> similarity(ArrayList<ArrayList<String>> allDocs){
        ArrayList<String> selected = allDocs.get(0);
        ArrayList<Map<String, Integer>> matching = new ArrayList<>();
        for(int i = 0; i < allDocs.size(); ++i){
            if(i != allDocs.indexOf(selected)){
                matching.add(matchingTerms(selected, allDocs.get(i)));
            }
        }

        Map<Integer, Integer> similarityOfDoc = getSimilarity(matching);

        similarityOfDoc = sortTheMap(similarityOfDoc);

        return similarityOfDoc;
    }

    private static Map<Integer, Integer> sortTheMap(Map<Integer, Integer> similarityOfDoc) {
        for (int i = 0; i < similarityOfDoc.size()-1; i++) {
            for (int j = 0; j < similarityOfDoc.size()-1-i; j++) {
                if(similarityOfDoc.get(j).intValue() <  similarityOfDoc.get(j+1).intValue()){
                    int tempKey = similarityOfDoc.get(j);
                    int tempValue = similarityOfDoc.get(j).intValue();
                    similarityOfDoc.put(similarityOfDoc.get(j+1), similarityOfDoc.get(j+1).intValue());
                    similarityOfDoc.put(tempKey, tempValue);
                }
            }
        }
        return similarityOfDoc;
    }

    private static Map<Integer, Integer> getSimilarity(ArrayList<Map<String, Integer>> matching) {
        Map<Integer, Integer> maxSame = new HashMap<>();
        int i = 0;
        for(Map<String, Integer> valueMap : matching){
            for(Map.Entry<String, Integer> entry : valueMap.entrySet()){
                if(maxSame.get(i) < entry.getValue()){
                    maxSame.put(i, entry.getValue());
                }
            }
            ++i;
        }
        return maxSame;
    }

    private static Map<String, Integer> matchingTerms(ArrayList<String> selected, ArrayList<String> comparing){
        Map<String, Integer> matching = new HashMap<>();
        for(String word1 : selected){
            for(String word2 : comparing){
                if(word1.equals(word2)){
                    matching.put(word1, matching.getOrDefault(word1, 0)+1);
                }
            }
        }
        return matching;
    }
}
