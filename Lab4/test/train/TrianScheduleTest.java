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
	// ����addCarriage()
	// ����δ���б��У����������б���
	// ����removeCarriage()
	// ����δ���б��У��������б��У��������ڱ�ʹ��
	// ����addLocation()
	// λ��δ���б��У�λ�������б���
	// ����removeLocation()
	// λ��δ���б��У�λ�������б��У�λ����ʹ����
	// ����addTrain()
	// �ƻ�����ڣ��ƻ����Ѵ���
	// ����cancelTrain()
	// �ƻ���Ϊ�գ��ƻ��Ϊ�տ�ȡ�����ƻ��Ϊ�ղ���ȡ��
	// ����allocateTrain()
	// �ɷ�����Դ��״̬�����������Դ����������������ƻ��������Դ��ռ��ͻ
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	//test for addCarriage
	@Test
	public void testAddCarriage() {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//����δ���б���
		assertTrue(tsa.addCarriage(carriage));
		//���������б���
		assertFalse(tsa.addCarriage(carriage));
	}
	
	//tests for removeCarriage
	@Test
	public void testRemoveCarriage() throws EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Carriage carriage = new Carriage(1, "ErDengZuo", 100, 2020);
		//����δ���б���
		assertFalse(tsa.removeCarriage(carriage));
		//���������б���
		tsa.addCarriage(carriage);
		assertTrue(tsa.removeCarriage(carriage));
	}
	
	//�������ڱ�ʹ��
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
		// λ�ò����б���
		assertTrue(tsa.addLocation(loc));
		// λ�������б���
		assertFalse(tsa.addLocation(loc));
	}
	
	// tests for removeLocation
	@Test
	public void testRemoveLocation() throws EntryUseResourceOrLocationException {
		TrainScheduleApp tsa = new TrainScheduleApp();
		Location loc = new Location("Beijing", true);
		// λ�ò����б���
		assertFalse(tsa.removeLocation(loc));
		// λ�������б���
		tsa.addLocation(loc);
		assertTrue(tsa.removeLocation(loc));
	}
	
	//λ�����ڱ�ʹ��
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
		//�ƻ��������
		assertTrue(tsa.addTrain(name, locations, times));
		//�ƻ����Ѿ�����
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
		//�ƻ���Ϊ��
		assertFalse(tsa.cancelTrain(null));
		//�ƻ��Ϊ�գ���ȡ��
		assertTrue(tsa.cancelTrain(fe));
	}
	
	//����ȡ���ļƻ���
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
		//���Ա�������Դ
		assertTrue(tsa.allocateTrain(fe, train));
		//��ʱ״̬���ɱ�������Դ
		fe.running();
		assertFalse(tsa.allocateTrain(fe, train));
	}
	
	//���Է�������������ƻ��������Դ��ռ��ͻ
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
		//�ƻ���Aʹ����Դ
		tsa.addTrain(name, locations, times);
		PlanningEntry<Carriage> fe = tsa.findTrainEntry(name, locations, times);
		tsa.allocateTrain(fe, train);
		//�ƻ���Bʹ����Դ
		tsa.addTrain("B", locations, times);
		fe = tsa.findTrainEntry("B", locations, times);
		tsa.allocateTrain(fe, train);
	}
}
