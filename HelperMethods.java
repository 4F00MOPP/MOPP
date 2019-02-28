/*See Tokenizer.java for notes*/
package tokenizer;

public class HelperMethods {

    /*
    print2DStringArray:
            Originally written to support printing out the regex table for 
            the tokenizer.      
     */
    public static void print2DStringArray(String array[][]){
           
        for (int i=0;i<array.length;i++){
            System.out.println("");
            for (int j=0; j<array[0].length;j++){
                try{
                    System.out.print(array[i][j]+" ");           
                }catch (ArrayIndexOutOfBoundsException e){ //used to handle ragged array of values
                    j++;//skips to next line
                }
            }
        }
        System.out.println("");
        System.out.println("");
    }//print2DStringArray   
}
