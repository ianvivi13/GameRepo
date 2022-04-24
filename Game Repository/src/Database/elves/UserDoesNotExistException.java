package Database.elves;

public class UserDoesNotExistException extends DobbyException {
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException(String msg) {
		super(msg);
	}
	
	public UserDoesNotExistException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
