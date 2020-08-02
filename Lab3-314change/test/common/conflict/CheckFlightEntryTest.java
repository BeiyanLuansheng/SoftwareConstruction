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
	// ����checkResourceExclusiveConflict()
	// �ƻ����б���û�мƻ���ƻ����б�ֻ��һ���ƻ���ƻ����б��������������ƻ���
	// �ƻ����в����ڳ�ͻ���ƻ����д��ڳ�ͻ
	// ����findPreEntryPerResource()
	// �������������ļƻ�����������������ļƻ���

	//tests for checkResourceExclusiveConflict()
	@Test
	public void testCheckResourceExclusConflict() {
		CheckFlightEntry<Plane> cfec = new CheckFlightEntry<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		//�ռƻ����б�
		assertFalse(cfec.checkResourceExclusiveConflict(null));
		//û�мƻ�����յ��б�
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//ֻ��һ���ƻ�����б�
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe = PlanningEntry.flightEntry("A1", departure, null, arrival, "2020-04-26 10:00", "2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//�����ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.flightEntry("B2", departure, null, arrival, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cfec.checkResourceExclusiveConflict(flightEntries));
		//���ڳ�ͻ�����������ƻ�����б�
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
		//���������������ļƻ���
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe1 = PlanningEntry.flightEntry("A1", departure, null, arrival, "2020-04-26 10:00", "2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe1).allocatePlane(plane);
		flightEntries.add(pe1);
		assertEquals(null, cfec.findPreEntryPerResource(plane, pe1, flightEntries));
		//�������������ļƻ���
		PlanningEntry<Plane> pe2 = PlanningEntry.flightEntry("B2", departure, null, arrival, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe2).allocatePlane(plane);
		flightEntries.add(pe2);
		assertEquals(pe1, cfec.findPreEntryPerResource(plane, pe2, flightEntries));
	}
}
