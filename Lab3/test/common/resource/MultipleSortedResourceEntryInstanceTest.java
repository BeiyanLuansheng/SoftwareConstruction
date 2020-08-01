package common.resource;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import train.Carriage;

public abstract class MultipleSortedResourceEntryInstanceTest {

	// Test strategy
	// ����getResources()
	// ���Է��ص���Դ�Ƿ������������

	/**
	 * ����һ����Դ�б�
	 * @param train ��Դ�б�
	 * @return һ���������Դ�б�
	 */
	public abstract MultipleSortedResourceEntry<Carriage> createMSRE(List<Carriage> train);

	@Test
	public void testGetResources() {
		List<Carriage> train = new ArrayList<>();
		train.add(new Carriage(1, "ErDengZuo", 100, 2020));
		train.add(new Carriage(2, "ErDengZuo", 100, 2020));
		MultipleSortedResourceEntry<Carriage> msre = createMSRE(train);
		assertEquals(train, msre.getResources());
	}

}
