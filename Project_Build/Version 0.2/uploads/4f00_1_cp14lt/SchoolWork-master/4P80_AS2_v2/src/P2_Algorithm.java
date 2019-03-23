import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class P2_Algorithm {
		
	int frameSize;				//Size of each frame
	String fileName;			//File name
	
	Color OriginalImage[][];	//The initial image file
	Color ImageFrames[][][];	//The frames that the image is being broken into [f][x][y] 
	
	public P2_Algorithm(int size) {
		
		frameSize = size; 
		
		//File Selector
		P2_FileChooser fileChooser = new P2_FileChooser();
		fileChooser.run(250, 110);
		
		//Waits for file to be selected
		while (fileChooser.isVisible()) {
			 try {
                 Thread.sleep(1000);
             } catch (InterruptedException e1) {
                 break;
             }//End Try
		}//End while
		
		fileName = fileChooser.getFile();
		
		//Ensuring a file was properly selected
		if (fileName.compareTo("Cancelled") == 0) {
			System.out.println("File not selected.");
		} else {
			
			//File selected
			try {
				OriginalImage = readImage(fileName);
			} catch (IOException e) {
				System.out.println("Error reading the image file.");
			}//End Try-Catch
			
			//Determining all the Frames
			P2_ImgFrame imgFrames[][] = imageToFrames(OriginalImage, frameSize);
			
			int test[][] = imgFrames[0][0].getVector();
			for (int i = 0; i < test.length; i++) {
				for (int j = 0; j < test[i].length; j++) {
					System.out.print(", " +test[i][j]);
					
				}
				System.out.println();
			}
			
			
		} //End if
		
		
	}//End Constructor
	
	
	
	//Converts an image into an array of Frames
	public P2_ImgFrame[][] imageToFrames(Color oriImg[][], int frameSize){
		
		int rowCount = oriImg.length;
		int colCount = oriImg[0].length;
		int numRows = divRoundUp(rowCount, frameSize);
		int numCols = divRoundUp(colCount, frameSize);

		P2_ImgFrame result[][] = new P2_ImgFrame[numRows][numCols];
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				result[i][j] = new P2_ImgFrame(oriImg, frameSize, i, j);
			}//End for
		}//End for
		
		return result;
	}//End imageToFrames
	
	//divides and rounds up if need be
	private int divRoundUp(int num, int denum) {
		int result;
		result = num/denum;
		if (result * denum != num) result++;
		return result;
	}//End divRoundUp
	
	
	private Color[][] readImage(String fileName) throws IOException{
		Color pixArr[][];
		
		//Reading the image
		try {
			BufferedImage image = ImageIO.read(new File(fileName));

			int width = image.getWidth();
		    int height = image.getHeight();
		    pixArr = new Color[height][width];

		    for (int row = 0; row < height; row++) {
		       for (int col = 0; col < width; col++) {
		    	   pixArr[row][col] = new Color (image.getRGB(col,row));
		       }//End for
		    }//End for
		    
		    return pixArr;
		} catch (IOException e) {throw e;}
	}//End readImage
	
}//End P2_Algorithm
