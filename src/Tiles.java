/*
*	Bayeta Reynaldo III
*	CMSC 170 U3L
*/

import javax.swing.*;													//libraries
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tiles extends JButton {
	
	private int xcoord, ycoord;

	public Tiles(int x, int y){
		super();

		xcoord = x;
		ycoord = y;

		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setFont(new Font("Arial", Font.BOLD, 45));
	}

	public int getXCoord() {
		return xcoord;
	}

	public int getYCoord() {
		return ycoord;
	}

}