package common.conflict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import train.Carriage;
import train.TrainEntry;

public class CheckTrainEntryTest {

	// Test strategy
	// 测试checkResourceExclusiveConflict()
	// 计划项列表中没有计划项，计划项列表只有一个计划项，计划项列表中至少有两个计划项
	// 计划项中不存在冲突，计划项中存在冲突
	// 测试findPreEntryPerResource()
	// 存在满足条件的计划项，不存在满足条件的计划项

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

	//tests for checkResourceExclusiveConflict()
	@Test
	public void testCheckResourceExclusConflict() {
		CheckTrainEntry<Carriage> ctec = new CheckTrainEntry<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		//空计划项列表
		assertFalse(ctec.checkResourceExclusiveConflict(null));
		//没有计划项但不空的列表
		assertFalse(ctec.checkResourceExclusiveConflict(trainEntries));
		//只有一个计划项的列表
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
		assertFalse(ctec.checkResourceExclusiveConflict(trainEntries));
		//不存在冲突的至少两个计划项的列表
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
		assertFalse(ctec.checkResourceExclusiveConflict(trainEntries));
		//存在冲突的至少两个计划项的列表
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
		assertTrue(ctec.checkResourceExclusiveConflict(trainEntries));
	}

	//tests for findPreEntryPerResource()
	@Test
	public void testFindPreEntryPerResource() {
		CheckTrainEntry<Carriage> ctec = new CheckTrainEntry<>();
		List<PlanningEntry<Carriage>> trainEntries = new ArrayList<>();
		//不存在满足条件的计划项
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
		assertEquals(null, ctec.findPreEntryPerResource(c2, pe1, trainEntries));
		//存在满足条件的计划项
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
		assertEquals(pe1, ctec.findPreEntryPerResource(c2, pe2, trainEntries));
	}
}
