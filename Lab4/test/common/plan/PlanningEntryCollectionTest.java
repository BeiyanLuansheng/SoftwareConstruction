package common.plan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import activity.Material;

public class PlanningEntryCollectionTest {
	// Test strategy
	// ����AddEntries()
	// ��ӵļƻ���δnull���ƻ��Ϊnull
	// ����getEntries()
	// ��û�мƻ���ʱ�����мƻ���ʱ
	// ����sort()
	// �����ܹ�����ʱ����Ⱥ�˳����ƻ�������

	@Test
	public void testAddEntries() {
		PlanningEntryCollection<Material> pec = new PlanningEntryCollection<>();
		assertFalse(pec.addEntries(null));
		assertTrue(pec.addEntries(PlanningEntry.activityEntry("A", "2020-05-09 14:00", "2020-05-09 15:00")));
	}

	@Test
	public void testGetEntries() {
		PlanningEntryCollection<Material> pec = new PlanningEntryCollection<>();
		List<PlanningEntry<Material>> entries = new ArrayList<>();
		assertEquals(entries, pec.getEntries());
		PlanningEntry<Material> pe = PlanningEntry.activityEntry("A", "2020-05-09 14:00", "2020-05-09 15:00");
		pec.addEntries(pe);
		entries.add(pe);
		assertEquals(entries, pec.getEntries());
	}

	@Test
	public void testSort() {
		PlanningEntryCollection<Material> pec = new PlanningEntryCollection<>();
		List<PlanningEntry<Material>> entries = new ArrayList<>();
		PlanningEntry<Material> pe1 = PlanningEntry.activityEntry("A", "2020-05-09 14:00", "2020-05-09 15:00");
		PlanningEntry<Material> pe2 = PlanningEntry.activityEntry("A", "2020-05-09 15:00", "2020-05-09 16:00");
		entries.add(pe1);
		entries.add(pe2);
		pec.addEntries(pe2);
		pec.addEntries(pe1);
		pec.sort();
		assertEquals(entries, pec.getEntries());
	}
}
