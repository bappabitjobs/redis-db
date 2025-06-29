package com.redis.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ReadAndWriteFile {
	final String deltaValue = "D:\\Ongoing Projects\\Redis Cache\\DeltaValue.txt";
	private Map<String,String> mapFileContents = new HashMap<String,String>();
	public Map<String, String> getMapFileContents() {
		return mapFileContents;
	}

	public void setMapFileContents(Map<String, String> mapFileContents) {
		this.mapFileContents = mapFileContents;
	}



	private static Map<String,String> deltaValues = new HashMap<String,String>();
	BufferedReader br = null;
	public Map<String,String> getHashMapValue(){
		return mapFileContents;
	}

    public String getDeltaDump() {
    	return deltaValue;
    }

    public Map<String,String> readFromFile(String filepath){
        File file = new File(filepath);
        int garbageCountValue = 0 ; 
        try {
             Scanner scan = new Scanner(new FileReader(file));
             while(scan.hasNextLine()) {
                Scanner lineScanner = new Scanner(scan.nextLine());
                String line = lineScanner.nextLine();
                 if(line.contains(":")&&!line.endsWith(":")&&!line.contains("?")){
                   String[] parts = line.split(":",2);
                   mapFileContents.put(parts[0], parts[1]);
                }
                else {
                   garbageCountValue = garbageCountValue +  1; 
                   
                }
                lineScanner.close();
             }
            scan.close(); 
        }catch(FileNotFoundException e) {
           e.printStackTrace();
        }
        return mapFileContents;
     }

	/*
	 * public Map<String,String> readFromDelta(String filepath){ File file = new
	 * File(filepath); int garbageCountValue = 0; try { Scanner scan = new
	 * Scanner(new FileReader(file)); while(scan.hasNextLine()) { Scanner
	 * lineScanner = new Scanner(scan.nextLine()); String line =
	 * lineScanner.nextLine(); if(line.contains(":")) { String[] parts =
	 * line.split(":",2); deltaValues.put(parts[0], parts[1]);
	 * 
	 * } } scan.close();
	 * 
	 * } catch(FileNotFoundException e) { e.printStackTrace(); } return deltaValues;
	 * }
	 */
    public Map<String,String> readFromDelta(String filepath){
        File file = new File(filepath);
        int garbageCountValue = 0;
        try {
             Scanner scan = new Scanner(new FileReader(file));
             while(scan.hasNextLine()) {
                Scanner lineScanner = new Scanner(scan.nextLine());
                String line = lineScanner.nextLine();
                if(line.contains(":")) {
                   String[] parts = line.split(":",2);
                   deltaValues.put(parts[0], parts[1]);
                   
                }
             }
             scan.close();
             
        } catch(FileNotFoundException e) {
           e.printStackTrace();
        }
        return deltaValues;
     }

	
    public void removeJunkmapValues(String[]  garbageValueToBeRemoved,boolean removeIntegerKeys) {
        String onlyNumbers = "^[0-9.]*$";

       Map<String,String> removedJunkValues = mapFileContents.entrySet().stream().filter(key -> {
          for(String junkString : garbageValueToBeRemoved){
             if(key.toString().startsWith(junkString)){
                return true;
             }else{
                return false;
             }
          }
       }).collect(Collectors.toMap();

       /** Todo the removeIntegerKeys ******/

       setMapFileContents(removedJunkValues);

     }
	 
    

    public void writeToaFile(String outputFilePath,String[] garbageValueToBeRemoved,boolean b) {

        removeJunkmapValues(garbageValueToBeRemoved,b);

         BufferedWriter bf = null;
         try {
            File file = new File(outputFilePath);
            bf= new BufferedWriter(new FileWriter(file));
            for(Map.Entry<String,String> entry:mapFileContents.entrySet()) {
               bf.write(entry.getKey() + ":" +entry.getValue());
               bf.newLine();
            }
            bf.flush();
         }catch(IOException e) {
            e.printStackTrace();
         }
         finally {
                     try {
                          bf.close();
                     }catch(IOException e) {
                        e.printStackTrace();
                     }
         }
      }
    
}
