package common.location;

public class ModifiableSingleLocationTest extends ModifiableSingleLocationEntryInstanceTest {

	@Override
	public ModifiableSingleLocationEntry createMSLE() {
		return new ModifiableSingleLocation();
	}

}
