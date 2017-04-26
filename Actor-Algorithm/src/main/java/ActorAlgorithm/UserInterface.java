package main.java.ActorAlgorithm;

import javax.json.JsonException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface extends JFrame implements ActionListener{
	static Scanner key = new Scanner(System.in);
	static String actor1;
	static String actor2;
	Boolean run = false;
	JTextField input1, input2;
	JLabel info1,info2;
	JButton enter;
	public UserInterface(){
		setTitle("Actor Algorithm");
		setLayout(null);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setResizable(false);
//		setSize(400, 400);
//		setVisible(true);
		
		info1 = new JLabel("Enter Actor A's name");
		info1.setBounds(0, 20, 200, 20);
		add(info1);
		
		input1 = new JTextField(5);
		input1.setBounds(200,20,100,20);
		add(input1); 
		
		info2 = new JLabel("Enter Actor B's name");
		info2.setBounds(0, 50, 200, 20);
		add(info2);
		
		input2 = new JTextField(5);
		input2.setBounds(200,50,100,20);
		add(input2); 
		
		enter = new JButton("Enter");
		enter.addActionListener(this);
		enter.setBounds(200, 90, 100, 20);
		add(enter);
	

	}
	public static void main(String[] args) {
//		UserInterface ui = new UserInterface();
//		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		ui.setResizable(false);
//		ui.setSize(400, 400);
//		ui.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		run = true;
		actor1 = input1.getText();
		actor2 = input2.getText();
		//System.out.println(actor1 + actor2);
		ActorAlgorithm algorithm = new ActorAlgorithm();
		try {
			algorithm.init(actor1, actor2);
		} catch (JsonException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

