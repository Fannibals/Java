import java.io.*;
import java.util.*;

/**
 * This project is aim to run a game called NIM.
 * Nimsys includes the idle part before or after the real gaming part begin.
 */



public class Nimsys {

    private static int MAXIMUMPLAYERNUMBER = 100;              // assuming there are up to 100 players
    private int numOfPlayer = 0;                               // tracking number of total players
    private NimPlayer[] playerList = new NimPlayer[MAXIMUMPLAYERNUMBER];
    private StringTokenizer st;                                // creating an object to tokenize following input
    private Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        Nimsys sys = new Nimsys();
        NimGame nimGame = new NimGame();
        sys.fileInPut();      // reading the players.dat
        System.out.println("Welcome to Nim");

        boolean stop = false;
        while (!stop) {
            System.out.print("\n$");
            String startInput = sys.userInput.nextLine();                      
            sys.st = new StringTokenizer(startInput);

            try{
                if (sys.st.hasMoreTokens()) {                       // check whether there is an input
                    String invokeMethod = sys.st.nextToken();       // if user really inputs,read the first part of user input
                    if (invokeMethod.equals("addplayer")) {
                        sys.addPlayer("human");
                    } else if (invokeMethod.equals("addaiplayer")) {
                        sys.addPlayer("ai");

                    } else if (invokeMethod.equals("removeplayer")) {
                        sys.removePlayer();

                    } else if (invokeMethod.equals("editplayer")) {
                        sys.editPlayer();

                    } else if (invokeMethod.equals("resetstats")) {
                        sys.resetStats();

                    } else if (invokeMethod.equals("displayplayer")) {
                        sys.displayPlayer();

                    } else if (invokeMethod.equals("rankings")) {
                        sys.rankings();

                    } else if (invokeMethod.equals("exit")) {
                        System.out.println();
                        sys.fileOutPut();                         // writing objects in playerlist to players.dat
                        System.exit(0);

                    } else if (invokeMethod.equals("startgame")) {
                        sys.startGame(nimGame);

                    } else {        // if the invokemethod is unreadable, throw an Invalid Command Exception
                        throw new InvalidCommandException("'" + invokeMethod + "'" + " is not a valid command.");
                    }
                }
            }catch (InvalidCommandException e){     // custom an exception class for handling the invalid command exception
                System.out.println(e.getMessage());
            }catch (NoSuchElementException e){
                // If the argument count is insufficient, variables that storing argument will throw NoSuchElementException
                // Thus, in this case, invalid argument exception can be catched by NoSuchElementException
                System.out.println("Incorrect number of arguments supplied to command.");
            }
        }sys.userInput.close();
    }

    private boolean nameNotExist(String userName) {                //check if there is already have a same username
        for (int x = 0; x < numOfPlayer; x++) {
            if (playerList[x].getUserName().equals(userName)) {return false;}
        }
        return true;
    }

    private int indexOf(String userName) {                                          // get the index of a player
        for (int x = 0; x < numOfPlayer; x++) {
            if (playerList[x].getUserName().equals(userName)) { return x; }
        }
        return -1;
    }

    private void addPlayer(String type) throws NoSuchElementException{
        String userName = st.nextToken(" ,");
        String familyName = st.nextToken(" ,");
        String givenName = st.nextToken(" ,");

        if (nameNotExist(userName) && type.equals("human")) {           // add a new human player after checking the name
            playerList[numOfPlayer] = new NimHumanPlayer(userName, givenName, familyName);
            System.out.println(playerList[numOfPlayer]);
            numOfPlayer += 1;// add num of player when a new player sign in

        } else if (nameNotExist(userName) && type.equals("ai")) {       // add a new ai player after checking the name
            playerList[numOfPlayer] = new NimAIPlayer(userName, givenName, familyName);
            numOfPlayer += 1;
        } else {
            System.out.println("The player already exists.");
        }
    }

    private void removePlayer() throws NoSuchElementException{
        if (st.hasMoreTokens()) {
            String userName = st.nextToken(" ,");
            if (!nameNotExist(userName)) {
                int i = indexOf(userName);
                playerList[i] = null;
                for (int x = 0; x < numOfPlayer; x++) {
                    if (x >= i) { playerList[x] = playerList[x + 1]; }
                }
                numOfPlayer -=1;             // remove the num of player when this method has invoked
            } else {
                System.out.println("The player does not exist.");
            }
        } else {
            System.out.println("Are you sure you want to remove all players? (y/n)");
            String answer = userInput.nextLine();
            if (answer.equals("y")) {
                for (int x = 0; x < numOfPlayer; x++) { playerList[x] = null; }
            }
            numOfPlayer =0;                  // set number of player to 0 if all players are removed.
        }
    }

    private void editPlayer() throws NoSuchElementException{
        String UserName = st.nextToken(" ,");
        String newFamilyName = st.nextToken(" ,");
        String newGivenName = st.nextToken(" ,");

        if (!nameNotExist(UserName)) {
            int i = indexOf(UserName);
            playerList[i].setFamilyName(newFamilyName);          //set to new family and given names
            playerList[i].setGivenName(newGivenName);
        } else {
            System.out.println("The player does not exist.");
        }
    }

    private void resetStats() throws NoSuchElementException{
        if (st.hasMoreTokens()) {
            String userName = st.nextToken(" ,");
            if (!nameNotExist(userName)) {
                int i = indexOf(userName);
                playerList[i].setGamePlayed(0);
                playerList[i].setGameWin(0);         // reset player's game played number and game win number to 0;
            } else {
                System.out.println("The player does not exist.");
            }
        } else {
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            String answer = userInput.nextLine();
            if (answer.equals("y")) {
                for (int x = 0; x < numOfPlayer; x++) {
                    playerList[x].setGamePlayed(0);
                    playerList[x].setGameWin(0);
                }
            }
        }
    }

    private void displayPlayer() throws NoSuchElementException{
        if (st.hasMoreTokens()) {
            String userName = st.nextToken(" ,");
            if (!nameNotExist(userName)) {
                int i = indexOf(userName);
                playerList[i].display();                                // invoke display method of Nimplayer
            } else {
                System.out.println("The player does not exist.");
            }
        } else {
            NimPlayer[] displayer = new NimPlayer[numOfPlayer];
            System.arraycopy(playerList, 0, displayer, 0, numOfPlayer);
            Arrays.sort(displayer,new alphaComparator());               // sort in alphabetical order
            for (int x = 0; x < numOfPlayer; x++) {
                displayer[x].display();
            }
        }
    }


    private void rankings() throws NoSuchElementException{
        NimPlayer[] ranks = new NimPlayer[numOfPlayer];
        System.arraycopy(playerList,0,ranks,0,numOfPlayer);

        if (st.hasMoreTokens()) {
            String order = st.nextToken(" ,");
            if (order.equals("asc")) {
                Arrays.sort(ranks,new ascComparator());                // sort the array in ascending order
                for (int x = 0; x < numOfPlayer; x++) {ranks[x].gameStats();}
            } else if(order.equals("desc") ) {
                Arrays.sort(ranks,new descComparator());               // ranking on DESC
                for (int x = 0; x < numOfPlayer; x++) {ranks[x].gameStats();}
            }
        } else {
            Arrays.sort(ranks,new descComparator());
            for (int x = 0; x < numOfPlayer; x++) {ranks[x].gameStats();}
        }
    }

    private void startGame(NimGame nimGame) throws NoSuchElementException{
        // first four lines are setting the variables needed in the game
        nimGame.setCurrentStone(Integer.parseInt(st.nextToken(" ,")));
        nimGame.setUpperBound(Integer.parseInt(st.nextToken(" ,")));

        String userName = st.nextToken(",");
        String userName2 = st.nextToken(",");

        if (!nameNotExist(userName) && !nameNotExist(userName2)){
            nimGame.setPlayer1(playerList,indexOf(userName));
            nimGame.setPlayer2(playerList,indexOf(userName2));
            nimGame.playingProcess(userInput);            // start the game
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    private void fileInPut(){       // input the file recording players' statistics
        try{
            File fileObject = new File("./players.dat");
            if (fileObject.length() >0){        // read the file only when it is not empty
                ObjectInputStream inputStream =
                        new ObjectInputStream(new FileInputStream(fileObject));
                playerList = (NimPlayer[])inputStream.readObject();
                inputStream.close();
                int count = 0;
                // owing to the numOfPlayer is initialized as 0, when the program read the file,
                // numOfPlayer should replaced with the number of players in player list
                for (int x = 0; x < playerList.length ; x++) {
                    if (playerList[x]!= null) {
                        count += 1; }
                }
                numOfPlayer = count;
            }
        }catch (FileNotFoundException e) {              // catch the exception if file is not found
            System.out.println("Cannot find file");
        }catch (ClassNotFoundException e){              // catch the exception if class is not found
            System.out.println("Cannot find the class");
        }catch (IOException e){
            System.out.println("Other problems with file input.");
        }
    }

    private void fileOutPut(){  // output the updated statistics data to the file
        try{
            // output player's array into the binary file
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream("./players.dat"));
            outputStream.writeObject(playerList);
            outputStream.close();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

    private int nameCompare(String s1, String s2){   // method using for customized comparison functions below
        int bigger = 1, smaller = -1, equal = 0;
        if (s1.compareTo(s2) >0)
            return bigger;
        else if (s1.compareTo(s2) <0)
            return smaller;
        return equal;
    }

    private int winRateCompare(double w1, double w2){ // method using for customized comparison functions below
        int bigger = 1, smaller = -1, equal = 0;
        if (w1 > w2)
            return bigger;
        else if (w1 < w2)
            return smaller;
        return equal;
    }

    class alphaComparator implements Comparator<NimPlayer> {
        // comparison function using for sort array in alphabetical order
        @Override
        public int compare(NimPlayer p1, NimPlayer p2) {
            return nameCompare(p1.getUserName(),p2.getUserName());
        }
    }

    class ascComparator implements Comparator<NimPlayer>{
        int equal =0;
        // comparison function using for sort array in ascending order
        @Override
        public int compare(NimPlayer p1, NimPlayer p2) {
            int value = winRateCompare(p1.winRate(), p2.winRate());
            if (value == equal)            // if win rate is equal, sort in alphabetical order
                return nameCompare(p1.getUserName(), p2.getUserName());
            return value;
        }
    }

    class descComparator implements Comparator<NimPlayer>{
        int equal =0, reverse = -1;
        // comparison function using for sort array in descending order
        @Override
        public int compare(NimPlayer p1, NimPlayer p2) {
            int value = winRateCompare(p1.winRate(),p2.winRate());
            if (value == equal)         // if win rate is equal, sort in alphabetical order
                return nameCompare(p1.getUserName(),p2.getUserName());
            return value*(reverse);     // reverse the value return from ascComparator and get desc one
        }
    }
}
