package activity;

import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;

import common.location.Location;
import common.plan.PlanningEntry;
import exceptions.CannotCancelledException;
import exceptions.EntryUseResourceOrLocationException;
import exceptions.ResourceOrLocationConflictException;

import org.junit.Test;
import org.junit.Rule;

public class ActivityScheduleTest {
	// Test stragey
	// ����addMaterial()
	// ����δ���б��У����������б���
	// ����removeMaterial()
	// ����δ���б��У��������б��У��������ڱ�ʹ��
	// ����addLocation()
	// λ��δ���б��У�λ�������б���
	// ����removeLocation()
	// λ��δ���б��У�λ�������б��У�λ����ʹ����
	// ����addActivity()
	// �ƻ�����ڣ��ƻ����Ѵ��ڣ����õ�λ�õ����������ƻ������λ�ö�ռ��ͻ
	// ����cancelActivity()
	// �ƻ���Ϊ�գ��ƻ��Ϊ�տ�ȡ�����ƻ��Ϊ�ղ���ȡ��
	// ����allocateActivity()
	// �ɷ�����Դ��״̬�����������Դ
	// ����setLocation()
	// ������λ�ã���������λ�ã�����Ϊ֮�����������ƻ������λ�ö�ռ��ͻ
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addMaterial
	@Test
	public void testAddMaterial() {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		//����δ���б���
		assertTrue(fsa.addMaterial(material));
		//���������б���
		assertFalse(fsa.addMaterial(material));
	}
	
	//tests for removeMaterial
	@Test
	public void testRemoveMaterial() throws EntryUseResourceOrLocationException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		//����δ���б���
		assertFalse(fsa.removeMaterial(material));
		//���������б���
		fsa.addMaterial(material);
		assertTrue(fsa.removeMaterial(material));
	}
	
	//�������ڱ�ʹ��
	@Test(expected = EntryUseResourceOrLocationException.class)
	public void testRemoveMaterialException() throws ResourceOrLocationConflictException, EntryUseResourceOrLocationException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		fsa.addActivity(name, start, end, Loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, Loc.getName(), start, end);
		fsa.allocateActivity(fe, material);
		fe.running();
		fsa.removeMaterial(material);
	}
	
	// tests for addLocation
	@Test
	public void testAddLocation() {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Location loc = new Location("Beijing", true);
		// λ�ò����б���
		assertTrue(fsa.addLocation(loc));
		// λ�������б���
		assertFalse(fsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
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
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		fsa.addActivity(name, start, end, Loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, Loc.getName(), start, end);
		fsa.allocateActivity(fe, material);
		fe.running();
		fsa.removeLocation(Loc);
	}
	
	//tests for addActivity
	@Test
	public void testAddActivity() throws ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		//�ƻ��������
		assertTrue(fsa.addActivity(name, start, end, Loc));
		//�ƻ����Ѿ�����
		assertFalse(fsa.addActivity(name, start, end, Loc));
	}

	//����λ�ö�ռ��ͻ
	@Test(expected = ResourceOrLocationConflictException.class)
	public void testAddActivityException() throws ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(loc);
		fsa.addActivity(name, start, end, loc);
		fsa.addActivity("B", start, end, loc);
	}

	//tests for cancelActivity
	@Test
	public void testCancelActivity() throws CannotCancelledException, ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		fsa.addActivity(name, start, end, Loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, Loc.getName(), start, end);
		fsa.allocateActivity(fe, material);
		//�ƻ���Ϊ��
		assertFalse(fsa.cancelActivity(null));
		//�ƻ��Ϊ�գ���ȡ��
		assertTrue(fsa.cancelActivity(fe));
	}
	
	//����ȡ���ļƻ���
	@Test(expected = CannotCancelledException.class)
	public void testCancelActivityException() throws ResourceOrLocationConflictException, CannotCancelledException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		fsa.addActivity(name, start, end, Loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, Loc.getName(), start, end);
		fsa.allocateActivity(fe, material);
		fe.running();
		fsa.cancelActivity(fe);
	}
	
	//tests for allocateActivity
	@Test
	public void testAllocateActivity() throws ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location Loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(Loc);
		fsa.addActivity(name, start, end, Loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, Loc.getName(), start, end);
		//���Ա�������Դ
		assertTrue(fsa.allocateActivity(fe, material));
		//��ʱ״̬���ɱ�������Դ
		fe.running();
		assertFalse(fsa.allocateActivity(fe, material));
	}

	//tests for setLocation
	@Test
	public void testSetLocation() throws ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(loc);
		fsa.addActivity(name, start, end, loc);
		PlanningEntry<Material> fe = fsa.findActivityEntry(name, loc.getName(), start, end);
		fsa.allocateActivity(fe, material);
		//��������λ��
		assertTrue(fsa.setLocation(fe, loc));
		//����������λ��
		fe.running();
		assertFalse(fsa.setLocation(fe, loc));
	}

	//����λ�ö�ռ��ͻ
	@Test(expected = ResourceOrLocationConflictException.class)
	public void testSetLocationException() throws ResourceOrLocationConflictException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		String name = "A";
		Location loc = new Location("Beijing", true);
		String start = "2020-05-27 11:00";
		String end = "2020-05-27 12:00";
		fsa.addMaterial(material);
		fsa.addLocation(loc);
		fsa.addActivity(name, start, end, loc);
		Location newLoc = new Location("Beijing", true);
		fsa.addLocation(newLoc);
		fsa.addActivity("B", start, end, newLoc);
		PlanningEntry<Material> fe = fsa.findActivityEntry("B", newLoc.getName(), start, end);
		fsa.setLocation(fe, loc);
	}
}
