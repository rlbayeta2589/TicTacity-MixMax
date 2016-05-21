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

        int[][] tiles = {
            { 0, 0, 0 },
            { 0, 0, 0 },
            { 0, 0, 0 }        
        };


            System.out.println("YOUR TURN");
            System.out.print("X: ");
            x = scanner.nextInt();

            System.out.print("Y: ");
            y = scanner.nextInt();

            State game = new State(tiles, null, new Point(x,y), -1);

            System.out.println(game.value(0, -100, 100));

            System.out.println();
            System.out.println();
            System.out.println();

            for(State s : game.getSuccessors()) {
                s.print();
            }

            
        
    }
}
