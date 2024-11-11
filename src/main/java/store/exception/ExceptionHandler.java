package store.exception;

public class ExceptionHandler {

    private static final String MESSAGE_HEADER = "[ERROR] ";

    public static void inputException(ErrorMessage errorMessage) {
        System.out.println(MESSAGE_HEADER + errorMessage.getMessage());
        throw new IllegalArgumentException(MESSAGE_HEADER + errorMessage.getMessage());
    }

    public static void firstInputException(ErrorMessage errorMessage) {
        System.out.println(MESSAGE_HEADER + errorMessage.getMessage());
    }
}
