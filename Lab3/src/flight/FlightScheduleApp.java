package flight;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.conflict.PlanningEntryAPIs;
import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;

/**
 * �������Ӧ��
 */
public class FlightScheduleApp {
	private JFrame frame = new JFrame();
	private JPanel buttonPanel = new JPanel();
	private Set<Plane> planes = new HashSet<>();
	private Set<Location> locations = new HashSet<>();
	private Board<Plane> board = new Board<>();
	private PlanningEntryAPIs<Plane> checkConf = new PlanningEntryAPIs<>();

	public FlightScheduleApp() {
		buttonPanel.setLayout(new GridLayout(8, 2));

		JButton addPlane = new JButton("Add Plane");
		buttonPanel.add(addPlane);
		addPlane.addActionListener(event -> addPlane());

		JButton removePlane = new JButton("Remove Plane");
		buttonPanel.add(removePlane);
		removePlane.addActionListener(event -> removePlane());

		JButton addLocation = new JButton("Add Location");
		buttonPanel.add(addLocation);
		addLocation.addActionListener(event -> addLocation());

		JButton removeLocation = new JButton("Remove Location");
		buttonPanel.add(removeLocation);
		removeLocation.addActionListener(event -> removeLocation());

		JButton addFlight = new JButton("Add FlightEntry");
		buttonPanel.add(addFlight);
		addFlight.addActionListener(event -> addFlight());

		JButton cancelFlight = new JButton("Cancel FlightEntry");
		buttonPanel.add(cancelFlight);
		cancelFlight.addActionListener(event -> cancelFlight());

		JButton allocateFlight = new JButton("Allocate FlightEntry");
		buttonPanel.add(allocateFlight);
		allocateFlight.addActionListener(event -> allocateFlight());

		JButton runFlight = new JButton("Run FlightEnrty");
		buttonPanel.add(runFlight);
		runFlight.addActionListener(event -> runFlight());

		JButton endFlight = new JButton("End FlightEntry");
		buttonPanel.add(endFlight);
		endFlight.addActionListener(event -> endFlight());

		JButton getState = new JButton("Get Entry State");
		buttonPanel.add(getState);
		getState.addActionListener(event -> getState());

		JButton checkConflict = new JButton("Check Resource Conflict");
		buttonPanel.add(checkConflict);
		checkConflict.addActionListener(event -> checkConflict());

		JButton findEntryUseResource = new JButton("Find All Entry Use Resource");
		buttonPanel.add(findEntryUseResource);
		findEntryUseResource.addActionListener(event -> findAllEntryUseResource());

		JButton findPreEntryUseResource = new JButton("Find PreEntry Use Resource");
		buttonPanel.add(findPreEntryUseResource);
		findPreEntryUseResource.addActionListener(event -> findPreEntryUseResource());

		JButton findLocation = new JButton("Find Entry At Location");
		buttonPanel.add(findLocation);
		findLocation.addActionListener(event -> findLocation());

		JButton readFile = new JButton("Read entry from file");
		buttonPanel.add(readFile);
		readFile.addActionListener(event -> readFileCreatFlightSchedule());

		frame.add(buttonPanel);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * ��ӿ��÷ɻ���Դ
	 */
	public void addPlane() {
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ����ͺţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		if (planes.contains(plane))
			JOptionPane.showMessageDialog(null, "�˷ɻ��Ѵ��ڣ�");
		else {
			planes.add(plane);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ�������÷ɻ���Դ
	 */
	public void removePlane() {
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ����ͺţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		if (planes.contains(plane)) {
			planes.remove(plane);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˷ɻ������ڣ�");
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("�������ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "�˻����Ѵ��ڣ�");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ�������õ�λ��
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("�������ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˻��������ڣ�");
	}

	/**
	 * �ҵ�ָ���ĺ���ƻ���
	 * 
	 * @return �ǿ� ��Ӧ�ĺ���ƻ��� null û����Ӧ�ĺ���ƻ���
	 */
	private PlanningEntry<Plane> findFlightEntry() {
		String name = JOptionPane.showInputDialog("����ţ�");
		String departure = JOptionPane.showInputDialog("��ɻ�����");
		String arrival = JOptionPane.showInputDialog("���������");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("���ʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");

		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (fe.getName().equals(name) && fe.getDeparture().getName().equals(departure)
					&& fe.getArrival().getName().equals(arrival) && fe.getTimeslot().equals(new Timeslot(start, end)))
				return fe;
		}
		return null;
	}

	/**
	 * ����һ���µļƻ���
	 */
	public void addFlight() {
		String name = JOptionPane.showInputDialog("����ţ�");
		String departure = JOptionPane.showInputDialog("��ɻ�����");
		String arrival = JOptionPane.showInputDialog("���������");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("���ʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");

		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (fe.getName().equals(name) && fe.getDeparture().getName().equals(departure)
					&& fe.getArrival().getName().equals(arrival) && fe.getTimeslot().equals(new Timeslot(start, end))) {
				JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
				return;
			}
		}
		Location departureLoc = null, arrivalLoc = null;
		for (Location loc : locations) {
			if (loc.getName().equals(departure))
				departureLoc = loc;
			else if (loc.getName().equals(arrival))
				arrivalLoc = loc;
		}
		if (departureLoc == null || arrivalLoc == null)
			JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
		else {
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			board.addFlightEntry(flightEntry);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ��ȡ��ʧ�ܣ�");
		else if (fe.cancel())
			JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));

		if (fe == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
		else if (!planes.contains(plane))
			JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
		else if (((FlightEntry<Plane>) fe).allocatePlane(plane))
			JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (fe.running())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (fe.end())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		else
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + fe.getState());
	}

	/**
	 * ��⵱ǰ�ļƻ�����п��ܴ��ڵ���Դ��ռ��ͻ
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries()))
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		else
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
	}

	/**
	 * ����û�ѡ����ĳ����Դ���г�ʹ�ø���Դ�����мƻ���
	 */
	public void findAllEntryUseResource() {
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ����ͺţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));

		if (!planes.contains(plane)) {
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
			return;
		}
		List<PlanningEntry<Plane>> list = new ArrayList<>();
		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (((FlightEntry<Plane>) fe).getResource().equals(plane))
				list.add(fe);
		}

		Object[][] str = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) list.get(i);
			String[] s = { fe.getName(), fe.getTimeslot().toString(),
					fe.getDeparture().getName() + "->" + fe.getArrival().getName(), fe.getState() };
			str[i] = s;
		}
		String[] title = { "Entry", "Time", "From and To", "State" };
		board.visualize(str, title, "ʹ�ø���Դ�����мƻ���");
	}

	/**
	 * ����û�ѡ����ĳ����Դ��ĳ���ƻ���֮���ҳ�����ǰ��ƻ���
	 */
	public void findPreEntryUseResource() {
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ����ͺţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		PlanningEntry<Plane> fe = findFlightEntry();
		if (!planes.contains(plane))
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
		else if (fe == null)
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		else {
			FlightEntry<Plane> prefe = (FlightEntry<Plane>) checkConf.findPreEntryPerResource(plane, fe,
					board.getFlightEntries());
			JOptionPane.showMessageDialog(null,
					"Entry:" + prefe.getName() + "\nTime:" + prefe.getTimeslot().toString() + "\nFrom:"
							+ prefe.getDeparture().getName() + "\nTo:" + prefe.getArrival().getName() + "\nState:"
							+ fe.getState());
		}
	}

	/**
	 * ѡ���ض�λ�ã����ӻ�չʾ��ǰʱ�̸�λ�õ���Ϣ��
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("���������֣�");
		String time = JOptionPane.showInputDialog("��ѯʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		while (!time.matches(regex))
			time = JOptionPane.showInputDialog("��ѯʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		Location loc = new Location(name, true);
		if (!locations.contains(loc)) {
			JOptionPane.showMessageDialog(null, "�޴�λ�ã�");
			return;
		}
		board.allEntryAtLocation(loc, time);
	}

	/**
	 * ���ļ��ж�ȡ����ƻ�����Ϣ
	 */
	public void readFileCreatFlightSchedule() {
		List<PlanningEntry<Plane>> entryList = new ArrayList<>();
		Scanner input = null;
		try {
			String filePath = JOptionPane.showInputDialog("�ļ�·����");
			input = new Scanner(new File(filePath));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�ļ�����");
		}
		String patten = "(Flight:\\d{4}-\\d{2}-\\d{2},[A-Z]{2}\\d{2,4}\\{DepartureAirport:[a-zA-Z]+"
				+ "ArrivalAirport:[a-zA-Z]+DepatureTime:\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"
				+ "ArrivalTime:\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}Plane:[N|B]\\d{4}\\{Type:[a-zA-Z0-9]+)"
				+ "(Seats:[1-6]\\d{2}|[5-9]\\d)(Age:(\\d|[1-2]\\d|30)\\.?\\d?\\}\\})";
		StringBuffer sb;
		while (input.hasNext()) {
			// ���벢ƥ��
			sb = new StringBuffer();
			String in = "";
			while (!in.equals("}")) {
				in = input.nextLine();
				sb.append(in);
			}
			in = input.nextLine();
			sb.append(in);
			String s = sb.toString();
			if (!s.matches(patten)) {
				input.close();
				JOptionPane.showMessageDialog(null, "�ƻ����ʽ����");
				return;
			}
			// ������Ϣ
			int departureAirportIndex = s.indexOf("DepartureAirport:");
			int arrivalAirportIndex = s.indexOf("ArrivalAirport:");
			int departureTimeIndex = s.indexOf("DepatureTime:");
			int seatsIndex = s.indexOf("Seats:");
			int ageIndex = s.indexOf("Age:");
			String date = s.substring(7, 17);
			String name = s.substring(18, departureAirportIndex - 1);
			String departure = s.substring(departureAirportIndex + 17, arrivalAirportIndex);
			String arrival = s.substring(arrivalAirportIndex + 15, departureTimeIndex);
			String start = s.substring(departureTimeIndex + 13, departureTimeIndex + 29);
			String end = s.substring(departureTimeIndex + 41, departureTimeIndex + 57);
			String id = s.substring(departureTimeIndex + 63, departureTimeIndex + 68);
			String type = s.substring(departureTimeIndex + 74, seatsIndex);
			String seats = s.substring(seatsIndex + 6, ageIndex);
			String age = s.substring(ageIndex + 4, s.length() - 2);
			// �ж��Ƿ����㿪ʼ����ʱ���Ҫ��
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate flightDate = LocalDate.parse(date, df);
			LocalDate endDate = LocalDate.parse(end.substring(0, 10), df);
			LocalDate startDate = LocalDate.parse(start.substring(0, 10), df);
			if (!startDate.equals(flightDate)
					|| !(endDate.equals(flightDate) || endDate.plusDays(-1).equals(flightDate))) {
				input.close();
				JOptionPane.showMessageDialog(null, "�ƻ����ʽ����");
				return;
			}
			// �ж��Ƿ��в�����ǰ�������ļƻ���
			String charOfName = name.substring(0, 2);
			int numOfName = Integer.parseInt(name.substring(2));
			String startStr = start.substring(11);
			String endStr = end.substring(11);
			for (PlanningEntry<Plane> pe : entryList) {
				String peDate = ((FlightEntry<Plane>) pe).getTimeslot().getEndTimeString().substring(0, 10);
				String charOfpeName = pe.getName().substring(0, 2);
				int numOfpeName = Integer.parseInt(pe.getName().substring(2));
				String peDeparture = ((FlightEntry<Plane>) pe).getDeparture().getName();
				String peArrival = ((FlightEntry<Plane>) pe).getArrival().getName();
				String peStart = ((FlightEntry<Plane>) pe).getTimeslot().getStartTimeString().substring(11);
				String peEnd = ((FlightEntry<Plane>) pe).getTimeslot().getEndTimeString().substring(11);
				// ͬһ�������
				if (charOfpeName.equals(charOfName) && numOfpeName == numOfName) {
					// ����һ��
					if (peDate.equals(date)) {
						JOptionPane.showMessageDialog(null, name + "�ƻ���ʱ���ͻ��");
						return;
					}
					// �����͵��������ȫ��ͬ
					if (!peDeparture.equals(departure) || !peArrival.equals(arrival)) {
						JOptionPane.showMessageDialog(null, "ͬ���ƻ���ص㲻ͬ��");
						return;
					}
					// �����͵���ʱ�䲻ȫ��ͬ
					if (!peStart.equals(startStr) || !peEnd.equals(endStr)) {
						JOptionPane.showMessageDialog(null, "ͬ���ƻ���ʱ�䲻ͬ��");
						return;
					}
				}
			}
			// �Ѳ��ڼ����е���Դ��λ�ü��뵽������
			Plane plane = new Plane(id, type, Integer.valueOf(seats), Float.valueOf(age));
			if (!planes.contains(plane))
				planes.add(plane);
			Location departureLoc = new Location(departure, true);
			Location arrivalLoc = new Location(arrival, true);
			if (!locations.contains(departureLoc))
				locations.add(departureLoc);
			if (!locations.contains(arrivalLoc))
				locations.add(arrivalLoc);
			// �������������뵽�ƻ����б���
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			board.addFlightEntry(flightEntry);
			entryList.add(flightEntry);
			((FlightEntry<Plane>) flightEntry).allocatePlane(plane);
		}
		input.close();
	}

	/**
	 * ������
	 */
	public static void main(String[] args) {
		new FlightScheduleApp();
	}
}
