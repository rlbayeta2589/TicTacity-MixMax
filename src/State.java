import java.util.*;
import java.awt.*;

public class State{
    private LinkedList<State> successors;
	private State   parent;
	private int[][] state;
	private int     player;
	private int     type;
	private int     utility;
    private Point   turn;

	public State(int[][] state, State parent) {
	    this.state = new int[3][3];
		this.parent = parent;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.state[i][j] = state[i][j];
            }
        }
	}

    public State(int[][] state, State parent, Point action, int player) {
        int i, j, x, y;

        this.state  = new int[3][3];
		this.parent = parent;
	    this.player = player;
        this.type   = player; //or *-1?
        this.turn   = action;

        x = (int) action.getX();
        y = (int) action.getY();

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if( x == i && j == y) this.state[i][j] = player;
                else this.state[i][j] = state[i][j];
            }
        }

        if (winner(x,y) != 0) {
            type = 0;
            utility = player;
        }

    }

    public State getParent() {
        return parent;
    }

    public int getType() {
        return type;
    }

    public int value(int depth, int alpha, int beta) {
        if(getType() ==  0) {
            parent.utility = utility;
            return utility;
        }

        if(getType() ==  1) {
            return maxValue(depth, alpha, beta);
        }
        
        if(getType() == -1) {
            return minValue(depth, alpha, beta);
        }

        return 0;
    }

    public int maxValue(int depth, int alpha, int beta) {
        int value = -100;

        for(State next : generateSuccessors()) {
            value = Math.max(value, next.value(depth+1, alpha, beta));

            if(value >= beta) return value;
            alpha = Math.max(alpha, value);
        }

        return value;
    }

    public int minValue(int depth, int alpha, int beta) {
        int value = 100;

        for(State next : generateSuccessors()) {
            value = Math.min(value, next.value(depth+1, alpha, beta));

            if(value <= alpha) return value;
            beta = Math.min(beta, value);
        }

        return value;
    }

    private LinkedList<State> generateSuccessors() {
        LinkedList<Point> actions   =  new LinkedList<Point>();
        int x, y, player;

        actions     =  availableMoves();
        successors  =  new LinkedList<State>();

        if (actions.size() > 0) {
            player = changePlayer();
        
            for (Point action : actions) {
                State child = new State(state, this, action, player);

                successors.add(child);

                child.print();
            }
        }

        return successors;
    }

    public LinkedList<State> getSuccessors() {
        return successors;
    }

    public int winner(int x, int y) {
        if( state[x][0] == state[x][1] && state[x][0] == state[x][2] ) return state[x][y];
		if( state[0][y] == state[1][y] && state[0][y] == state[2][y] ) return state[x][y];

		if( (x+y) % 2 ==0 ){
			if ( x+y==2 && state[0][2] == state[1][1] && state[0][2] == state[2][0] ) return state[x][y];
			if ( x==y && state[0][0] == state[1][1] && state[0][0] == state[2][2] ) return state[x][y];
		}

		return 0;
    }

    public int changePlayer() {
        return player *= -1;
    }

    public LinkedList<Point> availableMoves() {
        LinkedList<Point> actions = new LinkedList<Point>();
        int i, j;

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if(state[i][j] == 0) actions.add(new Point(i,j));
            }
        }

        return actions;
    }

	public void print() {
		for (int i=0 ; i<3 ; i++ ){
			for(int j=0; j<3 ; j++){
				System.out.print(this.state[i][j] + " ");
			}
			System.out.println();
		}
			System.out.println("TURN " + player + ": @ " + turn.getX() + ", " + turn.getY());
			System.out.println(type == 0 ? "UTILITY NODE" : type == 1 ? "MAXIMIZATION" : "MINIMIZATION");
			System.out.println("UTILITY: " + utility);
            System.out.println();
	}
}
