package common.conflict;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import activity.ActivityEntry;
import activity.Material;
import common.location.Location;
import common.plan.PlanningEntry;

public class CheckActivityEntryTest {

	// Test strategy
	// 测试checkLocationConflict()
	// 计划项列表中没有计划项，计划项列表只有一个计划项，计划项列表中至少有两个计划项
	// 计划项中不存在冲突，计划项中存在冲突

	// tests for CheckActivityEntryPlanA.checkLocationConflict()
	@Test
	public void testCheckLocationConflictPlanA() {
		CheckActivityEntry<Material> caec = new CheckActivityEntryPlanA<>();
		List<PlanningEntry<Material>> activityEntries = new ArrayList<>();
		// 空计划项列表
		assertFalse(caec.checkLocationConflict(null));
		// 没有计划项但不空的列表
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 只有一个计划项的列表
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 不存在冲突的至少两个计划项的列表
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 存在冲突的至少两个计划项的列表
		pe = PlanningEntry.activityEntry("C3", "2020-04-26 11:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertTrue(caec.checkLocationConflict(activityEntries));
	}

	// tests for CheckActivityEntryPlanB.checkLocationConflict()
	@Test
	public void testCheckLocationConflictPlanB() {
		CheckActivityEntry<Material> caec = new CheckActivityEntryPlanB<>();
		List<PlanningEntry<Material>> activityEntries = new ArrayList<>();
		// 空计划项列表
		assertFalse(caec.checkLocationConflict(null));
		// 没有计划项但不空的列表
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 只有一个计划项的列表
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 不存在冲突的至少两个计划项的列表
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// 存在冲突的至少两个计划项的列表
		pe = PlanningEntry.activityEntry("C3", "2020-04-26 11:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertTrue(caec.checkLocationConflict(activityEntries));
	}
}
