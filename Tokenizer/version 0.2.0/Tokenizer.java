/*
*Purpose: Tokenier for 4F00 course project. 
*   Once fully implemented, this program will convert standard Java, C, and C++
*   code into simplified tokenized data that can be compared for similarity with
*   other code that is tokenized the same way.
*
*********************************REVISIONS**************************************

*CONTRIBUTOR:       DATE:           VERSION:        COMMENTS:
*Levi Goertz        Feb. 26 2019    Version 0.1.0   Initial build
*Levi Goertz        Mar. 06 2019    Version 0.2.0   Expanded Regex list
*
*
***********************************TODO*****************************************
*   1: ADD HANDLING OF INSTRUCTOR PROVIDED CODE TO BE IGNORED
*   2: IMRPOVE THE WAY IDENTIFIERS IS INITIATED ~ LINE 140
*   3: MAKE SURE OPERATORS LIST IS COMPLETE ~ LINE 160
*   4: ADD DETECTION OF VARIABLE INITS IN METHOD SIGS
*   5: 
 */

package tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import static tokenizer.HelperMethods.regExTest;
import static tokenizer.HelperMethods.print2DStringArray;

public class Tokenizer {
     
    /*****************************TOKENIZER METHOD*****************************/
    
    public void tokenizeFirstPass(String inputFile) throws FileNotFoundException{ //accepts the filename to tokenize
        
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
        
        String [][] regExTable = new String[][]{
       
        {":"                                                , ":COLON:"}, //GETS RID OF COLONS SO TOKENS WORK PROPERLY
        {"public static void main\\s*\\(\\s*String.*"       , ":MAIN:"}, //main method 
        {"(:|\\s+)\\w+\\s*\\(.*\\)\\{"                      , ":METHODNAME:"}, //METHOD NAMES;
        
        //this sets up a format that can be searched for later when tokenizing identifers (2nd pass)
        {"(:|\\s*)\\w+\\s*="                                , " UNKNOWNVAR ="}, //identifiers

        {"=\\w*s*(\\[\\w*\\]\\s*)+"                         , " :ARRAY-ASSIGN:"}, //assigning values to arrays =[value]+
       
        // of the form: int aNumber = 100;
        {"\\bint\\b\\s+\\w+\\s*=\\s*[0-9]+\\s*;"                , ":NUM-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//int name = value;
        {"\\bbyte\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"               , ":BYTE-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//byte name = value;
        {"\\bshort\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"              , ":SHORT-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//short name = value;
        {"\\blong\\b\\s+\\w+\\s*=\\s*[0-9]*\\s*;"               , ":LONG-VAR-DECLARATION::NUM-VAR::EQUAL:NUM-LITERAL::SEMICOLON:"},//long name = value;
        {"\\bdouble\\b\\s+\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;"   , ":DOUBLE-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//double name = value;
        {"\\bfloat\\b\\s+\\w+\\s*=\\s*[0-9]+[.]*[0-9]*\\s*;"    , ":FLOAT-VAR-DECLARATION::NUM-VAR::EQUAL::NUM-LITERAL::SEMICOLON:"},//float name = value;
        {"\\bString\\b\\s+\\w+\\s*=\\s*\"[A-Za-z0-9]*\"\\s*;"   , ":STRING-VAR-DECLARATION::STRING-VAR::EQUAL::STRING-LITERAL::SEMICOLON:"},//String name = value;
        {"\\bchar\\b\\s+\\w+\\s*=\\s*\'[A-Za-z0-9]\'\\s*;"      , ":CHAR-VAR-DECLARATION::STRING-VAR::EQUAL::STRING-LITERAL::SEMICOLON:"},//char name = value;
        {"\\bboolean\\b\\s+\\w+\\s*=\\s*(false|true)\\s*;"      , ":BOOLEAN-VAR-DECLARATION::BOOL-VAR::EQUAL::BOOL-LITERAL::SEMICOLON:"},//boolean name = value;
         
        
        //2D arrays of the form: int [][] aNumber;
        {"(^|\\s+)\\bint\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"          , ":NUM-ARRAY-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":NUM-ARRAY-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"        , ":NUM-ARRAY-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":NUM-ARRAY-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"       , ":NUM-VAR-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"        , ":NUM-ARRAY-DECLATATION::NUM-ARRAY::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"       , ":STRING-ARRAY-DECLARATION::STRING-ARRAY::SEMICOLON:"},//String dec;
        {"(^|\\s+)\\bchar\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"         , ":CHAR-ARRAY-DECLARATION::STRING-ARRAY::SEMICOLON:"},//string dec;
        {"(^|\\s+)\\bboolean\\b(\\[\\s*\\]\\s*)+\\s+\\w+;"      , ":BOOLEAN-ARRAY-DECLARATION::BOOL-ARRAY::SEMICOLON:"},//boolean dec;
        
        
        // of the form: int aNumber;
        {"(^|\\s+)\\bint\\b\\s+\\w+;"                           , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b\\s+\\w+;"                          , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b\\s+\\w+;"                         , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b\\s+\\w+;"                          , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b\\s+\\w+;"                        , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b\\s+\\w+;"                         , ":NUM-VAR-DECLATATION::NUM-VAR::SEMICOLON:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b\\s+\\w+;"                        , ":STRING-VAR-DECLARATION::STRING-VAR::SEMICOLON:"},//String dec;
        {"(^|\\s+)\\bchar\\b\\s+\\w+;"                          , ":CHAR-VAR-DECLARATION::STRING-VAR::SEMICOLON:"},//string dec;
        {"(^|\\s+)\\bboolean\\b\\s+\\w+;"                       , ":BOOLEAN-VAR-DECLARATION::BOOL-VAR::SEMICOLON:"},//boolean dec;
        
        // of the form: int
        {"(^|\\s+)\\bint\\b\\s+"                                , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bbyte\\b\\s+"                               , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bshort\\b\\s+"                              , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\blong\\b\\s+"                               , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bdouble\\b\\s+"                             , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bfloat\\b\\s+"                              , ":NUM-VAR-DECLATATION:"}, //numeral type declarations;
        {"(^|\\s+)\\bString\\b\\s+"                             , ":STRING-VAR-DECLARATION:"},//String dec;
        {"(^|\\s+)\\bchar\\b\\s+"                               , ":CHAR-VAR-DECLARATION:"},//string dec;
        {"(^|\\s+)\\bboolean\\b\\s+"                            , ":BOOLEAN-VAR-DECLARATION:"},//boolean dec;
         
        //This finds any remaining identifers (i.e. from Objects)
        {"^.+\\s*="                                             , ":UNKNOWNVAR:="}, //identifier;
         
        {"[\\s+|\\{|=|(]\\d+(?:\\.\\d+)?"                       , ":NUM-LITERAL:"}, //any number by itself   
         
        //LOOPS
        {"if\\s*\\(.*"                                          , ":IF-LOOP:"},
        {"for\\s*\\(.*"                                         , ":FOR-LOOP:"}, 
        {"while\\s*\\(.*"                                       , ":WHILE-LOOP:"},
        
        {"/.*"                                                  ,":COMMENT:"},// Comments       
        {"^\\s*\\*.*"                                           ,":COMMENT:"},// Multi-line Comments       
        {".*System.*;"                                          , ":JAVAIO:"}, //anything to do with JAVA IO, this can be expanded
        {"enum\\s+\\w+.*"                                       , ":ENUM-DECLARATION:"}, 
        {"package\\s+.*"                                        , ":PACKAGE-DECLARATION:"},
        {"\".*\""                                               , ":STRING-OF-TEXT:"},   
        {"\'.*\'"                                               , ":STRING-OF-TEXT:"},   
        {"^\\s*$"                                               , ":EMPTY:"}, //EMPTY LINES
        //***************ADD ADDITIONAL REGEX HERE
        };
        
        String outputFile = "outputOfFirstPass";
        
        Scanner scan = new Scanner(new File(inputFile)); //creates a scanner object on the file for reading in values
        PrintWriter firstPass = new PrintWriter(outputFile); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        String [] identifiers = new String[1000]; // this is used to find and replace all the identifiers that were found during the first pass
        int identifiersIndex=0;
        
        for (int i=0; i<identifiers.length;i++){ //init array
            identifiers[i]="197839728sjdhsajfwyvnqefhoalskfqpsnkvnb";//initializing array with value that is very unlikely to be encountered
        }
        
        System.out.println("************START PROCESSING OF FILE************");
       
        while (scan.hasNext()!=false){ //while there is still content in the file
        
            //reads in the next line and trims white space from beginning and end, and reduces all multiple spaces to 1 space
            lineOfCode = scan.nextLine().trim().replaceAll(" +"," "); 
            
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
        firstPass.close();//writes everything to the file
              
        System.out.println("SECOND PASS STARTED");
        TokenizeSecondPass(outputFile,identifiers);
       
    }   
    
    void TokenizeSecondPass(String inputFile, String [] identifiers) throws FileNotFoundException{
        
        /***************************START OF SECOND PASS************************
         THE SECOND PASS IS RESPONSIBLE FOR TOKENIZING THE IDENTIFIERS THAT WERE 
         FOUND IN THE FIRST PASS. 
         THIS PASS ALSO PROVIDES SOME POST PROCESSING TO CLEAN UP THE CODE
         */
        
        Scanner scan = new Scanner(new File(inputFile)); //creates a scanner object on the file for reading in values
        PrintWriter secondPass = new PrintWriter("outputOfSecondPass"); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        //PRINTS OUT THE IDENTIFIERS THAT WERE FOUND IN THE FIRST PASS
        for (int i=0; i<identifiers.length;i++){ 
            if (!identifiers[i].equals("197839728sjdhsajfwyvnqefhoalskfqpsnkvnb")){
                System.out.println(i+":"+identifiers[i].trim());
            }
        }
        
        while (scan.hasNext()==true){ //while there is still content in the file
        
            lineOfCode = scan.nextLine();//reads in the next line

            String [] symArr = {"\\:"};
            
            for (int i=0;i<symArr.length;i++){ //this puts space before and after each token to ensure the surrounding content can be processed correctly
                lineOfCode = lineOfCode.replaceAll(symArr[i], " "+symArr[i]+" ");
            }
            
            StringBuffer buffer = new StringBuffer(); //for checking RegEx
           
            for (int i=0;i<identifiers.length;i++){ //for each identifer
          
                String pattern="";
                pattern = "\\97839728sjdhsajfw111yvnqefhoalskfqpsnkvn\\b";
                Pattern currentPattern = Pattern.compile(pattern); //get current pattern

                pattern ="\\s+"+identifiers[i].trim()+"(\\s+)";
                currentPattern = Pattern.compile(pattern); //get current pattern

                Matcher currentMatcher = currentPattern.matcher(lineOfCode); //get current matcher
                  
                while (currentMatcher.find()) { //when a match is found
                    currentMatcher.appendReplacement(buffer, " :UNKNOWNVAR2NDPASS: ");//replace with token 
                }
 
                currentMatcher.appendTail(buffer);//used to add remaining characters after the last match to the line
                                                  //this will also just end up being the original line if no matches made

                lineOfCode = buffer.toString(); //sets the newline of code to 
                buffer.delete(0, buffer.length());

            }
            
            lineOfCode = lineOfCode.replaceAll("\\s+"," ");//Removes any duplicate spaces
            lineOfCode = lineOfCode.replaceAll("\\s*:\\s*",":");//Removes all remaining spaces around colons

            secondPass.print(lineOfCode); //if a token was found, write new line
            secondPass.println();//keeps everything from ending up on one line  
        }
        secondPass.close();//writes everything to the file    
    }
    
    public static void main(String[] args) throws FileNotFoundException {
    
        regExTest();
            
        Tokenizer aTokenizer = new Tokenizer();
       
        aTokenizer.tokenizeFirstPass("testFileofSourceCode"); //reads in the file to tokenize
    }//main   
}//Tokenizer class