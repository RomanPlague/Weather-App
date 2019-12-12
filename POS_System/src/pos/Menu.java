 package pos;

import java.awt.*;
import javax.swing.*;

import pos.Menu;

import java.awt.event.*;
import java.io.*;


public class Menu {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int width=(int) screenSize.getWidth();
	static int height=(int) screenSize.getHeight();
	static final JFrame frame = new JFrame();
	JPanel buttonPanel;
	JButton button;
	static int buttonWidth=80;
	static int buttonHeight=30;
	static int frameWidth=buttonWidth+10;
	static int frameHeight=buttonHeight+10;
	static Color w = new Color(255,255,255,255);
	static Color b = new Color(0,0,0,255);
	static Color g = new Color(150,150,150,255);
	
	public static void frme() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(g);
    frame.setLayout(new BorderLayout());
	//frame.setContentPane(new JLabel(new ImageIcon(startPicture)));
    frame.setSize(width,height);
    frame.setResizable(false);
    frame.setTitle("Austin's Burgers and Fries");
    frame.setVisible(true);
    
    
    JPanel outputLabelPanel = new JPanel();
    outputLabelPanel.setBounds(10,15,width/5,20);
    outputLabelPanel.setBackground(w);
    JLabel orderLabel = new JLabel();
    orderLabel.setForeground(b);
    orderLabel.setBackground(w);
    orderLabel.setText("<html>Order:</html>");
    outputLabelPanel.add(orderLabel);
    
    JPanel outputPanel = new JPanel();
    outputPanel.setBounds(10,30,width/5,height-150);
    outputPanel.setBackground(w);
    
    JTextArea outputBox=new JTextArea();
    outputBox.setSize(10,10);
    outputBox.setText("------------------------------------------------------------\n");
    outputBox.setForeground(b);
    outputBox.setEditable(false);
    outputPanel.add(outputBox);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBounds(width/5+15,50,width*4/5-35,height-110);
    buttonPanel.setBackground(w);
    GridLayout buttonLayout = new GridLayout(0,3);
    buttonLayout.setHgap(buttonWidth/2);
    buttonLayout.setVgap(buttonHeight);
    buttonPanel.setLayout(buttonLayout);
    
    JPanel removeButtonPanel = new JPanel();
    removeButtonPanel.setBounds(10,height-100,buttonWidth,buttonHeight);
    removeButtonPanel.setBackground(w);
    
    JButton removeButton = new JButton();
    removeButton.setForeground(b);
    removeButton.setText("<html><center>Remove</center></html>");
    removeButton.setBackground(w);
    removeButtonPanel.add(removeButton);
    
    
    
    JButton startButton = new JButton();
    startButton.setForeground(b);
    //startButton.setFont(buttonFont);
    startButton.setText("<html><center>Burger</center></html>");
    startButton.setBackground(w);
    //startButton.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
    //startButton.setFocusPainted(true);
    startButton.addActionListener(new ActionListener()
    {
			public void actionPerformed(ActionEvent click) {
				outputBox.append("Burger\n");
			}
		}); 
    
    JPanel emptyPanel = new JPanel();
    emptyPanel.setBounds(width,height,0,0);
       
    
    buttonPanel.add(new JButton("Button 1"));
    buttonPanel.add(new JButton("Button 2"));
    buttonPanel.add(new JButton("Button 3"));
    buttonPanel.add(new JButton("Button 4"));
    buttonPanel.add(new JButton("Button 5"));
    buttonPanel.add(new JButton("Button 6"));
    buttonPanel.add(new JButton("Button 7"));
    buttonPanel.add(new JButton("Button 8"));
    buttonPanel.add(new JButton("Button 9"));
    buttonPanel.add(new JButton("Button 10"));
    buttonPanel.add(new JButton("Button 11"));
    buttonPanel.add(new JButton("Button 12"));
    buttonPanel.add(new JButton("Button 13"));
    buttonPanel.add(new JButton("Button 14"));
    buttonPanel.add(new JButton("Button 15"));
    buttonPanel.add(new JButton("Button 16"));
    buttonPanel.add(new JButton("Button 17"));
    buttonPanel.add(new JButton("Button 18"));
    buttonPanel.add(new JButton("Button 19"));
    buttonPanel.add(new JButton("Button 20"));
    buttonPanel.add(startButton);
    
    frame.add(outputLabelPanel);
    frame.add(outputPanel);
    frame.add(removeButtonPanel);
    frame.add(buttonPanel);
    frame.add(emptyPanel);
	}
	
	
	public static void main(String... args) {
		Menu.frme();
	    frame.revalidate();
		frame.repaint();
	  }
}