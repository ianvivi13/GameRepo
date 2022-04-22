package Database.elves;

public class IncorrectPlayerTypeException extends DobbyException {
	private static final long serialVersionUID = 1L;

	public IncorrectPlayerTypeException(String msg) {
		super(msg);
	}
	
	public IncorrectPlayerTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
