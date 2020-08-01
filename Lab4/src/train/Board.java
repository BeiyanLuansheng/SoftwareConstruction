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
 * һ��mutable�����ͣ���ʾ�ƻ����嵥
 */
public class Board<R> {

	private final PlanningEntryCollection<R> trainEntries = new PlanningEntryCollection<>();

	// Abstraction function:
	// AF(trainEntries)=��ʵ�еĸ������μƻ����嵥
	// Representation invariant:
	// trainEntries!=null
	// Safety from rep exposure:
	// ���е���������˽�е���final�޶�

	/**
	 * ��鲻����
	 */
	private void checkRep(){
		assert trainEntries != null;
	}

	/**
	 * �ƻ����嵥������
	 * 
	 * @return ������
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return trainEntries.iterator();
	}

	/**
	 * ��Ӽƻ���
	 * 
	 * @param trainEntry ����ӵļƻ���
	 * @return false�����ʧ�ܣ��ƻ���Ϊ�� true����ӳɹ�
	 */
	public boolean addTrainEntry(PlanningEntry<R> trainEntry) {
		checkRep();
		return trainEntries.addEntries(trainEntry);
	}

	
	/**
	 * ��üƻ����嵥
	 * 
	 * @return �ƻ����б�
	 */
	public List<PlanningEntry<R>> getTrainEntries() {
		checkRep();
		return trainEntries.getEntries();
	}

	/**
	 * ��ʾĳλ�ã�ĳʱ��ǰ��һСʱ�ڵ����мƻ���
	 * 
	 * @param loc  λ��
	 * @param time ʱ�䣬��ʽyyyy-MM-dd HH:mm
	 */
	public void allEntryAtLocation(Location loc, String time) {
		//������ǰ���������׳��쳣
		if(loc == null || time == null || !time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("�������Ϸ�");
		checkRep();
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
	 * ����������
	 * 
	 * @param content    ����ʾ������
	 * @param title      ����һ�еı���
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