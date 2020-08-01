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
 * 航班管理应用
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
	 * 添加可用飞机资源
	 */
	public void addPlane() {
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的型号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));
		if (planes.contains(plane))
			JOptionPane.showMessageDialog(null, "此飞机已存在！");
		else {
			planes.add(plane);
			JOptionPane.showMessageDialog(null, "添加成功！");
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
		if (planes.contains(plane)) {
			planes.remove(plane);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此飞机不存在！");
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("机场名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "此机场已存在！");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 删除不可用的位置
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("机场名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此机场不存在！");
	}

	/**
	 * 找到指定的航班计划项
	 * 
	 * @return 非空 相应的航班计划项 null 没有相应的航班计划项
	 */
	private PlanningEntry<Plane> findFlightEntry() {
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

		Iterator<PlanningEntry<Plane>> iter = board.iterator();
		while (iter.hasNext()) {
			FlightEntry<Plane> fe = (FlightEntry<Plane>) iter.next();
			if (fe.getName().equals(name) && fe.getDeparture().getName().equals(departure)
					&& fe.getArrival().getName().equals(arrival) && fe.getTimeslot().equals(new Timeslot(start, end))) {
				JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
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
			JOptionPane.showMessageDialog(null, "无位置，添加失败！");
		else {
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			board.addFlightEntry(flightEntry);
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 取消某个计划项
	 */
	public void cancelFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，取消失败！");
		else if (fe.cancel())
			JOptionPane.showMessageDialog(null, "取消成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		String id = JOptionPane.showInputDialog("飞机的编号：");
		String type = JOptionPane.showInputDialog("飞机的编号：");
		String seats = JOptionPane.showInputDialog("飞机的座位数：");
		String age = JOptionPane.showInputDialog("飞机的机龄：");
		Plane plane = new Plane(id, type, Integer.parseInt(seats), Double.parseDouble(age));

		if (fe == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
		else if (!planes.contains(plane))
			JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
		else if (((FlightEntry<Plane>) fe).allocatePlane(plane))
			JOptionPane.showMessageDialog(null, "资源分配成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
	}

	/**
	 * 启动某个计划项
	 */
	public void runFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，启动失败！");
		else if (fe.running())
			JOptionPane.showMessageDialog(null, "启动成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
	}

	/**
	 * 结束某个计划项
	 */
	public void endFlight() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		else if (fe.end())
			JOptionPane.showMessageDialog(null, "结束成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		PlanningEntry<Plane> fe = findFlightEntry();
		if (fe == null)
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		else
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + fe.getState());
	}

	/**
	 * 检测当前的计划项集合中可能存在的资源独占冲突
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getFlightEntries()))
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		else
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
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

		if (!planes.contains(plane)) {
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
		PlanningEntry<Plane> fe = findFlightEntry();
		if (!planes.contains(plane))
			JOptionPane.showMessageDialog(null, "此资源不存在！");
		else if (fe == null)
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
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
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		board.allEntryAtLocation(loc, time);
	}

	/**
	 * 从文件中读取航班计划项信息
	 */
	public void readFileCreatFlightSchedule() {
		List<PlanningEntry<Plane>> entryList = new ArrayList<>();
		Scanner input = null;
		try {
			String filePath = JOptionPane.showInputDialog("文件路径：");
			input = new Scanner(new File(filePath));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "文件错误！");
		}
		String patten = "(Flight:\\d{4}-\\d{2}-\\d{2},[A-Z]{2}\\d{2,4}\\{DepartureAirport:[a-zA-Z]+"
				+ "ArrivalAirport:[a-zA-Z]+DepatureTime:\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}"
				+ "ArrivalTime:\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}Plane:[N|B]\\d{4}\\{Type:[a-zA-Z0-9]+)"
				+ "(Seats:[1-6]\\d{2}|[5-9]\\d)(Age:(\\d|[1-2]\\d|30)\\.?\\d?\\}\\})";
		StringBuffer sb;
		while (input.hasNext()) {
			// 读入并匹配
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
				JOptionPane.showMessageDialog(null, "计划项格式错误！");
				return;
			}
			// 解析信息
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
			// 判断是否满足开始结束时间的要求
			DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate flightDate = LocalDate.parse(date, df);
			LocalDate endDate = LocalDate.parse(end.substring(0, 10), df);
			LocalDate startDate = LocalDate.parse(start.substring(0, 10), df);
			if (!startDate.equals(flightDate)
					|| !(endDate.equals(flightDate) || endDate.plusDays(-1).equals(flightDate))) {
				input.close();
				JOptionPane.showMessageDialog(null, "计划项格式错误！");
				return;
			}
			// 判断是否有不满足前提条件的计划项
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
				// 同一个航班号
				if (charOfpeName.equals(charOfName) && numOfpeName == numOfName) {
					// 日期一样
					if (peDate.equals(date)) {
						JOptionPane.showMessageDialog(null, name + "计划项时间冲突！");
						return;
					}
					// 出发和到达机场不全相同
					if (!peDeparture.equals(departure) || !peArrival.equals(arrival)) {
						JOptionPane.showMessageDialog(null, "同名计划项地点不同！");
						return;
					}
					// 出发和到达时间不全相同
					if (!peStart.equals(startStr) || !peEnd.equals(endStr)) {
						JOptionPane.showMessageDialog(null, "同名计划项时间不同！");
						return;
					}
				}
			}
			// 把不在集合中的资源和位置加入到集合中
			Plane plane = new Plane(id, type, Integer.valueOf(seats), Float.valueOf(age));
			if (!planes.contains(plane))
				planes.add(plane);
			Location departureLoc = new Location(departure, true);
			Location arrivalLoc = new Location(arrival, true);
			if (!locations.contains(departureLoc))
				locations.add(departureLoc);
			if (!locations.contains(arrivalLoc))
				locations.add(arrivalLoc);
			// 满足条件，加入到计划项列表中
			PlanningEntry<Plane> flightEntry = PlanningEntry.flightEntry(name, departureLoc, arrivalLoc, start, end);
			board.addFlightEntry(flightEntry);
			entryList.add(flightEntry);
			((FlightEntry<Plane>) flightEntry).allocatePlane(plane);
		}
		input.close();
	}

	/**
	 * 主程序
	 */
	public static void main(String[] args) {
		new FlightScheduleApp();
	}
}
