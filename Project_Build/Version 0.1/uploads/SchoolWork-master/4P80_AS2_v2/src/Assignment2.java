import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assignment2 {

	public static void main (String args[]) {
		
		Part2();
		
	}//End main
	
	private static void Part1() {
		
		int imageSize = 200;
		int numEpochs = 1000;
		double neighbourConstant = 0.9;
		double learningConstant = 1;
		
		new P1_Algorithm(imageSize, numEpochs, neighbourConstant, learningConstant);
		
	}//End Part1
	
	private static void Part2() {
		
		int frameSize = 3;
		int numEpochs = 100;
		double neighbourConstant = 0.9;
		double learningConstant = 1;
		
		new P2_Algorithm(frameSize, numEpochs, neighbourConstant, learningConstant);
	}//End Part2
	
	
}//End main

