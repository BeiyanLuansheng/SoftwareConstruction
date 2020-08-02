package flight;

import static org.junit.Assert.*;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryInstanceTest;

public class FlightEntryTest extends PlanningEntryInstanceTest<Plane> {

	// Test strategy
	// 测试allocatePlane()
	// 状态可分配资源，状态不可分配资源
	// 测试getResource()
	// 测试返回的资源与期望的是否相同
	// 测试getDeparture()和getArrival()
	// 测试返回的位置是否与期望的相同
	// 测试getTimeslot()
	// 测试返回的时刻表是否和预期的相同
	// 测试compareTo()
	// 在pe之前开始，在pe之后开始，在pe同时开始

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
		//状态可分配资源
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		assertTrue(((FlightEntry<Plane>) pe).allocatePlane(plane));
		//状态不可分配资源
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
		//在pe之前开始
        PlanningEntry<Plane> beforePe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 13:00", "2020-04-27 14:00", null, null);
        assertEquals(1, pe.compareTo(beforePe));
        //在pe之后开始
        PlanningEntry<Plane> afterPe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 15:00", "2020-04-27 16:00", null, null);
        assertEquals(-1, pe.compareTo(afterPe));
        //与pe同时开始
        PlanningEntry<Plane> equalPe = PlanningEntry.flightEntry(name, departure, null, arrival, "2020-04-27 14:00", "2020-04-27 15:00", null, null);
        assertEquals(0, pe.compareTo(equalPe));
	}
}
