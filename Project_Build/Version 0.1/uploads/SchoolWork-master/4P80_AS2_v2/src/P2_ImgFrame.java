import java.awt.Color;

public class P2_ImgFrame {
	
	int size;	
	Color pixColours[][];

	public P2_ImgFrame(Color oriImg[][], int frameSize, int frameRow, int frameCol) {
		
		size = frameSize;
		pixColours = new Color[size][size];
		
		int imgRow;
		int imgCol;
		
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				
				imgRow = size*frameRow + row;
				imgCol = size*frameCol + col;
				
				if (imgRow >= oriImg.length || imgCol >= oriImg.length) {
					pixColours[row][col] = Color.white;
				} else {
					pixColours[row][col] = oriImg[imgRow][imgCol];
				}//End if
				
			}//End for
		}//End for
		
	}//End Constructor
	
	public void updatePixel(int row, int col, int greyScale) {
		pixColours[row][col] = new Color(greyScale, greyScale, greyScale);
	}//End updatePixel
	
	public int[] get1DVector() {
		int result[] = new int[size*size*3];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i + size*(j + size*(0))] = pixColours[i][j].getRed();
				result[i + size*(j + size*(1))] = pixColours[i][j].getGreen();
				result[i + size*(j + size*(2))] = pixColours[i][j].getBlue();
			}//End for
		}//End for
		
		return result;
	}//End get1DVector
	
	public Color getColourAt(int row, int col) {
		return pixColours[row][col];
	}
	
	public int[][] get2DVector(){
		int result[][] = new int[size*size][3];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result[i*size + j][0] = pixColours[i][j].getRed();
				result[i*size + j][1] = pixColours[i][j].getGreen();
				result[i*size + j][2] = pixColours[i][j].getBlue();
			}//End for
		}//End for
		
		return result;
	}//End get2DVector
	
	public int getSize() {
		return size;
	}
	
}//End P2_ImgFrame
