package train;

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

	private final PlanningEntryCollection<R> trainEntries = new PlanningEntryCollection<>();

	// Abstraction function:
	// AF(trainEntries)=现实中的高铁车次计划项清单
	// Representation invariant:
	// trainEntries!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的用final限定

	/**
	 * 计划项清单迭代器
	 * 
	 * @return 迭代器
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		return trainEntries.iterator();
	}

	/**
	 * 添加计划项
	 * 
	 * @param trainEntry 待添加的计划项
	 * @return false，添加失败，计划项为空 true，添加成功
	 */
	public boolean addTrainEntry(PlanningEntry<R> trainEntry) {
		return trainEntries.addEntries(trainEntry);
	}

	
	/**
	 * 获得计划项清单
	 * 
	 * @return 计划项列表
	 */
	public List<PlanningEntry<R>> getTrainEntries() {
		return trainEntries.getEntries();
	}

	/**
	 * 显示某位置，某时刻前后一小时内的所有计划项
	 * 
	 * @param loc  位置
	 * @param time 时间，格式yyyy-MM-dd HH:mm
	 */
	public void allEntryAtLocation(Location loc, String time) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime from = LocalDateTime.parse(time, df).plusHours(-1);
		LocalDateTime to = LocalDateTime.parse(time, df).plusHours(1);

		List<String[]> arrivalList = new ArrayList<>();
		List<String[]> departureList = new ArrayList<>();
		trainEntries.sort();
		for (PlanningEntry<R> pe : trainEntries.getEntries()) {
			TrainEntry<R> te = (TrainEntry<R>) pe;
			List<Location> locations = te.getLocations();
			LocalDateTime start = te.getTimeslot().get(0).getStartTime();
			LocalDateTime end = te.getTimeslot().get(te.getTimeslot().size() - 1).getEndTime();
			if (locations.get(0).equals(loc) && start.isAfter(from) && start.isBefore(to)) {
				String[] str = { start.toString(), te.getName(),
						locations.get(0).getName() + "->" + locations.get(locations.size() - 1).getName(),
						te.getState() };
				departureList.add(str);
			} else if (locations.get(locations.size() - 1).equals(loc) && end.isAfter(from) && end.isBefore(to)) {
				String[] str = { end.toString(), te.getName(),
						locations.get(0).getName() + "->" + locations.get(locations.size() - 1).getName(),
						te.getState() };
				arrivalList.add(str);
			} else if (locations.contains(loc)) {
				int index = locations.indexOf(loc);
				LocalDateTime arrival = te.getTimeslot().get(index - 1).getEndTime();
				LocalDateTime departure = te.getTimeslot().get(index).getStartTime();
				if (arrival.isAfter(from) && arrival.isBefore(to)) {
					String[] arrivalStr = { arrival.toString(), te.getName(),
							locations.get(0).getName() + "->" + locations.get(locations.size() - 1).getName(),
							te.getState() };
					arrivalList.add(arrivalStr);
				}
				if (departure.isAfter(from) && departure.isBefore(to)) {
					String[] departureStr = { departure.toString(), te.getName(),
							locations.get(0).getName() + "->" + locations.get(locations.size() - 1).getName(),
							te.getState() };
					departureList.add(departureStr);
				}
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
	 * @param title      表格第一行的标题
	 * @param windowName 窗口的标题
	 */
	public void visualize(Object[][] content, String[] title, String windowName) {
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