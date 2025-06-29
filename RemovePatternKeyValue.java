package com.redis.db;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RemovePatternKeyValue {
      ReadAndWriteFile readAndWriteFile = new ReadAndWriteFile();
      Map<String,String> mapFileContents = readAndWriteFile.getHashMapValue();
      
      public void removeJunkmapValues(String junkString,boolean removeIntegerKeys) {
    	  String onlyNumbers = "^[0-9.]*$";
    	  boolean removeIntegerKey = removeIntegerKeys;
    	  Set<Entry<String,String>> entrySet = mapFileContents.entrySet();
    	  Iterator<Entry<String,String>> itr = entrySet.iterator();
    	  
    	  while(itr.hasNext()) {
    		  Entry<String,String> entry = itr.next();
    		  String key = entry.getKey();
    		  if(removeIntegerKey) {
    			  if(key.startsWith(junkString)||key.matches(onlyNumbers)) {
    				  itr.remove();
    			  }
    		  }
    	  }
      }
      public void deleteKeyValuePattern(String[] garbageValueToBeRemoved,boolean b) {
    	  try {
    		  for(String val:garbageValueToBeRemoved) {
    			  removeJunkmapValues(val,b);
    		  }
    	  } catch(NullPointerException e) {
			  e.printStackTrace();
			  System.out.println("None of the Key is Deleted");
      }
     }
}