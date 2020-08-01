package flight;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryCollection;

/**
 * 一个mutable的类型，表示计划项清单
 */
public class Board<R> {

	private final PlanningEntryCollection<R> flightEntries = new PlanningEntryCollection<>();

	// Abstraction function:
	// AF(flightEntries)=现实中的航班计划项清单
	// Representation invariant:
	// flightEntries!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的用final限定

	private void checkRep() {
		assert this.flightEntries != null;
	}

	/**
	 * 计划项清单迭代器
	 * 
	 * @return 迭代器
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return flightEntries.iterator();
	}

	/**
	 * 添加计划项
	 * 
	 * @param flightEntry 待添加的计划项
	 * @return false，添加失败，计划项为空 true，添加成功
	 */
	public boolean addFlightEntry(PlanningEntry<R> flightEntry) {
		checkRep();
		return flightEntries.addEntries(flightEntry);
	}

	/**
	 * 获得所有的计划项
	 * 
	 * @return 计划项列表
	 */
	public List<PlanningEntry<R>> getFlightEntries() {
		checkRep();
		return flightEntries.getEntries();
	}

	/**
	 * 显示某位置，某时刻前后一小时内的所有计划项
	 * 
	 * @param loc  位置
	 * @param time 时间，格式yyyy-MM-dd HH:mm
	 */
	public void allEntryAtLocation(Location loc, String time) {
		// 不满足前置条件，抛出异常
		if (loc == null || time == null || !time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("参数不合法");
		checkRep();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime from = LocalDateTime.parse(time, df).plusHours(-1);
		LocalDateTime to = LocalDateTime.parse(time, df).plusHours(1);

		List<String[]> departureList = new ArrayList<>();
		List<String[]> arrivalList = new ArrayList<>();
		flightEntries.sort();
		for (PlanningEntry<R> pe : flightEntries.getEntries()) {
			FlightEntry<R> fe = (FlightEntry<R>) pe;
			LocalDateTime start = fe.getTimeslot().getStartTime();
			LocalDateTime end = fe.getTimeslot().getEndTime();
			if (fe.getDeparture().equals(loc) && start.isAfter(from) && start.isBefore(to)) {
				String[] str = { fe.getTimeslot().getStartTimeString(), fe.getName(),
						fe.getDeparture().getName() + "->" + fe.getArrival().getName(), fe.getState() };
				departureList.add(str);
			} else if (fe.getArrival().equals(loc) && end.isAfter(from) && end.isBefore(to)) {
				String[] str = { fe.getTimeslot().getEndTimeString(), fe.getName(),
						fe.getDeparture().getName() + "->" + fe.getArrival().getName(), fe.getState() };
				arrivalList.add(str);
			}
		}
		Object[][] departure = new Object[departureList.size()][4];
		for (int i = 0; i < departureList.size(); i++) {
			departure[i] = departureList.get(i);
		}
		Object[][] arrival = new Object[arrivalList.size()][4];
		for (int i = 0; i < arrivalList.size(); i++) {
			arrival[i] = arrivalList.get(i);
		}
		String[] title1 = { "Departure Time", "Entry", "From->To", "State" };
		String[] title2 = { "Arrival Time", "Entry", "From->To", "State" };
		visualize(departure, title1, time + "," + loc.getName() + " DepartureTable");
		visualize(arrival, title2, time + "," + loc.getName() + " ArrivalTable");
	}

	/**
	 * 表格内容面板
	 * 
	 * @param content    待显示的内容
	 * @param title      表格第一行的标题，和content的列数要相同
	 * @param windowName 窗口的标题
	 */
	public void visualize(Object[][] content, String[] title, String windowName) {
		if(content.length != 0 && content[0].length != title.length) //当content的列数和title的列数不同时抛出异常
			throw new IllegalArgumentException("内容和标题的列数不一样");
		JFrame frame = new JFrame();
		JTable table = new JTable(content, title);
		table.setPreferredScrollableViewportSize(new Dimension(500, 500));
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setTitle(windowName);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				frame.setVisible(false);
			}
		});
	}
}