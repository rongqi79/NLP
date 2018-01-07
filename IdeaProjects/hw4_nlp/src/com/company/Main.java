package com.company;

import java.lang.StringBuilder;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // job 1. get BROWN-clean.pos1.txt

        HashMap<String, HashMap<String, Integer>> myResult= new  HashMap<String, HashMap<String, Integer>>();
        myResult=readFileByLine("example");

        //sort

        //sort HashMap based on value
        com.company.ValueComparator bvc = new ValueComparator(myResult);
        TreeMap<String, HashMap<String, Integer>> sorted_map = new TreeMap<String, HashMap<String, Integer>>(bvc);
        //System.out.println("unsorted map: " + myWordsCount);
        sorted_map.putAll(myResult);
        //System.out.println("results: " + sorted_map);

        //print the first 10 elements of sorted map
        int count = 0;
        System.out.println("The top 20 words(word : frequency) are listed below:");
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.printf("%s : %d\n", key, value);
            count++;
            if (count > topNumber - 1)
                break;
        }
        return sorted_map;
    }




    public static  HashMap<String, HashMap<String, Integer>>  readFileByLine(String fileName) {

        //String treeResult = "";
        //StringBuilder str = new StringBuilder("");
        //
        // HashMap<String, StringBuilder> myWordsCount = new HashMap<String, StringBuilder>();

        HashMap<String, HashMap<String, Integer>> myWordsCount = new HashMap<String, HashMap<String, Integer>>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int bracketNumber=0;
            String[] rules=new String[1000];
            Arrays.fill(rules, "");
            while (scanner.hasNext()) {
                StringBuilder allRule= new StringBuilder();


                String readWord = scanner.next();
                if (!readWord.equals("(TOP") && !readWord.equals("END_OF_TEXT_UNIT)") ) {
                    if (readWord.equals("(S"))  {
                        rules[bracketNumber]=rules[bracketNumber]+" " +"S";
                        System.out.println(rules[bracketNumber]);
                    }
                    else if (readWord.charAt(0)=='('){
                        bracketNumber++;
                        rules[bracketNumber]=rules[bracketNumber]+ " " + readWord.substring(1);
                        //System.out.println(bracketNumber);
                        System.out.println(rules[bracketNumber]);
                    }
                    else if (readWord.charAt(readWord.length()-1)==')' ){
                        int count = readWord.length() - readWord.replaceAll("\\)","").length();
                        System.out.println(bracketNumber);
                        bracketNumber--;
                        while (count >1 ) {
                            bracketNumber=bracketNumber--;
                            count--;
                            int index= rules[bracketNumber].split(" ").length;
                            String key= rules[bracketNumber].split(" ")[index-1] ;
                            String value= rules[bracketNumber+1];
                            System.out.println("key"+key);
                            System.out.println("value"+value);
                            //myWordsCount.put(key,value);
                            if (!myWordsCount.containsKey(key)){
                                HashMap<String, Integer> temp=new HashMap<String, Integer>();
                                temp.put(value,1);
                                myWordsCount.put(key,temp);
                            }
                            else {
                                HashMap<String, Integer> temp = new HashMap<String, Integer>();
                                temp = myWordsCount.get(key);

                                if (!myWordsCount.get(key).containsKey(value)) {
                                    temp.put(value, 1);
                                    // System.out.print("  found word  " + word + " fre is  " + tempHash.get(pos));
                                    //System.out.println();

                                } else {
                                    //System.out.print("  found word and pos  " + word + " fre is  " + result.get(word).get(pos));
                                    // System.out.println();
                                    temp.put(value, temp.get(value) + 1);
                                    // System.out.print("  found word and pos;;;  " + word + " fre is  " + result.get(word).get(pos));
                                    //System.out.print("  found word and pos  " + word + " fre is  " + result.get(word).get(pos));
                                }
                                myWordsCount.put(key, temp);
                            }
                        }
                    }

                }


                //System.out.println(readWord);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return myWordsCount;

    }

}