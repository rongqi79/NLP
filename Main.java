package com.company;

import java.lang.StringBuilder;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // job 1. get BROWN-clean.pos1.txt
        readFileByLine("BROWN.pos.all");

        //job 2 : print the top 20 tag
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>();
        sorted_map = wordFrequency("BROWN-clean.pos", 20);
        String keyFirst = sorted_map.firstEntry().getKey().toUpperCase();
        int valueFirst = sorted_map.firstEntry().getValue();
        System.out.println("the most frequent TAG is " + keyFirst);
        System.out.println("it will appear " + valueFirst + " times!");


        //job 3 and 4 : construct hash of hash and calculate perfomance
        HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
        try {
            result = GetHash("BROWN-clean.pos");

            Performance("BROWN-clean.pos", keyFirst);
            }
        catch (FileNotFoundException e) {
            e.printStackTrace();
                                        }

        }

    public static void readFileByLine(String fileName) {

        //String treeResult = "";
        StringBuilder str = new StringBuilder("");
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String readline = scanner.nextLine();
                if (readline.contains("TOP END_OF_TEXT_UNIT") || readline.contains("(, ,)") || readline.contains("`` ``") || readline.contains("-NONE-") || readline.contains("'' ''")
                        || readline.trim().isEmpty()||readline.matches ("/^[a-zA-Z]*$/"))
                    str= str;
                else if (readline.contains("(. .))"))
                    str= str.append( "\n");
                    //System.out.println(readline);
                    //System.out.println("reversed");
                else {
                    String reverse = LineProcess(readline);
                    //System.out.println("result");
                    String result = reverseWithStack(reverse);
                    //System.out.println(result);
                    //treeResult = treeResult + result + " ";
                    str=str.append(result).append(" ");
                }
            }

            System.out.println(str);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            FileWriter fwriter = new FileWriter("BROWN-clean.pos");
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(str.toString());
            bwriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String LineProcess(String line) {
        int len = line.length();
        String lineResult = "";
        for (int i = len - 1; i >= 0; i--) {
            if (line.charAt(i) == ')') {
                lineResult = lineResult;
            } else if (line.charAt(i) == '(')
                return lineResult;
            else
                lineResult += line.charAt(i);
        }
        return lineResult;
    }


    public static String reverseWithStack(String beforeReverse) {

        String word = "reverse please";
        Stack<Character> chStack = new Stack<Character>();
        for (int i = 0; i < beforeReverse.length(); i++) {
            chStack.push(beforeReverse.charAt(i));
        }

        String out = "";
        while (chStack.size() != 0) {
            out += chStack.pop();

        }
        return out;
    }

    public static TreeMap<String, Integer> wordFrequency(String FileName, int topNumber) throws FileNotFoundException {
        //get file
        Scanner input = new Scanner(new File(FileName));
        // Hashmap for word and wordCount pair
        HashMap<String, Integer> myWordsCount = new HashMap<String, Integer>();

        // while loop to add elements to HashMap
        while (input.hasNext()) {
            String next = input.next().toLowerCase();
            //nextWord is word without punctuation
            //String nextWord=next.replaceAll("[^a-zA-Z0-9]", "");
            //nextPunc only contains punctuation
            //String nextPunc =next.replaceAll("[a-zA-Z0-9]", "");
            //System.out.println(next);
            //.out.println(nextWord);
            //System.out.println(nextPunc);
            if (!myWordsCount.containsKey(next)) {
                myWordsCount.put(next, 1);
            } else {
                myWordsCount.put(next, myWordsCount.get(next) + 1);
            }
        }
        myWordsCount.remove("");//remove empty value in HashMap

        //sort HashMap based on value
        ValueComparator bvc = new ValueComparator(myWordsCount);
        TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
        //System.out.println("unsorted map: " + myWordsCount);
        sorted_map.putAll(myWordsCount);
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

    public static HashMap GetHash(String FileName) throws FileNotFoundException {

        Scanner input = new Scanner(new File(FileName));
        HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();

        while (input.hasNext()) {

            String pos = input.next();
            //System.out.print("  pos  "+ pos);
            String word = input.next();
            //System.out.print("  word  "+ word);
            if (!result.containsKey(word)) {
                //System.out.print(" no word, so put");

                HashMap<String, Integer> tempHash = new HashMap<String, Integer>();
                tempHash.put(pos, 1);
                result.put(word, tempHash);
                //System.out.println(result.get(word).get(pos));
            } else {
                HashMap<String, Integer> tempHash = new HashMap<String, Integer>();
                tempHash = result.get(word);
                //System.out.println(tempHash.values().toString());
                //System.out.println(tempHash.keySet().toString());
                //System.out.println()
                //System.out.println(hashLevel2.values().toString());
                if (!result.get(word).containsKey(pos)) {
                    tempHash.put(pos, 1);
                    // System.out.print("  found word  " + word + " fre is  " + tempHash.get(pos));
                    //System.out.println();

                } else {
                    //System.out.print("  found word and pos  " + word + " fre is  " + result.get(word).get(pos));
                    // System.out.println();
                    tempHash.put(pos, tempHash.get(pos) + 1);
                    // System.out.print("  found word and pos;;;  " + word + " fre is  " + result.get(word).get(pos));
                    //System.out.print("  found word and pos  " + word + " fre is  " + result.get(word).get(pos));
                }
                result.put(word, tempHash);
            }

        }
        //System.out.println(result.keySet().toString());

        //System.out.println(result.get("The").keySet().toString());
        // System.out.println(result.get("The").values().toString());
        return result;
    }

    public static double Performance(String FileName, String tag)throws FileNotFoundException {
        Scanner input = new Scanner(new File(FileName));
        double count=0.0;
        double accurate=0.0;
        while (input.hasNext()) {
            String pos = input.next();
            //System.out.print("  pos  "+ pos);
            String word = input.next();
            count++;
            if (pos.equals(tag))
                accurate++;
        }
        double result=(double)(accurate/count);
        System.out.println("performance is "+ result );
        return result;
    }

}