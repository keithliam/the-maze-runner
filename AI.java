package themazerunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class AI {
	private String initMap = new String();
	private HashMap<Integer, String> minHeap = new HashMap<Integer, String>();
	private HashMap<String, Integer> minHeapRev = new HashMap<String, Integer>();
	private HashMap<String, String> moves = new HashMap<String, String>();
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
	}

	public void printQueue(){
		String map;
		for(int i = 1; i <= this.minHeap.size(); i++){
			map = this.minHeap.get(i);
			System.out.println(i + ": " + this.moves.get(map));
		}
	}

	public String aStar(){
		HashMap<String, String> closedList = new HashMap<String, String>();
		String s, e, x, y = this.initMap.substring(0, this.initMap.indexOf('|'));
		int g;
		boolean isFrontier, isExplored;
		this.minHeap.put(1, y);
		this.moves.put(y, this.initMap.substring(this.initMap.indexOf('|') + 1));
		while(this.minHeap.size() != 0){
			s = this.getMin();
			closedList.put(s.substring(0, s.indexOf('|')), s.substring(s.indexOf('|') + 1));
			if(this.goalTest(s)) return this.getPath(s);
			for(int a : this.actions(s)){
				x = result(s, a);
				g = x.length() - x.indexOf('&') - 1;
				e = x.substring(0, x.indexOf('|'));
				isFrontier = this.minHeap.containsValue(e);
				isExplored = closedList.containsKey(e);
				if(!isFrontier && !isExplored) this.addToHeap(x, false);
				if(isFrontier){
					y = this.moves.get(e);
					if(g < y.length() - y.indexOf('&') - 1){
						if(!this.minHeapRev.containsKey(x)) return "none";
						this.addToHeap(x, true);
					}
				} else if(isExplored){
					y = closedList.get(e);
					if(g < y.length() - y.indexOf('&') - 1){
						this.addToHeap(x, false);
					}
				}
			}
		}
		return "none";
	}

	private void printMap(String map){
		System.out.println("\nPRINTED MAP");
		for(int i = 0; i < this.size * this.size; i++){
			if(i % this.size == 0 && i != 0) System.out.println();
			System.out.print(map.charAt(i) + " ");
		}
		System.out.println();
		System.out.println("\nH: " + this.findF(map));
	}

	private float findF(String map){
		return Float.parseFloat(map.substring(map.indexOf('|') + 1, map.indexOf('&')));
	}

	private float getF(String map){
		String temp = this.moves.get(map);
		return Float.parseFloat(temp.substring(0, temp.indexOf('&')));
	}

	private String getPath(String map){
		return map.substring(map.indexOf('&') + 1);
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
		String temp = this.minHeap.get(index);
		this.minHeapRev.put(this.minHeap.get(parentIndex), index);
		this.minHeap.put(index, this.minHeap.get(parentIndex));
		this.minHeapRev.put(temp, parentIndex);
		this.minHeap.put(parentIndex, temp);
	}

	private void addToHeap(String map, boolean isDuplicate){
		String temp = map.substring(0, map.indexOf('|'));

		if(!isDuplicate){
			this.minHeap.put(this.minHeap.size() + 1, temp);
			this.minHeapRev.put(temp, this.minHeap.size());
		}
		
		this.moves.put(temp, map.substring(map.indexOf('|') + 1, map.length()));
		float value = this.findF(map);
		int index;
		if(isDuplicate){
			index = this.minHeapRev.get(map);
		} else {
			index = this.minHeap.size();
		}

		while(this.getParentIndex(index) != 0 && value < this.getF(this.minHeap.get(this.getParentIndex(index)))){
			this.swap(index, this.getParentIndex(index));
			index = this.getParentIndex(index);
		}
	}

	private String getMin(){
		if(this.minHeap.size() != 0){
			String temp = this.minHeap.get(1);
			temp = temp + '|' + this.moves.get(temp);
			this.swap(1, this.minHeap.size());
			float value = this.getF(this.minHeap.get(1));
			String toRemove = this.minHeap.remove(this.minHeap.size());
			this.moves.remove(toRemove);
			this.minHeapRev.remove(toRemove);
			int index = 1, size = this.minHeap.size();
			int leftChildIndex = this.getLeftChildIndex(1);
			int rightChildIndex = this.getRightChildIndex(1);
			float leftH = (leftChildIndex <= size)? this.getF(this.minHeap.get(leftChildIndex)) : -1;
			float rightH = (rightChildIndex <= size)? this.getF(this.minHeap.get(rightChildIndex)) : -1;
			
			while((leftChildIndex < size || (rightChildIndex < size)) && ((leftChildIndex != -1 && leftH < value) || (rightChildIndex != -1 && rightH < value))){
				if(leftChildIndex != -1 && leftChildIndex < size && leftH < value && leftH < rightH){
					this.swap(index, leftChildIndex);
					index = leftChildIndex;
				} else if(rightChildIndex != -1 && rightChildIndex < size && rightH < value && rightH < leftH){
					this.swap(index, rightChildIndex);
					index = rightChildIndex;
				} else {
					break;
				}
				leftChildIndex = this.getLeftChildIndex(index);
				rightChildIndex = this.getRightChildIndex(index);
				leftH = (leftChildIndex <= size)? this.getF(this.minHeap.get(leftChildIndex)) : -1;
				rightH = (rightChildIndex <= size)? this.getF(this.minHeap.get(rightChildIndex)) : -1;
				// this.printQueue();
			}
			return temp;
		} else {
			return "none";
		}
	}

	private float getDistance(int x, int y, int a, int b){
		return (float) Math.sqrt(((x - a) * (x - a)) + ((y - b) * (y - b)));
	}

	private char getChar(String map, int x, int y){
		return map.charAt((this.size * y) + x);
	}

	private HashSet<Integer> actions(String map){
		HashSet<Integer> moves = new HashSet<Integer>();
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
		newMap = newMap.substring(0, newMap.indexOf('|') + 1) + Float.toString((float) (this.getDistance(playerX, playerY, goalX, goalY) + (newMap.substring(newMap.indexOf('&') + 1).length() * 0.1))) + newMap.substring(newMap.indexOf('&'));
		if(direction == UP) newMap = newMap + "U";
		else if(direction == LEFT) newMap = newMap + "L";
		else if(direction == DOWN) newMap = newMap + "D";
		else if(direction == RIGHT) newMap = newMap + "R";
		return newMap;
	}
}