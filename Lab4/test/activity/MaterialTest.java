package activity;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaterialTest {

	// Test strategy
	// 测试getName()
	// 测试返回的名字与期望是否相同
	// 测试getDepartment()
	// 测试返回的名字与期望是否相同
	// 测试getDate()
	// 测试返回的时间与期望是否相同
	// 测试equals()
	// 相同的资源，不同的资源

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
		//相同的资源
		Material same = new Material("material", "Learning department", "2020-04-26");
		assertTrue(material.equals(same));
		//不同的资源
		Material different = new Material("different", "Learning department", "2020-04-26");
		assertFalse(material.equals(different));
	}

}
