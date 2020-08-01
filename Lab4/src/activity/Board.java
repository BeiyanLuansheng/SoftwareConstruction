package activity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryCollection;

public class Board<R> {

	private final PlanningEntryCollection<R> activityEntries = new PlanningEntryCollection<>();

	// Abstraction function:
	// AF(activityEntries)=现实中的活动计划项清单
	// Representation invariant:
	// activityEntries!=null
	// Safety from rep exposure:
	// 所有的数据域都是私有的用final限定

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.activityEntries != null;
	}

	/**
	 * 计划项清单迭代器
	 * 
	 * @return 迭代器
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return activityEntries.iterator();
	}

	/**
	 * 添加计划项
	 * 
	 * @param activityEntry 待添加的计划项
	 * @return false，添加失败，计划项为空 true，添加成功
	 */
	public boolean addActivityEntry(PlanningEntry<R> activityEntry) {
		checkRep();
		return activityEntries.addEntries(activityEntry);
	}

	/**
	 * 获得计划项清单
	 * 
	 * @return 计划项列表
	 */
	public List<PlanningEntry<R>> getActivityEntries() {
		checkRep();
		return activityEntries.getEntries();
	}

	/**
	 * 显示某位置，某天所有计划项
	 * 
	 * @param loc 位置，非空
	 * @param day 日期，格式yyyy-MM-dd
	 */
	public void allEntryAtLocation(Location loc, String day) {
		if (loc == null || !day.matches("\\d{4}-\\d{2}-\\d{2}")) // 当loc为空，或者day的格式不满足要求时抛出异常
			throw new IllegalArgumentException("参数不合法");
		checkRep();
		activityEntries.sort();
		List<String[]> info = new ArrayList<>();
		for (PlanningEntry<R> pe : activityEntries.getEntries()) {
			ActivityEntry<R> ae = (ActivityEntry<R>) pe;
			if (ae.getLocation().equals(loc)) {
				String time = ae.getTimeslot().getStartTime().toString();
				if (time.substring(0, 10).equals(day)) {
					String[] str = { time.substring(11) + "-" + ae.getTimeslot().getEndTime().toString().substring(11),
							ae.getName(), ae.getState() };
					info.add(str);
				}
			}
		}

		Object[][] content = new Object[info.size()][3];
		for (int i = 0; i < info.size(); i++)
			content[i] = info.get(i);
		String[] title = { "Last Time", "Entry", "State" };
		visualize(content, title, day + "," + loc.getName() + " ActivityTable");
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
