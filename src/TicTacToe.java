import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TicTacToe extends JFrame implements ActionListener{
	
    private static JFrame frame;
	private static Tiles[][] tiles;
	private static String TURN1 = "X";
	private static String TURN2 = "O";
	private static String TURN = TURN1;
	private static int TURN_COUNT = 0;

	public TicTacToe(String title){
		super(title);

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

        State init = getGameState();
        
        System.out.println(init.value(0, -100, 100));

		setPreferredSize(new Dimension(300, 300));
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}

	public void actionPerformed(ActionEvent e){
		Tiles source = (Tiles)e.getSource();
		int response;
		String winner;
		boolean GAME_OVER = false;

		if(source.getText()!="") return;

		source.setText(TURN);
		TURN_COUNT++;

		winner = isGoalState(source.getXCoord(), source.getYCoord());

		if( winner != "" ) {
			JOptionPane.showMessageDialog(frame,"WINNER : "+winner, "TICTACITY", JOptionPane.INFORMATION_MESSAGE);
			GAME_OVER = true;
		}

		else if (TURN_COUNT==9){
			JOptionPane.showMessageDialog(frame, "DRAW" , "TICTACITY", JOptionPane.INFORMATION_MESSAGE);
			GAME_OVER = true;
		}
		
		if(GAME_OVER){
			response = JOptionPane.showConfirmDialog(frame,"Do you want to play again?", "TICTACITY", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				RESTART();
			}

			else if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
				System.exit(0);
			}
		}

		TURN = TURN == TURN1
			? TURN2
			: TURN1;

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

	public static void RESTART() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j].setText("");
			}
		}
		TURN_COUNT = 0;
		TURN = TURN1;
	}

	public State getGameState(){
		int[][] temp = new int[3][3];

		for (int i=0 ; i<3 ; i++ ){
			for(int j=0; j<3 ; j++){
				switch(tiles[i][j].getText()){
					case "X":
						temp[i][j] = 1;
						break;
					case "O":
						temp[i][j] = -1;
						break;
					case "":
						temp[i][j] = 0;
						break;
				}
			}
		}

		return new State(temp, null);
	}

	public void AlphaBetaPruning(){

	}

	public static void main(String[] args) {
		TicTacToe eight = new TicTacToe("Tic Tac Toe");
	}
}
