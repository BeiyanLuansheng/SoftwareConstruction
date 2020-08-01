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
	// AF(activityEntries)=��ʵ�еĻ�ƻ����嵥
	// Representation invariant:
	// activityEntries!=null
	// Safety from rep exposure:
	// ���е���������˽�е���final�޶�

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.activityEntries != null;
	}

	/**
	 * �ƻ����嵥������
	 * 
	 * @return ������
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return activityEntries.iterator();
	}

	/**
	 * ��Ӽƻ���
	 * 
	 * @param activityEntry ����ӵļƻ���
	 * @return false�����ʧ�ܣ��ƻ���Ϊ�� true����ӳɹ�
	 */
	public boolean addActivityEntry(PlanningEntry<R> activityEntry) {
		checkRep();
		return activityEntries.addEntries(activityEntry);
	}

	/**
	 * ��üƻ����嵥
	 * 
	 * @return �ƻ����б�
	 */
	public List<PlanningEntry<R>> getActivityEntries() {
		checkRep();
		return activityEntries.getEntries();
	}

	/**
	 * ��ʾĳλ�ã�ĳ�����мƻ���
	 * 
	 * @param loc λ�ã��ǿ�
	 * @param day ���ڣ���ʽyyyy-MM-dd
	 */
	public void allEntryAtLocation(Location loc, String day) {
		if (loc == null || !day.matches("\\d{4}-\\d{2}-\\d{2}")) // ��locΪ�գ�����day�ĸ�ʽ������Ҫ��ʱ�׳��쳣
			throw new IllegalArgumentException("�������Ϸ�");
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
	 * ����������
	 * 
	 * @param content    ����ʾ������
	 * @param title      ����һ�еı��⣬��content������Ҫ��ͬ
	 * @param windowName ���ڵı���
	 */
	public void visualize(Object[][] content, String[] title, String windowName) {
		if(content.length != 0 && content[0].length != title.length) //��content��������title��������ͬʱ�׳��쳣
			throw new IllegalArgumentException("���ݺͱ����������һ��");
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
