package themazerunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Pattern;

public class AI {
	private String initMap = new String();
	private ArrayList<String> minHeap = new ArrayList<String>();
	private int size;

	public final static int W = 0;
	public final static int U = 1;
	public final static int P = 2;
	public final static int G = 3;
	public final static int V = 4;
	public final static int UP = 1;
	public final static int LEFT = 2;
	public final static int DOWN = 3;
	public final static int RIGHT = 4;

	public AI(int[][] map, int size){
		int playerX = 0, playerY = 0, goalX = 0, goalY = 0;
		for(int i = 0; i < size * size; i++){
			this.initMap = this.initMap + Integer.toString(map[(int) i / size][i % size]);
			if(map[(int) i / size][i % size] == P){
				playerY = i / size;
				playerX = i % size;
			} else if(map[(int) i / size][i % size] == G){
				goalY = i / size;
				goalX = i % size;
			}
		}
		this.size = size;
		this.initMap = this.initMap + '|' + Float.toString(this.getDistance(playerX, playerY, goalX, goalY)) + '&';
		this.addToHeap(this.initMap);
		// System.out.println(this.actions(this.initMap));
		// this.printMap(this.result(this.initMap, UP));
		// this.addToHeap(this.result(this.initMap, RIGHT));
			this.addToHeap(this.result(this.initMap, RIGHT));
			this.addToHeap(this.result(this.result(this.initMap, RIGHT), UP));
		// this.addToHeap(this.result(this.initMap, DOWN));
		this.addToHeap(this.result(this.result(this.result(this.result(this.initMap, RIGHT), UP), RIGHT), UP));
		this.addToHeap(this.result(this.result(this.result(this.result(this.result(this.initMap, RIGHT), UP), RIGHT), UP), LEFT));
		this.addToHeap(this.result(this.result(this.result(this.result(this.result(this.result(this.initMap, RIGHT), UP), RIGHT), UP), LEFT), LEFT));
		this.addToHeap(this.result(this.result(this.result(this.result(this.result(this.result(this.result(this.initMap, RIGHT), UP), RIGHT), UP), LEFT), LEFT), DOWN));
		// this.addToHeap(this.result(this.result(this.result(this.initMap, RIGHT), UP), RIGHT));
		// this.addToHeap(this.result(this.initMap, UP));
		// this.addToHeap(this.result(this.initMap, LEFT));
		// this.addToHeap(this.result(this.initMap, LEFT));
		// this.addToHeap(this.result(this.initMap, DOWN));
		// this.addToHeap(this.result(this.initMap, RIGHT));
		// this.printMap(this.minHeap.get(0));
		// this.printMap(this.minHeap.get(1));
		// this.printMap(this.minHeap.get(2));
		System.out.println(this.minHeap);
		// this.printMap(this.getMin());
		// System.out.println(this.minHeap);
		// this.printMap(this.getMin());
		// System.out.println(this.minHeap);
		// this.printMap(this.getMin());
		// System.out.println(this.minHeap);
		// this.printMap(this.getMin());
		// System.out.println(this.minHeap);

	}

	// public String aStar()

	private void printMap(String map){
		System.out.println("\nPRINTED MAP");
		for(int i = 0; i < this.size * this.size; i++){
			// System.out.println("this");
			if(i % this.size == 0 && i != 0) System.out.println();
			System.out.print(map.charAt(i) + " ");
		}
		System.out.println();
		System.out.println("\nH: " + this.getH(map));
	}

	private float getH(String map){
		return Float.parseFloat(map.substring(map.indexOf('|') + 1, map.indexOf('&')));
	}

	private int getParentIndex(int index){
		return (int) (index / 2);
	}

	private int getLeftChildIndex(int index){
		return index * 2;
	}

	private int getRightChildIndex(int index){
		return (index * 2) + 1;
	}

	private void swap(int index, int parentIndex){
		String temp = this.minHeap.get(parentIndex);
		// System.out.println("Index: " + index);
		this.minHeap.set(parentIndex, this.minHeap.get(index));
		this.minHeap.set(index, temp);
	}

	private void addToHeap(String map){
		// System.out.println("dsf");
		this.minHeap.add(map);
		System.out.println("THIS: " + map);
		float value = this.getH(map);
		int index = this.minHeap.size() - 1;
		// System.out.println("----------------------");
		// System.out.println(this.minHeap);
		// System.out.println("IF " + index + " != 0 && " + value + " < " + this.getH(this.minHeap.get(this.getParentIndex(index))));
		while(index != 0 && value < this.getH(this.minHeap.get(this.getParentIndex(index)))){
			// System.out.println("LOOPEDT");
			this.swap(index, this.getParentIndex(index));
			index = this.getParentIndex(index);
		}
		// System.out.println(this.minHeap)7;
	}

	private String getMin(){
		String temp = this.minHeap.get(0);
		this.minHeap.set(0, this.minHeap.get(this.minHeap.size() - 1));
		this.minHeap.remove(this.minHeap.size() - 1);
		float value = this.getH(this.minHeap.get(0));
		int index = 0, size = this.minHeap.size();
		int leftChildIndex = this.getLeftChildIndex(0);
		int rightChildIndex = this.getRightChildIndex(0);
		float leftH = (leftChildIndex < size)? this.getH(this.minHeap.get(leftChildIndex)) : -1;
		float rightH = (rightChildIndex < size)? this.getH(this.minHeap.get(rightChildIndex)) : -1;
		while((leftChildIndex < size || (rightChildIndex < size)) && ((leftChildIndex != -1 && leftH < value) || (rightChildIndex != -1 && rightH < value))){
			if(leftChildIndex != -1 && leftChildIndex < size && leftH < value && leftH < rightH){
				this.swap(index, leftChildIndex);
				index = leftChildIndex;
			} else if(rightChildIndex != -1 && rightChildIndex < size && rightH < value && rightH < leftH){
				this.swap(index, rightChildIndex);
				index = rightChildIndex;
			}
			leftChildIndex = this.getLeftChildIndex(index);
			rightChildIndex = this.getRightChildIndex(index);
			leftH = (leftChildIndex < size && leftChildIndex > 0)? this.getH(this.minHeap.get(leftChildIndex)) : -1;
			rightH = (rightChildIndex < size && rightChildIndex > 0)? this.getH(this.minHeap.get(rightChildIndex)) : -1;
		}
		return temp;
	}

	private float getDistance(int x, int y, int a, int b){
		return (float) Math.sqrt(((x - a) * (x - a)) + ((y - b) * (y - b)));
	}

	private char getChar(String map, int x, int y){
		return map.charAt((this.size * y) + x);
	}

	private Queue<Integer> actions(String map){
		Queue<Integer> moves = new LinkedList<Integer>();
		int index = map.indexOf('2');
		int playerX = index % this.size;
		int playerY = index / this.size;
		if(playerY > 0 && this.getChar(map, playerX, playerY - 1) != '0') 				moves.add(UP);
		if(playerY < this.size - 1 && this.getChar(map, playerX, playerY + 1) != '0') 	moves.add(DOWN);
		if(playerX > 0 && this.getChar(map, playerX - 1, playerY) != '0') 				moves.add(LEFT);
		if(playerX < this.size - 1 && this.getChar(map, playerX + 1, playerY) != '0')	moves.add(RIGHT);
		return moves;
	}

	private boolean goalTest(String map){
		return (map.indexOf('3') == -1)? true : false;
	}

	private String result(String map, int direction){
		String moves = map.substring(map.indexOf('&'));
		int index = map.indexOf('2');
		int goalIndex = map.indexOf('3');
		int playerX = index % this.size;
		int playerY = index / this.size;
		int goalX = goalIndex % this.size;
		int goalY = goalIndex / this.size;
		String newMap = new String(map);
		newMap = newMap.substring(0, (this.size * (playerY)) + playerX) + '4' + newMap.substring((this.size * (playerY)) + playerX + 1);
		if(direction == UP) playerY--;
		else if(direction == DOWN) playerY++;
		else if(direction == LEFT) playerX--;
		else playerX++;
		newMap = newMap.substring(0, (this.size * (playerY)) + playerX) + '2' + newMap.substring((this.size * (playerY)) + playerX + 1);
		newMap = newMap.substring(0, newMap.indexOf('|') + 1) + Float.toString(this.getDistance(playerX, playerY, goalX, goalY)) + newMap.substring(newMap.indexOf('&'));
		if(direction == UP) newMap = newMap + "U";
		else if(direction == LEFT) newMap = newMap + "L";
		else if(direction == DOWN) newMap = newMap + "D";
		else if(direction == RIGHT) newMap = newMap + "R";
		return newMap;
	}
}