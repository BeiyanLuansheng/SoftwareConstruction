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
 * һ��mutable�����ͣ���ʾ�ƻ����嵥
 */
public class Board<R> {

	private final PlanningEntryCollection<R> flightEntries = new PlanningEntryCollection<>();

	// Abstraction function:
	// AF(flightEntries)=��ʵ�еĺ���ƻ����嵥
	// Representation invariant:
	// flightEntries!=null
	// Safety from rep exposure:
	// ���е���������˽�е���final�޶�

	private void checkRep() {
		assert this.flightEntries != null;
	}

	/**
	 * �ƻ����嵥������
	 * 
	 * @return ������
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return flightEntries.iterator();
	}

	/**
	 * ��Ӽƻ���
	 * 
	 * @param flightEntry ����ӵļƻ���
	 * @return false�����ʧ�ܣ��ƻ���Ϊ�� true����ӳɹ�
	 */
	public boolean addFlightEntry(PlanningEntry<R> flightEntry) {
		checkRep();
		return flightEntries.addEntries(flightEntry);
	}

	/**
	 * ������еļƻ���
	 * 
	 * @return �ƻ����б�
	 */
	public List<PlanningEntry<R>> getFlightEntries() {
		checkRep();
		return flightEntries.getEntries();
	}

	/**
	 * ��ʾĳλ�ã�ĳʱ��ǰ��һСʱ�ڵ����мƻ���
	 * 
	 * @param loc  λ��
	 * @param time ʱ�䣬��ʽyyyy-MM-dd HH:mm
	 */
	public void allEntryAtLocation(Location loc, String time) {
		// ������ǰ���������׳��쳣
		if (loc == null || time == null || !time.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\d{2}"))
			throw new IllegalArgumentException("�������Ϸ�");
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