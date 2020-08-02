package common.conflict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import flight.FlightEntry;
import flight.Plane;

public class CheckFlightEntryTest {

	// Test strategy
	// 测试checkResourceExclusiveConflict()
	// 计划项列表中没有计划项，计划项列表只有一个计划项，计划项列表中至少有两个计划项
	// 计划项中不存在冲突，计划项中存在冲突
	// 测试findPreEntryPerResource()
	// 存在满足条件的计划项，不存在满足条件的计划项

	//tests for checkResourceExclusiveConflict()
	@Test
	public void testCheckResourceExclusConflict() {
		CheckFlightEntry<Plane> cfec = new CheckFlightEntry<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		//空计划项列表
		assertFalse(cfec.checkResourceExclusiveConflict(null));
		//没有计划项但不空的列表
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//只有一个计划项的列表
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe = PlanningEntry.flightEntry("A1", departure, null, arrival, "2020-04-26 10:00", "2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//不存在冲突的至少两个计划项的列表
		pe = PlanningEntry.flightEntry("B2", departure, null, arrival, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//存在冲突的至少两个计划项的列表
		pe = PlanningEntry.flightEntry("C3", departure, null, arrival, "2020-04-26 11:00", "2020-04-27 13:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertTrue(cfec.checkResourceExclusiveConflict(flightEntries));
	}

	//tests for findPreEntryPerResource()
	@Test
	public void testFindPreEntryPerResource() {
		CheckFlightEntry<Plane> cfec = new CheckFlightEntry<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		//不存在满足条件的计划项
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe1 = PlanningEntry.flightEntry("A1", departure, null, arrival, "2020-04-26 10:00", "2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe1).allocatePlane(plane);
		flightEntries.add(pe1);
		assertEquals(null, cfec.findPreEntryPerResource(plane, pe1, flightEntries));
		//存在满足条件的计划项
		PlanningEntry<Plane> pe2 = PlanningEntry.flightEntry("B2", departure, null, arrival, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe2).allocatePlane(plane);
		flightEntries.add(pe2);
		assertEquals(pe1, cfec.findPreEntryPerResource(plane, pe2, flightEntries));
	}
}
