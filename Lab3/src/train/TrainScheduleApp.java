package train;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
 * �������ι���Ӧ��
 */
public class TrainScheduleApp {
	private JFrame frame = new JFrame();
	private JPanel buttonPanel = new JPanel();
	private Set<Location> locations = new HashSet<>();
	private Set<Carriage> carriages = new HashSet<>();
	private Board<Carriage> board = new Board<>();
	private PlanningEntryAPIs<Carriage> checkConf = new PlanningEntryAPIs<>();

	public TrainScheduleApp() {
		buttonPanel.setLayout(new GridLayout(8, 2));

		JButton addCarriage = new JButton("Add Carriage");
		buttonPanel.add(addCarriage);
		addCarriage.addActionListener(event -> addCarriage());

		JButton removeCarriage = new JButton("Remove Carriage");
		buttonPanel.add(removeCarriage);
		removeCarriage.addActionListener(event -> removeCarriage());

		JButton addLocation = new JButton("Add Location");
		buttonPanel.add(addLocation);
		addLocation.addActionListener(event -> addLocation());

		JButton removeLocation = new JButton("Remove Location");
		buttonPanel.add(removeLocation);
		removeLocation.addActionListener(event -> removeLocation());

		JButton addTrain = new JButton("Add TrainEntry");
		buttonPanel.add(addTrain);
		addTrain.addActionListener(event -> addTrain());

		JButton cancelTrain = new JButton("Cancel TrainEntry");
		buttonPanel.add(cancelTrain);
		cancelTrain.addActionListener(event -> cancelTrain());

		JButton allocateTrain = new JButton("Allocate TrainEntry");
		buttonPanel.add(allocateTrain);
		allocateTrain.addActionListener(event -> allocateTrain());

		JButton runTrain = new JButton("Run TrainEntry");
		buttonPanel.add(runTrain);
		runTrain.addActionListener(event -> runTrain());

		JButton blockTrain = new JButton("Block TrainEntry");
		buttonPanel.add(blockTrain);
		blockTrain.addActionListener(event -> blockTrain());

		JButton endTrain = new JButton("End TrainEntry");
		buttonPanel.add(endTrain);
		endTrain.addActionListener(event -> endTrain());

		JButton getState = new JButton("Get Entry State");
		buttonPanel.add(getState);
		getState.addActionListener(event -> getState());

		JButton checkConflict = new JButton("Check Resource Conflict");
		buttonPanel.add(checkConflict);
		checkConflict.addActionListener(event -> checkConflict());

		JButton findEntryUseResource = new JButton("Find All Entry Use Resource");
		buttonPanel.add(findEntryUseResource);
		findEntryUseResource.addActionListener(event -> findAllEntryUseResource());

		JButton findResource = new JButton("Find PreEntry Use Resource");
		buttonPanel.add(findResource);
		findResource.addActionListener(event -> findPreEntryUseResource());

		JButton findLocation = new JButton("Find Entry At Location");
		buttonPanel.add(findLocation);
		findLocation.addActionListener(event -> findLocation());

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
	 * ��ӿ��ó�����Դ
	 */
	public void addCarriage() {
		String id = JOptionPane.showInputDialog("����ı�ţ�");
		String type = JOptionPane.showInputDialog("������ͺţ�");
		String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
		String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		if (carriages.contains(carriage))
			JOptionPane.showMessageDialog(null, "�˳����Ѵ��ڣ�");
		else {
			carriages.add(carriage);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ�������ó�����Դ
	 */
	public void removeCarriage() {
		String id = JOptionPane.showInputDialog("����ı�ţ�");
		String type = JOptionPane.showInputDialog("������ͺţ�");
		String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
		String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		if (carriages.contains(carriage)) {
			carriages.remove(carriage);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˳��᲻���ڣ�");
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("��վ���ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "�˳�վ�Ѵ��ڣ�");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ�������õ�λ��
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("��վ���ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˳�վ�����ڣ�");
	}

	/**
	 * �ҵ�ָ���ĳ��μƻ���
	 * 
	 * @return �ǿ� ��Ӧ�ĳ��μƻ��� null û����Ӧ�ĳ��μƻ���
	 */
	private PlanningEntry<Carriage> findTrainEntry() {
		String name = JOptionPane.showInputDialog("���κţ�");
		List<String> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String num = JOptionPane.showInputDialog("�����ĳ�վ����:");
		for (int i = 1; i < Integer.parseInt(num); i++) {
			String info = JOptionPane.showInputDialog("�����ĵ�" + i + "����վ:");
			locations.add(info);
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("��վʱ�䣬��ʽyyyy-MM-dd HH:mm��");
			times.add(info);
			info = "";
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("��һվ��վվʱ�䣬��ʽyyyy-MM-dd HH:mm��");
			times.add(info);
		}
		String info = JOptionPane.showInputDialog("���������һ����վ:");
		locations.add(info);

		List<Location> locs = new ArrayList<>();
		for (String l : locations)
			locs.add(new Location(l, true));
		List<Timeslot> t = new ArrayList<>();
		for (int i = 0; i < times.size(); i += 2)
			t.add(new Timeslot(times.get(i), times.get(i + 1)));
		Iterator<PlanningEntry<Carriage>> iter = board.iterator();
		while (iter.hasNext()) {
			TrainEntry<Carriage> te = (TrainEntry<Carriage>) iter.next();
			if (te.getName().equals(name) && te.getLocations().equals(locs) && te.getTimeslot().equals(t))
				return te;
		}
		return null;
	}

	/**
	 * ����һ���µļƻ���
	 */
	public void addTrain() {
		String name = JOptionPane.showInputDialog("���κţ�");
		List<String> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String num = JOptionPane.showInputDialog("�����ĳ�վ����:");
		for (int i = 1; i < Integer.parseInt(num); i++) {
			String info = JOptionPane.showInputDialog("�����ĵ�" + i + "����վ:");
			locations.add(info);
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("��վʱ�䣬��ʽyyyy-MM-dd HH:mm��");
			times.add(info);
			info = "";
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("��һվ��վվʱ�䣬��ʽyyyy-MM-dd HH:mm��");
			times.add(info);
		}
		String info = JOptionPane.showInputDialog("���������һ����վ:");
		locations.add(info);
		// �ڿ���λ���б���Ѱ��λ��
		List<Location> locationList = new ArrayList<>();
		Iterator<String> locIter = locations.iterator();
		while (locIter.hasNext()) {
			String tempLoc = locIter.next();
			boolean isFind = false;
			for (Location loc : this.locations) {
				if (loc.getName().equals(tempLoc)) {
					locationList.add(loc);
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
				return;
			}
		}

		List<Location> locs = new ArrayList<>();
		for (String l : locations)
			locs.add(new Location(l, true));
		List<Timeslot> t = new ArrayList<>();
		for (int i = 0; i < times.size(); i += 2)
			t.add(new Timeslot(times.get(i), times.get(i + 1)));
		Iterator<PlanningEntry<Carriage>> iter = board.iterator();
		while (iter.hasNext()) {
			TrainEntry<Carriage> te = (TrainEntry<Carriage>) iter.next();
			if (te.getName().equals(name) && te.getLocations().equals(locs) && te.getTimeslot().equals(t)) {
				JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
				return;
			}
		}
		PlanningEntry<Carriage> trainEntry = PlanningEntry.trainEntry(name, locationList, times);
		board.addTrainEntry(trainEntry);
		JOptionPane.showMessageDialog(null, "��ӳɹ���");
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "ȡ��ʧ�ܣ�");
		else if (te.cancel())
			JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		// ������Դ
		List<Carriage> train = new ArrayList<>();
		String carriageNum = JOptionPane.showInputDialog("���������");
		for (int i = 0; i < Integer.parseInt(carriageNum); i++) {
			String id = JOptionPane.showInputDialog("����ı�ţ�");
			String type = JOptionPane.showInputDialog("������ͺţ�");
			String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
			String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
			Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
					Integer.parseInt(manufactureYear));
			train.add(carriage);
		}
		// ȷ��������Դ���ǿ��õ�
		for (Carriage carriage : train)
			if (!this.carriages.contains(carriage)) {
				JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
				return;
			}
		if (te == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
		else if (((TrainEntry<Carriage>) te).allocateTrain(train))
			JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
		else if (te.running())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void blockTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (((TrainEntry<Carriage>) te).block())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬����������");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (te.end())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		else
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + te.getState());
	}

	/**
	 * ��⵱ǰ�ļƻ�����п��ܴ��ڵ���Դ��ռ��ͻ
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries()))
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		else
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
	}

	/**
	 * ����û�ѡ����ĳ����Դ���г�ʹ�ø���Դ�����мƻ���
	 */
	public void findAllEntryUseResource() {
		String id = JOptionPane.showInputDialog("����ı�ţ�");
		String type = JOptionPane.showInputDialog("������ͺţ�");
		String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
		String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));

		if (!carriages.contains(carriage)) {
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
			return;
		}
		List<PlanningEntry<Carriage>> list = new ArrayList<>();
		for (PlanningEntry<Carriage> pe : board.getTrainEntries())
			if (((TrainEntry<Carriage>) pe).getResources().contains(carriage))
				list.add(pe);

		Object[][] str = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			TrainEntry<Carriage> fe = (TrainEntry<Carriage>) list.get(i);
			String[] s = { fe.getName(), fe.getTimeslot().toString(), fe.getLocations().get(0).getName() + "->"
					+ fe.getLocations().get(fe.getLocations().size() - 1).getName(), fe.getState() };
			str[i] = s;
		}
		String[] title = { "Entry", "Time", "From and To", "State" };
		board.visualize(str, title, "ʹ�ø���Դ�����мƻ���");
	}

	/**
	 * ����û�ѡ����ĳ����Դ��ĳ���ƻ���֮���ҳ�����ǰ��ƻ���
	 */
	public void findPreEntryUseResource() {
		String id = JOptionPane.showInputDialog("����ı�ţ�");
		String type = JOptionPane.showInputDialog("������ͺţ�");
		String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
		String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));

		PlanningEntry<Carriage> te = findTrainEntry();
		if (!carriages.contains(carriage))
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
		else if (te == null)
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		else {
			TrainEntry<Carriage> prete = (TrainEntry<Carriage>) checkConf.findPreEntryPerResource(carriage, te,
					board.getTrainEntries());
			JOptionPane.showMessageDialog(null,
					"Entry:" + prete.getName() + "\nTime:" + prete.getTimeslot().toString() + "\nFrom:"
							+ prete.getLocations().get(0).getName() + "\nTo:"
							+ prete.getLocations().get(prete.getLocations().size() - 1).getName() + "\nState:"
							+ prete.getState());
		}
	}

	/**
	 * ѡ���ض�λ�ã����ӻ�չʾ��ǰʱ�̸�λ�õ���Ϣ��
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("��վ������:");
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
	 * ������
	 */
	public static void main(String[] args) {
		new TrainScheduleApp();
	}
}
