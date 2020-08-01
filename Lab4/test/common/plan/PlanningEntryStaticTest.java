package common.plan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import activity.ActivityEntry;
import activity.Material;
import common.location.Location;
import common.time.Timeslot;
import flight.FlightEntry;
import flight.Plane;
import train.Carriage;
import train.TrainEntry;

public class PlanningEntryStaticTest {

	// Test strategy
	// 测试flightEntry()
	// 通过getName()、getState()、getDeparture()、getArrival()、getTimeslot()观察
	// 测试trainEntry()
	// 通过getName()、getState()、getLocations()、getTimeslot()观察
	// 测试activityEntry()
	// 通过getName()、getState()、getLocation()、getTimeslot()观察

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	@Test
	public void testFlightEntry() {
		String name = "A";
		Location departure = new Location("Beijing", true);
		Location arrival = new Location("Shanghai", true);
		String start = "2020-04-26 10:00";
		String end = "2020-04-26 12:00";
		Timeslot time = new Timeslot(start, end);
		PlanningEntry<Plane> pe = PlanningEntry.flightEntry(name, departure, arrival, start, end);
		FlightEntry<Plane> fe = (FlightEntry<Plane>) pe;
		assertEquals(name, fe.getName());
		assertEquals("WAITING", fe.getState());
		assertEquals(departure, fe.getDeparture());
		assertEquals(arrival, fe.getArrival());
		assertEquals(time, fe.getTimeslot());
	}

	@Test
	public void testTrainEntry() {
		String name = "A";
		List<Location> locationList = new ArrayList<>();
		List<String> times = new ArrayList<>();
		List<Timeslot> timeslotList = new ArrayList<>();
		locationList.add(new Location("Beijing", true));
		locationList.add(new Location("Tianjing", true));
		locationList.add(new Location("Tangshan", true));
		locationList.add(new Location("Qinhuangdao", true));
		times.add("2020-04-26 07:55"); times.add("2020-04-26 08:40");
		timeslotList.add(new Timeslot(times.get(0), times.get(1)));
		times.add("2020-04-26 08:42"); times.add("2020-04-26 09:14");
		timeslotList.add(new Timeslot(times.get(2), times.get(3)));
		times.add("2020-04-26 09:16"); times.add("2020-04-26 09:53");
		timeslotList.add(new Timeslot(times.get(4), times.get(5)));

		PlanningEntry<Carriage> pe = PlanningEntry.trainEntry(name, locationList, times);
		TrainEntry<Carriage> te = (TrainEntry<Carriage>) pe;
		assertEquals(name, te.getName());
		assertEquals("WAITING", te.getState());
		assertEquals(locationList, te.getLocations());
		assertEquals(timeslotList, te.getTimeslot());
	}

	@Test
	public void testActivityEntry() {
		String name = "A";
		String start = "2020-04-26 10:00";
		String end = "2020-04-26 12:00";
		Timeslot time = new Timeslot(start, end);
		PlanningEntry<Material> pe = PlanningEntry.activityEntry(name, start, end);
		ActivityEntry<Material> ae = (ActivityEntry<Material>) pe;

		assertEquals(name, ae.getName());
		assertEquals("WAITING", ae.getState());
		assertEquals(time, ae.getTimeslot());
		assertNull(ae.getLocation());
	}

}
