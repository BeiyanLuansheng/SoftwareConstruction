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
	// 测试addMaterial()
	// 材料未在列表中，材料已在列表中
	// 测试removeMaterial()
	// 材料未在列表中，材料在列表中，材料正在被使用
	// 测试addLocation()
	// 位置未在列表中，位置已在列表中
	// 测试removeLocation()
	// 位置未在列表中，位置已在列表中，位置在使用中
	// 测试addActivity()
	// 计划项不存在，计划项已存在，设置的位置导致与其他计划项产生位置独占冲突
	// 测试cancelActivity()
	// 计划项为空，计划项不为空可取消，计划项不为空不可取消
	// 测试allocateActivity()
	// 可分配资源，状态不允许分配资源
	// 测试setLocation()
	// 可设置位置，不可设置位置，设置为之后导致与其他计划项产生位置独占冲突
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addMaterial
	@Test
	public void testAddMaterial() {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		//材料未在列表中
		assertTrue(fsa.addMaterial(material));
		//材料已在列表中
		assertFalse(fsa.addMaterial(material));
	}
	
	//tests for removeMaterial
	@Test
	public void testRemoveMaterial() throws EntryUseResourceOrLocationException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
		Material material = new Material("material", "Learning department", "2020-04-26");
		//材料未在列表中
		assertFalse(fsa.removeMaterial(material));
		//材料已在列表中
		fsa.addMaterial(material);
		assertTrue(fsa.removeMaterial(material));
	}
	
	//材料正在被使用
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
		// 位置不在列表中
		assertTrue(fsa.addLocation(loc));
		// 位置已在列表中
		assertFalse(fsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		ActivityScheduleApp fsa = new ActivityScheduleApp();
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
		//计划项还不存在
		assertTrue(fsa.addActivity(name, start, end, Loc));
		//计划项已经存在
		assertFalse(fsa.addActivity(name, start, end, Loc));
	}

	//产生位置独占冲突
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
		//计划项为空
		assertFalse(fsa.cancelActivity(null));
		//计划项不为空，可取消
		assertTrue(fsa.cancelActivity(fe));
	}
	
	//不可取消的计划项
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
		//可以被分配资源
		assertTrue(fsa.allocateActivity(fe, material));
		//此时状态不可被分配资源
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
		//可以设置位置
		assertTrue(fsa.setLocation(fe, loc));
		//不可以设置位置
		fe.running();
		assertFalse(fsa.setLocation(fe, loc));
	}

	//产生位置独占冲突
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
