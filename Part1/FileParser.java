/**
 * -Prompt user for file to parse
 *         -Check it's all good ( static method, note the use of static )
 *         -Create output files if it's kosher ( stat method )
 *         -Parse it up ( stat method )
 *         -Output to proper file
 *         -Close
 * @author ThomasHegarty
 * ID: 40155703
 * COMP249
 * Assignment #4
 * @version 1.1
 * DUE: December 6th, 2021
 * Assumes perfect user
 */
// -----------------------------------------------------
// Assignment 4
// Question: 1 // Written by: Thomas Hegarty ID: 40155703
// -----------------------------------------------------
package Part1;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.*;
public class FileParser {


    /*
        -Prompt user for file to parse
        -Check it's all good ( static method, note the use of static )
        -Create output files if it's kosher ( stat method )
        -Parse it up ( stat method )
        -Close
         */

    //I wrote this before we were told that .sort() was allowed....

    /**
     * Sorts a passed arraylist
     * @param preProc arraylist
     * @return arraylist
     */
    public static ArrayList<String> alphaSort(ArrayList<String> preProc){
        ArrayList<String> processed = new ArrayList<>();

        for(int i=0; i<preProc.size(); i++){
            for(int j=0; j<preProc.size(); j++){
                if(preProc.get(i).charAt(0) < preProc.get(j).charAt(0)){
                    String temp = preProc.get(i);
                    preProc.set(i, preProc.get(j));
                    preProc.set(j, temp);
                }
            }
        }


        return processed;
    }

    /**
     * Removes duplicate entries from passed arraylist
     * @param before arraylist
     * @return arraylist
     */
    public static ArrayList<String> duplicateRemover(ArrayList<String> before){
        ArrayList<String> removed = new ArrayList<>();
        for(String thing: before)
            if(!removed.contains(thing))
                removed.add(thing);


        return removed;
    }

    /**
     * Parses the input file of all o, n, and a containing words. Uses a character array for each word to achieve this
     * @param preProc arraylist
     * @return arraylist
     */
    public static ArrayList<String> onaParser(ArrayList<String> preProc){
        ArrayList<String> processed = new ArrayList<>();
        //Builds arraylist of individual words and characters from each line
        for(String thing: preProc){
            char[] a = thing.toCharArray();
            boolean contains = false;
            for(int i=0; i<a.length; i++){
                if(a[i] == 'o' | a[i] == 'n' | a[i] == 'a'){
                    contains = true;
                }
            }
            if(!contains)
                processed.add(new String(a));
        }
        /*
        System.out.println(obsessive_non_ona.size());
        obsessive_non_ona.trimToSize();
        System.out.println(obsessive_non_ona.size());
        System.out.println(obsessive_non_ona);
        */

        return duplicateRemover(processed);
    }
    //For the popular varieties...

    /**
     * Parses input array such that words that occure more than 3 times will be put into an arraylist, uses two arraylist and a counter to achieve this
     * @param preProc arraylist
     * @return arraylist
     */
    public static ArrayList<String> popularParser(ArrayList<String> preProc){
        ArrayList<String> processed = new ArrayList<>();
        ArrayList<String> comparitor = new ArrayList<>();

        for(String thing: preProc) {
            comparitor.add(thing);
        }

        for(String thing: comparitor){
            int k=0;
            for(int i=0; i<comparitor.size(); i++){
                if(thing.equals(comparitor.get(i)))
                    k++;
            }
            if(k>3 && !processed.contains(thing))
                processed.add(thing);
        }


        return duplicateRemover(processed);
    }
    //For removing alphanumerical shtuff, this one is gtg

    /**
     * Using two comparison char arrays this method converst each word of the passed arraylist
     * into char array that is compared to the fore-mentioned arrays. This removes all alphanumeric words
     * @param preProc arraylist
     * @return arraylist
     */
    public static ArrayList<String> alphaParser(ArrayList<String> preProc){
        ArrayList<String> processed = new ArrayList<>();
        char[] numeric = new char[]{
                '0', '1', '2','3', '4', '5', '6', '7', '8', '9'
        };
        char[] alpha = new char[]{
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z'
        };

        for (String thing : preProc) {
            char[] a = thing.toCharArray();
            boolean containsAplha = false;
            boolean containsNum = false;
            for(int i=0; i<a.length; i++){
                for(int j=0;j<numeric.length; j++){
                    if(a[i] == numeric[j])
                        containsNum = true;
                }
                for(int k=0; k<alpha.length; k++){
                    if(a[i] == alpha[k])
                        containsAplha = true;
                }
            }
            if(!containsNum && !containsAplha)
                processed.add(new String(a));
        }

        return duplicateRemover(processed);
    }

    /**
     * Used to process all requirements at the same time and return an array of them in the order in which they will be
     * written to output files
     * @param preProcessed arraylist
     * @return Arraylist array
     */
    static ArrayList[] superParser(ArrayList<String> preProcessed){//this will return an array of ArrayLists...
        ArrayList<String> obsessive_non_ona = onaParser(preProcessed);
        ArrayList<String> popular_peeps = popularParser(preProcessed);
        ArrayList<String> non_alphanumerical = alphaParser(preProcessed);

        Collections.sort(obsessive_non_ona);//will need static method for Ca
        Collections.sort(popular_peeps);

        ArrayList[] processd = new ArrayList[]{
                obsessive_non_ona,
                popular_peeps,
                non_alphanumerical
        };

        return processd;
    }

    /**
     * Writes file to passed output file
     * @param processed arraylist
     * @param path file path
     */
    static void fileWriter(ArrayList<String> processed, String path){
        //will have word count...
        PrintWriter output = null;
        try{
            output = new PrintWriter(new FileWriter(path));
            output.println("Word count: "+processed.size());
            for(int i=1; i<processed.size();i++)
                output.println(processed.get(i));


            output.close();

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    //could augment to return Array of ArrayLists

    /**
     * Main file processor.
     * @param outies file array
     * @param innie file
     */
    static void fileProcessor(File[] outies, File innie){//buiids 3 ArrayLists....
        //needs to take files, parse using arraylist, output correctly with word count
        Scanner input = null;
        ArrayList<String[]> shtuff = new ArrayList<>();//note the optionality for Polymorphism with List as static type
        ArrayList<String> actualShtuff = new ArrayList<>();
        try{
            input = new Scanner(new FileReader(innie));
            while(input.hasNextLine()){
                shtuff.add(input.nextLine().toLowerCase().split(" "));
            }

            for(String[] thing: shtuff)
                for(int i=0; i<thing.length; i++)
                    actualShtuff.add(thing[i]);


            ArrayList[] shtuffAfter = superParser(actualShtuff);

            for(int i=0; i<outies.length; i++)
                fileWriter(shtuffAfter[i], outies[i].getPath());

            //will run superParser on Shtuff which will return an array of ArrayLists with which we will write each

            input.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }


    }

    /**
     * Creates output files
     * @param namedFiles file array
     */
    static void fileCreator(File[] namedFiles){
        for(int i=0; i<namedFiles.length; i++){
            try{
                if(namedFiles[i].createNewFile()){
                    System.out.println(namedFiles[i].getName()+" successfully created!");
                }else{
                    System.out.println(namedFiles[i]+" already exists!!");
                    System.out.println("Deleting files and closing program...");
                    for(int j=0; j<i; j++){
                        namedFiles[j].delete();
                    }
                    System.exit(0);
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /*
    File paths
    - obsessive_non_ona.txt
    - popular_peeps.txt
    - non_alphanumerics.txt
    - f
     */

    /**
     * main method
     * @param args arguments
     */
    public static void main(String... args){
        System.out.println("---------------\t\tWELCOME TO THE FILE PARSER EXTRAORDINAIRE! BY THOMAS HEGARTY\t---------------");
        File toBParsed = null;
        Scanner userIn = new Scanner(System.in);
        System.out.println("Enter a text file preferably with stuff in it: ");
        toBParsed = new File(userIn.nextLine());
        //userIn.nextLine();
        File[] outPutFiles = new File[3];
        //Output files...
        outPutFiles[0] = new File("obsessive_non_ona_processed.txt");
        outPutFiles[1] = new File("popular_peeps_processed.txt");
        outPutFiles[2] = new File("non_alphanumerical_processed.txt");
        //create files
        fileCreator(outPutFiles);

        fileProcessor(outPutFiles, toBParsed);

        System.out.println("\nFile successfully processed!\n");

        userIn.close();
    }
}
