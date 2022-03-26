package Database.elves;

import Database.elves.DatabaseProvider;
import Database.elves.DerbyDatabase;

public class InitDatabase {
	public static void init() {
		DatabaseProvider.setInstance(new DerbyDatabase());
	}
}
