import java.awt.Color;
import java.util.Random;

public class PixelArray {
	
	private static Random rng;
	private static int ArrayDimension;
	private static int PixelArray[][][];
	
	public PixelArray(int imgDim) {
		
		ArrayDimension = imgDim;
		PixelArray = new int[imgDim][imgDim][3];
		rng = new Random();
		
		randomizeArray();
		
	}//End constructor
	
	//Generates a random Pixel Array
	private static void randomizeArray() {
		for (int i = 0; i < ArrayDimension; i++) {
			for (int j = 0; j < ArrayDimension; j++) {
				for (int k = 0; k < 3; k++) {
					PixelArray[i][j][k] = (int) (rng.nextFloat()*255);
				}//End for
			}//End for
		}//End for
	}//End randomizeArray
	
	public int[][][] getArray(){
		return PixelArray;
	}//End getArray
	
	public int getArrayDimension() {
		return ArrayDimension;
	}//End getArrayDimension
	
	//Updates the values
	public void updateArr() {
		for (int i = 0; i < ArrayDimension; i++) {
			for (int j = 0; j < ArrayDimension; j++) {
				for (int k = 0; k < 3; k++) {
					PixelArray[i][j][k] += 10;
					if (PixelArray[i][j][k] >= 255) {
						PixelArray[i][j][k] = 255;
					}//End if
				}//End for
			}//End for
		}//End for
	}//End setArray
	
}//End class
