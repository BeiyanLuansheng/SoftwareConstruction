package train;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarriageTest {

	// Test strategy
	// 测试getID()
	// 测试得到的编号是否和预期的相等
	// 测试getType()
	// 测试得到的类型是否和预期的相同
	// 测试getSeats()
	// 测试得到的座位数是否和预期的相等
	// 测试getAge()
	// 测试得到的生产年份是否和预期的相等
	// 测试equals()
	// 两车厢是同一车厢，两车厢不是同一个车厢
	
	@Test
	public void testGetID() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(1, carriage.getID());
	}

	@Test
	public void testGetType() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals("ErDengZuo", carriage.getType());
	}

	@Test
	public void testGetSeats() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(100, carriage.getSeats());
	}

	@Test
	public void testGetAge() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		assertEquals(2020, carriage.getManufactureYear());
	}

	@Test
	public void testEquals() {
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//两车厢是同一车厢
		Carriage same = new Carriage(1, "ErDengZuo", 100, 2020);
		assertTrue(carriage.equals(same));
		//两车厢不是同一个车厢
		Carriage different = new Carriage(2, "ErDengZuo", 100, 2020);
		assertFalse(carriage.equals(different));
	}

}
