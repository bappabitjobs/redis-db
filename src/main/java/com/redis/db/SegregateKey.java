package com.redis.db;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SegregateKey {
ReadAndWriteFile readAndWriteFile = new ReadAndWriteFile();

String getDeltaValue = readAndWriteFile.getDeltaDump();
Map<String,String> keyValuePairList = readAndWriteFile.getHashMapValue();
Map<String,String> redisDelta = readAndWriteFile.readFromDelta(getDeltaValue);
Map<String,Integer> deltaValueAddition = new HashMap<String,Integer>();

public Set<String> getMatchingPatterns() {
	 Set<String> differentPatternKeys = keyValuePairList.keySet().stream().map(
            key -> {
                if(key.contentEquals("CP")){
                    return key.substring(0,7);
                }else {
                    return key.substring(0,2);
                }
            }
    ).collect(Collectors.toSet());


    return differentPatternKeys;
}

	void getTotalValue(Set<String> keyPatterns) {
        keyValuePairList.entrySet().stream()
	            .filter(entry -> patternMatch(entry.getKey(),keyPatterns))
	            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	    updateKeyByLargeDisplacement(keyValuePairList);
        deltaValueAddition.forEach((key,value)->System.out.println(" " + key + ":" +value));

}
	private boolean patternMatch(String matchString, Set<String> patterns){
	    for(String pattern : patterns){
	        if(matchString.startsWith(pattern)){
	            return true;
	        }
	    }
	    return false;
	}
	private void updateKeyByLargeDisplacement(Map<String, String> filteredCounters) {
		// TODO Auto-generated method stub
		for(Map.Entry backup : filteredCounters.entrySet()) {
			for(Map.Entry Delta : redisDelta.entrySet()) {
				if(backup.getKey().toString().startsWith(Delta.getKey().toString())) {
					deltaValueAddition.put(backup.getKey().toString(),Integer.parseInt(backup.getValue().toString())+Integer.parseInt(Delta.getValue().toString()));
				}
			}
		}
	
	}


}
