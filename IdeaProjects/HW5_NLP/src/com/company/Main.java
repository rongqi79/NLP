package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;


public class Main {
    static String testCase = "A similar resolution passed in the Senate";
    static String[] wordArray = testCase.toUpperCase().split(" ");
    static int length = wordArray.length;
    static int[] wordSummatryCount = new int[length];
    static HashMap<String, Integer> myResult = new HashMap<String, Integer>();
    public static Set<String> wordSet = new HashSet<String>();

    public static void main(String[] args) {
        getSentetce("BROWN.pos.all");

        myResult = readFileByLine("BROWN.pos.all");
        for (int i = 0; i < length; i++) {
            wordSummatryCount[i] = wordCount(wordArray[i], "BROWN-clean.pos");
            //System.out.println(wordArray[i] + " count is :" + wordSummatryCount[i]);
        }

        int vocabSize = wordSetSize("BROWN-clean.pos");
        //System.out.println(vocabSize);

        //BEFORE add-one
       Double[] probNoAddOne= new Double[length-1];
        Double probSumNoAdd = 1.0;
        for (int i=0; i<length-1; i++){
            probNoAddOne[i]=(biGramCount(wordArray[i],wordArray[i+1],"BROWN-clean.pos"))/wordCount(wordArray[i],"BROWN-clean.pos");
            //System.out.println(probNoAddOne[i]);
            probSumNoAdd=probSumNoAdd*probNoAddOne[i];
        }
        System.out.println("before add-one, the probabilty is " + probSumNoAdd);


        //After add-one
        Double[] probAddOne = new Double[length - 1];
        Double probMulAddOne = 1.0;
        for (int i = 0; i < length - 1; i++) {
            //System.out.println(biGramCount(wordArray[i], wordArray[i + 1], "BROWN-clean.pos"));
            probAddOne[i] = (biGramCount(wordArray[i], wordArray[i + 1], "BROWN-clean.pos") + 1) / (wordCount(wordArray[i], "BROWN-clean.pos") + vocabSize);
            //System.out.println(probAddOne[i]);
            probMulAddOne = probMulAddOne * probAddOne[i];

        }
        System.out.println("after add-one, the probabilty is " + probMulAddOne);


        LineChart_AWT chart = new LineChart_AWT("Rank Vs Frequency", "Zipf's Law");

        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

    }
    // write your code here


    public static double biGramCount(String firstWord, String secWord, String fileName) {
        double count = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String readWord = scanner.next();
                if (readWord.equals(firstWord)) {
                    //System.out.println(readWord+ "  "+ firstWord);
                    String word2 = scanner.next();
                    if (word2.equals(secWord)) {
                        //System.out.println(readWord+"@@@"+word2+ "  "+ secWord);
                        count++;
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int wordSetSize(String fileName) {

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String readWord = scanner.next();
                if (!wordSet.contains(readWord)) {
                    wordSet.add(readWord);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return wordSet.size();

    }

    public static int wordCount(String word, String fileName) {
        int wordSum = 0;
        try {

            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String readWord = scanner.next();
                if (word.equals(readWord)) {
                    wordSum++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordSum;

    }

    public static HashMap<String, Integer> readFileByLine(String fileName) {

        //String treeResult = "";
        HashMap<String, Integer> myWordsCount = new HashMap<String, Integer>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String readline = scanner.nextLine();
                int lenthOfLine = readline.length();
                if (readline.contains(")")) {
                    int indexOfEnd = readline.indexOf(")");
                    int count = 0;
                    int countSpace = 0;
                    for (int i = indexOfEnd; i >= 0; i--) {
                        count++;
                        if (readline.charAt(i) == '(') {
                            break;
                        }
                    }
                    //System.out.println(readline.charAt(indexOfEnd - count + 1));
                    for (int i = indexOfEnd - count + 1; i < lenthOfLine; i++) {
                        countSpace++;
                        if (readline.charAt(i) == ' ') {
                            break;
                        }
                    }
                    //System.out.println(readline.charAt(indexOfEnd - count + countSpace));
                    String thisWord = readline.substring(indexOfEnd - count + 2, indexOfEnd - count + countSpace);
                    //System.out.println(thisWord);
                    String wordsRemoved = thisWord.replaceAll("[^a-zA-Z ]", "");
                    //System.out.println(wordsRemoved);
                    if (!wordsRemoved.isEmpty()) {
                        if (!myWordsCount.containsKey(thisWord)) {
                            myWordsCount.put(thisWord, 1);
                        } else {
                            myWordsCount.put(thisWord, myWordsCount.get(thisWord) + 1);
                        }
                    }

                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return myWordsCount;
    }

    public static void getSentetce(String fileName) {

        //String treeResult = "";
        StringBuilder str = new StringBuilder("");
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String readline = scanner.nextLine();
                if (readline.contains("TOP END_OF_TEXT_UNIT") || readline.contains("(, ,)") || readline.contains("`` ``") || readline.contains("-NONE-") || readline.contains("'' ''")
                        || readline.trim().isEmpty() || readline.matches("/^[a-zA-Z]*$/"))
                    str = str;
                else if (readline.contains("(. .))"))
                    str = str.append("\n");
                    //System.out.println(readline);
                    //System.out.println("reversed");
                else {
                    String reverse = LineProcess(readline);
                    //System.out.println("result");
                    String result = reverseWithStack(reverse);
                    //System.out.println(result);
                    //treeResult = treeResult + result + " ";
                    str = str.append(result).append(" ");
                }
            }

            //System.out.println(str);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fwriter = new FileWriter("BROWN-clean.pos");
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            bwriter.write(str.toString().toUpperCase());
            bwriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String LineProcess(String line) {
        int len = line.length();
        String lineResult = "";
        for (int i = len - 1; i >= 0; i--) {
            if (line.charAt(i) == ')') {
                lineResult = lineResult;
            } else if (line.charAt(i) == ' ')
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


}
