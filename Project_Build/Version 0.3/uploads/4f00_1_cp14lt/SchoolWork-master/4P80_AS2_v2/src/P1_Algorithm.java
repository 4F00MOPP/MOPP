
public class P1_Algorithm {
	
	public P1_Algorithm(int imageSize, int numEpochs, double neighbour, double learning) {
		
		P1_PixelArray pixArr;		//The array of pixels
		P1_PixelDisplayer pixDis;	//The displayer
		
		pixArr = new P1_PixelArray(imageSize);	//Initializes the pixels
		pixDis = new P1_PixelDisplayer(imageSize, pixArr, numEpochs, neighbour, learning);
		
		pixDis.toggleShow();
		
	}//End constructor
	
	
	
}
