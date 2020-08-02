package common.plan;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PlanningEntryInstanceTest<R> {

	// Test strategy
	// ����allocate()
	// ��ʱ��״̬ΪWATIING��ALLOCATED����ʱ��״̬��ΪWAITING��ALLOCATED
	// ����running()
	// ��ʱ״̬ΪALLOCATED��BLOCKED��RUNNING����ʱ״̬��ΪALLOCATED��BLOCKED��RUNNING
	// ����cancel()
    // ��ʱ״̬ΪWATIING��ALLOCATED��BLOCKED��CANCELLED����ʱ״̬��ΪWATIING��ALLOCATED��BLOCKED��CANCELLED
    // ����end()
    // ��ʱ״̬ΪRUNNING��ENDED����ʱ״̬��ΪRUNNING��ENDED
    // ����getName()
    // �����Ƿ񷵻ؼƻ��������
	// ����getState()
	// �����Ƿ���ȷ���ؼƻ����״̬
	
	public final String name = "A1";
	
	/**
	 * ����һ���ƻ���ʵ��
	 * @return һ���ƻ���
	 */
	public abstract PlanningEntry<R> entryInstance();

	@Test
	public void testAllocate() {
		PlanningEntry<R> pe = entryInstance();
		assertTrue(pe.allocate()); //��ʱ״̬ΪWAITING
		assertTrue(pe.allocate()); //��ʱ״̬ΪALLOCATED
		pe.running();
		assertFalse(pe.allocate()); //��ʱ״̬��ΪWAITING��ALLOCATED
	}

	@Test
	public void testRunning() {
		PlanningEntry<R> pe = entryInstance();
		assertFalse(pe.running()); //��ʱ��ΪALLOCATED��BLOCKED
		pe.allocate();
		assertTrue(pe.running()); //��ʱΪALLOCATED
		assertTrue(pe.running()); //��ʱΪRUNNING
	}

	@Test
	public void testCancel() {
		PlanningEntry<R> pe1 = entryInstance();
		assertTrue(pe1.cancel()); //��ʱΪWATIING
		
		PlanningEntry<R> pe2 = entryInstance();
		pe2.allocate();
		assertTrue(pe2.cancel()); //��ʱΪALLOCATED
		assertTrue(pe2.cancel()); //��ʱΪCANCELLED
		
		PlanningEntry<R> pe3 = entryInstance();
		pe3.allocate();
		pe3.running();
		assertFalse(pe3.cancel()); //��ʱ��ΪWATIING��ALLOCATED��BLOCKED��CANCELLED
	}

	@Test
	public void testEnd() {
		PlanningEntry<R> pe = entryInstance();
		assertFalse(pe.end()); //��ʱ״̬��ΪRUNNING��ENDED
		pe.allocate();
		assertFalse(pe.end()); //��ʱ״̬��ΪRUNNING��ENDED
		pe.running();
		assertTrue(pe.end()); //��ʱ״̬ΪRUNNING
		assertTrue(pe.end()); //��ʱ״̬ΪENDED
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
