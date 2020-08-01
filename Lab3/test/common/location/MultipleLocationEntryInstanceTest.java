package common.location;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public abstract class MultipleLocationEntryInstanceTest {

	// Test strategy
	// ����getLocations()
	// ���Է��ص�λ���б��������Ƿ����

	/**
	 * ����һ��λ����
	 * @param locations ����ӵ�λ���б�
	 * @return ��װ���λ���б�
	 */
	public abstract MultipleLocationEntry createMLE(List<Location> locations);

	@Test
	public void testGetLocations() {
		List<Location> locations = new ArrayList<>();
		locations.add(new Location("place1", true));
		locations.add(new Location("place2", true));
		locations.add(new Location("place3", true));
		MultipleLocationEntry mle = createMLE(locations);
		assertEquals(locations, mle.getLocations());
	}

}
