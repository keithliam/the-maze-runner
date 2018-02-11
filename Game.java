package themazerunner;

import javax.swing.JPanel;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.imageio.ImageIO;

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
	public final static int W = 1;
	public final static int U = 2;
	public final static int P = 3;
	public final static int G = 4;
	public final static int V = 5;
	public final static int UP = 1;
	public final static int LEFT = 2;
	public final static int DOWN = 3;
	public final static int RIGHT = 4;

	public Game(int size){
		this.setLayout(null);
		this.size = size;
		this.block = 600 / size;
		this.map = new int[size][size];
		this.initialMap = new int[size][size];
		Random rand = new Random();
		int piece;
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				piece = rand.nextInt(2);
				this.map[i][j] = piece;
				this.initialMap[i][j] = piece;
			}
		}
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
		this.printMap();

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
			System.out.println("this: " + this.playerY + " : " + this.size);
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
		this.printMap();
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