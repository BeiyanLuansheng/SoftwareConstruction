package flight;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;

import org.junit.Test;
import org.junit.Rule;

import exceptions.*;

public class FlightScheduleTest {

	// Test stragey
	// 测试addPlane()
	// 飞机未在列表中，飞机已在列表中
	// 测试removePlane()
	// 飞机未在列表中，飞机在列表中，飞机正在被使用
	// 测试addLocation()
	// 位置未在列表中，位置已在列表中
	// 测试removeLocation()
	// 位置未在列表中，位置已在列表中，位置在使用中
	// 测试addFlight()
	// 计划项不存在，计划项已存在
	// 测试cancelFlight()
	// 计划项为空，计划项不为空可取消，计划项不为空不可取消
	// 测试allocateFlight()
	// 可分配资源，状态不允许分配资源，分配后导致与其他计划项产生资源独占冲突
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addPlane
	@Test
	public void testAddPlane() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//飞机未在列表中
		assertTrue(fsa.addPlane(plane));
		//飞机已在列表中
		assertFalse(fsa.addPlane(plane));
	}
	
	//tests for removePlane
	@Test
	public void testRemovePlane() throws EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//飞机未在列表中
		assertFalse(fsa.removePlane(plane));
		//飞机已在列表中
		fsa.addPlane(plane);
		assertTrue(fsa.removePlane(plane));
	}
	
	//飞机正在被使用
	@Test(expected = EntryUseResourceOrLocationException.class)
	public void testRemovePlaneException() throws ResourceOrLocationConflictException, EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		fsa.addLocation(arrivalLoc);
		fsa.addLocation(departureLoc);
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		fe.running();
		fsa.removePlane(plane);
	}
	
	// tests for addLocation
	@Test
	public void testAddLocation() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Location loc = new Location("Beijing", true);
		// 位置不在列表中
		assertTrue(fsa.addLocation(loc));
		// 位置已在列表中
		assertFalse(fsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Location loc = new Location("Beijing", true);
		// 位置不在列表中
		assertFalse(fsa.removeLocation(loc));
		// 位置已在列表中
		fsa.addLocation(loc);
		assertTrue(fsa.removeLocation(loc));
	}
	
	//位置正在被使用
	@Test(expected = EntryUseResourceOrLocationException.class)
	public void testRemoveLocationException() throws ResourceOrLocationConflictException, EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		fsa.addLocation(arrivalLoc);
		fsa.addLocation(departureLoc);
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		fe.running();
		fsa.removeLocation(departureLoc);
	}
	
	//tests for addFlight
	@Test
	public void testAddFlight() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		//计划项还不存在
		assertTrue(fsa.addFlight(name, departureLoc, arrivalLoc, start, end));
		//计划项已经存在
		assertFalse(fsa.addFlight(name, departureLoc, arrivalLoc, start, end));
	}
	
	//tests for cancelFlight
	@Test
	public void testCancelFlight() throws CannotCancelledException, ResourceOrLocationConflictException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		fsa.addLocation(arrivalLoc);
		fsa.addLocation(departureLoc);
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		//计划项为空
		assertFalse(fsa.cancelFlight(null));
		//计划项不为空，可取消
		assertTrue(fsa.cancelFlight(fe));
	}
	
	//不可取消的计划项
	@Test(expected = CannotCancelledException.class)
	public void testCancelFlightException() throws ResourceOrLocationConflictException, CannotCancelledException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		fsa.addLocation(arrivalLoc);
		fsa.addLocation(departureLoc);
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		fe.running();
		fsa.cancelFlight(fe);
	}
	
	//tests for allocateFlight
	@Test
	public void testAllocateFlight() throws ResourceOrLocationConflictException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		fsa.addLocation(arrivalLoc);
		fsa.addLocation(departureLoc);
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		//可以被分配资源
		assertTrue(fsa.allocateFlight(fe, plane));
		//此时状态不可被分配资源
		fe.running();
		assertFalse(fsa.allocateFlight(fe, plane));
	}
	
	//测试分配后导致与其他计划项产生资源独占冲突
	@Test(expected = ResourceOrLocationConflictException.class)
	public void testAllocateFlightException() throws ResourceOrLocationConflictException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addPlane(plane);
		//计划项A使用资源
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		//计划项B使用资源
		fsa.addFlight("B", departureLoc, arrivalLoc, start, end);
		fe = fsa.findFlightEntry("B", departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
	}
}
