package debug;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FlightClientTest {

    // Test strategy
    // 飞机数大于等于航班数
    // 飞机数小于航班数：不存在冲突，存在冲突

    FlightClient fc = new FlightClient();

    @Test
    public void testMorePlane(){
        List<Plane> planes = new ArrayList<>();
        Plane plane = new Plane();
        plane.setPlaneNo("A1");
        planes.add(plane);
        plane = new Plane();
        plane.setPlaneNo("A2");
        planes.add(plane);


        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        Calendar departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 11, 0);
        Calendar arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 12, 0);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        flight = new Flight();
        departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 11, 30);
        arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 13, 0);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        assertTrue(fc.planeAllocation(planes, flights));
    }

    @Test
    public void testNoConflict(){
        List<Plane> planes = new ArrayList<>();
        Plane plane = new Plane();
        plane.setPlaneNo("A1");
        planes.add(plane);

        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        Calendar departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 11, 0);
        Calendar arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 12, 0);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        flight = new Flight();
        departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 12, 0);
        arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 13, 0);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        assertTrue(fc.planeAllocation(planes, flights));
    }

    @Test
    public void testConflict(){
        List<Plane> planes = new ArrayList<>();
        Plane plane = new Plane();
        plane.setPlaneNo("A1");
        planes.add(plane);

        List<Flight> flights = new ArrayList<>();
        Flight flight = new Flight();
        Calendar departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 11, 0);
        Calendar arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 12, 0);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        flight = new Flight();
        departTime = Calendar.getInstance();
        departTime.set(2020, 5, 28, 11, 30);
        arrivalTime = Calendar.getInstance();
        arrivalTime.set(2020, 5, 28, 12, 30);
        flight.setDepartTime(departTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        assertFalse(fc.planeAllocation(planes, flights));
    }
}