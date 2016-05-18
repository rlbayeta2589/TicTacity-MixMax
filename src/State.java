import java.util.*;
import java.awt.*;

public class State{
	private LinkedList<State>   descendants;
	private LinkedList<Point>	action;
	private State               parent;
	private int[][]				state;
	private String 				id = "";

	public State(int[][] state, State parent) {
		this.state = state;
		this.parent = parent;

		action = new LinkedList<Point>();

		for (int i=0 ; i<3 ; i++ ){
			for(int j=0; j<3 ; j++){
				id += state[i][j];
				if(state[i][j]==0){
					action.add(new Point(i,j));
				}
			}
		}
	}

    public LinkedList<State> getDescendants() {
        return descendants;
    }

    public State getParent() {
        return parent;
    }

    public LinkedList<Point> getAction(){
    	return action;
    }

    public State getNextState(Point action, int player) {
    	int x = (int) action.getX(), y = (int) action.getY();

    	int[][] new_state = new int[3][3];

    	for(int i=0;i<3;i++){
    		new_state[i] = state[i].clone();
    	}

    	new_state[x][y] = (player==1 ? -1 : 1);

    	return new State(new_state,this);
    }

    public int getValue(int turn_count){
    	for(int i=0;i<3;i++){
    		if(state[i][0]==state[i][1] && state[i][1]==state[i][2] && state[i][0]!=0) return state[i][0];
    	}

    	for(int i=0;i<3;i++){
    		if(state[0][i]==state[1][i] && state[1][i]==state[2][i] && state[0][i]!=0) return state[0][i];
    	}

		if(state[0][0]==state[1][1] && state[1][1]==state[2][2] && state[1][1]!=0) return state[1][1];
		if(state[0][2]==state[1][1] && state[1][1]==state[2][0] && state[1][1]!=0) return state[1][1];

		return turn_count==9 ? 0 : -99;
    }

	public void print() {
		for (int i=0 ; i<3 ; i++ ){
			for(int j=0; j<3 ; j++){
				System.out.print(this.state[i][j] + " ");
			}
			System.out.println();
		}
			System.out.println();
	}
}
