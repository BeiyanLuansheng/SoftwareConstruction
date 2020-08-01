package common.resource;

import java.util.List;

import train.Carriage;

public class MultipleSortedResourceTest extends MultipleSortedResourceEntryInstanceTest {

	@Override
	public MultipleSortedResourceEntry<Carriage> createMSRE(List<Carriage> train) {
		return new MultipleSortedResource<>(train);
	}

}
