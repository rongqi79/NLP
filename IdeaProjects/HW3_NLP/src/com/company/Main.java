package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // get BROWN-clean.pos1.txt
        readFileByLine("BROWN.pos.all", "BROWN-clean.pos");
        readFileByLine("SnapshotBROWN.pos.all.txt", "BROWN-SnapshotBROWN.pos");


        //construct hash of hash and calculate perfomance
        HashMap<String, HashMap<String, Integer>> resultHashofHash = new HashMap<String, HashMap<String, Integer>>();

        //this hash is the word with the most frequent tag of the word
        HashMap<String, String> Top1Hash = new HashMap<String, String>();
        try {
            resultHashofHash = GetHash("BROWN-clean.pos");
            Top1Hash = GetFinalHash(resultHashofHash);

            //Use the baseline lexicalized statistical tagger to tag
            //all the words in the SnapshotBROWN.pos.all.txt file. Evaluate and report the
            //performance of this baseline tagger on the Snapshot file.
            Performance(Top1Hash, "BROWN-SnapshotBROWN.pos");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //test new sentence
        String[] sentences =
                {"PRP I VBD learned IN for NN example IN that PRP he VBD made DT a NN practice IN of VBG yapping IN at NNS dogs PRP he VBD encountered CC and IN in NN winter IN of VBG sprinkling NN salt IN on DT the JJ icy NN pavement TO to VB scarify PRP$ their NNS feet ",
                        "PRP$ His NN energy VBD was JJ prodigious : ; : ; RB sometimes PRP he MD would VB be IN up IN before NN dawn VBN clad IN as DT a NN garbage NN collector CC and VBG hurling NNS pails IN into NNS areaways TO to VB exasperate PRP us CC and RB thereafter MD would VB hurry TO to DT the NNP Bronx NNP Zoo TO to VB grimace IN at DT the NNS lions CC and NN press NN cigar NNS butts IN against PRP$ their NNS paws ",
                        "RB Evenings PRP he VBD was RB frequently TO to VB be VBN seen IN at NNS restaurants IN like NNP Enrico CC & NNP Paglieri POS 's CC or NNP Peter POS 's NN Backyard RB drunkenly VBG donning NNS ladies POS ' NNS hats CC and VBG singing NNP O NNP Sole NNP Mio ",
                        "IN In JJ short CC and TO to VB borrow DT an JJ arboreal NN phrase VB slash NN timber ",
                        "RB Well DT the JJ odious JJ little NN toad VBD went RB along VBG chivying NNS animals CC and NNS humans WP who MD could RB n't VB retaliate CC and IN in JJ due NN course IN as VBD was JJ inevitable VBD overreached PRP himself ",
                        "CD One NN morning PRP we VBD discovered RB not RB only IN that DT the NNS pennies VBD were VBG missing IN from DT the NN idol CC but IN that DT a NN cigarette VBD had VBN been VBN stubbed RB out IN in PRP$ its NN lap ",
                        "RB Now PRP he VBZ 's VBD bought PRP it VBD said PRP$ my NN wife RB contentedly ",
                        "DT No NN divinity MD will VB hold RB still IN for DT that ",
                        "PRP He VBZ 's RB really VBG asking IN for PRP it ",
                        "CC And WRB how JJ right PRP she VBD was ",
                        "DT The JJ next NN time PRP we VBD saw PRP him PRP he VBD was DT a JJ changed NN person : ; : ; PRP he VBD had VBN aged CD thirty NNS years CC and PRP$ his NN face DT the NN color IN of NN tallow VBD was VBN crisscrossed IN with NNS wrinkles IN as IN though PRP it VBD had VBN been VBN wrapped IN in NN chicken NN wire ",
                        "DT Some NN sort IN of NN nemesis VBD was VBG haunting PRP$ his NNS footsteps PRP he VBD told PRP us IN in DT a VBG quavering NN voice : -- CC either DT an NN ape NN specter CC or NNP Abe NNP Spector DT a NN process-server PRP we MD could RB n't VB determine WDT which ",
                        "PRP$ His NNS eyes VBD had DT the JJ same NN dreadful JJ rigid NN stare IN as NNP Dr. NNP Grimesby NNP Roylott POS 's WRB when PRP he VBD was VBN found IN before PRP$ his JJ open NN safe VBG wearing DT the JJ speckled NN band ",
                        "DT The NN grocery DT the NN youth VBD worked IN for RB soon VBN tired IN of PRP$ his JJ depressing NN effect IN on NNS customers RBS most IN of WP whom VBD were RB sufficiently JJ neurotic IN without DT the NN threat IN of NNS incubi CC and VB let PRP him VB go ",
                        "DT The JJ beautiful DT the JJ satisfying NN part IN of PRP$ his NN disintegration RB however VBD was DT the JJ masterly NN way DT the NNP Buddha VBD polished PRP him RP off ",
                        "VBN Reduced TO to NN beggary PRP he IN at RB last VBD got DT a NN job IN as NN office NN boy TO to DT a NN television NN producer ",
                        "PRP$ His NN hubris NN deficiency IN of NN taste CC and NN sadism VBD carried PRP him RB straightaway TO to DT the NN top ",
                        "PRP He VBD evolved NNS programs WDT that VBD plumbed JJ new NNS depths IN of NN bathos CC and VBD besmirched JJ whole NNS networks CC and RB quickly VBD superseded PRP$ his NN boss ",
                        "RB Not RB long RB ago PRP I VBD rode IN down IN with PRP him IN in DT an NN elevator IN in NNP Radio NNP City : ; : ; PRP he VBD was VBG talking TO to PRP himself CD thirteen TO to DT the NN dozen CC and VBG smoking CD two NNS cigars IN at RB once RB clearly DT a NN man FW in FW extremis ",
                        "VB See DT that NN guy . ? . ? DT The NN operator VBD asked RB pityingly ",
                        "PRP I MD would RB n't VB be IN in PRP$ his NNS shoes IN for PDT all DT the NN rice IN in NNP China ",
                        "EX There VBZ 's DT some NN kind IN of DT a NN nemesis VBG haunting PRP$ his NNS footsteps ",
                        "WRB However PRP one VBZ looks IN at PRP it RB therefore PRP I MD 'd VB say DT that PRP$ your NN horoscope IN for DT this NN autumn VBZ is DT the NN reverse IN of JJ rosy ",
                        "DT The NN inventory PRP you VBD acquired IN from PRP me VBZ is RB n't VBG going TO to VB be JJ easy TO to VB move : ; : ; PRP you MD ca RB n't RB very RB well VB sidle RB up TO to NNS people IN on DT the NN street CC and VB ask IN if PRP they VB want TO to VB buy DT a JJ hot NNP Bodhisattva ",
                        "RB Additionally IN since PRP you VBP 're VBG going TO to VB be FW hors FW de FW combat RB pretty RB soon IN with NN sprue NNS yaws NNP Delhi NN boil DT the NNP Granville NN wilt NN liver NN fluke NN bilharziasis CC and DT a NN host IN of JJ other NNS complications IN of DT the NN hex PRP you VBP 've VBN aroused PRP you MD must RB n't VB expect TO to VB be VBN lionized RB socially ",
                        "PRP$ My NN advice IN if PRP you VBP live RB long RB enough TO to VB continue PRP$ your NN vocation VBZ is IN that DT the JJ next NN time PRP you VBP 're VBN attracted IN by DT the JJ exotic VB pass PRP it RP up : -- PRP it VBZ 's NN nothing CC but DT a NN headache ",
                        "IN As PRP you MD can VB count IN on PRP me TO to VB do DT the JJ same ",
                        "RB Compassionately PRP yours NNP S.J. NNP Perelman NN revulsion IN in DT the NN desert DT the NNS doors IN of DT the NN D NN train VBD slid VBN shut CC and IN as PRP I VBD dropped IN into DT a NN seat CC and VBG exhaling VBD looked RB up IN across DT the NN aisle DT the JJ whole NN aviary IN in PRP$ my NN head NN burst IN into NN song ",
                        "PRP She VBD was DT a VBG living NN doll CC and DT no NN mistake : -- DT the JJ blue-black NN bang DT the JJ wide NNS cheekbones JJ olive-flushed WDT that VBD betrayed DT the NNP Cherokee NN strain IN in PRP$ her NNP Midwestern NN lineage CC and DT the NN mouth WP$ whose JJ only NN fault IN in DT the NN novelist POS 's VBG carping NN phrase VBD was IN that DT the JJR lower NN lip VBD was DT a NN trifle RB too JJ voluptuous ",
                        "IN From WP what PRP I VBD was JJ able TO to VB gauge IN in DT a NN swift JJ greedy NN glance DT the NN figure IN inside DT the JJ coral-colored NN boucle NN dress VBD was JJ stupefying ",
                };
        for (int i = 0; i < sentences.length; i++) {
            newSentencePerformance(Top1Hash, sentences[i]);
        }
    }

    public static void readFileByLine(String OriginalFileName,String TargetFileName ) {

        //String treeResult = "";
        StringBuilder str = new StringBuilder("");
        try {
            File file = new File(OriginalFileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String readline = scanner.nextLine();
                if (readline.contains("TOP END_OF_TEXT_UNIT") || readline.contains("(, ,)") || readline.contains("`` ``") || readline.contains("-NONE-") || readline.contains("'' ''")
                        || readline.trim().isEmpty()||readline.matches("/^[a-zA-Z]*$/"))
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
            FileWriter fwriter = new FileWriter(TargetFileName);
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

    /* public static TreeMap<String, Integer> wordFrequency(String FileName, int topNumber) throws FileNotFoundException {
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
    } */

    public static HashMap<String, HashMap<String, Integer>> GetHash(String FileName) throws FileNotFoundException {

        Scanner input = new Scanner(new File(FileName));
        HashMap<String, HashMap<String, Integer>> result = new HashMap<String, HashMap<String, Integer>>();
        String[] tag = {"CC", "CD", "DT", "EX", "FW", "IN", "JJ", "JJR", "JJS", "LS", "MD", "NN", "NNS", "NNP", "NNPS", "PDT", "POS",
                "PRP", "PRP$", "RB", "RBR", "RBS", "RP", "SYM", "TO", "UH", "VB", "VBD", "VBG", "VBN", "VBP", "VBZ", "WDT", "WP", "WP$",
                "WRB", "$", "#", "\"", "\"", "(", ")", ",", ".", ":"};
        while (input.hasNext()) {

            String pos = input.next().toUpperCase();
            //System.out.print("  pos  "+ pos);
            String word = input.next().toUpperCase();
            //System.out.print("  word  "+ word);
            if (!result.containsKey(word)) {
                //System.out.print(" no word, so put");

                HashMap<String, Integer> tempHash = new HashMap<String, Integer>();
                if (Arrays.asList(tag).contains(pos)) {
                    tempHash.put(pos, 1);
                    result.put(word, tempHash);
                }
                //System.out.println(result.get(word).get(pos));
            } else {
                HashMap<String, Integer> tempHash = new HashMap<String, Integer>();
                tempHash = result.get(word);
                //System.out.println(tempHash.values().toString());
                //System.out.println(tempHash.keySet().toString());
                //System.out.println()
                //System.out.println(hashLevel2.values().toString());
                if (!result.get(word).containsKey(pos)) {
                    if (Arrays.asList(tag).contains(pos))
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

    public static HashMap<String, String> GetFinalHash(HashMap<String, HashMap<String, Integer>> HashofHash) {
        HashMap<String, String> result = new HashMap<String, String>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (Map.Entry<String, HashMap<String, Integer>> entry : HashofHash.entrySet()) {
            //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            int maxValueInMap = (Collections.max(entry.getValue().values()));
            for (Map.Entry<String, Integer> entry2 : entry.getValue().entrySet()) {  // Itrate through hashmap
                if (entry2.getValue() == maxValueInMap) {
                    String No1Frequence = entry2.getKey();
                    //System.out.println(entry2.getKey());
                    //System.out.println(" when word is " + entry.getKey() + " the most frequenct tag is " + entry2.getKey());
                    result.put(entry.getKey(), entry2.getKey());
                    break;// Print the key with max value
                }
            }

        }
        return result;
    }


    public static double Performance(HashMap<String, String> top1Hash, String FileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(FileName));
        double count = 0.0;
        double accurate = 0.0;
        double result = 0.0;
        while (input.hasNext())
        {
            String pos = input.next().toUpperCase();
            //System.out.print("  pos  "+ pos);
            String word = input.next().toUpperCase();
            String tag = top1Hash.get(word);
            count++;
            if (pos.equals(tag))
                accurate++;

        }
        result = accurate / count;
        System.out.println("HW3 use the baseline lexicalized statistical tagger to tag \n" +
                "all the words in the SnapshotBROWN.pos.all.txt file \nPerformance is " + result);
        return result;
    }

    public static double newSentencePerformance(HashMap<String, String> top1Hash, String sentence)  {
       String[] sentenceArray=sentence.split(" ");
        double count=0.0;
        double accurate=0.0;
        double result =0.0;
        for (int i=0; i<sentenceArray.length; i=i+2)
        {
            String posFromSentetce=sentenceArray[i].toUpperCase();
            String wordFromSentence=sentenceArray[i+1].toUpperCase();
            //System.out.println("posFromSentetce is " +posFromSentetce );
            //System.out.println(" wordFromSentence is " +wordFromSentence);
            String posFromHash=top1Hash.get(wordFromSentence);
            if(posFromHash!=null){

            }
            else {
                posFromHash= unknownWord(wordFromSentence);
                //System.out.println(wordFromSentence+ " is a new word");
                //System.out.println("the assigned tag based on rule is "+posFromHash  );
                //System.out.println("the actual tag from sentence is "+posFromSentetce  );
            }
            count++;
            //System.out.println("count +1 "+ count);
            if (posFromHash.toUpperCase().equals(posFromSentetce)) {
                accurate++;
                //System.out.println("accuracy +1 "+ accurate);
            }
        }
        result = accurate / count;
        System.out.println("new sentence performance is " + result);
        return result;
                }


    public static String unknownWord(String word) {
        word=word.toUpperCase();
        String result="";
        String[] adj = {"AL", "ARY", "FUL", "IC", "ICAL", "ISH", "LESS", "LIKE", "LY", "OUS", "ABLE",
                "IBLE", "ANT", "ENT", "IVE", "ING", "ED", "EN"};
        String[] adv = {"LY", "ILY","ALLY","WARD","WARDS","WISE"};
        String[] verb ={"ATE","EN","IFY","ISE"};

        for (int i = 0; i < adj.length; i++) {
            if (word.endsWith(adj[i]))
            {
                result="JJ";
                break;
            }
        }

        for (int i = 0; i < adv.length; i++) {
            if (word.endsWith(adv[i]))
            {
                result="RB";
                break;
            }
        }

        for (int i = 0; i < verb.length; i++) {
            if (word.endsWith(verb[i]))
            {
                result="VB";
                break;
            }
        }

        if (!result.equals("JJ")&&!result.equals("RB")&& !result.equals("VB") )
               result="NN";

        return result;
    }
}