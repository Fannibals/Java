/*
	NimAIPlayer.java
	This class is provided as a skeleton code for the tasks of 
	Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
	to finish the tasks. 
*/

import java.util.Scanner;
import java.util.Random;

public class NimAIPlayer extends NimPlayer {
	public NimAIPlayer(String userName, String givenName, String familyName) {
		super(userName, givenName, familyName);
	}

	@Override
	public int removeStone(Scanner userInput,int turn,int currentStone,int maximum) {
	    Random rand = new Random();
        int newUp = (currentStone >= maximum) ? maximum : currentStone;
        // check whether meet the winning condition through the victory guaranteed strategy
        boolean cannotWin = (currentStone-1)%(newUp+1) == 0;

        if(!cannotWin){	// if meet the winning condition, return the number through algorithm
            for (int x = 1; x < newUp+1 ; x++) {
                if ((currentStone - x - 1) % (newUp + 1) == 0) {
                    return x; }
            }
        }
		// return a random number within the rule if not meet the winning condition
		int randomNum = rand.nextInt(newUp)+ 1; // here we plus 1 due to rand.nextInt start from 0
        return randomNum;
	}

	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";

		return move;
	}

}
