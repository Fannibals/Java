/**
 * Project C
 * Author: Yuming Shen, ID: 719088, Date: 2018/5/24
 */
public class InvalidCommandException extends Exception {
    //It is created for handling the exception due to the invalid command called by the player
    public InvalidCommandException(String message) {
        super(message);
    }
}
