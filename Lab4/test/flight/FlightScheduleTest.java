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
	// ����addPlane()
	// �ɻ�δ���б��У��ɻ������б���
	// ����removePlane()
	// �ɻ�δ���б��У��ɻ����б��У��ɻ����ڱ�ʹ��
	// ����addLocation()
	// λ��δ���б��У�λ�������б���
	// ����removeLocation()
	// λ��δ���б��У�λ�������б��У�λ����ʹ����
	// ����addFlight()
	// �ƻ�����ڣ��ƻ����Ѵ���
	// ����cancelFlight()
	// �ƻ���Ϊ�գ��ƻ��Ϊ�տ�ȡ�����ƻ��Ϊ�ղ���ȡ��
	// ����allocateFlight()
	// �ɷ�����Դ��״̬�����������Դ����������������ƻ��������Դ��ռ��ͻ
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addPlane
	@Test
	public void testAddPlane() {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//�ɻ�δ���б���
		assertTrue(fsa.addPlane(plane));
		//�ɻ������б���
		assertFalse(fsa.addPlane(plane));
	}
	
	//tests for removePlane
	@Test
	public void testRemovePlane() throws EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Plane plane = new Plane("B0741", "A380", 468, 1.1);
		//�ɻ�δ���б���
		assertFalse(fsa.removePlane(plane));
		//�ɻ������б���
		fsa.addPlane(plane);
		assertTrue(fsa.removePlane(plane));
	}
	
	//�ɻ����ڱ�ʹ��
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
		// λ�ò����б���
		assertTrue(fsa.addLocation(loc));
		// λ�������б���
		assertFalse(fsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		FlightScheduleApp fsa = new FlightScheduleApp();
		Location loc = new Location("Beijing", true);
		// λ�ò����б���
		assertFalse(fsa.removeLocation(loc));
		// λ�������б���
		fsa.addLocation(loc);
		assertTrue(fsa.removeLocation(loc));
	}
	
	//λ�����ڱ�ʹ��
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
		//�ƻ��������
		assertTrue(fsa.addFlight(name, departureLoc, arrivalLoc, start, end));
		//�ƻ����Ѿ�����
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
		//�ƻ���Ϊ��
		assertFalse(fsa.cancelFlight(null));
		//�ƻ��Ϊ�գ���ȡ��
		assertTrue(fsa.cancelFlight(fe));
	}
	
	//����ȡ���ļƻ���
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
		//���Ա�������Դ
		assertTrue(fsa.allocateFlight(fe, plane));
		//��ʱ״̬���ɱ�������Դ
		fe.running();
		assertFalse(fsa.allocateFlight(fe, plane));
	}
	
	//���Է�������������ƻ��������Դ��ռ��ͻ
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
		//�ƻ���Aʹ����Դ
		fsa.addFlight(name,  departureLoc, arrivalLoc, start, end);
		PlanningEntry<Plane> fe = fsa.findFlightEntry(name, departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
		//�ƻ���Bʹ����Դ
		fsa.addFlight("B", departureLoc, arrivalLoc, start, end);
		fe = fsa.findFlightEntry("B", departureLoc.getName(), arrivalLoc.getName(), new Timeslot(start, end));
		fsa.allocateFlight(fe, plane);
	}
}
