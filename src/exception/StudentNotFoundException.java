package exception;

/**
 * Custom exception class for when a student is not found.
 * This demonstrates custom exception handling in Java.
 */
public class StudentNotFoundException extends Exception {
    // Constructor that accepts a custom error message
    public StudentNotFoundException(String message) {
        super(message);
    }
}