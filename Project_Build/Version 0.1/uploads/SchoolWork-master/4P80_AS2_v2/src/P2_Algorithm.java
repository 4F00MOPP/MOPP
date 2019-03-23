import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class P2_Algorithm {
		
	int frameSize;				//Size of each frame
	int numEpochs;
	double neighbourConst;
	double learningConst;
	String fileName;			//File name
	
	Color OriginalImage[][];	//The initial image file
	Color ImageFrames[][][];	//The frames that the image is being broken into [f][x][y] 
	
	public P2_Algorithm(int size, int epochs, double neighbour, double learning) {
		
		frameSize = size; 
		numEpochs = epochs;
		neighbourConst = neighbour;
		learningConst = learning;
		
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
			P2_ImgFrame trainFrames[][] = imageToFrames(OriginalImage, frameSize);
			P2_ImgFrame imgFrames[][]   = imageToFrames(OriginalImage, frameSize);
			
			int frameRows = trainFrames.length;
			int frameCols = trainFrames[0].length;
			
			//P2_FrameDisplayer testDisplayer = new P2_FrameDisplayer(trainFrames[1][0]);
			//testDisplayer.toggleShow();
			
			//Main Algorithm...
			for (int t = 0; t < numEpochs; t++) {

				//Pick a frame at random from trainFrame
				int chosenRow = (int)(Math.random()*frameRows);
				int chosenCol = (int)(Math.random()*frameCols);
				
				//Determine closest frame in imgFrame
				double frameDist_Best = -1;
				double frameDist_Curr;
				int rowBMU = -1;
				int colBMU = -1;
				for (int i = 0; i < frameRows; i++) {
					for (int j = 0; j < frameCols; j++) {
						frameDist_Curr = FrameEuclidDist(trainFrames[chosenRow][chosenCol], imgFrames[i][j]);
						if (frameDist_Best == -1 || frameDist_Curr < frameDist_Best) {
							rowBMU = i;
							colBMU = j;
							frameDist_Best = frameDist_Curr;
						}//End if
					}//End for
				}//End for
				
				int mapDimension = Math.max(frameRows, frameCols);
				double mapRadius = mapDimension/2;
				double timeConst = numEpochs/Math.log(mapRadius);
				double neighbourhoodRadius = mapRadius * Math.exp(-(t/timeConst));
				
				double influence;
				double distSq;
				double widthSq = neighbourhoodRadius*neighbourhoodRadius;
				double learningRate = learning*Math.exp(-t/numEpochs);
				
				
				//For every frame
				for (int row = 0; row < frameRows; row++) {
					for (int col = 0; col < frameCols; col++) {
						if (inRadius(row, col, rowBMU, colBMU, neighbourhoodRadius)) {
							
							distSq = FrameEuclidDist(trainFrames[chosenRow][chosenCol], imgFrames[row][col]);
							influence = Math.exp(-(distSq) / (2*widthSq));
							
							P2_ImgFrame currFrame = imgFrames[row][col];
							
							
							//For every pixel in the Frame
							for (int pixRow = 0; pixRow < frameSize; pixRow++) {
								for (int pixCol = 0; pixCol < frameSize; pixCol++) {
									Color currColor = currFrame.getColourAt(pixRow, pixCol);
									int newVal = (int) (currColor.getRed() + influence*learningRate*(trainFrames[chosenRow][chosenCol].getColourAt(pixRow, pixCol).getRed() - currColor.getRed()));
									
									if (newVal >= 255) {
										newVal = 254;
									} else if (newVal < 0) {
										newVal = 0;
									}//End if
									
									currFrame.updatePixel(pixRow, pixCol, newVal);
								}//End for
							}//End for

						}//End if
					}//End for
				}//End for*/
				
				
			}//End for
			
			
			
			
			
			
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
	
	private double FrameEuclidDist(P2_ImgFrame frame1, P2_ImgFrame frame2) {
		
		//int length = vector1.length;
		double result = 0;
		
		Color pix1;
		Color pix2;
		
		for (int row = 0; row < frameSize; row++) {
			for (int col = 0; col < frameSize; col++) {
				
				pix1 = frame1.getColourAt(row, col);
				pix2 = frame2.getColourAt(row, col);
				
				result += Math.pow(pix1.getRed() - pix2.getRed(), 2);
				
			}//End for
		}//End for

		return result;
	}//End FrameEuclidDist
	
	//Returns if the pixels are within the distance of each other
	private boolean inRadius(int row, int col, int row2, int col2, double size) {
		return (Math.pow(row - row2, 2) + Math.pow(col - col2, 2) <= Math.pow(size, 2));
	}//End inRadius
	
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
