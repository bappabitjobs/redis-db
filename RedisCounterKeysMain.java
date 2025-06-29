package com.redis.db;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedisCounterKeysMain {
	/*This is the main txt file from where all the keys are read , it is the initial file to read*/
	final static String readFilePath = "D:\\Ongoing Projects\\Redis Cache\\writeTwo.txt";
	
	/* This is the file in which filtered key value pair are kept after removing garbage key value pair*/
	final static String mainFileRemovedGarbage="D:\\Ongoing projects\\Redis Cache\\RemovedGarbage.txt";
	/* This is the array of garbage key which need to be get removed*/
	static String[] garbageKeyValuesToBeRemoved = {"WAMP","AMD"};
	/*This is the array of unused key pattern */
	static String[] keyPattern= {"CPIN_TXN_ID","KRN"};
	/* This boolean is used to remove keys of type integer , currently I don't know whether to remove these type of keys or not */
	/* Value is true means we not require these keys for processing*/
	static boolean removeIntegerKey = true;
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
             ReadAndWriteFile readAndWriteFile = new ReadAndWriteFile();
             readAndWriteFile.readFromFile(readFilePath);
             readAndWriteFile.writeToaFile(mainFileRemovedGarbage,garbageKeyValuesToBeRemoved,removeIntegerKey);
             SegregateKey segregateKey = new SegregateKey();
             Set<String> keyValueSet2 = new HashSet<>(Arrays.asList(getMatchingPattern(segregateKey.getMatchingPatterns())));
             segregateKey.getTotalValue(keyValueSet2);
	}
private static String[] getMatchingPattern(Set matchingPatterns) {
	String text = matchingPatterns.toString().replaceAll("(^\\[|\\]$)", "");
	String[] elements = text.split("");
	return elements;
  }
}
