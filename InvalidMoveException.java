/**
 * Project C
 * Author: Yuming Shen, ID: 719088, Date: 2018/5/24
 */
public class InvalidMoveException extends Exception {
    //It is created for handling the exception due to the invalid move made by the player
    public InvalidMoveException(String message) {
        super(message);
    }
}
