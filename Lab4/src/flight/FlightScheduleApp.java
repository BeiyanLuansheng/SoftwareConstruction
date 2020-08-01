package flight;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.conflict.PlanningEntryAPIs;
import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;

import exceptions.*;

/**
 * �������Ӧ��
 */
public class FlightScheduleApp {
	private final JFrame frame = new JFrame();
	private final JPanel buttonPanel = new JPanel();
	private final Set<Plane> planes = new HashSet<>();
	private final Set<Location> locations = new HashSet<>();
	private final Board<Plane> board = new Board<>();
	private final PlanningEntryAPIs<Plane> checkConf = new PlanningEntryAPIs<>();
	private final Logger log = Logger.getLogger(FlightScheduleApp.class.getName());

	public FlightScheduleApp() {
		try {
			new FileWriter("src/log/flightlog.txt").close();
			Locale.setDefault(new Locale("en", "EN"));
			log.setLevel(Level.INFO);
			FileHandler fh = new FileHandler("src/log/flightlog.txt", true);
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������
	 */
	public void run() {

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

		JButton readLog = new JButton("Read log file");
		buttonPanel.add(readLog);
		readLog.addActionListener(event -> readLog());

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
		String planeInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		if (addPlane(plane)) {
			log.info("AddPlane;" + planeInfo + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddPlane;" + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "�˷ɻ��Ѵ��ڣ�");
		}
	}

	/**
	 * ��ӿ��÷ɻ���Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param plane ��ӵķɻ�
	 * @return true ��ӳɹ�;false ���ʧ�ܣ��ɻ��Ѵ���
	 */
	public boolean addPlane(Plane plane) {
		if (planes.contains(plane))
			return false;
		else {
			planes.add(plane);
			return true;
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
		String planeInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		try {
			if (removePlane(plane)) {
				log.info("RemovePlane;" + planeInfo + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemovePlane;" + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "�˷ɻ������ڣ�");
			}

		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemovePlane;" + planeInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ����Դ��") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemovePlane;EntryUseResourceOrLocationException:FlightScheduleApp.removePlane(plane);reselect resource");
				removePlane();
			} else
				log.warning("RemovePlane;EntryUseResourceOrLocationException:FlightScheduleApp.removePlane(plane);end");
		}
	}

	/**
	 * ɾ�������÷ɻ���Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param plane ��ɾ������Դ
	 * @return true ɾ���ɹ�;false ������
	 * @throws EntryUseResourceOrLocationException ��Դ��ʹ����
	 */
	public boolean removePlane(Plane plane) throws EntryUseResourceOrLocationException {
		if (planes.contains(plane)) {
			for (PlanningEntry<Plane> pe : board.getFlightEntries())
				if (((FlightEntry<Plane>) pe).getResource().equals(plane) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø���Դ������ɾ��");
			planes.remove(plane);
			return true;
		} else
			return false;
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("�������ƣ�");
		Location loc = new Location(name, true);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "�˻����Ѵ��ڣ�");
		}
	}

	/**
	 * ��ӿ���λ�õĹ���ʵ�ֲ���
	 * 
	 * @param loc ����ӵ�λ��
	 * @return true ��ӳɹ�;false λ���Ѵ���
	 */
	public boolean addLocation(Location loc) {
		if (locations.contains(loc))
			return false;
		else {
			locations.add(loc);
			return true;
		}
	}

	/**
	 * ɾ�������õ�λ��
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("�������ƣ�");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "�˻��������ڣ�");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ��λ�ã�") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:FlightScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:FlightScheduleApp.removeLocation(loc);end");
		}
	}

	/**
	 * ɾ�������õ�λ�õĹ���ʵ�ֲ���
	 * 
	 * @param loc ��ɾ����λ��
	 * @return true ɾ���ɹ�;false ������λ��
	 * @throws EntryUseResourceOrLocationException �мƻ�����ʹ�ø�λ��
	 */
	public boolean removeLocation(Location loc) throws EntryUseResourceOrLocationException {
		if (locations.contains(loc)) {
			for (PlanningEntry<Plane> pe : board.getFlightEntries())
				if ((((FlightEntry<Plane>) pe).getDeparture().equals(loc)
						|| ((FlightEntry<Plane>) pe).getArrival().equals(loc)) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø�λ�ã�����ɾ��");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * �ҵ�ָ���ĺ���ƻ���
	 * 
	 * @return �ǿ� ��Ӧ�ĺ���ƻ��� null û����Ӧ�ĺ���ƻ���
	 */
	private PlanningEntry<Plane> findFlightEntry(StringBuilder flightInfo) {
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
		flightInfo.append("Entry:" + name);
		flightInfo.append(",Departure" + departure);
		flightInfo.append(",Arrival:" + arrival);
		flightInfo.append(",StartTime:" + start);
		flightInfo.append(",EndTime:" + end);

		return findFlightEntry(name, departure, arrival, new Timeslot(start, end));
	}

	/**
	 * �ҵ�ָ���ļƻ���
	 * 
	 * @param name      �ƻ�����
	 * @param departure ����λ��
	 * @param arrival   ����λ��
	 * @param time      ʱ���
	 * @return �ǿ� ��Ӧ�ĺ���ƻ��� null û����Ӧ�ĺ���ƻ���
	 */
	public PlanningEntry<Plane> findFlightEntry(String name, String departure, String arrival, Timeslot time) {
		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (fe.getName().equals(name) && fe.getDeparture().getName().equals(departure)
					&& fe.getArrival().getName().equals(arrival) && fe.getTimeslot().equals(time))
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

		Location departureLoc = null, arrivalLoc = null;
		for (Location loc : locations) {
			if (loc.getName().equals(departure))
				departureLoc = loc;
			else if (loc.getName().equals(arrival))
				arrivalLoc = loc;
		}
		String flightInfo = "Entry:" + name + ",Departure:" + departure + ",Arrival:" + arrival + ",StartTime:" + start
				+ ",EndTime:" + end;
		if (departureLoc == null || arrivalLoc == null) {
			log.info("AddFlight;" + flightInfo + ";fail");
			JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
		} else if (addFlight(name, departureLoc, arrivalLoc, start, end)) {
			log.info("AddFlight;" + flightInfo + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddFlight;" + flightInfo + ";fail");
			JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
		}
	}

	/**
	 * ����һ���µļƻ���Ĺ���ʵ�ֲ���
	 * 
	 * @param name         ����ţ��ǿ�
	 * @param departureLoc ��ɻ������ǿ�
	 * @param arrivalLoc   ����������ǿգ�����ɻ�����ͬ
	 * @param start        ���ʱ�䣬��ʽyyyy-MM-dd
	 * @param end          ����ʱ�䣬��ʽyyyy-MM-dd
	 * @return true ��ӳɹ�;false �ƻ����Ѵ���
	 */
	public boolean addFlight(String name, Location departureLoc, Location arrivalLoc, String start, String end) {
		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (fe.getName().equals(name) && fe.getDeparture().equals(departureLoc)
					&& fe.getArrival().equals(arrivalLoc) && fe.getTimeslot().equals(new Timeslot(start, end)))
				return false;
		}
		PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
		board.addFlightEntry(flightEntry);
		return true;
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		try {
			if (cancelFlight(fe)) {
				log.info("CancelFlight;" + flightInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
			} else {
				log.info("CancelFlight;" + flightInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "û�иüƻ��ȡ��ʧ�ܣ�");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelFlight;" + flightInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ѡ��ȡ���ļƻ��") == JOptionPane.YES_OPTION) {
				log.warning("CancelFlight;CannotCancelledException:FlightScheduleApp.cancelFlight(fe);reselect entry");
				cancelFlight();
			} else
				log.warning("CancelFlight;CannotCancelledException:FlightScheduleApp.cancelFlight(fe);end");
		}

	}

	/**
	 * ȡ���ƻ����ʵ�ֲ���
	 * 
	 * @param fe ȡ���ļƻ���
	 * @return true ȡ���ɹ�;false ȡ��ʧ�ܣ��ƻ���Ϊ��
	 * @throws CannotCancelledException ��ǰ�ƻ���״̬����ȡ��
	 */
	public boolean cancelFlight(PlanningEntry<Plane> fe) throws CannotCancelledException {
		if (fe == null)
			return false;
		if (fe.cancel())
			return true;
		else
			throw new CannotCancelledException("��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		String id = JOptionPane.showInputDialog("�ɻ��ı�ţ�");
		String type = JOptionPane.showInputDialog("�ɻ����ͺţ�");
		String seats = JOptionPane.showInputDialog("�ɻ�����λ����");
		String age = JOptionPane.showInputDialog("�ɻ��Ļ��䣺");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		try {
			if (fe == null) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
			} else if (!planes.contains(plane)) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
			} else if (allocateFlight(fe, plane)) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";success");
				JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
			} else {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
			}
		} catch (ResourceOrLocationConflictException e) {
			log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n�����·�����Դ");
			log.warning(
					"AllocateFlight;ResourceOrLocationConflictException:FlightScheduleApp.allocateFlight(fe,plane);reallocate");
			allocateFlight();
		}
	}

	/**
	 * Ϊ�ƻ��������Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param fe    ������ļƻ���
	 * @param plane ���������Դ
	 * @return true ����ɹ�; false ����ʧ�ܣ��ƻ���״̬���ܷ�����Դ
	 * @throws ResourceOrLocationConflictException ��������������ƻ��������Դ��ռ��ͻ
	 */
	public boolean allocateFlight(PlanningEntry<Plane> fe, Plane plane) throws ResourceOrLocationConflictException {
		if (((FlightEntry<Plane>) fe).allocatePlane(plane)) {
			if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries()))
				throw new ResourceOrLocationConflictException("��������������ƻ��������Դ��ռ��ͻ");
			return true;
		} else
			return false;
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("RunFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		} else if (fe.running()) {
			log.info("RunFlight;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		} else {
			log.info("RunFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("EndFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		} else if (fe.end()) {
			log.info("EndFlight;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		} else {
			log.info("EndFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("GetState;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		} else {
			log.info("GetState;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + fe.getState());
		}
	}

	/**
	 * ��⵱ǰ�ļƻ�����п��ܴ��ڵ���Դ��ռ��ͻ
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries())) {
			log.info("CheckResourceConflict;existence;success");
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		} else {
			log.info("CheckResourcConflict;inexistence;success");
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
		}
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
		String planeInfo = "���:" + id + ",����:" + type + ",��λ��" + seats + ",����:" + age;
		if (!planes.contains(plane)) {
			log.info("FindAllEntryUseResource;" + planeInfo + ";fail");
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
		log.info("FindAllEntryUseResource;" + planeInfo + ";success");
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
		String planeInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (!planes.contains(plane)) {
			log.info("FindPreEntryUseResource;" + flightInfo + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
		} else if (fe == null) {
			log.info("FindPreEntryUseResource;" + flightInfo + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		} else {
			FlightEntry<Plane> prefe = (FlightEntry<Plane>) checkConf.findPreEntryPerResource(plane, fe,
					board.getFlightEntries());
			JOptionPane.showMessageDialog(null,
					"Entry:" + prefe.getName() + "\nTime:" + prefe.getTimeslot().toString() + "\nFrom:"
							+ prefe.getDeparture().getName() + "\nTo:" + prefe.getArrival().getName() + "\nState:"
							+ fe.getState());
			log.info("FindPreEntryUseResource;" + flightInfo + planeInfo + ";success");
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
			log.info("FindLocation;Name:" + name + ",Time:" + time + ";fail");
			JOptionPane.showMessageDialog(null, "�޴�λ�ã�");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Time:" + time + ";success");
		board.allEntryAtLocation(loc, time);
	}

	/**
	 * ���ļ��ж�ȡ����ƻ�����Ϣ
	 */
	public void readFileCreatFlightSchedule() {
		String filePath = JOptionPane.showInputDialog("�ļ�·����");
		try {
			List<PlanningEntry<Plane>> entryList = entries(filePath);
			for (PlanningEntry<Plane> pe : entryList) {
				board.addFlightEntry(pe);
				Plane plane = ((FlightEntry<Plane>) pe).getResource();
				Location departureLoc = ((FlightEntry<Plane>) pe).getDeparture();
				Location arrivalLoc = ((FlightEntry<Plane>) pe).getArrival();
				if (!planes.contains(plane))
					planes.add(plane);
				if (!locations.contains(departureLoc))
					locations.add(departureLoc);
				if (!locations.contains(arrivalLoc))
					locations.add(arrivalLoc);
			}
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";success");
		} catch (InherentFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;InherentFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;InherentFormatException:FlightSchedule.entry(filePath);end");
		} catch (DateTimeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;DateTimeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;DateTimeFormatException:FlightSchedule.entry(filePath);end");
		} catch (EntryNameFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;EntryNameFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;EntryNameFormatException:FlightSchedule.entry(filePath);end");
		} catch (AirportFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;AirportFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;AirportFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneCodeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneCodeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneCodeFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneTypeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneTypeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneTypeFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneSeatsFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneSeatsFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneSeatsFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneAgeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneAgeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneAgeFormatException:FlightSchedule.entry(filePath);end");
		} catch (DepartureDateInconsistentException e) { // �����쳣1
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				readFileCreatFlightSchedule();
			}
		} catch (ArrivalDateInconsistentException e) { // �����쳣2
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;ArrivalDateInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;ArrivalDateInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (SameInfoEntryException e) { // ��ǩ��ͬ�쳣
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;SameInfoEntryException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;SameInfoEntryException:FlightSchedule.entry(filePath);end");
		} catch (EntryInfoInconsistentException e) { // �����쳣3
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;EntryInfoInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;EntryInfoInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (PlaneInfoInconsistentException e) { // �����쳣4
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneInfoInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneInfoInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (FileNotFoundException e) { // �ļ�������
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, "�ļ�������\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);end");
		} catch (Exception e) { // ��ʽ�����µ�δ֪�쳣
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n�Ƿ�Ҫ�����ļ���") == JOptionPane.YES_OPTION) {
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);end");
		}
	}

	/**
	 * �ļ���Ϣ����
	 * 
	 * @param filePath �ļ�·��
	 * @return �����ļ������еļƻ�����Ϣ���б�
	 * @throws InherentFormatException            �ļ��̶���ʽ����
	 * @throws DateTimeFormatException            ʱ����Ϣ����
	 * @throws EntryNameFormatException           �ƻ������Ƹ�ʽ����
	 * @throws AirportFormatException             �������Ƹ�ʽ����
	 * @throws PlaneCodeFormatException           �ɻ���Ÿ�ʽ����
	 * @throws PlaneTypeFormatException           �ɻ����͸�ʽ����
	 * @throws PlaneSeatsFormatException          �ɻ���λ��ʽ����
	 * @throws PlaneAgeFormatException            �ɻ������ʽ����
	 * @throws DepartureDateInconsistentException ������ںͺ������ڲ�һ�´���
	 * @throws ArrivalDateInconsistentException   �������ںͺ������ڲ�೬��һ�����
	 * @throws SameInfoEntryException             ͬ��ͬ���ڼƻ������
	 * @throws EntryInfoInconsistentException     ��ͬ����ͬ���ƻ�����ʱ��ͻ�������ȫһ�´���
	 * @throws PlaneInfoInconsistentException     ͬ��ŷɻ���Ϣ��һ�´���
	 * @throws FileNotFoundException              �ļ�������
	 */
	public List<PlanningEntry<Plane>> entries(String filePath)
			throws InherentFormatException, DateTimeFormatException, EntryNameFormatException, AirportFormatException,
			PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException, PlaneAgeFormatException,
			DepartureDateInconsistentException, ArrivalDateInconsistentException, SameInfoEntryException,
			EntryInfoInconsistentException, PlaneInfoInconsistentException, FileNotFoundException {
		List<PlanningEntry<Plane>> entryList = new ArrayList<>();
		Scanner input = new Scanner(new File(filePath));
		// ��ֳ�����ƥ��
		String[] pattens = { "Flight:", "\\d{4}-\\d{2}-\\d{2}", ",", "[A-Z]{2}\\d{2,4}", "\\{", "DepartureAirport:",
				"[a-zA-Z]+", "ArrivalAirport:", "DepatureTime:", "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}", "ArrivalTime:",
				"Plane:", "[N|B]\\d{4}", "Type:", "[a-zA-Z0-9]+", "Seats:", "[1-6]\\d{2}|[5-9]\\d", "Age:",
				"(\\d|[1-2]\\d|30)\\.?\\d?", "\\}\\}" };
		List<String> stringList;
		int fileLineNum = 0;
		while (input.hasNext()) {
			int entryLineNum = 0;
			stringList = new ArrayList<>();
			String in = "", date = null, name = null, departure = null, arrival = null;
			String start = null, end = null, id = null, type = null, seats = null, age = null;
			Plane plane = null;
			while (!in.equals("}}")) {
				in = input.nextLine();
				entryLineNum++;
				if (in.equals("}")) {
					in = in + input.nextLine();
					entryLineNum++;
				}
				if (!in.equals(""))
					stringList.add(in);
			}
			// ����
			for (int i = 0; i < stringList.size(); i++) {
				String s = stringList.get(i);
				switch (i) {
				case 0:
					date = s.substring(7, 17);
					name = s.substring(18);
					if (!s.substring(0, 7).matches(pattens[0]) || !s.substring(17, 18).matches(pattens[2]))
						throw new InherentFormatException("��" + (fileLineNum + 1) + "�й̶���ʽ����");
					if (!date.matches(pattens[1]))
						throw new DateTimeFormatException("��" + (fileLineNum + 1) + "�����ڸ�ʽ����");
					if (!name.matches(pattens[3]))
						throw new EntryNameFormatException("��" + (fileLineNum + 1) + "�к���Ÿ�ʽ����");
					break;
				case 1:
					if (!s.matches(pattens[4]))
						throw new InherentFormatException("��" + (fileLineNum + 2) + "��������Ÿ�ʽ����");
					break;
				case 2:
					departure = s.substring(17);
					if (!s.substring(0, 17).matches(pattens[5]))
						throw new InherentFormatException("��" + (fileLineNum + 3) + "�й̶���ʽ����");
					if (!departure.matches(pattens[6]))
						throw new AirportFormatException("��" + (fileLineNum + 3) + "�л������Ƹ�ʽ����");
					break;
				case 3:
					arrival = s.substring(15);
					if (!s.substring(0, 15).matches(pattens[7]))
						throw new InherentFormatException("��" + (fileLineNum + 4) + "�й̶���ʽ����");
					if (!arrival.matches(pattens[6]))
						throw new AirportFormatException("��" + (fileLineNum + 4) + "�л������Ƹ�ʽ����");
					break;
				case 4:
					start = s.substring(13);
					if (!s.substring(0, 13).matches(pattens[8]))
						throw new InherentFormatException("��" + (fileLineNum + 5) + "�й̶���ʽ����");
					if (!start.matches(pattens[9]))
						throw new DateTimeFormatException("��" + (fileLineNum + 5) + "��ʱ���ʽ����");
					break;
				case 5:
					end = s.substring(12);
					if (!s.substring(0, 12).matches(pattens[10]))
						throw new InherentFormatException("��" + (fileLineNum + 6) + "�й̶���ʽ����");
					if (!end.matches(pattens[9]))
						throw new DateTimeFormatException("��" + (fileLineNum + 6) + "��ʱ���ʽ����");
					break;
				case 6:
					id = s.substring(6);
					if (!s.substring(0, 6).matches(pattens[11]))
						throw new InherentFormatException("��" + (fileLineNum + 7) + "�й̶���ʽ����");
					if (!id.matches(pattens[12]))
						throw new PlaneCodeFormatException("��" + (fileLineNum + 7) + "�зɻ���Ÿ�ʽ����");
					break;
				case 7:
					if (!s.matches(pattens[4]))
						throw new InherentFormatException("��" + (fileLineNum + 8) + "��������Ÿ�ʽ����");
					break;
				case 8:
					type = s.substring(5);
					if (!s.substring(0, 5).matches(pattens[13]))
						throw new InherentFormatException("��" + (fileLineNum + 9) + "�й̶���ʽ����");
					if (!type.matches(pattens[14]))
						throw new PlaneTypeFormatException("��" + (fileLineNum + 9) + "�зɻ����͸�ʽ����");
					break;
				case 9:
					seats = s.substring(6);
					if (!s.substring(0, 6).matches(pattens[15]))
						throw new InherentFormatException("��" + (fileLineNum + 10) + "�й̶���ʽ����");
					if (!seats.matches(pattens[16]))
						throw new PlaneSeatsFormatException("��" + (fileLineNum + 10) + "����λ����ʽ����");
					break;
				case 10:
					age = s.substring(4);
					if (!s.substring(0, 4).matches(pattens[17]))
						throw new InherentFormatException("��" + (fileLineNum + 11) + "�й̶���ʽ����");
					if (!age.matches(pattens[18]))
						throw new PlaneAgeFormatException("��" + (fileLineNum + 11) + "�л����ʽ����");
					break;
				case 11:
					if (!s.matches(pattens[19]))
						throw new InherentFormatException("��" + (fileLineNum + 12) + "�������Ÿ�ʽ����");
					break;
				default:
					throw new InherentFormatException("��" + fileLineNum + "�п�ʼ�ļƻ����ڲ���δ֪����");
				}

			}
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate flightDate = LocalDate.parse(date, df);
			LocalDate endDate = LocalDate.parse(end.substring(0, 10), df);
			LocalDate startDate = LocalDate.parse(start.substring(0, 10), df);
			if (!startDate.equals(flightDate))
				throw new DepartureDateInconsistentException("��" + (fileLineNum+1) + "�мƻ����������ںͺ������ڲ�һ��");
			if (!(endDate.equals(flightDate) || endDate.plusDays(-1).equals(flightDate)))
				throw new ArrivalDateInconsistentException("��" + (fileLineNum+1) + "�мƻ���Ľ������ںͺ������ڲ�೬��һ��");
			String charOfName = name.substring(0, 2);
			int numOfName = Integer.parseInt(name.substring(2));
			String startStr = start.substring(11);
			String endStr = end.substring(11);
			plane = new Plane(id, type, Integer.valueOf(seats), Float.valueOf(age));
			for (PlanningEntry<Plane> pe : entryList) {
				String peDate = ((FlightEntry<Plane>) pe).getTimeslot().getStartTimeString().substring(0, 10);
				String charOfpeName = pe.getName().substring(0, 2);
				int numOfpeName = Integer.parseInt(pe.getName().substring(2));
				String peDeparture = ((FlightEntry<Plane>) pe).getDeparture().getName();
				String peArrival = ((FlightEntry<Plane>) pe).getArrival().getName();
				String peStart = ((FlightEntry<Plane>) pe).getTimeslot().getStartTimeString().substring(11);
				String peEnd = ((FlightEntry<Plane>) pe).getTimeslot().getEndTimeString().substring(11);
				// ͬһ�������
				if (charOfpeName.equals(charOfName) && numOfpeName == numOfName) {
					// ����һ��
					if (peDate.equals(date))
						throw new SameInfoEntryException("��" + (fileLineNum+1) + "�мƻ���ı�ǩ��֮ǰ�ļƻ�����ȫһ��");
					// �����͵��������ȫ��ͬ
					if (!peDeparture.equals(departure) || !peArrival.equals(arrival))
						throw new EntryInfoInconsistentException("��" + (fileLineNum+1) + "�мƻ���ͬ�����𽵻�����ͬ");
					// �����͵���ʱ�䲻ȫ��ͬ
					if (!peStart.equals(startStr) || !peEnd.equals(endStr))
						throw new EntryInfoInconsistentException("��" + (fileLineNum+1) + "�мƻ���ͬ������ʱ�䲻ͬ");
				}
				// �ɻ������ͬ
				if (((FlightEntry<Plane>) pe).getResource().getID().equals(id)
						&& !((FlightEntry<Plane>) pe).getResource().equals(plane))
					throw new PlaneInfoInconsistentException("��" + (fileLineNum+1) + "�мƻ���ķɻ���֮ǰ��ͬ��ŷɻ���Ϣ��һ��");
			}
			fileLineNum += entryLineNum;
			Location departureLoc = new Location(departure, true);
			Location arrivalLoc = new Location(arrival, true);
			// �������������뵽�ƻ����б���
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			entryList.add(flightEntry);
			((FlightEntry<Plane>) flightEntry).allocatePlane(plane);
		}
		input.close();
		return entryList;
	}

	/**
	 * ��ʾ��־
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/flightlog.txt"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��־�ļ�����");
			return;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = null, end = null;
		JOptionPane.showMessageDialog(null, "�����������ݲ�����ʽ��������Ϊ����������");
		String startTime = JOptionPane.showInputDialog("��ѯʱ��εĿ�ʼʱ��\n��ʽyyyy-MM-dd HH:mm��");
		if (startTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			start = LocalDateTime.parse(startTime, df);
		String endTime = JOptionPane.showInputDialog("��ѯʱ��εĿ�ʼʱ��\n��ʽyyyy-MM-dd HH:mm��");
		if (endTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			end = LocalDateTime.parse(endTime, df);
		String actionType = JOptionPane.showInputDialog("��ѯ�������ͣ�");
		String entryName = JOptionPane.showInputDialog("��ѯ�ƻ�������");

		List<String[]> list = new ArrayList<>();
		DateFormat formatFrom = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss aa", Locale.ENGLISH);
		DateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			while (input.hasNext()) {
				String[] logline = new String[5];
				String line1 = input.nextLine();
				String line2 = input.nextLine();
				int pos = line1.indexOf("flight");
				logline[0] = line1.substring(0, pos - 1);
				Date date = formatFrom.parse(logline[0]);
				LocalDateTime time = LocalDateTime.parse(formatTo.format(date), df2);
				pos = line2.indexOf(":");
				logline[1] = line2.substring(0, pos);
				String[] message = line2.substring(pos+1).split(";");
				logline[2] = message[0];
				logline[3] = message[1];
				logline[4] = message[2];
				if ((start != null && start.isAfter(time)) || (end != null && end.isBefore(time)))
					continue;
				if (!actionType.equals("") && actionType.equals(logline[2]))
					continue;
				if (!entryName.equals("") && entryName.equals(logline[4]))
					continue;
				list.add(logline);
			}
		} catch (ParseException e) {
			return;
		}
		input.close();
		String[] title = { "Time", "Type", "Option", "Message", "Result" };
		Object[][] content = new Object[list.size()][5];
		for (int i = 0; i < list.size(); i++)
			content[i] = list.get(i);
		board.visualize(content, title, "log");
	}

	/**
	 * ������
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		new FlightScheduleApp().run();
	}
}
