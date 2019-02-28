/*
*Purpose: Tokenier for 4F00 course project. 
*   Once fully implemented, this program will convert standard Java, C, and C++
*   code into simplified tokenized data that can be compared for similarity with
*   other code that is tokenized the same way.
*
*********************************REVISIONS**************************************

*CONTRIBUTOR:       DATE:           VERSION:        COMMENTS:
*Levi Goertz        Feb. 26 2019    Version 0.1.0   Initial build
*
*
***********************************TODO*****************************************
*   1: ADD HANDLING OF INSTRUCTOR PROVIDED CODE TO BE IGNORED
*   2: 
 */

package tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static tokenizer.HelperMethods.print2DStringArray;

public class Tokenizer {
    
    public void regExTest(){               
        //TESTING REGEX OUT - USE THIS SECTION FOR TESTING IF A REGEX WORKS PROPERLY
        String testPattern = "\\bint\\b\\s\\w+=[0-9]+;";//SET THIS TO THE THE REGEX YOU WANT TO TEST
        String testInput ="int something=2423423432;";  //SET THIS TO A STRING THAT SHOULD WORK WITH THE ABOVE REGEX
      
        Pattern pattern = Pattern.compile(testPattern); //SETS THE PATTERN
        Matcher matcher = pattern.matcher(testInput);   //SETS THE MATCHER
        
        System.out.println("REGULAR EXPRESSIONS TEST:");
        
        while (matcher.find()){ //LOOK FOR MATCH
            System.out.println("REGEX: "+matcher.pattern()); //PRINT OUT WHAT THE REGEX
            System.out.println("MATCH: "+matcher.group()); //PRINT OUT WHAT WAS MATCHED               
        }
        System.out.println("");
    }//regExTest
    
    
    /*****************************TOKENIZER METHOD*****************************/
    
    public void tokenize(String filename) throws FileNotFoundException{ //accepts the filename to tokenize
        
        /***************************START OF FIRST PASS*************************
        There may be multiple passes, depending on the processing that we want
        to do, so the naming convention reflects this. 
        
        -This pass will start to transform the data.
        -Between tokens that are inserted, colons will be used.
        -An assumption for now is that the code is written normally, that is to 
        say, certain conventions are followed such as each statement is on its 
        own line.
            - The approach here is to first create the ability to identify 
              normal, well-written code, and then make it more advanced.
        */
        
        Scanner scan = new Scanner(new File(filename)); //creates a scanner object on the file for reading in values
        PrintWriter firstPass = new PrintWriter("outputOfFirstPass"); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        /***************************REGEX TABLE*********************************
        -Regular expressions are used for matching lines to tokens
        -Aside from minor program tweaks, at this point most of the work is just
        going to be adding the expressions for all three languages.
        -These need to be tested thoroughly to ensure they're working exactly
        how we intend them to (it's easy to make a regex that accepts what we
        want but also additional strings).
        -Add comment after each one to give insight to what is being matched
        -With how it currently checks, moving an expression up in the list
        increases its priority for being checked first
        */
        String [][] regExTable = new String[][] 
        {
         {"\\bint\\b\\s\\w+\\s*=\\s*[0-9]+\\s*;" , ":NUM-VAR-DECLARATION:EQUAL:LITERAL:SEMICOLON"},//int name = value;
         {"\\bbyte\\b\\s\\w+\\s*=\\s*[0-9]*\\s*;" , ":BYTE-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//byte name = value;
         {"\\bshort\\b\\s\\w+\\s*=\\s*[0-9]*\\s*;" , ":SHORT-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//short name = value;
         {"\\blong\\b\\s\\w+\\s*=\\s*[0-9]*\\s*;" , ":LONG-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//long name = value;
         {"\\bdouble\\b\\s\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;" , ":DOUBLE-VAR-DECLARATION:EQUAL:NUMERAL-LITERAL:SEMICOLON"},//double name = value;
         {"\\bfloat\\b\\s\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;" , ":FLOAT-VAR-DECLARATION:EQUAL:NUMERAL-LITERAL:SEMICOLON"},//float name = value;
         {"\\bString\\b\\s\\w+\\s*=\\s*\"[A-Za-z0-9]*\"\\s*;" , ":STRING-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//String name = value;
         {"\\bchar\\b\\s\\w+\\s*=\\s*\'[A-Za-z0-9]\'\\s*;" , ":CHAR-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//char name = value;
         {"\\bboolean\\b\\s\\w+\\s*=\\s*(false|true)\\s*;" , ":BOOLEAN-VAR-DECLARATION:EQUAL:STRING-LITERAL:SEMICOLON"},//boolean name = value;
         {"[}]" , ":RBRACE"},// }
         {"[{]" , ":LBRACE"},// {
         {"(//).*" , ":COMMENT"},// {
         {"^\\s*$" , ":EMPTY"} //EMPTY LINES
         //ADD ADDITIONAL REGEX HERE
        };
        
        char c = 'C';
        
        System.out.println("************START PROCESSING OF FILE************");
       
        while (scan.hasNext()!=false){ //while there is still content in the file
        
            //reads in the next line and trims white space from beginning and end, and reduces all multiple spaces to 1 space
            lineOfCode = scan.nextLine().trim().replaceAll(" +"," "); 
            
            System.out.println("");
            System.out.println("Processing: "+lineOfCode);
                      
            StringBuffer buffer = new StringBuffer(); //for checking RegEx
            String newLineOfCode="";
            
            
            for (int i=0;i<regExTable.length;i++){ //for each entry in regExTable

                Pattern currentPattern = Pattern.compile(regExTable[i][0]); //get current pattern
                Matcher currentMatcher = currentPattern.matcher(lineOfCode); //get current matcher
               
                while (currentMatcher.find()) { //when a match is found
                    currentMatcher.appendReplacement(buffer, regExTable[i][1]);//replace with token    
                }
                currentMatcher.appendTail(buffer);//used to add remaining characters after the last match to the line
                                                  //this will also just end up being the original line if no matches made
                                            
                lineOfCode = buffer.toString(); //sets the newline of code to 
                buffer.delete(0, buffer.length());
            }
            firstPass.print(lineOfCode); //if a token was found, write new line
            System.out.println("Replaced with: "+lineOfCode); //if a token was found, write new line          
            firstPass.println();//keeps everything from ending up on one line            
        }
        firstPass.close();//writes everything to the file
    }    
    public static void main(String[] args) throws FileNotFoundException {
    
        Tokenizer aTokenizer = new Tokenizer();
       
        aTokenizer.regExTest();//reads in the file to tokenize
        aTokenizer.tokenize("testFileofSourceCode"); //reads in the file to tokenize

    }//main   
}//Tokenizer class