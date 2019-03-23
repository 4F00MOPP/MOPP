import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class P2_ImageDisplayer extends JFrame implements ActionListener {
	
	
	//Constructor
	public P2_ImageDisplayer (int w, int h) {
		/*this.setPreferredSize(new Dimension(w, h));
		this.setTitle("Image Displayer");
		this.setBounds(100, 100, 550, 400);
		
	    JPanel contentPane = new JPanel();
	    contentPane.setBorder(new LineBorder(Color.black));
	    contentPane.setLayout(new BorderLayout(0,0));*/
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        contentPane.add(panel, BorderLayout.WEST);

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseButtonActionPerformed(e);
            }
        });

        JLabel lblSelectTargetPicture = new JLabel("Select target picture..");

        JButton btnComp = new JButton("Compress");
        btnComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JButton btnUnComp = new JButton("Uncompress");
        btnUnComp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(6)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addComponent(lblSelectTargetPicture)
                            .addGap(6)
                            .addComponent(btnBrowse))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(10)
                            .addComponent(btnComp)
                            .addGap(18)
                            .addComponent(btnUnComp))))
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(7)
                            .addComponent(lblSelectTargetPicture))
                        .addGroup(gl_panel.createSequentialGroup()
                            .addGap(3)
                            .addComponent(btnBrowse)))
                    .addGap(18)
                    .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
                    .addGap(22)
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnComp)
                        .addComponent(btnUnComp))
                    .addGap(18)
                    .addContainerGap())
        );

        panel.setLayout(gl_panel);
        
        btnComp.setEnabled(false);
        btnUnComp.setEnabled(false);
	    
	    /*contentPane.add(button);
	    this.getContentPane().add(contentPane);
	    button.addActionListener(this);
	    
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);*/
        this.setVisible(true);
	}//End constructor
	
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        //fc.setFileFilter(new JPEGImageFileFilter());
        int res = fc.showOpenDialog(null);
        // We have an image!
        try {
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //setTarget(file);
            } // Oops!
            else {
                //JOptionPane.showMessageDialog(null,
                        //"You must select one image to be the reference.", "Aborting...",
                        //JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception iOException) {
        }

    }
	
}
