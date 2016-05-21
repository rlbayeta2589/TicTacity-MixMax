import java.awt.*;
import java.util.*;

public class TicTacity {
    private int turn_count;

    public static int  TURN1   =  1;
    public static int  TURN2   = -1;
    public static int  TURN    = TURN1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean first_turn = true;
        int x, y;

        int chosen_one = 1;
        
        State next_state;

        int[][] tiles = {
            { 0, 0, 0 },
            { 0, 0, 0 },
            { 0, 0, 0 }        
        };

        while(true) {
            System.out.println("YOUR TURN");
            System.out.print("X: ");
            x = scanner.nextInt();

            System.out.print("Y: ");
            y = scanner.nextInt();

            tiles[x][y] = 1;


            State game = new State(tiles, null, new Point(x,y), 1);

            System.out.println(game.value(0, -1000, 1000));

        
            System.out.println("POSSIBLE MOVES!");

            for(State child : game.getSuccessors()) {
                child.print();
            }
            System.out.println();
        

            chosen_one = 0;
            for(int i = 0; i < game.getSuccessors().size(); i++) {
                if (game.getSuccessors().get(chosen_one).getUtility() >= game.getSuccessors().get(i).getUtility()) {
                     chosen_one = i;
                }
            }

            next_state =  game.getSuccessors().get(chosen_one);

            x = (int) next_state.getTurn().getX();
            y = (int) next_state.getTurn().getY();
            
            tiles[x][y] = -1;

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    System.out.print(tiles[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();

        }

            
        
    }
}
