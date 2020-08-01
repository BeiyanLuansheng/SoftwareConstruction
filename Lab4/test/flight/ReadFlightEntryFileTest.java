package flight;

import java.io.FileNotFoundException;

import org.junit.rules.ExpectedException;

import org.junit.Test;
import org.junit.Rule;

import exceptions.*;

public class ReadFlightEntryFileTest {

	// 测试entries()
	// 根据3.1中处理的异常设计文件并测试

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testInherentFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(InherentFormatException.class);
		exception.expectMessage("第1行固定格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/InherentFormatException.txt");
	}

	@Test
	public void testDateTimeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(DateTimeFormatException.class);
		exception.expectMessage("第5行时间格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/DateTimeFormatException.txt");
	}

	@Test
	public void testEntryNameFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(EntryNameFormatException.class);
		exception.expectMessage("第1行航班号格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/EntryNameFormatException.txt");
	}

	@Test
	public void testAirportFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(AirportFormatException.class);
		exception.expectMessage("第3行机场名称格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/AirportFormatException.txt");
	}

	@Test
	public void testPlaneCodeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneCodeFormatException.class);
		exception.expectMessage("第7行飞机编号格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneCodeFormatException.txt");
	}

	@Test
	public void testPlaneTypeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneTypeFormatException.class);
		exception.expectMessage("第9行飞机机型格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneTypeFormatException.txt");
	}

	@Test
	public void testPlaneSeatsFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneSeatsFormatException.class);
		exception.expectMessage("第10行座位数格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneSeatsFormatException.txt");
	}

	@Test
	public void testPlaneAgeFormatException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneAgeFormatException.class);
		exception.expectMessage("第11行机龄格式有误");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneAgeFormatException.txt");
	}

	@Test
	public void testDepartureDateInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(DepartureDateInconsistentException.class);
		exception.expectMessage("第1行计划项的起飞日期和航班日期不一致");
		new FlightScheduleApp().entries("test/flight/FlightTest/DepartureDateInconsistentException.txt");
	}

	@Test
	public void testArrivalDateInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(ArrivalDateInconsistentException.class);
		exception.expectMessage("第1行计划项的降落日期和航班日期差距超过一天");
		new FlightScheduleApp().entries("test/flight/FlightTest/ArrivalDateInconsistentException.txt");
	}

	@Test
	public void testSameInfoEntryException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(SameInfoEntryException.class);
		exception.expectMessage("第14行计划项的标签与之前的计划项完全一样");
		new FlightScheduleApp().entries("test/flight/FlightTest/SameInfoEntryException.txt");
	}

	@Test
	public void testEntryInfoInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(EntryInfoInconsistentException.class);
		exception.expectMessage("第14行计划项同名但起降机场不同");
		new FlightScheduleApp().entries("test/flight/FlightTest/EntryInfoInconsistentException.txt");
	}

	@Test
	public void PlaneInfoInconsistentException()
			throws FileNotFoundException, InherentFormatException, DateTimeFormatException, EntryNameFormatException,
			AirportFormatException, PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException,
			PlaneAgeFormatException, DepartureDateInconsistentException, ArrivalDateInconsistentException,
			SameInfoEntryException, EntryInfoInconsistentException, PlaneInfoInconsistentException {
		exception.expect(PlaneInfoInconsistentException.class);
		exception.expectMessage("第14行计划项的飞机与之前的同编号飞机信息不一致");
		new FlightScheduleApp().entries("test/flight/FlightTest/PlaneInfoInconsistentException.txt");
	}
}
