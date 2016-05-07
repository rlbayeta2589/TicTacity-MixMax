import java.util.*;

public class State{
	private int                 equal_flag;  //-1 if less than, 0 if equal, 1 if greater than
	private int                 node;        //-1 if minimization, 0 if utility, 1 if maximization
	private ArrayList<State>    descendants;
	private State               parent;

	public int getNode() {
	    return node;
	}

    public int getEqualFlag() {
        return equal_flag;
    }

    public ArrayList<State> getDescendants() {
        return descendants;
    }

    public State getParent() {
        return parent;
    }
}
