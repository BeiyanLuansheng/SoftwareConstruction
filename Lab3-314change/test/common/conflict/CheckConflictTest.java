package common.conflict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import activity.ActivityEntry;
import activity.Material;
import common.location.Location;
import common.plan.PlanningEntry;
import flight.FlightEntry;
import flight.Plane;
import train.Carriage;
import train.TrainEntry;

public class CheckConflictTest {

	// Test strategy
	// ����checkLocationConflict()
	// �ƻ����б���û�мƻ���ƻ����б�ֻ��һ���ƻ���ƻ����б��������������ƻ���
	// �ƻ����в����ڳ�ͻ���ƻ����д��ڳ�ͻ
	// ����checkResourceExclusiveConflict()
	// ���ƻ������ͷ֣��ƻ���Ϊ����ƻ���ƻ���Ϊ�������μƻ���
	// ���ƻ��������֣��ƻ����б���û�мƻ���ƻ����б�ֻ��һ���ƻ���ƻ����б��������������ƻ���
	// ���ƻ����Ƿ��г�ͻ�֣��ƻ����в����ڳ�ͻ���ƻ����д��ڳ�ͻ
	// ����findPreEntryPerResource()
	// ���ƻ������ͷ֣��ƻ���Ϊ����ƻ���ƻ���Ϊ�������μƻ���
	// ���Ƿ�������������ļƻ���֣��������������ļƻ�����������������ļƻ���

	// tests for checkLocationConflict()
	@Test
	public void testCheckLocationConflict() {
		PlanningEntryAPIs<Material> cc = new PlanningEntryAPIs<>();
		CheckActivityEntryPlanA<Material> caepa = new CheckActivityEntryPlanA<>();
		List<PlanningEntry<Material>> activityEntries = new ArrayList<>();
		// �ռƻ����б�
		assertFalse(cc.checkLocationConflict(caepa, null));
		// û�мƻ�����յ��б�
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// ֻ��һ���ƻ�����б�
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// �����ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// ���ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.activityEntry("C3", "2020-04-26 11:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertTrue(cc.checkLocationConflict(caepa, activityEntries));
	}

	final Location loc1 = new Location("Beijing", true);
	final Location loc2 = new Location("Tianjin", true);
	final String t1 = "2020-04-26 10:00";
	final String t2 = "2020-04-26 11:00";
	final String t3 = "2020-04-26 12:00";
	final Carriage c1 = new Carriage(1, "ErDengZuo", 100, 2020);
	final Carriage c2 = new Carriage(2, "ErDengZuo", 100, 2020);
	final Carriage c3 = new Carriage(3, "ErDengZuo", 100, 2020);
	final Carriage c4 = new Carriage(4, "ErDengZuo", 100, 2020);
	final Carriage c5 = new Carriage(5, "ErDengZuo", 100, 2020);

	// tests for checkResourceExclusiveConflict()
	// ���Ժ���ƻ���
	@Test
	public void testFlightCheckResourceExclusConflict() {
		PlanningEntryAPIs<Plane> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		// �ռƻ����б�
		assertFalse(cc.checkResourceExclusiveConflict(null));
		// û�мƻ�����յ��б�
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// ֻ��һ���ƻ�����б�
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe = PlanningEntry.flightEntry("A1", loc1, null, loc2, "2020-04-26 10:00",
				"2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// �����ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.flightEntry("B2", loc1, null, loc2, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// ���ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.flightEntry("C3", loc1, null, loc2, "2020-04-26 11:00", "2020-04-27 13:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertTrue(cc.checkResourceExclusiveConflict(flightEntries));
	}

	// ���Ը������μƻ���
	@Test
	public void testTrainCheckResourceExclusConflict() {
		PlanningEntryAPIs<Carriage> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		// �ռƻ����б�
		assertFalse(cc.checkResourceExclusiveConflict(null));
		// û�мƻ�����յ��б�
		assertFalse(cc.checkResourceExclusiveConflict(trainEntries));
		// ֻ��һ���ƻ�����б�
		List<Location> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		List<Carriage> train = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		times.add(t1);
		times.add(t2);
		train.add(c1);
		train.add(c2);
		PlanningEntry<Carriage> pe = PlanningEntry.trainEntry("A1", locations, times);
		((TrainEntry<Carriage>) pe).allocateTrain(train);
		trainEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(trainEntries));
		// �����ڳ�ͻ�����������ƻ�����б�
		locations = new ArrayList<>();
		times = new ArrayList<>();
		train = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		times.add(t1);
		times.add(t2);
		train.add(c3);
		train.add(c4);
		pe = PlanningEntry.trainEntry("B2", locations, times);
		((TrainEntry<Carriage>) pe).allocateTrain(train);
		trainEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(trainEntries));
		// ���ڳ�ͻ�����������ƻ�����б�
		locations = new ArrayList<>();
		times = new ArrayList<>();
		train = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		times.add(t1);
		times.add(t2);
		train.add(c2);
		train.add(c4);
		train.add(c5);
		pe = PlanningEntry.trainEntry("C3", locations, times);
		((TrainEntry<Carriage>) pe).allocateTrain(train);
		trainEntries.add(pe);
		assertTrue(cc.checkResourceExclusiveConflict(trainEntries));
	}

	// tests for findPreEntryPerResource()
	// ���Ժ���ƻ���
	@Test
	public void testFlightFindPreEntryPerResource() {
		PlanningEntryAPIs<Plane> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		// ���������������ļƻ���
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe1 = PlanningEntry.flightEntry("A1", loc1, null, loc2, "2020-04-26 10:00",
				"2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe1).allocatePlane(plane);
		flightEntries.add(pe1);
		assertEquals(null, cc.findPreEntryPerResource(plane, pe1, flightEntries));
		// �������������ļƻ���
		PlanningEntry<Plane> pe2 = PlanningEntry.flightEntry("B2", loc1, null, loc2, "2020-04-27 10:00",
				"2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe2).allocatePlane(plane);
		flightEntries.add(pe2);
		assertEquals(pe1, cc.findPreEntryPerResource(plane, pe2, flightEntries));
	}

	// ���Ը������μƻ���
	@Test
	public void testTrainFindPreEntryPerResource() {
		PlanningEntryAPIs<Carriage> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		// ���������������ļƻ���
		List<Location> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		List<Carriage> train = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		times.add(t1);
		times.add(t2);
		train.add(c1);
		train.add(c2);
		PlanningEntry<Carriage> pe1 = PlanningEntry.trainEntry("A1", locations, times);
		((TrainEntry<Carriage>) pe1).allocateTrain(train);
		trainEntries.add(pe1);
		assertEquals(null, cc.findPreEntryPerResource(c2, pe1, trainEntries));
		// �������������ļƻ���
		locations = new ArrayList<>();
		times = new ArrayList<>();
		train = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		times.add(t2);
		times.add(t3);
		train.add(c2);
		train.add(c4);
		train.add(c5);
		PlanningEntry<Carriage> pe2 = PlanningEntry.trainEntry("B2", locations, times);
		((TrainEntry<Carriage>) pe2).allocateTrain(train);
		trainEntries.add(pe2);
		assertEquals(pe1, cc.findPreEntryPerResource(c2, pe2, trainEntries));
	}
}
