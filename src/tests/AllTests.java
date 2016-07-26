package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CellTests.class,
	CorridorTests.class,
	RoomTests.class,
	PlayerTests.class,
	WeaponTokenTests.class,
})

public class AllTests {}