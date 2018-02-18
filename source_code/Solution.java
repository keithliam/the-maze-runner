package themazerunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Solution {
	private int index = 0, move;
	private char character;

	public Solution(int size, int[][] map, String solution){
		JFrame frame = new JFrame();
		frame.setTitle("Solution");
		frame.setPreferredSize(new Dimension(600, 682));
		frame.setResizable(false);
		final Container container = frame.getContentPane();
		Game game = new Game(size, 0, null, solution, map); // wall occurrence %

		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int width = frame.getWidth();
		int east = screenWidth / 2;
		frame.setLocation(east, (screenHeight - 640) / 2);

		container.add(game);

		frame.pack();
		frame.setVisible(true);
	}
}