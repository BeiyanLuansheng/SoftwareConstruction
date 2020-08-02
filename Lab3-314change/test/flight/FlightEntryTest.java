package flight;

import static org.junit.Assert.*;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryInstanceTest;

public class FlightEntryTest extends PlanningEntryInstanceTest<Plane> {

	// Test strategy
	// ����allocatePlane()
	// ״̬�ɷ�����Դ��״̬���ɷ�����Դ
	// ����getResource()
	// ���Է��ص���Դ���������Ƿ���ͬ
	// ����getDeparture()��getArrival()
	// ���Է��ص�λ���Ƿ�����������ͬ
	// ����getTimeslot()
	// ���Է��ص�ʱ�̱��Ƿ��Ԥ�ڵ���ͬ
	// ����compareTo()
	// ��pe֮ǰ��ʼ����pe֮��ʼ����peͬʱ��ʼ

	final Location departure = new Location("Beijing", true);
	final Location arrival = new Location("Shanghai", true);

	@Override
	public PlanningEntry<Plane> entryInstance() {
		String start = "2020-04-27 14:00";
		String end = "2020-04-27 15:00";
		return PlanningEntry.flightEntry(name, departure, null, arrival, start, end, null, null);
	}

	@Test
	public void testAllocatePlane() {
		PlanningEntry<Plane> pe = entryInstance();
		//״̬�ɷ�����Դ
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertTrue(((FlightEntry<Plane>) pe).allocatePlane(plane));
		//״̬���ɷ�����Դ
		pe.cancel();
		assertFalse(((FlightEntry<Plane>) pe).allocatePlane(plane));
	}

	@Test
	public void testGetResource() {
		PlanningEntry<Plane> pe = entryInstance();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		assertEquals(plane, ((FlightEntry<Plane>) pe).getResource());
	}

	@Test
	public void testGetDepartureAndGetArrival() {
		PlanningEntry<Plane> pe = entryInstance();
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		assertEquals(departure, ((FlightEntry<Plane>) pe).getDeparture());
		assertEquals(arrival, ((FlightEntry<Plane>) pe).getArrival());
	}

	@Test
	public void testCompareTo() {
		PlanningEntry<Plane> pe = entryInstance();
		//��pe֮ǰ��ʼ
        PlanningEntry<Plane> beforePe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 13:00", "2020-04-27 14:00", null, null);
        assertEquals(1, pe.compareTo(beforePe));
        //��pe֮��ʼ
        PlanningEntry<Plane> afterPe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 15:00", "2020-04-27 16:00", null, null);
        assertEquals(-1, pe.compareTo(afterPe));
        //��peͬʱ��ʼ
        PlanningEntry<Plane> equalPe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 14:00", "2020-04-27 15:00", null, null);
        assertEquals(0, pe.compareTo(equalPe));
	}
}
