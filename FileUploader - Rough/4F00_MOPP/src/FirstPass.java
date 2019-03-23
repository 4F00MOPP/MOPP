/*See Tokenizer.java for notes*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstPass {

    public static String [] tokenizeFirstPass(String inputFile, String outputFile) throws FileNotFoundException{ //accepts the filename to tokenize
        
        /***************************START OF FIRST PASS*************************
         * converts each line of code according to Regex table in Tokens.java
        */
            
        String [][] regExTable = Tokens.importTokens(); //get list of tokens
        
        Scanner scan = new Scanner(new File(inputFile)); //creates a scanner object on the file for reading in values
        PrintWriter firstPass = new PrintWriter(outputFile); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        String [] identifiers = new String[50000];  // this is used to find and replace all the identifiers that were found during the first pass. 
        int identifiersIndex=0;                     // this is something that can be improved, maybe use an array list
        
        for (int i=0; i<identifiers.length;i++){ //init array
            identifiers[i]="197839728sjdhsajfwyvnqefhoalskfqpsnkvnb";//initializing array with value that is very unlikely to be encountered
        }
        
       // System.out.println("************START PROCESSING OF FILE************");
       
        while (scan.hasNext()!=false){ //while there is still content in the file
        
            //reads in the next line and trims white space from beginning and end, and reduces all multiple spaces to 1 space
            lineOfCode = scan.nextLine().trim().replaceAll(" +"," "); //TODO is the substitution for + necessary?
            
            //puts space between all operators, brackets, to make finding identifiers easier
            String [] symArr = {"\\[","\\]","\\+","\\-","\\(","\\)","\\=","\\*","\\'","\"","\\<","\\>","\\}","\\{"};
            
            for (int i=0;i<symArr.length;i++){
                lineOfCode = lineOfCode.replaceAll(symArr[i], " "+symArr[i]+" ");
            }
             
            StringBuffer buffer = new StringBuffer(); //for checking RegEx
            
            for (int i=0;i<regExTable.length;i++){ //for each entry in regExTable

                Pattern currentPattern = Pattern.compile(regExTable[i][0]); //get current pattern
                Matcher currentMatcher = currentPattern.matcher(lineOfCode); //get current matcher
               
                while (currentMatcher.find()) { //when a match is found
                    currentMatcher.appendReplacement(buffer, regExTable[i][1]);//replace with token 
                   
                    if (regExTable[i][1].equals(" UNKNOWNVAR =")){ //if an identifier is found, add to array for 2nd pass
                        
                        String identToTrim = currentMatcher.group();
                        
                        identToTrim = identToTrim.replace("=", "");
                        identToTrim.trim();
                        
                        identifiers[identifiersIndex] = identToTrim; //keeps track of identifiers so they can be found and replaced                        
                        identifiersIndex++;
                    }
                }
                currentMatcher.appendTail(buffer);//used to add remaining characters after the last match to the line
                                                  //this will also just end up being the original line if no matches made
                                            
                lineOfCode = buffer.toString(); //sets the newline of code to 
                buffer.delete(0, buffer.length());
            }
            firstPass.print(lineOfCode); //if a token was found, write new line
            firstPass.println();//keeps everything from ending up on one line            
        }
        scan.close();
        firstPass.close();//writes everything to the file

        return identifiers;      
    }         
}
