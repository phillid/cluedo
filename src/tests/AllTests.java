package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CardTests.class,
	CellTests.class,
	CorridorTests.class,
	GameTests.class,
	PlayerTests.class,
	RoomTests.class,
	WeaponTokenTests.class,
})

public class AllTests {}