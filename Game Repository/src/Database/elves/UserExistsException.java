package Database.elves;

public class UserExistsException extends DobbyException {
	private static final long serialVersionUID = 1L;

	public UserExistsException(String msg) {
		super(msg);
	}
	
	public UserExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
