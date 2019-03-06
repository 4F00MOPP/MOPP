
import java.io.PrintWriter;
import java.io.IOException;

public class StartAlgorithm {

	public static void main(String args[]) throws IOException {
		
		try {
			
			PrintWriter writer = new PrintWriter("test.txt", "UTF-8");
			writer.println("Did I do it dad?");
			
			for (int i = 0; i < args.length; i++) {
				writer.println(args[i]);
			}//End for
			
			writer.close();
			
		} catch (IOException e){
			throw e;
		}//End Try-Catch
		
		
		
	}//End main
	
}//End test4F00
