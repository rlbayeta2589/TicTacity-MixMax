/*
*	Bayeta Reynaldo III
*	CMSC 170 U3L
*/
import java.awt.*;												//libraries
import java.util.*;

class State{
	private int fff_total_dist;									//variable declarations
	private int gg_cover_dist;
	private int h_remain_dist;
	private int[][] curr;
	private String id = "";
	private State parent;										//state's parent
	private Point space;										//where the space is located
	private LinkedList<Point> actions = new LinkedList<Point>();//possible actions
	private static Point[] ACTUAL_POS = {
		new Point(0,0),new Point(0,1),new Point(0,2),			//actual position of the 
		new Point(1,0),new Point(1,1),new Point(1,2),			// tiles
		new Point(2,0),new Point(2,1),new Point(2,2)
	};

	public State(int[][] c,State parent){
		int x,y;
		this.curr = c;

		for (int i=0 ; i<9 ; i++ ){
			x = i/3;
			y = i%3;
			if(curr[x][y]==0){
				space = new Point(x,y);							//initialization of space
			}
			this.id += curr[x][y];								//id
		}

		this.parent = parent;
		gg_cover_dist = parent==null ? 0 : parent.getDistGGG() + 1;
		h_remain_dist = computeHHH();
		fff_total_dist = gg_cover_dist + h_remain_dist;

		x = (int)space.getX();
		y = (int)space.getY();
		if(y+1<3) actions.addLast(new Point(x,y+1)); 			//inserting all possible actions
		if(x+1<3) actions.addLast(new Point(x+1,y));
		if(x-1>=0) actions.addLast(new Point(x-1,y));
		if(y-1>=0) actions.addLast(new Point(x,y-1));

	}
	public int[][] getCurr(){									//getters
		return this.curr;
	}
	public String getId(){
		return this.id;
	}
	public State getParent(){
		return this.parent;
	}
	public Point getSpace(){
		return this.space;
	}
	public int getDistFFF(){
		return this.fff_total_dist;
	}
	public int getDistGGG(){
		return this.gg_cover_dist;
	}
	public int getDistHHH(){
		return this.h_remain_dist;
	}
	public LinkedList<Point> getActions(){
		return this.actions;
	}
	public State getNextState(int x, int y){					//get next state given the 
		int[][] next = curr.clone();							//	coordinate to be clicked
		int space_x, space_y, temp;

		for (int i=0 ; i<3 ; i++ ){
			next[i] = curr[i].clone();							//cloning of the int array
		}

		space_x = (int)space.getX();
		space_y = (int)space.getY();

		temp = next[space_x][space_y];							//swap
		next[space_x][space_y] = next[x][y];
		next[x][y] = temp;

		return new State(next,this);
	}

	public void setGGG(int num){								//setGGG for if two same states are found
		this.gg_cover_dist = num;
	}
	public int computeHHH(){									//compuote foe heuristic distance
		int distance_HHH = 0;
		int temp, tempx, tempy;

		for (int i=0 ; i<3 ; i++ ){
			for (int j=0 ; j<3 ; j++ ){
				temp = curr[i][j]-1;

				if(temp==-1) continue;							//summation of manhattan distance

				tempx = (int)ACTUAL_POS[temp].getX();
				tempy = (int)ACTUAL_POS[temp].getY();

				distance_HHH += Math.abs(i - tempx) + Math.abs(j - tempy);
			}
		}
		return distance_HHH;
	}
}