package flight;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlaneTest {

	// Test strategy
	// 测试getID()
	// 测试得到的ID是否和预期的相等
	// 测试getType()
	// 测试得到的类型是否和预期的相同
	// 测试getSeats()
	// 测试得到的座位数是否和预期的相等
	// 测试getAge()
	// 测试得到的机龄是否和预期的相等
	// 测试hashCode()
	// 测试得到的哈希值是否和预期大的相等
	// 测试equals()
	// 两架飞机是同一架飞机，两架飞机不是同一架飞机

	@Test
	public void testGetID() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("B0741", plane.getID());
	}

	@Test
	public void testGetType() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("A380", plane.getType());
	}

	@Test
	public void testGetSeats() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals(468, plane.getSeats());
	}

	@Test
	public void testGetAge() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals(1.1, plane.getAge(), 0.0);
	}
	
	@Test
	public void testHashCode() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertEquals("B0741".hashCode(), plane.hashCode());
	}

	@Test
	public void testEquals() {
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//同一架飞机
		Plane same = new Plane("B0741", "A380", 468, 1.1);
		assertTrue(plane.equals(same));
		//不是同一架飞机
		Plane different = new Plane("N5375", "B757", 261, 3.1);
		assertFalse(plane.equals(different));

	}
}
