package themazerunner;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;

import java.util.Random;

public class Game extends JPanel {
	private MazeRunner frame;
	private int[][] map;
	private int[][] initialMap;
	private int size, threshold;
	private int playerX;
	private int playerY;
	private int goalX;
	private int goalY;
	private int initPlayerX;
	private int initPlayerY;
	private int initGoalX;
	private int initGoalY;
	private int index = 0, nextMove;
	private char character;
	private String solution;
	private final int block;
	public final static int W = 0;
	public final static int U = 1;
	public final static int P = 2;
	public final static int G = 3;
	public final static int V = 4;
	public final static int UP = 1;
	public final static int LEFT = 2;
	public final static int DOWN = 3;
	public final static int RIGHT = 4;

	public Game(int size, int threshold, MazeRunner frame, String solution, int[][] map){
		this.frame = frame;
		this.solution = solution;
		this.setLayout(null);
		this.threshold = threshold;
		this.size = size;
		this.block = 600 / size;
		if(solution.equals("")) this.randomizeMap();
		// this.printMap();

		Game game = this;

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

		if(!solution.equals("")){
			this.map = new int[size][size];
			this.initialMap = new int[size][size];
			for(int i = 0; i < size; i++){
				System.arraycopy(map[i], 0, this.map[i], 0, size);
				System.arraycopy(map[i], 0, this.initialMap[i], 0 , size);
			}

			for(int i = 0; i < this.size; i++){
				for(int j = 0; j < this.size; j++){
					if(this.map[i][j] == P){
						this.playerX = j;
						this.playerY = i;
						this.initPlayerX = j;
						this.initPlayerY = i;
					} else if(this.map[i][j] == G){
						this.goalX = j;
						this.goalY = i;
						this.initGoalX = j;
						this.initGoalY = i;
					}
				}
			}
			JButton button1 = new JButton();
			JButton button2 = new JButton();
			ImageIcon left = new ImageIcon("img/left.png");
			ImageIcon right = new ImageIcon("img/right.png");

			Game sol = this;

			button1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(sol.index > 0){
						sol.index--;
						sol.restart();
						for(int i = 0; i < sol.index; i++){
							sol.character = solution.charAt(i);
							sol.nextMove = (sol.character == 'U')? 1 : (sol.character == 'L')? 2 : (sol.character == 'D')? 3 : 4;
							sol.move(sol.nextMove);
						}
					}
				}
			});

			button2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(sol.index < solution.length()){
						sol.character = solution.charAt(sol.index);
						sol.nextMove = (sol.character == 'U')? 1 : (sol.character == 'L')? 2 : (sol.character == 'D')? 3 : 4;
						sol.move(sol.nextMove);
						sol.index++;
					}
				}
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
			this.add(button1);
			this.add(button2);
		} else {
			
			this.requestFocus();
			this.addKeyListener(new KeyListener(){
				public void keyPressed(KeyEvent e){
					if(e.getKeyCode() == KeyEvent.VK_UP){
						game.move(UP);
					} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
						game.move(LEFT);
					} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
						game.move(DOWN);
					} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
						game.move(RIGHT);
					}
				}
				public void keyTyped(KeyEvent e){}
				public void keyReleased(KeyEvent e){}
			});
			ImageIcon buttonIcon = new ImageIcon("img/game_button.png");
			JButton button3 = new JButton("Reset");
			button3.setIcon(buttonIcon);
			button3.setBounds(120, 607, 144, 46);
			this.add(button3);
			button3.setContentAreaFilled(false);
			button3.setFocusPainted(false);
			button3.setBorderPainted(false);

			JButton button4 = new JButton("Solve");
			button4.setIcon(buttonIcon);
			button4.setBounds(360, 607, 144, 46);
			this.add(button4);
			button4.setContentAreaFilled(false);
			button4.setFocusPainted(false);
			button4.setBorderPainted(false);

			JLabel button_label1 = new JLabel();
			button_label1.setIcon(new ImageIcon("img/reset_label.png"));
			button3.add(button_label1);
			JLabel button_label2 = new JLabel();
			button_label2.setIcon(new ImageIcon("img/solve_maze_label.png"));
			button4.add(button_label2);

			button3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					game.randomizeMap();
					game.repaint();
					game.requestFocus();
				}
			});

			button4.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					game.frame.solve();
					game.requestFocus();
				}
			});
		}
		this.add(bottomPanel1);
		this.add(bottomPanel2);
		this.add(bottomPanel3);
		this.add(bottomPanel4);
		this.add(bottomPanel5);
		this.add(bottomPanel6);
		this.add(bottomPanel7);
		this.add(bottomPanel8);
		this.add(bottomPanel9);
		this.add(bottomPanel10);
	}

	public void restart(){
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				this.map[i][j] = this.initialMap[i][j];
			}
		}
		this.playerX = this.initPlayerX;
		this.playerY = this.initPlayerY;
		this.goalX = this.initGoalX;
		this.goalY = this.initGoalY;
		this.repaint();
	}

	public int[][] getMap(){
		return this.map;
	}

	public void move(int direction){
		this.repaint();
		if(direction == UP){
			if(this.playerY > 0 && this.map[this.playerY - 1][this.playerX] != W){
				this.map[this.playerY - 1][this.playerX] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerY--;
				if(this.solution.equals("")) this.goalTest();
			}
		} else if(direction == DOWN){
			// System.out.println("this: " + this.playerY + " : " + this.size);
			if(this.playerY < this.size - 1 && this.map[this.playerY + 1][this.playerX] != W){
				this.map[this.playerY + 1][this.playerX] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerY++;
				if(this.solution.equals("")) this.goalTest();
			}
		} else if(direction == LEFT){
			if(this.playerX > 0 && this.map[this.playerY][this.playerX - 1] != W){
				this.map[this.playerY][this.playerX - 1] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerX--;
				if(this.solution.equals("")) this.goalTest();
			}
		} else  if(direction == RIGHT){
			if(this.playerX < this.size - 1 && this.map[this.playerY][this.playerX + 1] != W){
				this.map[this.playerY][this.playerX + 1] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerX++;
				if(this.solution.equals("")) this.goalTest();
			}
		}
	}

	private void randomizeMap(){
		this.map = new int[this.size][this.size];
		this.initialMap = new int[this.size][this.size];
		Random rand = new Random();
		int piece;
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				piece = rand.nextInt((int) 100 / this.threshold);
				this.map[i][j] = (piece == W)? W : U;
				this.initialMap[i][j] = (piece == W)? W : U;
			}
		}

		int pos1 = rand.nextInt(this.size * this.size);
		this.playerX = pos1 % this.size;
		this.playerY = pos1 / this.size;
		this.initPlayerX = this.playerX;
		this.initPlayerY = this.playerY;
		this.map[(int) pos1 / this.size][(int) pos1 % this.size] = P;
		this.initialMap[(int) pos1 / this.size][(int) pos1 % this.size] = P;
		int pos2 = rand.nextInt(this.size * this.size);
		while(pos2 == pos1){
			pos2 = rand.nextInt(this.size * this.size);
		}
		this.goalX = pos2 % this.size;
		this.goalY = pos2 / this.size;
		this.initGoalX = this.goalX;
		this.initGoalY = this.goalY;
		this.map[(int) pos2 / this.size][(int) pos2 % this.size] = G;
		this.initialMap[(int) pos2 / this.size][(int) pos2 % this.size] = G;
	}

	private void goalTest(){
		if(this.playerX == this.goalX && this.playerY == this.goalY){
			for(int i = 0; i < this.size; i++){
				System.arraycopy(this.initialMap[i], 0, this.map[i], 0, this.size);
			}
			this.playerX = this.initPlayerX;
			this.playerY = this.initPlayerY;
			this.goalX = this.initGoalX;
			this.goalY = this.initGoalY;
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				if(this.map[i][j] == W){
					g.setColor(Color.BLACK);
					g.fillRect(j * this.block, i * this.block, this.block, this.block);
				} else if(this.map[i][j] == U){
					g.setColor(Color.WHITE);
					g.fillRect(j * this.block, i * this.block, this.block, this.block);
				} else if(this.map[i][j] == V){
					g.setColor(Color.GREEN);
					g.fillRect(j * this.block, i * this.block, this.block, this.block);
				} else if(this.map[i][j] == P){
					g.setColor(Color.BLUE);
					g.fillRect(j * this.block, i * this.block, this.block, this.block);
				} else if(this.map[i][j] == G){
					g.setColor(Color.RED);
					g.fillRect(j * this.block, i * this.block, this.block, this.block);
				}
			}
		}
		// this.printMap();
	}

	public void printMap(){
		System.out.println();
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j< this.size; j++){
				System.out.print(this.map[i][j] + " ");
			}
			System.out.println();
		}
	}
}