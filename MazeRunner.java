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

		final Game mazeGame = new Game(N, 33); // wall occurrence %
		AI solver = new AI(mazeGame.getMap(), N);
		System.out.println("Solution: " + solver.aStar());

		frame.add(mazeGame);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		mazeGame.requestFocusInWindow();
	}
}