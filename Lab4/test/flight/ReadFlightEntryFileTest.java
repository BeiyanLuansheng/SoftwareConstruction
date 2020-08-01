package flight;

import java.io.FileNotFoundException;

import org.junit.rules.ExpectedException;

import org.junit.Test;
import org.junit.Rule;

import exceptions.*;

public class ReadFlightEntryFileTest {

	// ����entries()
	// ����3.1�д�����쳣����ļ�������

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testInherentFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(InherentFormatException.class);
		exception.expectMessage("��1�й̶���ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/InherentFormatException.txt");
	}

	@Test
	public void testDateTimeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(DateTimeFormatException.class);
		exception.expectMessage("��5��ʱ���ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/DateTimeFormatException.txt");
	}

	@Test
	public void testEntryNameFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(EntryNameFormatException.class);
		exception.expectMessage("��1�к���Ÿ�ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/EntryNameFormatException.txt");
	}

	@Test
	public void testAirportFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(AirportFormatException.class);
		exception.expectMessage("��3�л������Ƹ�ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/AirportFormatException.txt");
	}

	@Test
	public void testPlaneCodeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneCodeFormatException.class);
		exception.expectMessage("��7�зɻ���Ÿ�ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneCodeFormatException.txt");
	}

	@Test
	public void testPlaneTypeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneTypeFormatException.class);
		exception.expectMessage("��9�зɻ����͸�ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneTypeFormatException.txt");
	}

	@Test
	public void testPlaneSeatsFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneSeatsFormatException.class);
		exception.expectMessage("��10����λ����ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneSeatsFormatException.txt");
	}

	@Test
	public void testPlaneAgeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneAgeFormatException.class);
		exception.expectMessage("��11�л����ʽ����");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneAgeFormatException.txt");
	}

	@Test
	public void testDepartureDateInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(DepartureDateInconsistentException.class);
		exception.expectMessage("��1�мƻ����������ںͺ������ڲ�һ��");
		new FlightScheduleApp().entries("test/flight/FlightTest/DepartureDateInconsistentException.txt");
	}

	@Test
	public void testArrivalDateInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(ArrivalDateInconsistentException.class);
		exception.expectMessage("��1�мƻ���Ľ������ںͺ������ڲ�೬��һ��");
		new FlightScheduleApp().entries("test/flight/FlightTest/ArrivalDateInconsistentException.txt");
	}

	@Test
	public void testSameInfoEntryException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(SameInfoEntryException.class);
		exception.expectMessage("��14�мƻ���ı�ǩ��֮ǰ�ļƻ�����ȫһ��");
		new FlightScheduleApp().entries("test/flight/FlightTest/SameInfoEntryException.txt");
	}

	@Test
	public void testEntryInfoInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(EntryInfoInconsistentException.class);
		exception.expectMessage("��14�мƻ���ͬ�����𽵻�����ͬ");
		new FlightScheduleApp().entries("test/flight/FlightTest/EntryInfoInconsistentException.txt");
	}

	@Test
	public void PlaneInfoInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneInfoInconsistentException.class);
		exception.expectMessage("��14�мƻ���ķɻ���֮ǰ��ͬ��ŷɻ���Ϣ��һ��");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneInfoInconsistentException.txt");
	}
}
