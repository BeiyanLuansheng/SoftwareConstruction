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
 * 航班管理应用
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
	 * 操作面板
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
	 * 添加可用飞机资源
	 */
	public void addPlane() {
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		if (addPlane(plane)) {
			log.info("AddPlane;" + planeInfo + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddPlane;" + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此飞机已存在！");
		}
	}

	/**
	 * 添加可用飞机资源的功能实现部分
	 * 
	 * @param plane 添加的飞机
	 * @return true 添加成功;false 添加失败，飞机已存在
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
	 * 删除不可用飞机资源
	 */
	public void removePlane() {
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		try {
			if (removePlane(plane)) {
				log.info("RemovePlane;" + planeInfo + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemovePlane;" + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "此飞机不存在！");
			}

		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemovePlane;" + planeInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定资源？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemovePlane;EntryUseResourceOrLocationException:FlightScheduleApp.removePlane(plane);reselect resource");
				removePlane();
			} else
				log.warning("RemovePlane;EntryUseResourceOrLocationException:FlightScheduleApp.removePlane(plane);end");
		}
	}

	/**
	 * 删除不可用飞机资源的功能实现部分
	 * 
	 * @param plane 待删除的资源
	 * @return true 删除成功;false 不存在
	 * @throws EntryUseResourceOrLocationException 资源被使用中
	 */
	public boolean removePlane(Plane plane) throws EntryUseResourceOrLocationException {
		if (planes.contains(plane)) {
			for (PlanningEntry<Plane> pe : board.getFlightEntries())
				if (((FlightEntry<Plane>) pe).getResource().equals(plane) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该资源，不能删除");
			planes.remove(plane);
			return true;
		} else
			return false;
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("机场名称：");
		Location loc = new Location(name, true);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "此机场已存在！");
		}
	}

	/**
	 * 添加可用位置的功能实现部分
	 * 
	 * @param loc 待添加的位置
	 * @return true 添加成功;false 位置已存在
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
	 * 删除不可用的位置
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("机场名称：");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "此机场不存在！");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定位置？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:FlightScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:FlightScheduleApp.removeLocation(loc);end");
		}
	}

	/**
	 * 删除不可用的位置的功能实现部分
	 * 
	 * @param loc 待删除的位置
	 * @return true 删除成功;false 不存在位置
	 * @throws EntryUseResourceOrLocationException 有计划项在使用该位置
	 */
	public boolean removeLocation(Location loc) throws EntryUseResourceOrLocationException {
		if (locations.contains(loc)) {
			for (PlanningEntry<Plane> pe : board.getFlightEntries())
				if ((((FlightEntry<Plane>) pe).getDeparture().equals(loc)
						|| ((FlightEntry<Plane>) pe).getArrival().equals(loc)) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该位置，不可删除");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * 找到指定的航班计划项
	 * 
	 * @return 非空 相应的航班计划项 null 没有相应的航班计划项
	 */
	private PlanningEntry<Plane> findFlightEntry(StringBuilder flightInfo) {
		String name = JOptionPane.showInputDialog("航班号：");
		String departure = JOptionPane.showInputDialog("起飞机场：");
		String arrival = JOptionPane.showInputDialog("降落机场：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("起飞时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("降落时间，格式yyyy-MM-dd HH:mm：");
		flightInfo.append("Entry:" + name);
		flightInfo.append(",Departure" + departure);
		flightInfo.append(",Arrival:" + arrival);
		flightInfo.append(",StartTime:" + start);
		flightInfo.append(",EndTime:" + end);

		return findFlightEntry(name, departure, arrival, new Timeslot(start, end));
	}

	/**
	 * 找到指定的计划项
	 * 
	 * @param name      计划项名
	 * @param departure 出发位置
	 * @param arrival   到达位置
	 * @param time      时间表
	 * @return 非空 相应的航班计划项 null 没有相应的航班计划项
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
	 * 增加一条新的计划项
	 */
	public void addFlight() {
		String name = JOptionPane.showInputDialog("航班号：");
		String departure = JOptionPane.showInputDialog("起飞机场：");
		String arrival = JOptionPane.showInputDialog("降落机场：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("起飞时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("降落时间，格式yyyy-MM-dd HH:mm：");

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
			JOptionPane.showMessageDialog(null, "无位置，添加失败！");
		} else if (addFlight(name, departureLoc, arrivalLoc, start, end)) {
			log.info("AddFlight;" + flightInfo + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddFlight;" + flightInfo + ";fail");
			JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
		}
	}

	/**
	 * 增加一条新的计划项的功能实现部分
	 * 
	 * @param name         航班号，非空
	 * @param departureLoc 起飞机场，非空
	 * @param arrivalLoc   降落机场，非空，和起飞机场不同
	 * @param start        起飞时间，格式yyyy-MM-dd
	 * @param end          降落时间，格式yyyy-MM-dd
	 * @return true 添加成功;false 计划项已存在
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
	 * 取消某个计划项
	 */
	public void cancelFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		try {
			if (cancelFlight(fe)) {
				log.info("CancelFlight;" + flightInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "取消成功！");
			} else {
				log.info("CancelFlight;" + flightInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "没有该计划项，取消失败！");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelFlight;" + flightInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新选择取消的计划项？") == JOptionPane.YES_OPTION) {
				log.warning("CancelFlight;CannotCancelledException:FlightScheduleApp.cancelFlight(fe);reselect entry");
				cancelFlight();
			} else
				log.warning("CancelFlight;CannotCancelledException:FlightScheduleApp.cancelFlight(fe);end");
		}

	}

	/**
	 * 取消计划项功能实现部分
	 * 
	 * @param fe 取消的计划项
	 * @return true 取消成功;false 取消失败，计划项为空
	 * @throws CannotCancelledException 当前计划项状态不可取消
	 */
	public boolean cancelFlight(PlanningEntry<Plane> fe) throws CannotCancelledException {
		if (fe == null)
			return false;
		if (fe.cancel())
			return true;
		else
			throw new CannotCancelledException("当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		try {
			if (fe == null) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
			} else if (!planes.contains(plane)) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
			} else if (allocateFlight(fe, plane)) {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";success");
				JOptionPane.showMessageDialog(null, "资源分配成功！");
			} else {
				log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
				JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
			}
		} catch (ResourceOrLocationConflictException e) {
			log.info("AllocateFlight;" + flightInfo.toString() + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n请重新分配资源");
			log.warning(
					"AllocateFlight;ResourceOrLocationConflictException:FlightScheduleApp.allocateFlight(fe,plane);reallocate");
			allocateFlight();
		}
	}

	/**
	 * 为计划项分配资源的功能实现部分
	 * 
	 * @param fe    待分配的计划项
	 * @param plane 待分配的资源
	 * @return true 分配成功; false 分配失败，计划项状态不能分配资源
	 * @throws ResourceOrLocationConflictException 分配后导致与其他计划项产生资源独占冲突
	 */
	public boolean allocateFlight(PlanningEntry<Plane> fe, Plane plane) throws ResourceOrLocationConflictException {
		if (((FlightEntry<Plane>) fe).allocatePlane(plane)) {
			if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries()))
				throw new ResourceOrLocationConflictException("分配后导致与其他计划项产生资源独占冲突");
			return true;
		} else
			return false;
	}

	/**
	 * 启动某个计划项
	 */
	public void runFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("RunFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，启动失败！");
		} else if (fe.running()) {
			log.info("RunFlight;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "启动成功！");
		} else {
			log.info("RunFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
		}
	}

	/**
	 * 结束某个计划项
	 */
	public void endFlight() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("EndFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		} else if (fe.end()) {
			log.info("EndFlight;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "结束成功！");
		} else {
			log.info("EndFlight;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
		}
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (fe == null) {
			log.info("GetState;" + flightInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		} else {
			log.info("GetState;" + flightInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + fe.getState());
		}
	}

	/**
	 * 检测当前的计划项集合中可能存在的资源独占冲突
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries())) {
			log.info("CheckResourceConflict;existence;success");
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		} else {
			log.info("CheckResourcConflict;inexistence;success");
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
		}
	}

	/**
	 * 针对用户选定的某个资源，列出使用该资源的所有计划项
	 */
	public void findAllEntryUseResource() {
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = "编号:" + id + ",机型:" + type + ",座位数" + seats + ",机龄:" + age;
		if (!planes.contains(plane)) {
			log.info("FindAllEntryUseResource;" + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此资源不存在！");
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
		board.visualize(str, title, "使用该资源的所有计划项");
	}

	/**
	 * 针对用户选定的某个资源和某个计划项之后，找出它的前序计划项
	 */
	public void findPreEntryUseResource() {
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		String planeInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",Age:" + age;
		StringBuilder flightInfo = new StringBuilder();
		PlanningEntry<Plane> fe = findFlightEntry(flightInfo);
		if (!planes.contains(plane)) {
			log.info("FindPreEntryUseResource;" + flightInfo + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此资源不存在！");
		} else if (fe == null) {
			log.info("FindPreEntryUseResource;" + flightInfo + planeInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
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
	 * 选定特定位置，可视化展示当前时刻该位置的信息板
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("机场的名字：");
		String time = JOptionPane.showInputDialog("查询时间，格式yyyy-MM-dd HH:mm：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		while (!time.matches(regex))
			time = JOptionPane.showInputDialog("查询时间，格式yyyy-MM-dd HH:mm：");
		Location loc = new Location(name, true);
		if (!locations.contains(loc)) {
			log.info("FindLocation;Name:" + name + ",Time:" + time + ";fail");
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Time:" + time + ";success");
		board.allEntryAtLocation(loc, time);
	}

	/**
	 * 从文件中读取航班计划项信息
	 */
	public void readFileCreatFlightSchedule() {
		String filePath = JOptionPane.showInputDialog("文件路径：");
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
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;InherentFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;InherentFormatException:FlightSchedule.entry(filePath);end");
		} catch (DateTimeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;DateTimeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;DateTimeFormatException:FlightSchedule.entry(filePath);end");
		} catch (EntryNameFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;EntryNameFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;EntryNameFormatException:FlightSchedule.entry(filePath);end");
		} catch (AirportFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;AirportFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;AirportFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneCodeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneCodeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneCodeFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneTypeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneTypeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneTypeFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneSeatsFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneSeatsFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneSeatsFormatException:FlightSchedule.entry(filePath);end");
		} catch (PlaneAgeFormatException e) {
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneAgeFormatException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;PlaneAgeFormatException:FlightSchedule.entry(filePath);end");
		} catch (DepartureDateInconsistentException e) { // 依赖异常1
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				readFileCreatFlightSchedule();
			}
		} catch (ArrivalDateInconsistentException e) { // 依赖异常2
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;ArrivalDateInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;ArrivalDateInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (SameInfoEntryException e) { // 标签相同异常
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;SameInfoEntryException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;SameInfoEntryException:FlightSchedule.entry(filePath);end");
		} catch (EntryInfoInconsistentException e) { // 依赖异常3
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;EntryInfoInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;EntryInfoInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (PlaneInfoInconsistentException e) { // 依赖异常4
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneInfoInconsistentException:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning(
						"ReadFileCreatFlightSchedule;PlaneInfoInconsistentException:FlightSchedule.entry(filePath);end");
		} catch (FileNotFoundException e) { // 文件不存在
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, "文件不存在\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);end");
		} catch (Exception e) { // 格式错误导致的未知异常
			log.info("ReadFileCreatFlightSchedule;FilePath:" + filePath + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n是否要更换文件？") == JOptionPane.YES_OPTION) {
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);reselect file");
				readFileCreatFlightSchedule();
			} else
				log.warning("ReadFileCreatFlightSchedule;Exception:FlightSchedule.entry(filePath);end");
		}
	}

	/**
	 * 文件信息处理
	 * 
	 * @param filePath 文件路径
	 * @return 包含文件中所有的计划项信息的列表
	 * @throws InherentFormatException            文件固定格式错误
	 * @throws DateTimeFormatException            时间信息错误
	 * @throws EntryNameFormatException           计划项名称格式错误
	 * @throws AirportFormatException             机场名称格式错误
	 * @throws PlaneCodeFormatException           飞机编号格式错误
	 * @throws PlaneTypeFormatException           飞机类型格式错误
	 * @throws PlaneSeatsFormatException          飞机座位格式错误
	 * @throws PlaneAgeFormatException            飞机机龄格式错误
	 * @throws DepartureDateInconsistentException 起飞日期和航班日期不一致错误
	 * @throws ArrivalDateInconsistentException   降落日期和航班日期差距超过一天错误
	 * @throws SameInfoEntryException             同名同日期计划项错误
	 * @throws EntryInfoInconsistentException     不同日期同名计划项起降时间和机场不完全一致错误
	 * @throws PlaneInfoInconsistentException     同编号飞机信息不一致错误
	 * @throws FileNotFoundException              文件不存在
	 */
	public List<PlanningEntry<Plane>> entries(String filePath)
			throws InherentFormatException, DateTimeFormatException, EntryNameFormatException, AirportFormatException,
			PlaneCodeFormatException, PlaneTypeFormatException, PlaneSeatsFormatException, PlaneAgeFormatException,
			DepartureDateInconsistentException, ArrivalDateInconsistentException, SameInfoEntryException,
			EntryInfoInconsistentException, PlaneInfoInconsistentException, FileNotFoundException {
		List<PlanningEntry<Plane>> entryList = new ArrayList<>();
		Scanner input = new Scanner(new File(filePath));
		// 拆分成数组匹配
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
			// 解析
			for (int i = 0; i < stringList.size(); i++) {
				String s = stringList.get(i);
				switch (i) {
				case 0:
					date = s.substring(7, 17);
					name = s.substring(18);
					if (!s.substring(0, 7).matches(pattens[0]) || !s.substring(17, 18).matches(pattens[2]))
						throw new InherentFormatException("第" + (fileLineNum + 1) + "行固定格式有误");
					if (!date.matches(pattens[1]))
						throw new DateTimeFormatException("第" + (fileLineNum + 1) + "行日期格式有误");
					if (!name.matches(pattens[3]))
						throw new EntryNameFormatException("第" + (fileLineNum + 1) + "行航班号格式有误");
					break;
				case 1:
					if (!s.matches(pattens[4]))
						throw new InherentFormatException("第" + (fileLineNum + 2) + "行左大括号格式有误");
					break;
				case 2:
					departure = s.substring(17);
					if (!s.substring(0, 17).matches(pattens[5]))
						throw new InherentFormatException("第" + (fileLineNum + 3) + "行固定格式有误");
					if (!departure.matches(pattens[6]))
						throw new AirportFormatException("第" + (fileLineNum + 3) + "行机场名称格式有误");
					break;
				case 3:
					arrival = s.substring(15);
					if (!s.substring(0, 15).matches(pattens[7]))
						throw new InherentFormatException("第" + (fileLineNum + 4) + "行固定格式有误");
					if (!arrival.matches(pattens[6]))
						throw new AirportFormatException("第" + (fileLineNum + 4) + "行机场名称格式有误");
					break;
				case 4:
					start = s.substring(13);
					if (!s.substring(0, 13).matches(pattens[8]))
						throw new InherentFormatException("第" + (fileLineNum + 5) + "行固定格式有误");
					if (!start.matches(pattens[9]))
						throw new DateTimeFormatException("第" + (fileLineNum + 5) + "行时间格式有误");
					break;
				case 5:
					end = s.substring(12);
					if (!s.substring(0, 12).matches(pattens[10]))
						throw new InherentFormatException("第" + (fileLineNum + 6) + "行固定格式有误");
					if (!end.matches(pattens[9]))
						throw new DateTimeFormatException("第" + (fileLineNum + 6) + "行时间格式有误");
					break;
				case 6:
					id = s.substring(6);
					if (!s.substring(0, 6).matches(pattens[11]))
						throw new InherentFormatException("第" + (fileLineNum + 7) + "行固定格式有误");
					if (!id.matches(pattens[12]))
						throw new PlaneCodeFormatException("第" + (fileLineNum + 7) + "行飞机编号格式有误");
					break;
				case 7:
					if (!s.matches(pattens[4]))
						throw new InherentFormatException("第" + (fileLineNum + 8) + "行左大括号格式有误");
					break;
				case 8:
					type = s.substring(5);
					if (!s.substring(0, 5).matches(pattens[13]))
						throw new InherentFormatException("第" + (fileLineNum + 9) + "行固定格式有误");
					if (!type.matches(pattens[14]))
						throw new PlaneTypeFormatException("第" + (fileLineNum + 9) + "行飞机机型格式有误");
					break;
				case 9:
					seats = s.substring(6);
					if (!s.substring(0, 6).matches(pattens[15]))
						throw new InherentFormatException("第" + (fileLineNum + 10) + "行固定格式有误");
					if (!seats.matches(pattens[16]))
						throw new PlaneSeatsFormatException("第" + (fileLineNum + 10) + "行座位数格式有误");
					break;
				case 10:
					age = s.substring(4);
					if (!s.substring(0, 4).matches(pattens[17]))
						throw new InherentFormatException("第" + (fileLineNum + 11) + "行固定格式有误");
					if (!age.matches(pattens[18]))
						throw new PlaneAgeFormatException("第" + (fileLineNum + 11) + "行机龄格式有误");
					break;
				case 11:
					if (!s.matches(pattens[19]))
						throw new InherentFormatException("第" + (fileLineNum + 12) + "行右括号格式有误");
					break;
				default:
					throw new InherentFormatException("第" + fileLineNum + "行开始的计划项内部有未知错误");
				}

			}
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate flightDate = LocalDate.parse(date, df);
			LocalDate endDate = LocalDate.parse(end.substring(0, 10), df);
			LocalDate startDate = LocalDate.parse(start.substring(0, 10), df);
			if (!startDate.equals(flightDate))
				throw new DepartureDateInconsistentException("第" + (fileLineNum+1) + "行计划项的起飞日期和航班日期不一致");
			if (!(endDate.equals(flightDate) || endDate.plusDays(-1).equals(flightDate)))
				throw new ArrivalDateInconsistentException("第" + (fileLineNum+1) + "行计划项的降落日期和航班日期差距超过一天");
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
				// 同一个航班号
				if (charOfpeName.equals(charOfName) && numOfpeName == numOfName) {
					// 日期一样
					if (peDate.equals(date))
						throw new SameInfoEntryException("第" + (fileLineNum+1) + "行计划项的标签与之前的计划项完全一样");
					// 出发和到达机场不全相同
					if (!peDeparture.equals(departure) || !peArrival.equals(arrival))
						throw new EntryInfoInconsistentException("第" + (fileLineNum+1) + "行计划项同名但起降机场不同");
					// 出发和到达时间不全相同
					if (!peStart.equals(startStr) || !peEnd.equals(endStr))
						throw new EntryInfoInconsistentException("第" + (fileLineNum+1) + "行计划项同名但起降时间不同");
				}
				// 飞机编号相同
				if (((FlightEntry<Plane>) pe).getResource().getID().equals(id)
						&& !((FlightEntry<Plane>) pe).getResource().equals(plane))
					throw new PlaneInfoInconsistentException("第" + (fileLineNum+1) + "行计划项的飞机与之前的同编号飞机信息不一致");
			}
			fileLineNum += entryLineNum;
			Location departureLoc = new Location(departure, true);
			Location arrivalLoc = new Location(arrival, true);
			// 满足条件，加入到计划项列表中
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			entryList.add(flightEntry);
			((FlightEntry<Plane>) flightEntry).allocatePlane(plane);
		}
		input.close();
		return entryList;
	}

	/**
	 * 显示日志
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/flightlog.txt"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "日志文件错误！");
			return;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = null, end = null;
		JOptionPane.showMessageDialog(null, "以下四项内容不填或格式不符均视为该项无限制");
		String startTime = JOptionPane.showInputDialog("查询时间段的开始时间\n格式yyyy-MM-dd HH:mm：");
		if (startTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			start = LocalDateTime.parse(startTime, df);
		String endTime = JOptionPane.showInputDialog("查询时间段的开始时间\n格式yyyy-MM-dd HH:mm：");
		if (endTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			end = LocalDateTime.parse(endTime, df);
		String actionType = JOptionPane.showInputDialog("查询操作类型：");
		String entryName = JOptionPane.showInputDialog("查询计划项名：");

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
	 * 主程序
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		new FlightScheduleApp().run();
	}
}
