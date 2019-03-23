import java.awt.Color;
import java.util.Random;

public class P1_PixelArray {
	
	private static Random rng;
	private static int ArrayDimension;
	private static int PixelArray[][][];
	
	public P1_PixelArray(int imgDim) {
		
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
	public void updateArr(int red, int green, int blue, int currEpoch, int maxEpoch, double neighbour, double learning) {

		
		//Finding best matching pixel
		double pixDist_Best = -1;
		double pixDist_Curr;
		int row = -1;
		int col = -1;
		for (int i = 0; i < ArrayDimension; i++) {
			for (int j = 0; j < ArrayDimension; j++) {
				pixDist_Curr = EuclideanDist(new int[] {red, green, blue}, PixelArray[i][j]);
				if (pixDist_Best == -1 || pixDist_Curr < pixDist_Best) {
					row = i;
					col = j;
					pixDist_Best = pixDist_Curr;
				}//End if
			}//End for
		}//End for

		double mapRadius = ArrayDimension/2;
		double timeConst = maxEpoch/Math.log(mapRadius);
		double neighbourhoodRadius = mapRadius * Math.exp(-(currEpoch/timeConst));
		
		double influence;
		double distSq;
		double widthSq = neighbourhoodRadius*neighbourhoodRadius;
		double learningRate = learning*Math.exp(-currEpoch/maxEpoch);
		
		//For every pixel
		for (int i = 0; i < ArrayDimension; i++) {
			for (int j = 0; j < ArrayDimension; j++) {
				if (inRadius(i, j, row, col, neighbourhoodRadius)) {
					//PixelArray[i][j] = calcNewColour(PixelArray[i][j], new int[] {red, green, blue}, currEpoch, maxEpoch, neighbour, learning);
					
					distSq = EuclideanDist(PixelArray[i][j], new int[] {red, green, blue});
					influence = Math.exp(-(distSq) / (2*widthSq));
					
					//Updates
					PixelArray[i][j][0] = (int) (PixelArray[i][j][0] + influence*learningRate*(red - PixelArray[i][j][0]));
					PixelArray[i][j][1] = (int) (PixelArray[i][j][1] + influence*learningRate*(green - PixelArray[i][j][1]));
					PixelArray[i][j][2] = (int) (PixelArray[i][j][2] + influence*learningRate*(blue - PixelArray[i][j][2]));
					
					//Capping at 255 and 0
					for (int x = 0; x < 3; x++) {
						if (PixelArray[i][j][x] >= 255) PixelArray[i][j][x] = 254;
						if (PixelArray[i][j][x] < 0) PixelArray[i][j][x] = 0;
					}//End for
					
				}//End if
			}//End for
		}//End for
	}//End setArray

	//Returns if the pixels are within the distance of each other
	private boolean inRadius(int row, int col, int row2, int col2, double size) {
		return (Math.pow(row - row2, 2) + Math.pow(col - col2, 2) <= Math.pow(size, 2));
	}//End inRadius
	
	private double EuclideanDist(int vector1[], int vector2[]) {
		int length = vector1.length;
		double result = 0;
		
		for (int i = 0; i < length; i++) {
			result += Math.pow(vector1[i] - vector2[i], 2);
		}//End for
		
		return result;
	}//End dist
	
}//End class
