package themazerunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class MazeRunner {
	public MazeRunner(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter map size: ");
		int N = sc.nextInt();

		JFrame frame = new JFrame();
		frame.setTitle("Solution");
		frame.setPreferredSize(new Dimension(600, 682));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		final Container container = frame.getContentPane();

		final Game mazeGame = new Game(N);

		ImageIcon icon1 = new ImageIcon("img/tiles.png");
		JLabel bottomPanel1 = new JLabel();
		JLabel bottomPanel2 = new JLabel();
		JLabel bottomPanel3 = new JLabel();
		JLabel bottomPanel4 = new JLabel();
		JLabel bottomPanel5 = new JLabel();
		JLabel bottomPanel6 = new JLabel();
		JLabel bottomPanel7 = new JLabel();
		JLabel bottomPanel8 = new JLabel();
		JLabel bottomPanel9 = new JLabel();
		JLabel bottomPanel10 = new JLabel();
		bottomPanel1.setBounds(0, 600, 60, 60);
		bottomPanel2.setBounds(60, 600, 60, 60);
		bottomPanel3.setBounds(120, 600, 60, 60);
		bottomPanel4.setBounds(180, 600, 60, 60);
		bottomPanel5.setBounds(240, 600, 60, 60);
		bottomPanel6.setBounds(300, 600, 60, 60);
		bottomPanel7.setBounds(360, 600, 60, 60);
		bottomPanel8.setBounds(420, 600, 60, 60);
		bottomPanel9.setBounds(480, 600, 60, 60);
		bottomPanel10.setBounds(540, 600, 60, 60);
		bottomPanel1.setIcon(icon1);
		bottomPanel2.setIcon(icon1);
		bottomPanel3.setIcon(icon1);
		bottomPanel4.setIcon(icon1);
		bottomPanel5.setIcon(icon1);
		bottomPanel6.setIcon(icon1);
		bottomPanel7.setIcon(icon1);
		bottomPanel8.setIcon(icon1);
		bottomPanel9.setIcon(icon1);
		bottomPanel10.setIcon(icon1);

		JButton button1 = new JButton();
		JButton button2 = new JButton();
		ImageIcon left = new ImageIcon("img/left.png");
		ImageIcon right = new ImageIcon("img/right.png");

		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){}
		});

		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){}
		});

		button1.setBounds(250, 610, 40, 40);
		button2.setBounds(310, 610, 40, 40);
		button1.setIcon(left);
		button2.setIcon(right);
		button1.setContentAreaFilled(false);
		button1.setFocusPainted(false);
		button1.setBorderPainted(false);
		button2.setContentAreaFilled(false);
		button2.setFocusPainted(false);
		button2.setBorderPainted(false);
		frame.add(button1);
		frame.add(button2);

		frame.add(bottomPanel1);
		frame.add(bottomPanel2);
		frame.add(bottomPanel3);
		frame.add(bottomPanel4);
		frame.add(bottomPanel5);
		frame.add(bottomPanel6);
		frame.add(bottomPanel7);
		frame.add(bottomPanel8);
		frame.add(bottomPanel9);
		frame.add(bottomPanel10);
		container.add(mazeGame);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		mazeGame.requestFocusInWindow();
	}
}