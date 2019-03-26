import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelDisplayer extends JFrame implements ActionListener {
	
	PixelArray pixArr;
	
	//Constructor
	public PixelDisplayer (int imgDim, PixelArray inArr) {
		pixArr = inArr;
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
		updateArr();
		repaint();
	}//End actionPerformed
	
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
	
	//Updates the Array
	private void updateArr() {
		pixArr.updateArr();
	}//End updateArr
	
	//Toggles the visibilty of the UI
	public void toggleShow() {
		this.setVisible(!this.isVisible());
	}//End toggleShow
	
}//End PixelDisplayer
