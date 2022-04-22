package Database.elves;

public class DobbyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DobbyException(String msg) {
		super(msg);
	}
	
	public DobbyException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
