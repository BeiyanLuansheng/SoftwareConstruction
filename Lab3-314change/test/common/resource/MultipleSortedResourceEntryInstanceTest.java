package common.resource;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import train.Carriage;

public abstract class MultipleSortedResourceEntryInstanceTest {

	// Test strategy
	// 测试getResources()
	// 测试返回的资源是否与期望的相等

	/**
	 * 创建一个资源列表
	 * @param train 资源列表
	 * @return 一个有序的资源列表
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
