/*See Tokenizer.java for notes*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethods {

    /*
    regExTest:
            USE THIS METHOD FOR TESTING IF A REGEX WORKS PROPERLY
     */
    public static void regExTest(){               
              
        //System.out.println("character to int test [.] = "+(int)'.');
        
        String testPattern = "\".*\"";//SET THIS TO THE THE REGEX YOU WANT TO TEST
        String testInput ="\"s\"";  //SET THIS TO A STRING THAT SHOULD WORK WITH THE ABOVE REGEX
      
        Pattern pattern = Pattern.compile(testPattern); //SETS THE PATTERN
        Matcher matcher = pattern.matcher(testInput);   //SETS THE MATCHER
        
        //System.out.println("REGULAR EXPRESSIONS TEST:");
        
        while (matcher.find()){ //LOOK FOR MATCH
           // System.out.println("REGEX: "+matcher.pattern()); //PRINT OUT WHAT THE REGEX
           // System.out.println("MATCH: "+matcher.group()); //PRINT OUT WHAT WAS MATCHED               
        }
        //System.out.println("");
    }//regExTest
    
    /*
    print2DStringArray:
            Originally written to support printing out the regex table for 
            the tokenizer.      
     */
    public static void print2DStringArray(String array[][]){
           
        for (int i=0;i<array.length;i++){
       //     System.out.println("");
            for (int j=0; j<array[0].length;j++){
                try{
                //    System.out.print(array[i][j]+" ");           
                }catch (ArrayIndexOutOfBoundsException e){ //used to handle ragged array of values
                    j++;//skips to next line
                }
            }
        }
      //  System.out.println("");
     //   System.out.println("");
    }//print2DStringArray   

    
}

