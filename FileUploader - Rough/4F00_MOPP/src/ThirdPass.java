/*See Tokenizer.java for notes*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ThirdPass {
    
    static void tokenizeThirdPass(String inputFile, String outputFile) throws FileNotFoundException{
        
        /***************************START OF THIRD PASS************************
         THE THIRD PASS IS RESPONSIBLE FOR SOME MORE POST PROCESSING TO CLEAN UP THE CODE
         * ALSO ADDS LINE NUMBERS
         */
        
        Scanner scan = new Scanner(new File(inputFile)); //creates a scanner object on the file for reading in values
        PrintWriter thirdPass = new PrintWriter(outputFile); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        int lineCount=1;

        while (scan.hasNext()==true){ //while there is still content in the file
        
            lineOfCode = scan.nextLine();//reads in the next line
            
            //most of this section is just making sure there is a : around each token 
            lineOfCode = lineOfCode.replace(":", "::");      
            
            lineOfCode = lineOfCode.replace("::::", "::");
            lineOfCode = lineOfCode.replace(":::", "::");
           

            if (!(lineOfCode.charAt(0)==':')){
                lineOfCode = ":"+lineOfCode;
            }
            if (!(lineOfCode.charAt(lineOfCode.length()-1)==':')){
                lineOfCode = lineOfCode+":";
            }
            
            if ((lineOfCode.substring(0,2).equals("::"))){
                lineOfCode = lineOfCode.substring(1, lineOfCode.length());
            }
            
            if ((lineOfCode.substring(lineOfCode.length()-1,lineOfCode.length())).equals("::")){
                lineOfCode = lineOfCode.substring(0, lineOfCode.length());
            }
            
            lineOfCode = lineOfCode.replaceAll("\\s+","");//Removes any duplicate spaces

            thirdPass.print(lineOfCode+"LINE-"+lineCount+":"); //if a token was found, write new line
            thirdPass.println();//keeps everything from ending up on one line
            lineCount++;
        }
        thirdPass.print(":EOF:");
        scan.close();
        thirdPass.close();//writes everything to the file      
    }   
}
