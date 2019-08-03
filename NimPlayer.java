/**
 * There are several methods and variables, which will be used in Nimsys and NimGame
 */

import java.io.Serializable;
import java.util.Scanner;


public abstract class NimPlayer implements Serializable{
    @Override
    public String toString() {
        return "NimPlayer{" +
                "givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", userName='" + userName + '\'' +
                ", gamePlayed=" + gamePlayed +
                ", gameWin=" + gameWin +
                '}';
    }

    private String givenName;
    private String familyName;
    private String userName;
    private int gamePlayed = 0;
    private int gameWin = 0;

    public NimPlayer(String userName, String givenName, String familyName) {  // constructor of a Nimplayer object
        setPlayer(userName, givenName, familyName);
    }

    private void setPlayer(String userName, String givenName, String familyName) {
        this.userName = userName;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    public abstract int removeStone(Scanner userInput,int turn,int currentStone,int maximum)
            throws InvalidMoveException;

    public void display() {
        String s1 = this.userName + "," + this.givenName + "," + this.familyName+ "," ;
        String s2 = this.gamePlayed + " games," + this.gameWin + " wins";
        System.out.println(s1+s2);
    }

    public void gameStats() {
        String winRateS = String.format("%d%%", Math.round(winRate() * 100));    // format winrate to percentage
        String name = this.givenName + " " + this.familyName;
        System.out.print(String.format("%-4s | %02d games | %s\n", winRateS, gamePlayed, name));
    }

    public double winRate(){                        // return the win rate of the player
        if (gamePlayed == 0) {
            return 0;
        }
        return (1.0* gameWin)/(1.0*gamePlayed);     //the number multiplied by 1.0 for avoiding return integer.
    }

    public String getGivenName() {                 // following are the getter and setter of instance variables
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

    public void setGameWin(int gameWin) {
        this.gameWin = gameWin;
    }

    public void afterGameWin() {                // if a player wins, game win + 1
        this.gameWin +=1 ;
    }

    public void afterGamePlayed() {             // game played + 1 when the player played the game
        this.gamePlayed +=1 ;

    }
}

