/*See Tokenizer.java for notes*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondPass {
    
    
    static void TokenizeSecondPass(String inputFile, String [] identifiers, String outputFile) throws FileNotFoundException{
        
        /***************************START OF SECOND PASS************************
         THE SECOND PASS IS RESPONSIBLE FOR TOKENIZING THE IDENTIFIERS THAT WERE 
         FOUND IN THE FIRST PASS. 
         THIS PASS ALSO PROVIDES SOME POST PROCESSING TO CLEAN UP THE CODE
         */
        
        Scanner scan = new Scanner(new File(inputFile)); //creates a scanner object on the file for reading in values
        PrintWriter secondPass = new PrintWriter(outputFile); //creates output file 
        String lineOfCode = new String(); //will be used for processing each line of code from the input file
        
        //PRINTS OUT THE IDENTIFIERS THAT WERE FOUND IN THE FIRST PASS
        for (int i=0; i<identifiers.length;i++){ 
            if (!identifiers[i].equals("197839728sjdhsajfwyvnqefhoalskfqpsnkvnb")){ 
           //     System.out.println(i+":"+identifiers[i].trim());
            }
        }
        
        int lineNumber = 1;
        
        while (scan.hasNext()==true){ //while there is still content in the file
        
            lineOfCode = scan.nextLine();//reads in the next line

            String [] symArr = {"\\:"};
            
            for (int i=0;i<symArr.length;i++){ //this puts space before and after each token to ensure the surrounding content can be processed correctly
                lineOfCode = lineOfCode.replaceAll(symArr[i], " "+symArr[i]+" ");
            }
            
            StringBuffer buffer = new StringBuffer(); //for checking RegEx
           
            for (int i=0;i<identifiers.length;i++){ //for each identifer
               
                if (identifiers[i].equals("197839728sjdhsajfwyvnqefhoalskfqpsnkvnb")){ //leaves the loop if there are no more identifers
                    i=identifiers.length-1; 
                }
          
                String pattern="";
                pattern = "\\97839728sjdhsajfw111yvnqefhoalskfqpsnkvn\\b";
                Pattern currentPattern = Pattern.compile(pattern); //get current pattern

                pattern ="\\s+"+identifiers[i].trim()+"(\\s+)";
                currentPattern = Pattern.compile(pattern); //get current pattern

                Matcher currentMatcher = currentPattern.matcher(lineOfCode); //get current matcher
                  
                while (currentMatcher.find()) { //when a match is found
                    currentMatcher.appendReplacement(buffer, " :UNKNOWNVAR: ");//replace with token 
                }
 
                currentMatcher.appendTail(buffer);//used to add remaining characters after the last match to the line
                                                  //this will also just end up being the original line if no matches made

                lineOfCode = buffer.toString(); //sets the newline of code to 
                buffer.delete(0, buffer.length());

            }
            
            lineOfCode = lineOfCode.replaceAll("\\s*:\\s*",":");//Removes all remaining spaces around colons

            secondPass.print(lineOfCode); //if a token was found, write new line
            secondPass.println();//keeps everything from ending up on one line  
            
            if (lineNumber%100==0){
                //System.out.println("Line "+lineNumber+" complete.");
            }
            lineNumber++;
        }
        scan.close();
        secondPass.close();//writes everything to the file    
    } 
}
