import java.awt.Color;

public class P2_ImgFrame {
	
	int size;	
	Color pixColours[][];

	public P2_ImgFrame(Color oriImg[][], int frameSize, int row, int col) {
		
		size = frameSize;
		pixColours = new Color[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				//If the image is not a perfect fit
				if (i*size + row >= oriImg.length || j*size + col >= oriImg[0].length ) {
					pixColours[i][j] = Color.white;
				} else {
					pixColours[i][j] = oriImg[i*size + row][j*size + col];
				}//End if
				
				
			}//End for
		}//End for
		
	}//End Constructor
	
	public int[][] getVector(){
		int result[][] = new int[size*size][3];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i*size + j][0] = pixColours[i][j].getRed();
				result[i*size + j][1] = pixColours[i][j].getGreen();
				result[i*size + j][2] = pixColours[i][j].getBlue();
			}//End for
		}//End for
		
		return result;
	}//End getVector
	
}//End P2_ImgFrame
