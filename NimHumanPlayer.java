/**
 * Project C
 * Author: Yuming Shen, ID: 719088, Date: 2018/5/24
 * This is a NimHumanPlayer class inherited from NimPlayer
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer {

    public NimHumanPlayer(String userName, String givenName, String familyName){
        super(userName, givenName, familyName);
    }
    public int removeStone(Scanner userInput,int numOfTurn,int currentStone,int upperBound)
                                            throws InputMismatchException,InvalidMoveException{
        int removeNum = userInput.nextInt();
        int newUp = (currentStone >= upperBound) ? upperBound : currentStone;

        // if the player does not obey the gaming rule, throw an exception
        if (removeNum > newUp || removeNum < 1)
            throw new InvalidMoveException("\nInvalid move. You must remove between 1 and " + newUp + " stones.");

        String junk = userInput.nextLine();     // reading the string made by user input in gaming mode.
        return removeNum;
    }
}
