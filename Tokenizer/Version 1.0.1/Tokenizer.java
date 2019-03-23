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
*Levi Goertz        Mar. 20 2019    Version 1.0.0   Moved Regex list to own class
*                                                   Moved First and SecondPass into their own classes
*                                                   Added C and C++ tokens
*                                                   Added ThirdPass
*Levi Goertz        Mar. 22 2019    Version 1.0.1   Moved LINE token to start of line
*
***********************************TODO*****************************************
*   1: ADD HANDLING OF INSTRUCTOR PROVIDED CODE TO BE IGNORED
 */

package tokenizer;

import java.io.FileNotFoundException;
import static tokenizer.HelperMethods.regExTest;

public class Tokenizer {
           
    public Tokenizer(String inputFile, String outputFile) throws FileNotFoundException{
        
        String outputFirstPass = "outputFirstPass";
        String outputSecondPass = "outputSecondPass";
        
        regExTest();
        
        //performs bulk of tokenization 
        System.out.println("Starting first pass...");
        String [] identifiers = FirstPass.tokenizeFirstPass(inputFile,outputFirstPass); //First pass
        System.out.println("First pass done.");
        
        //the idenfiers array is used in the second pass to tokenize variable names
        System.out.println("Starting second pass...");
        SecondPass.TokenizeSecondPass(outputFirstPass,identifiers,outputSecondPass); //second pass
        System.out.println("Second pass done.");

        System.out.println("Starting third pass...");        
        ThirdPass.tokenizeThirdPass(outputSecondPass,outputFile); //second pass
        System.out.println("Third pass done.");
        
    }
    
    public static void main(String[] args) throws FileNotFoundException {    
            new Tokenizer("testFileOfSourceCode","tokenizedCode"); //Change these strings to adjust the file names  
    }//main   
}//Tokenizer class