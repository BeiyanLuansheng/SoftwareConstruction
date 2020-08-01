package train;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.ExpectedException;

import common.location.Location;
import common.plan.PlanningEntry;
import exceptions.CannotCancelledException;
import exceptions.EntryUseResourceOrLocationException;
import exceptions.ResourceOrLocationConflictException;

import org.junit.Test;
import org.junit.Rule;

public class TrianScheduleTest {
	// Test stragey
	// 测试addCarriage()
	// 车厢未在列表中，车厢已在列表中
	// 测试removeCarriage()
	// 车厢未在列表中，车厢在列表中，车厢正在被使用
	// 测试addLocation()
	// 位置未在列表中，位置已在列表中
	// 测试removeLocation()
	// 位置未在列表中，位置已在列表中，位置在使用中
	// 测试addTrain()
	// 计划项不存在，计划项已存在
	// 测试cancelTrain()
	// 计划项为空，计划项不为空可取消，计划项不为空不可取消
	// 测试allocateTrain()
	// 可分配资源，状态不允许分配资源，分配后导致与其他计划项产生资源独占冲突
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addCarriage
	@Test
	public void testAddCarriage() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//车厢未在列表中
		assertTrue(tsa.addCarriage(carriage));
		//车厢已在列表中
		assertFalse(tsa.addCarriage(carriage));
	}
	
	//tests for removeCarriage
	@Test
	public void testRemoveCarriage() throws EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//车厢未在列表中
		assertFalse(tsa.removeCarriage(carriage));
		//车厢已在列表中
		tsa.addCarriage(carriage);
		assertTrue(tsa.removeCarriage(carriage));
	}
	
	//车厢正在被使用
	@Test(expected = EntryUseResourceOrLocationException.class)
	public void testRemoveCarriageException() throws ResourceOrLocationConflictException, EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		tsa.addCarriage(carriage);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		tsa.addTrain(name,  locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		fe.running();
		tsa.removeCarriage(carriage);
	}
	
	// tests for addLocation
	@Test
	public void testAddLocation() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Location loc = new Location("Beijing", true);
		// 位置不在列表中
		assertTrue(tsa.addLocation(loc));
		// 位置已在列表中
		assertFalse(tsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Location loc = new Location("Beijing", true);
		// 位置不在列表中
		assertFalse(tsa.removeLocation(loc));
		// 位置已在列表中
		tsa.addLocation(loc);
		assertTrue(tsa.removeLocation(loc));
	}
	
	//位置正在被使用
	@Test(expected = EntryUseResourceOrLocationException.class)
	public void testRemoveLocationException() throws ResourceOrLocationConflictException, EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		tsa.addTrain(name,  locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		fe.running();
		tsa.removeLocation(departureLoc);
	}
	
	//tests for addTrain
	@Test
	public void testAddTrain() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		//计划项还不存在
		assertTrue(tsa.addTrain(name, locations, times));
		//计划项已经存在
		assertFalse(tsa.addTrain(name, locations, times));
	}
	
	//tests for cancelTrain
	@Test
	public void testCancelTrain() throws CannotCancelledException, ResourceOrLocationConflictException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		tsa.addTrain(name,  locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		//计划项为空
		assertFalse(tsa.cancelTrain(null));
		//计划项不为空，可取消
		assertTrue(tsa.cancelTrain(fe));
	}
	
	//不可取消的计划项
	@Test(expected = CannotCancelledException.class)
	public void testCancelTrainException() throws ResourceOrLocationConflictException, CannotCancelledException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		tsa.addTrain(name,  locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		fe.running();
		tsa.cancelTrain(fe);
	}
	
	//tests for allocateTrain
	@Test
	public void testAllocateTrain() throws ResourceOrLocationConflictException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");
		tsa.addTrain(name,  locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		//可以被分配资源
		assertTrue(tsa.allocateTrain(fe, train));
		//此时状态不可被分配资源
		fe.running();
		assertFalse(tsa.allocateTrain(fe, train));
	}
	
	//测试分配后导致与其他计划项产生资源独占冲突
	@Test(expected = ResourceOrLocationConflictException.class)
	public void testAllocateTrainException() throws ResourceOrLocationConflictException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		List<Carriage> train = new ArrayList<>();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		train.add(carriage);
		String name = "A";
		Location departureLoc = new Location("Beijing", true);
		Location arrivalLoc  = new Location("Shanghai", true);
		tsa.addLocation(arrivalLoc);
		tsa.addLocation(departureLoc);
		List<Location> locations = new ArrayList<>();
		locations.add(departureLoc);
		locations.add(arrivalLoc);
		List<String> times = new ArrayList<>();
		times.add("2020-05-27 11:00");
		times.add("2020-05-27 12:00");	
		//计划项A使用资源
		tsa.addTrain(name, locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		//计划项B使用资源
		tsa.addTrain("B", locations, times);
		fe = tsa.findTrainEntry("B", locations, times);
		tsa.allocateTrain(fe, train);
	}
}
