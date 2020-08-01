package common.plan;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PlanningEntryInstanceTest<R> {

	// Test strategy
	// 测试allocate()
	// 此时的状态为WATIING或ALLOCATED，此时的状态不为WAITING和ALLOCATED
	// 测试running()
	// 此时状态为ALLOCATED或BLOCKED或RUNNING，此时状态不为ALLOCATED和BLOCKED和RUNNING
	// 测试cancel()
    // 此时状态为WATIING或ALLOCATED或BLOCKED或CANCELLED，此时状态不为WATIING和ALLOCATED和BLOCKED或CANCELLED
    // 测试end()
    // 此时状态为RUNNING或ENDED，此时状态不为RUNNING和ENDED
    // 测试getName()
    // 测试是否返回计划项的名称
	// 测试getState()
	// 测试是否正确返回计划项的状态
	
	public final String name = "A1";
	
	/**
	 * 创建一个计划项实例
	 * @return 一个计划项
	 */
	public abstract PlanningEntry<R> entryInstance();

	@Test
	public void testAllocate() {
		PlanningEntry<R> pe = entryInstance();
		assertTrue(pe.allocate()); //此时状态为WAITING
		assertTrue(pe.allocate()); //此时状态为ALLOCATED
		pe.running();
		assertFalse(pe.allocate()); //此时状态不为WAITING和ALLOCATED
	}

	@Test
	public void testRunning() {
		PlanningEntry<R> pe = entryInstance();
		assertFalse(pe.running()); //此时不为ALLOCATED和BLOCKED
		pe.allocate();
		assertTrue(pe.running()); //此时为ALLOCATED
		assertTrue(pe.running()); //此时为RUNNING
	}

	@Test
	public void testCancel() {
		PlanningEntry<R> pe1 = entryInstance();
		assertTrue(pe1.cancel()); //此时为WATIING
		
		PlanningEntry<R> pe2 = entryInstance();
		pe2.allocate();
		assertTrue(pe2.cancel()); //此时为ALLOCATED
		assertTrue(pe2.cancel()); //此时为CANCELLED
		
		PlanningEntry<R> pe3 = entryInstance();
		pe3.allocate();
		pe3.running();
		assertFalse(pe3.cancel()); //此时不为WATIING和ALLOCATED和BLOCKED和CANCELLED
	}

	@Test
	public void testEnd() {
		PlanningEntry<R> pe = entryInstance();
		assertFalse(pe.end()); //此时状态不为RUNNING和ENDED
		pe.allocate();
		assertFalse(pe.end()); //此时状态不为RUNNING和ENDED
		pe.running();
		assertTrue(pe.end()); //此时状态为RUNNING
		assertTrue(pe.end()); //此时状态为ENDED
	}

	@Test
	public void testGetName() {
		PlanningEntry<R> pe = entryInstance();
		assertEquals(name, pe.getName());
	}

	@Test
	public void testGetState() {
		PlanningEntry<R> pe = entryInstance();
		assertEquals("WAITING", pe.getState());
		pe.allocate();
		assertEquals("ALLOCATED", pe.getState());
		pe.running();
		assertEquals("RUNNING", pe.getState());
		pe.end();
		assertEquals("ENDED", pe.getState());
	}
}
