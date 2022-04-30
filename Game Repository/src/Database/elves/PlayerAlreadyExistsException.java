package Database.elves;

public class PlayerAlreadyExistsException extends DobbyException {
	private static final long serialVersionUID = 1L;

	public PlayerAlreadyExistsException(String msg) {
		super(msg);
	}
	
	public PlayerAlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
