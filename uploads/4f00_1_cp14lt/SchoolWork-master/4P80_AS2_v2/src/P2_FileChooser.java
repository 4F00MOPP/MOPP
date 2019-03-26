import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class P2_FileChooser extends JFrame {

	 private JTextField filename = new JTextField(), dir = new JTextField();
	 private JButton open = new JButton("Choose File");

	  public P2_FileChooser() {
	    JPanel p = new JPanel();
	    open.addActionListener(new OpenL());
	    p.add(open);
	    Container cp = getContentPane();
	    cp.add(p, BorderLayout.SOUTH);
	    dir.setEditable(false);
	    filename.setEditable(false);
	    p = new JPanel();
	    p.setLayout(new GridLayout(2, 1));
	    p.add(filename);
	    p.add(dir);
	    cp.add(p, BorderLayout.NORTH);
	  }

	  class OpenL implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	      JFileChooser c = new JFileChooser();
	      // Demonstrate "Open" dialog:
	      int rVal = c.showOpenDialog(P2_FileChooser.this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        filename.setText(c.getSelectedFile().getName());
	        dir.setText(c.getCurrentDirectory().toString());
	        setVisible(false);
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	        filename.setText("Cancelled");
	        dir.setText("");
	      }
	    }
	  }

	  public void run(int width, int height) {
	    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	    this.setSize(width, height);
	    this.setVisible(true);
	  }
	  
	  public String getFile() {
		  return dir.getText() + '\\' + filename.getText();
	  }//End getFile
	  
}
