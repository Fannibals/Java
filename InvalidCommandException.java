public class InvalidCommandException extends Exception {
    //It is created for handling the exception due to the invalid command called by the player
    public InvalidCommandException(String message) {
        super(message);
    }
}
