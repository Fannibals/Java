/**
 * Project C
 * Author: Yuming Shen, ID: 719088, Date: 2018/5/24
 * This project is aim to run a game called NIM.
 * NimGame includes the process of the program in gaming state.
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class NimGame {

    private int currentStone;
    private int upperBound;
    private int numOfTurn =0;
    private NimPlayer player1;
    private NimPlayer player2;

    public void playingProcess(Scanner userInput) {

        int playerNumInGame = 2;                         // the game must need 2 players to attend
        int conditionOfEnd = 0;                          // condition of game over is remaining number == 0

        gameInfo();
        setNumOfTurn(0);
        while (currentStone > conditionOfEnd) {
            if (numOfTurn % playerNumInGame == 0) {      // judging whose turn to play based on number of turn played
                turnToRemove(player1.getGivenName());
                movingRule(userInput,player1);
            } else {
                turnToRemove(player2.getGivenName());
                movingRule(userInput,player2);
            }
            numOfTurn += 1;
        }

        if (numOfTurn % playerNumInGame == 0) {           // declare who is the winner by checking it is an odd/even turn game
            afterOneRound(player1);
        } else {
            afterOneRound(player2);
        }
    }

    private void printStones() {                          // method to print out the remaining stones
        for (int i = 1; i <= currentStone; i++) {
            System.out.print(" *");
        }
        System.out.print("\n");
    }

    private void turnToRemove(String nameOfPlayer) {       // the process of each playing turn
        System.out.print("\n"+currentStone + " stones left:");
        printStones();
        System.out.println(nameOfPlayer + "'s turn - remove how many?");
    }

    private void afterOneRound(NimPlayer aPlayer) {               // messages to display after one round game
        aPlayer.afterGameWin();                                   // recording the number of game won;
        System.out.println("\nGame Over");
        System.out.println(aPlayer.getGivenName()+ " "+aPlayer.getFamilyName()+" wins!");
    }

    private void gameInfo(){
        System.out.println("\nInitial stone count: "+currentStone);
        System.out.println("Maximum stone removal: "+upperBound);
        System.out.println("Player 1: "+player1.getGivenName()+" "+player1.getFamilyName());
        System.out.println("Player 2: "+player2.getGivenName()+" "+player2.getFamilyName());
        player1.afterGamePlayed();                  // recording the number of game played
        player2.afterGamePlayed();
    }

   private void movingRule(Scanner userInput,NimPlayer player){      // for detecting whether the move is valid or not
       try{
           int removeNum = player.removeStone(userInput,numOfTurn,currentStone,upperBound);
           currentStone -= removeNum;
       }catch (InputMismatchException e){         // catch the exception if the player inputs a non-integer during the game
            String junk = userInput.nextLine();
            int newUp = (currentStone >= upperBound) ? upperBound : currentStone;
            System.out.println("\nInvalid move. You must remove between 1 and " + newUp + " stones.");
            numOfTurn -= 1;
       }catch (InvalidMoveException e){           // catch if player does not obey the gaming rule
            System.out.println(e.getMessage());
            numOfTurn -= 1; }
   }

    public void setCurrentStone(int i){
        this.currentStone = i;
    }

    public void setUpperBound(int i){
        this.upperBound= i;
    }

    public void setNumOfTurn(int numOfTurn) {
        this.numOfTurn = numOfTurn;
    }

    public void setPlayer1(NimPlayer[] pList, int x){
        player1 = pList[x];
    }

    public void setPlayer2(NimPlayer[] pList,int x){
        player2 = pList[x];
    }
}
