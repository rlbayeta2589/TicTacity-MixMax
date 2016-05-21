import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TicTacToe extends JFrame implements ActionListener{
	
    private static JFrame frame;
	private Tiles[][] tiles;
	private static String TURN1 = "X";
	private static String TURN2 = "O";
	private static String TURN = TURN1;
	private static int TURN_COUNT = 0;
	private static int PLAYER;
    private State GAME;
    private Point action;
    
    private String PLAYER_TOKEN = TURN1;
    private String AI_TOKEN = TURN2;

	public TicTacToe(String title){
		super(title);

	    int best_state_index, x, y;
	    int temp[][] = {
            { 0, 0, 0 },
            { 0, 0, 0 },
            { 0, 0, 0 }        
        };
        State best_state;

		tiles = new Tiles[3][3];
		frame = this;

		Container container = getContentPane();
		container.setLayout(new GridLayout(3,3));
		
		for (int i=0 ; i<3 ; i++ ) {
			for(int j=0; j<3 ; j++){
				tiles[i][j] = new Tiles(i,j);
				tiles[i][j].addActionListener(this);
				container.add(tiles[i][j]);
			}
		}
        
		setPreferredSize(new Dimension(300, 300));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

        PLAYER = askFirstTurn();

        if (PLAYER == -1) {
            AI_TOKEN = TURN1;
            PLAYER_TOKEN = TURN2;

            GAME = new State(temp, -1);

            GAME.value(0, -1000, 1000);

            best_state_index = 0;
            for(int i = 0; i < GAME.getSuccessors().size(); i++) {
                if (GAME.getSuccessors().get(best_state_index).getUtility() >= GAME.getSuccessors().get(i).getUtility()) {
                     best_state_index = i;
                }
            }
            
            
            

            System.out.println("POSSIBLE MOVES");
            for (State s : GAME.getSuccessors()) {
                s.print();
            }
            
            System.out.println(best_state_index);

            best_state =  GAME.getSuccessors().get(best_state_index);

            x = (int) best_state.getTurn().getX();
            y = (int) best_state.getTurn().getY();
            
            tiles[x][y].setText(AI_TOKEN);
            
            
            TURN_COUNT++;
        }
        
	}

	public int askFirstTurn(){
		String[] val = { "Player", "AI"};
   		String init = "Player";
    	Object selection = JOptionPane.showInputDialog(this, "Who will take the first turn?",
        	"TURN", JOptionPane.QUESTION_MESSAGE, null, val, init);

    	return selection=="Player" ? 1 : -1;
	}

	public void actionPerformed(ActionEvent e){
		Tiles source = (Tiles)e.getSource();

		String winner;
		int best_state_index, x, y;
		State best_state;
		boolean game_over;

		if(source.getText()!="") return;

		source.setText(PLAYER_TOKEN);
		TURN_COUNT++;

		winner = isGoalState(source.getXCoord(), source.getYCoord());
		action = new Point(source.getXCoord(), source.getYCoord());

        game_over = checkGame(winner);

        if(!game_over) {
            GAME = new State(getGameState(), null, action, 1);
            GAME.value(0, -1000, 1000);

            best_state_index = 0;
            for(int i = 0; i < GAME.getSuccessors().size(); i++) {
                if (GAME.getSuccessors().get(best_state_index).getUtility() >= GAME.getSuccessors().get(i).getUtility()) {
                        
                     best_state_index = i;
                }
            }

            System.out.println("POSSIBLE MOVES");
            for (State s : GAME.getSuccessors()) {
                s.print();
            }
            
            System.out.println(best_state_index);


            best_state = GAME.getSuccessors().get(best_state_index);

            x = (int) best_state.getTurn().getX();
            y = (int) best_state.getTurn().getY();
            
            tiles[x][y].setText(AI_TOKEN);
    		TURN_COUNT++;

            winner = isGoalState(x, y);

            checkGame(winner);
        }
	}

    public boolean checkGame(String winner) {
		int response;
		boolean game_over = false;

        if( winner != "" ) {
			JOptionPane.showMessageDialog(frame,"WINNER : "+winner, "TICTACITY", JOptionPane.INFORMATION_MESSAGE);
			game_over = true;
		}

		else if (TURN_COUNT==9){
			JOptionPane.showMessageDialog(frame, "DRAW" , "TICTACITY", JOptionPane.INFORMATION_MESSAGE);
			game_over = true;
		}
		
		if(game_over){
			response = JOptionPane.showConfirmDialog(frame,"Do you want to play again?", "TICTACITY", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				restart();
			}

			else if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			}
		}

        return game_over;
    }

	public String isGoalState(int x, int y){
		if( tiles[x][0].getText() == tiles[x][1].getText() && tiles[x][0].getText() == tiles[x][2].getText() ) return tiles[x][y].getText();
		if( tiles[0][y].getText() == tiles[1][y].getText() && tiles[0][y].getText() == tiles[2][y].getText() ) return tiles[x][y].getText();

		if( (x+y) % 2 ==0 ){
			if ( x+y==2 && tiles[0][2].getText() == tiles[1][1].getText() && tiles[0][2].getText() == tiles[2][0].getText() ) return tiles[x][y].getText();
			if ( x==y && tiles[0][0].getText() == tiles[1][1].getText() && tiles[0][0].getText() == tiles[2][2].getText() ) return tiles[x][y].getText();
		}

		return "";
	}

	public void restart() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j].setText("");
			}
		}

		TURN_COUNT = 0;
		PLAYER = askFirstTurn();

	    int best_state_index, x, y;
	    int temp[][] = {
            { 0, 0, 0 },
            { 0, 0, 0 },
            { 0, 0, 0 }        
        };

        if (PLAYER == -1) {
            AI_TOKEN = TURN1;
            PLAYER_TOKEN = TURN2;

            GAME = new State(temp, PLAYER);

            GAME.value(0, -1000, 1000);

            best_state_index = 0;
            for(int i = 0; i < GAME.getSuccessors().size(); i++) {
                if (GAME.getSuccessors().get(best_state_index).getUtility() >= GAME.getSuccessors().get(i).getUtility()) {
                     best_state_index = i;
                }
            }

            State best_state =  GAME.getSuccessors().get(best_state_index);

            x = (int) best_state.getTurn().getX();
            y = (int) best_state.getTurn().getY();
            
            tiles[x][y].setText(AI_TOKEN);
            
            TURN_COUNT++;
        }

        else {
            AI_TOKEN = TURN2;
            PLAYER_TOKEN = TURN1;
        }
	}

	public int[][] getGameState(){
		int[][] temp = new int[3][3];

		for (int i=0 ; i<3 ; i++ ){
			for(int j=0; j<3 ; j++){
				if(tiles[i][j].getText() == PLAYER_TOKEN){
                    temp[i][j] = 1;
				}

                else if(tiles[i][j].getText() == AI_TOKEN) {
                    temp[i][j] = -1;
                }

                else {
                    temp[i][j] = 0;
                }
			}
		}

		return temp;
	}

	public static void main(String[] args) {
		TicTacToe eight = new TicTacToe("Tic Tac Toe");
	}
}
