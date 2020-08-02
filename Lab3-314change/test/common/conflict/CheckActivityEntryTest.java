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
	// ����checkLocationConflict()
	// �ƻ����б���û�мƻ���ƻ����б�ֻ��һ���ƻ���ƻ����б��������������ƻ���
	// �ƻ����в����ڳ�ͻ���ƻ����д��ڳ�ͻ

	// tests for CheckActivityEntryPlanA.checkLocationConflict()
	@Test
	public void testCheckLocationConflictPlanA() {
		CheckActivityEntry<Material> caec = new CheckActivityEntryPlanA<>();
		List<PlanningEntry<Material>> activityEntries = new ArrayList<>();
		// �ռƻ����б�
		assertFalse(caec.checkLocationConflict(null));
		// û�мƻ�����յ��б�
		assertFalse(caec.checkLocationConflict(activityEntries));
		// ֻ��һ���ƻ�����б�
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// �����ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// ���ڳ�ͻ�����������ƻ�����б�
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
		// �ռƻ����б�
		assertFalse(caec.checkLocationConflict(null));
		// û�мƻ�����յ��б�
		assertFalse(caec.checkLocationConflict(activityEntries));
		// ֻ��һ���ƻ�����б�
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A1", "2020-04-26 10:00", "2020-04-26 12:00");
		Location location = new Location("place", false);
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// �����ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.activityEntry("B2", "2020-04-26 12:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertFalse(caec.checkLocationConflict(activityEntries));
		// ���ڳ�ͻ�����������ƻ�����б�
		pe = PlanningEntry.activityEntry("C3", "2020-04-26 11:00", "2020-04-26 13:00");
		((ActivityEntry<Material>) pe).setLocation(location);
		activityEntries.add(pe);
		assertTrue(caec.checkLocationConflict(activityEntries));
	}
}
