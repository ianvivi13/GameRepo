package Database.elves;

public class InitDatabase {
	public static void init() {
		DatabaseProvider.setInstance(new DerbyDatabase());
	}
}
