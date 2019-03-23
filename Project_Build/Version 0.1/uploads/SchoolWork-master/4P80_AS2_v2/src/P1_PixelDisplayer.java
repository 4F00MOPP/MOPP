import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class P1_PixelDisplayer extends JFrame implements ActionListener {
	
	P1_PixelArray pixArr;
	int epochs;
	int imgSize;
	double neighbourRate;
	double learningRate;
	
	//Constructor
	public P1_PixelDisplayer (int imgDim, P1_PixelArray inArr, int numEpochs, double neighbour, double learning) {
		pixArr = inArr;
		epochs = numEpochs;
		imgSize = imgDim;
		neighbourRate = neighbour;
		learningRate = learning;
		this.setPreferredSize(new Dimension(imgDim+100, imgDim+100));
		this.setTitle("Pixel Displayer");
		
		JButton button = new JButton("Click here!");
	    JPanel panel = new JPanel();
	    panel.add(button);
	    this.getContentPane().add(panel);
	    button.addActionListener(this);
	    
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}//End constructor
	
	//Listener for the button
	@Override
	public void actionPerformed(ActionEvent e) {
		BeginAlgorithm();
	}//End actionPerformed
	
	
	private void BeginAlgorithm() {
		
		Random rng = new Random();
		int red;
		int green;
		int blue;
		
		for (int i = 0; i < epochs; i++) {
			
			red = (int) (rng.nextFloat()*255);
			green = (int) (rng.nextFloat()*255);
			blue = (int) (rng.nextFloat()*255);
			
			pixArr.updateArr(red, green, blue, i, epochs, neighbourRate, learningRate);
			
			if (i%5 == 0){
				System.out.println("Epoch: " + i);
				repaint();
			}
			
		}//End for
		
		
	}//End BeginAlgorithm
	
	//Paints
	@Override
    public void paint(Graphics g) {
        super.paint(g);

        // define the position
        int locX = 200;
        int locY = 200;

        // draw a line (there is no drawPoint..)
        //g.drawLine(locX, locY, locX, locY);
        
        drawArray(g);
    }
	
	//Draws the array
	private void drawArray(Graphics g) {
		
		int arrDim = pixArr.getArrayDimension();
		int arr[][][] = pixArr.getArray();
		
		for (int i = 0; i < arrDim; i++) {
			for (int j = 0; j < arrDim; j++) {
				for (int k = 0; k < 3; k++) {
					Color pixColor = new Color(arr[i][j][0], arr[i][j][1], arr[i][j][2]);
					g.setColor(pixColor);
					g.drawLine(50+i, 75+j, 50+i, 75+j); 
				}//End for
			}//End for
		}//End for
	}//End drawArray
	
	//Toggles the visibilty of the UI
	public void toggleShow() {
		this.setVisible(!this.isVisible());
	}//End toggleShow
	
}//End PixelDisplayer
