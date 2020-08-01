package common.location;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MultipleLocationTest extends MultipleLocationEntryInstanceTest {

	// Test strategy
	// ���Թ��췽��
	// �б��λ����������
	// �б��λ�����ظ�

	@Override
	public MultipleLocationEntry createMLE(List<Location> locations) {
		return new MultipleLocation(locations);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testMultipleLocation1(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�б��е�λ����������");
		new MultipleLocation(new ArrayList<>());
	}

	@Test
	public void testMultipleLocation2(){
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("λ���б����ظ�λ��");
		List<Location> list = new ArrayList<>();
		Location loc = new Location("place", true);
		list.add(loc);
		list.add(loc);
		new MultipleLocation(list);
	}
}
