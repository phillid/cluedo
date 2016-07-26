package tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CellTests.class,
	PlayerTests.class,
	WeaponTests.class,
})

public class AllTests {}