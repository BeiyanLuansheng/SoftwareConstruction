package train;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryInstanceTest;
import common.time.Timeslot;

public class TrainEntryTest extends PlanningEntryInstanceTest<Carriage> {

	// Test strategy
	// ����allocateTrain()
	// ״̬�ɷ�����Դ��״̬���ɷ�����Դ
	// ����getLocations()
	// ���Է��ص�λ���б��Ԥ�ڵ��Ƿ���ͬ
	// ����getResources()
	// ���Է��ص���Դ�б��Ԥ�ڵ��Ƿ���ͬ
	// ����getTimeslot()
	// ���Է��ص�ʱ�̱��Ԥ�ڵ��Ƿ���ͬ
	// ����block()
	// ��ʱ״̬ΪRUNNING��BLOCKED����ʱ״̬��ΪRUNNING��BLOCKED
	// ����compareTo()
	// ��pe֮ǰ��ʼ����pe֮��ʼ����peͬʱ��ʼ	

	final Location loc1 = new Location("place1", true);
	final Location loc2 = new Location("place2", true);
	final Location loc3 = new Location("place3", true);
	final String t0 = "2020-04-27 13:00";
	final String t1 = "2020-04-27 14:00";
	final String t2 = "2020-04-27 15:00";
	final String t3 = "2020-04-27 16:00";
	final String t4 = "2020-04-27 17:00";
	final String t5 = "2020-04-27 18:00";
	final Carriage c1 = new Carriage(1, "ErDengZuo", 100, 2020);
	final Carriage c2 = new Carriage(2, "ErDengZuo", 100, 2020);

	@Override
	public PlanningEntry<Carriage> entryInstance() {
		List<Location> locationList = new ArrayList<>();
		List<String> times = new ArrayList<>();
		locationList.add(loc1);
		locationList.add(loc2);
		locationList.add(loc3);
		times.add(t1);
		times.add(t2);
		times.add(t3);
		times.add(t4);
		return PlanningEntry.trainEntry(name, locationList, times);
	}

	@Test
	public void testAllocateTrain() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Carriage> train = new ArrayList<>();
		//״̬�ɷ�����Դ
		train.add(c1);
		train.add(c2);
		assertTrue(((TrainEntry<Carriage>) pe).allocateTrain(train));
		//״̬���ɷ�����Դ
		pe.cancel();
		assertFalse(((TrainEntry<Carriage>) pe).allocateTrain(train));
	}

	@Test
	public void testGetLocations() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Location> locations = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		locations.add(loc3);
		assertEquals(locations, ((TrainEntry<Carriage>) pe).getLocations());
	}

	@Test
	public void tsestGetResources() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Carriage> train = new ArrayList<>();
		train.add(c1);
		train.add(c2);
		((TrainEntry<Carriage>) pe).allocateTrain(train);
		assertEquals(train, ((TrainEntry<Carriage>) pe).getResources());
	}

	@Test
	public void testGetTimeslot() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Timeslot> times = new ArrayList<>();
		times.add(new Timeslot(t1, t2));
		times.add(new Timeslot(t3, t4));
		assertEquals(times, ((TrainEntry<Carriage>) pe).getTimeslot());
	}

	@Test
	public void testBlock() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Carriage> train = new ArrayList<>();
		train.add(c1);
		train.add(c2);
		((TrainEntry<Carriage>) pe).allocateTrain(train);
		//��ʱ״̬��ΪRUNNING��BLOCKED
		assertFalse(((TrainEntry<Carriage>) pe).block());
		//��ʱ״̬ΪRUNNING
		pe.running();
		assertTrue(((TrainEntry<Carriage>) pe).block());
		//��ʱ״̬ΪBLOCKED
		assertTrue(((TrainEntry<Carriage>) pe).block());
	}

	@Test
	public void testCompareTo() {
		PlanningEntry<Carriage> pe = entryInstance();
		List<Location> locations = new ArrayList<>();
		locations.add(loc1);
		locations.add(loc2);
		locations.add(loc3);
		//��pe֮ǰ��ʼ
		List<String> times1 = new ArrayList<>();
		times1.add(t0);
		times1.add(t2);
		times1.add(t3);
		times1.add(t4);
		PlanningEntry<Carriage> beforePe = PlanningEntry.trainEntry(name, locations, times1);
		assertEquals(1, pe.compareTo(beforePe));
		//��pe֮��ʼ
        List<String> times2 = new ArrayList<>();
		times2.add(t2);
		times2.add(t3);
		times2.add(t4);
		times2.add(t5);
        PlanningEntry<Carriage> afterPe = PlanningEntry.trainEntry(name, locations, times2);
		assertEquals(-1, pe.compareTo(afterPe));
		List<String> times3 = new ArrayList<>();
		times3.add(t1);
		times3.add(t2);
		times3.add(t3);
		times3.add(t4);
        //��peͬʱ��ʼ
        PlanningEntry<Carriage> equalPe = PlanningEntry.trainEntry(name, locations, times3);
        assertEquals(0, pe.compareTo(equalPe));
	}
}
