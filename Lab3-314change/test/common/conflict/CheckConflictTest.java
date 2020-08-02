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
	// 测试checkLocationConflict()
	// 计划项列表中没有计划项，计划项列表只有一个计划项，计划项列表中至少有两个计划项
	// 计划项中不存在冲突，计划项中存在冲突
	// 测试checkResourceExclusiveConflict()
	// 按计划项类型分：计划项为航班计划项，计划项为高铁车次计划项
	// 按计划项数量分：计划项列表中没有计划项，计划项列表只有一个计划项，计划项列表中至少有两个计划项
	// 按计划项是否有冲突分：计划项中不存在冲突，计划项中存在冲突
	// 测试findPreEntryPerResource()
	// 按计划项类型分：计划项为航班计划项，计划项为高铁车次计划项
	// 按是否存在满足条件的计划项分：存在满足条件的计划项，不存在满足条件的计划项

	// tests for checkLocationConflict()
	@Test
	public void testCheckLocationConflict() {
		PlanningEntryAPIs<Material> cc = new PlanningEntryAPIs<>();
		CheckActivityEntryPlanA<Material> caepa = new CheckActivityEntryPlanA<>();
		List<PlanningEntry<Material>> activityEntries = new ArrayList<>();
		// 空计划项列表
		assertFalse(cc.checkLocationConflict(caepa, null));
		// 没有计划项但不空的列表
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// 只有一个计划项的列表
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// 不存在冲突的至少两个计划项的列表
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(cc.checkLocationConflict(caepa, activityEntries));
		// 存在冲突的至少两个计划项的列表
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
	// 测试航班计划项
	@Test
	public void testFlightCheckResourceExclusConflict() {
		PlanningEntryAPIs<Plane> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		// 空计划项列表
		assertFalse(cc.checkResourceExclusiveConflict(null));
		// 没有计划项但不空的列表
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// 只有一个计划项的列表
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe = PlanningEntry.flightEntry("A1", loc1, null, loc2, "2020-04-26 10:00",
				"2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// 不存在冲突的至少两个计划项的列表
		pe = PlanningEntry.flightEntry("B2", loc1, null, loc2, "2020-04-27 10:00", "2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertFalse(cc.checkResourceExclusiveConflict(flightEntries));
		// 存在冲突的至少两个计划项的列表
		pe = PlanningEntry.flightEntry("C3", loc1, null, loc2, "2020-04-26 11:00", "2020-04-27 13:00", null, null);
		((FlightEntry<Plane>) pe).allocatePlane(plane);
		flightEntries.add(pe);
		assertTrue(cc.checkResourceExclusiveConflict(flightEntries));
	}

	// 测试高铁车次计划项
	@Test
	public void testTrainCheckResourceExclusConflict() {
		PlanningEntryAPIs<Carriage> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		// 空计划项列表
		assertFalse(cc.checkResourceExclusiveConflict(null));
		// 没有计划项但不空的列表
		assertFalse(cc.checkResourceExclusiveConflict(trainEntries));
		// 只有一个计划项的列表
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
		// 不存在冲突的至少两个计划项的列表
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
		// 存在冲突的至少两个计划项的列表
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
	// 测试航班计划项
	@Test
	public void testFlightFindPreEntryPerResource() {
		PlanningEntryAPIs<Plane> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Plane>> flightEntries = new ArrayList<>();
		// 不存在满足条件的计划项
		Plane plane = new Plane("B6967", "A370", 332, 23.7);
		PlanningEntry<Plane> pe1 = PlanningEntry.flightEntry("A1", loc1, null, loc2, "2020-04-26 10:00",
				"2020-04-26 12:00", null, null);
		((FlightEntry<Plane>) pe1).allocatePlane(plane);
		flightEntries.add(pe1);
		assertEquals(null, cc.findPreEntryPerResource(plane, pe1, flightEntries));
		// 存在满足条件的计划项
		PlanningEntry<Plane> pe2 = PlanningEntry.flightEntry("B2", loc1, null, loc2, "2020-04-27 10:00",
				"2020-04-27 12:00", null, null);
		((FlightEntry<Plane>) pe2).allocatePlane(plane);
		flightEntries.add(pe2);
		assertEquals(pe1, cc.findPreEntryPerResource(plane, pe2, flightEntries));
	}

	// 测试高铁车次计划项
	@Test
	public void testTrainFindPreEntryPerResource() {
		PlanningEntryAPIs<Carriage> cc = new PlanningEntryAPIs<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		// 不存在满足条件的计划项
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
		// 存在满足条件的计划项
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
