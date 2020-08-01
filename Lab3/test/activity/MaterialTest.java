package activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaterialTest {

	// Test strategy
	// ����getName()
	// ���Է��ص������������Ƿ���ͬ
	// ����getDepartment()
	// ���Է��ص������������Ƿ���ͬ
	// ����getDate()
	// ���Է��ص�ʱ���������Ƿ���ͬ
	// ����equals()
	// ��ͬ����Դ����ͬ����Դ

	Material material = new Material("material", "Learning department", "2020-04-26");

	@Test
	public void testGetName() {
		assertEquals("material", material.getName());
	}

	@Test
	public void testGetDepartment() {
		assertEquals("Learning department", material.getDepartment());
	}

	@Test
	public void testGetDate() {
		assertEquals("2020-04-26", material.getDate());
	}

	@Test
	public void testEquals() {
		//��ͬ����Դ
		Material same = new Material("material", "Learning department", "2020-04-26");
		assertTrue(material.equals(same));
		//��ͬ����Դ
		Material different = new Material("different", "Learning department", "2020-04-26");
		assertFalse(material.equals(different));
	}

}
