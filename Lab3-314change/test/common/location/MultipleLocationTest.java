package common.location;

import java.util.List;

public class MultipleLocationTest extends MultipleLocationEntryInstanceTest {

	@Override
	public MultipleLocationEntry createMLE(List<Location> locations) {
		return new MultipleLocation(locations);
	}
}
