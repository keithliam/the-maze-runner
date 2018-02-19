package themazerunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.PrintWriter;
import javax.imageio.ImageIO;

public class MazeRunner {
	private Game game;
	private AI solver;
	private int mapSize;
	private JFrame frame;

	public MazeRunner(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter map size: ");
		int N = sc.nextInt();

		JFrame frame = new JFrame();
		frame.setTitle("Maze Runner");
		frame.setPreferredSize(new Dimension(600, 682));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		this.frame = frame;
		final Container container = frame.getContentPane();

		this.mapSize = N;
		this.game = new Game(N, 33, this, "", null); // wall occurrence %

		frame.add(this.game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.game.requestFocusInWindow();
	}

	public void solve(){
		this.solver = new AI(this.game.getMap(), this.mapSize);
		String solution = this.solver.aStar();
		System.out.println("Solution: " + solution);
		Solution solutionWindow;
		if(solution.charAt(0) == 'U' || solution.charAt(0) == 'L' || solution.charAt(0) == 'D' || solution.charAt(0) == 'R'){
			int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
			int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
			int width = this.frame.getWidth();
			int height = this.frame.getHeight();
			int north = (screenHeight - height) / 2;
			int east = (screenWidth / 2) - width;
			this.frame.setLocation(east, north);
			solutionWindow = new Solution(this.mapSize, this.game.getMap(), solution);
		}
		this.outputFile(solution);
	}

	public void outputFile(String solution){
		try{
			if(solution.charAt(0) == 'U' || solution.charAt(0) == 'L' || solution.charAt(0) == 'D' || solution.charAt(0) == 'R'){
				solution = solution.replace("", " ").trim();
			}
			PrintWriter writer = new PrintWriter("maze.out", "UTF-8");
			writer.println(solution);
			writer.close();
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}