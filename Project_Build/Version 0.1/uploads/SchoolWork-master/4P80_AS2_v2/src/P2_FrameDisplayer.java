
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class P2_FrameDisplayer extends JFrame  {
	
	int size;
	P2_ImgFrame frame;
	
	//Constructor
	public P2_FrameDisplayer (P2_ImgFrame inFrame) {
		
		size = inFrame.getSize();
		frame = inFrame;
		
		this.setPreferredSize(new Dimension(600, 600));
		this.setTitle("Frame Displayer");
	    JPanel panel = new JPanel();
	    this.getContentPane().add(panel);
	    
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}//End constructor
	
	//Paints
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        
        //
        
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//System.out.println("a");
				g.setColor(frame.getColourAt(i, j));
				g.fillRect(i*100, j*100, 100, 100);	
			}//End for
		}//End for
        
    }
	
	//Toggles the visibilty of the UI
	public void toggleShow() {
		this.setVisible(!this.isVisible());
	}//End toggleShow
	
}//End 

