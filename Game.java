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
	private int[][] map;
	private int[][] initialMap;
	private int size;
	private int playerX;
	private int playerY;
	private int goalX;
	private int goalY;
	private int initPlayerX;
	private int initPlayerY;
	private int initGoalX;
	private int initGoalY;
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

	public Game(int size, int threshold){
		this.setLayout(null);
		this.size = size;
		this.block = 600 / size;
		this.map = new int[size][size];
		this.initialMap = new int[size][size];
		Random rand = new Random();
		int piece;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				piece = rand.nextInt((int) 100 / threshold);
				this.map[i][j] = (piece == W)? W : U;
				this.initialMap[i][j] = (piece == W)? W : U;
			}
		}
		// int pos1 = 90;
		int pos1 = rand.nextInt(size * size);
		this.playerX = pos1 % size;
		this.playerY = pos1 / size;
		this.initPlayerX = this.playerX;
		this.initPlayerY = this.playerY;
		this.map[(int) pos1 / size][(int) pos1 % size] = P;
		this.initialMap[(int) pos1 / size][(int) pos1 % size] = P;
		int pos2 = rand.nextInt(size * size);
		while(pos2 == pos1){
			pos2 = rand.nextInt(size * size);
		}
		this.goalX = pos2 % size;
		this.goalY = pos2 / size;
		this.initGoalX = this.goalX;
		this.initGoalY = this.goalY;
		this.map[(int) pos2 / size][(int) pos2 % size] = G;
		this.initialMap[(int) pos2 / size][(int) pos2 % size] = G;
		// this.printMap();

		Game game = this;
		game.requestFocus();
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
		this.add(button1);
		this.add(button2);

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
				this.goalTest();
			}
		} else if(direction == DOWN){
			// System.out.println("this: " + this.playerY + " : " + this.size);
			if(this.playerY < this.size - 1 && this.map[this.playerY + 1][this.playerX] != W){
				this.map[this.playerY + 1][this.playerX] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerY++;
				this.goalTest();
			}
		} else if(direction == LEFT){
			if(this.playerX > 0 && this.map[this.playerY][this.playerX - 1] != W){
				this.map[this.playerY][this.playerX - 1] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerX--;
				this.goalTest();
			}
		} else  if(direction == RIGHT){
			if(this.playerX < this.size - 1 && this.map[this.playerY][this.playerX + 1] != W){
				this.map[this.playerY][this.playerX + 1] = P;
				this.map[this.playerY][this.playerX] = V;
				this.playerX++;
				this.goalTest();
			}
		}
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