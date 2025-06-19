package User.management.user.management.Exception;

public class UserIsAlreadyExistExp extends RuntimeException {

    public UserIsAlreadyExistExp(String message) {
        super(message);
    }
    public UserIsAlreadyExistExp(String message, Throwable cause) {
        super(message, cause);
    }
}
