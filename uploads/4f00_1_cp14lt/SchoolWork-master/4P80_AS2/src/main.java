public class main {

	public static void main (String args[]) {
		
		int imageSize = 200;	//The size of the image
		PixelArray pixArr;		//The array of pixels
		PixelDisplayer pixDis;	//The displayer
		
		pixArr = new PixelArray(imageSize);	//Initializes the pixels
		pixDis = new PixelDisplayer(imageSize, pixArr);
		
		pixDis.toggleShow();
		
	}//End main
	
}//End main
