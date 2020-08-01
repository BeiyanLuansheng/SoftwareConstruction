package activity;

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

import common.conflict.CheckActivityEntryPlanA;
import common.conflict.CheckActivityEntryPlanB;
import common.conflict.PlanningEntryAPIs;
import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;

/**
 * ѧϰ�����Ӧ��
 */
public class ActivityScheduleApp {
	private JFrame frame = new JFrame();
	private JPanel buttonPanel = new JPanel();
	private Set<Location> locations = new HashSet<>();
	private Set<Material> materials = new HashSet<>();
	private Board<Material> board = new Board<>();
	private PlanningEntryAPIs<Material> checkConf = new PlanningEntryAPIs<>();

	public ActivityScheduleApp() {
		buttonPanel.setLayout(new GridLayout(8, 2));

		JButton addMaterial = new JButton("Add Material");
		buttonPanel.add(addMaterial);
		addMaterial.addActionListener(event -> addMaterrial());

		JButton removeMaterial = new JButton("Remove Material");
		buttonPanel.add(removeMaterial);
		removeMaterial.addActionListener(event -> removeMaterial());

		JButton addLocation = new JButton("Add Location");
		buttonPanel.add(addLocation);
		addLocation.addActionListener(event -> addLocation());

		JButton removeLocation = new JButton("Remove Location");
		buttonPanel.add(removeLocation);
		removeLocation.addActionListener(event -> removeLocation());

		JButton addActivity = new JButton("Add ActivityEntry");
		buttonPanel.add(addActivity);
		addActivity.addActionListener(event -> addActivity());

		JButton cancelActivity = new JButton("Cancel ActivityEntry");
		buttonPanel.add(cancelActivity);
		cancelActivity.addActionListener(event -> cancelActivity());

		JButton allocateActivity = new JButton("Allocate ActivityEntry");
		buttonPanel.add(allocateActivity);
		allocateActivity.addActionListener(event -> allocateActivity());

		JButton runActivity = new JButton("Run ActivityEntry");
		buttonPanel.add(runActivity);
		runActivity.addActionListener(event -> runActivity());

		JButton setLocation = new JButton("Set Location");
		buttonPanel.add(setLocation);
		setLocation.addActionListener(event -> setLocation());

		JButton endActivity = new JButton("End ActivityEntry");
		buttonPanel.add(endActivity);
		endActivity.addActionListener(event -> endActivity());

		JButton getState = new JButton("Get Entry State");
		buttonPanel.add(getState);
		getState.addActionListener(event -> getState());

		JButton findEntryUseResource = new JButton("Find All Entry Use Resource");
		buttonPanel.add(findEntryUseResource);
		findEntryUseResource.addActionListener(event -> findAllEntryUseResource());

		JButton checkConflictPlanA = new JButton("Check Location Conflict PlanA");
		buttonPanel.add(checkConflictPlanA);
		checkConflictPlanA.addActionListener(event -> checkConflictPlanA());

		JButton checkConflictPlanB = new JButton("Check Location Conflict PlanB");
		buttonPanel.add(checkConflictPlanB);
		checkConflictPlanB.addActionListener(event -> checkConflictPlanB());

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
	 * ��ӿ���ѧϰ����
	 */
	public void addMaterrial() {
		String name = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(name, department, date);
		if (materials.contains(material))
			JOptionPane.showMessageDialog(null, "�˲����Ѵ��ڣ�");
		else {
			materials.add(material);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ��������ѧϰ����
	 */
	public void removeMaterial() {
		String name = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(name, department, date);
		if (materials.contains(material)) {
			materials.remove(material);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˲��ϲ����ڣ�");
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("�ص����ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "�˵ص��Ѵ��ڣ�");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ɾ�������õ�λ��
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("�ص����ƣ�");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
		} else
			JOptionPane.showMessageDialog(null, "�˵ص㲻���ڣ�");
	}

	/**
	 * �ҵ�ָ���ļƻ���
	 * 
	 * @return null û����Ӧ�ļƻ��� �ǿ� ��Ӧ�ļƻ���
	 */
	private PlanningEntry<Material> findActivityEntry() {
		String name = JOptionPane.showInputDialog("����⣺");
		String location = JOptionPane.showInputDialog("��ص㣺");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("��ʼʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");

		for (PlanningEntry<Material> pe : board.getActivityEntries()) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) pe;
			if (ae.getName().equals(name) && ae.getLocation().getName().equals(location)
					&& ae.getTimeslot().equals(new Timeslot(start, end)))
				return pe;
		}
		return null;
	}

	/**
	 * ����һ���µļƻ���
	 */
	public void addActivity() {
		String name = JOptionPane.showInputDialog("����⣺");
		String location = JOptionPane.showInputDialog("��ص㣺");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("��ʼʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");

		for (PlanningEntry<Material> pe : board.getActivityEntries()) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) pe;
			if (ae.getName().equals(name) && ae.getLocation().getName().equals(location)
					&& ae.getTimeslot().equals(new Timeslot(start, end))) {
				JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
				return;
			}
		}

		Location loc = null;
		for (Location l : locations)
			if (l.getName().equals(location)) {
				loc = l;
				break;
			}
		if (loc == null)
			JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
		else {
			PlanningEntry<Material> activityEntry = PlanningEntry.activityEntry(name, start, end);
			((ActivityEntry<Material>) activityEntry).setLocation(loc);
			board.addActivityEntry(activityEntry);
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ��ȡ��ʧ�ܣ�");
		else if (ae.cancel())
			JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		String materialName = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(materialName, department, date);
		if (ae == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
		else if (!materials.contains(material))
			JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
		else if (((ActivityEntry<Material>) ae).allocateMaterial(material))
			JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (ae.running())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ���ĳ���Ѵ��ڵļƻ����λ��
	 */
	public void setLocation() {
		PlanningEntry<Material> ae = findActivityEntry();
		String newLocation = JOptionPane.showInputDialog("�»�ص㣺");
		Location loc = null;
		for (Location l : locations)
			if (l.getName().equals(newLocation)) {
				loc = l;
				break;
			}
		if (loc == null)
			JOptionPane.showMessageDialog(null, "����λ�ã�����ʧ�ܣ�");
		else if (ae == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (((ActivityEntry<Material>) ae).setLocation(loc))
			JOptionPane.showMessageDialog(null, "���óɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ܱ�����λ�ã�");
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		else if (ae.end())
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		else
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		else
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + ae.getState());
	}

	/**
	 * ��A��ʽ����Ƿ����λ�ó�ͻ
	 */
	public void checkConflictPlanA() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries()))
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		else
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
	}

	/**
	 * ��B��ʽ����Ƿ����λ�ó�ͻ
	 */
	public void checkConflictPlanB() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanB<Material>(), board.getActivityEntries()))
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		else
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
	}

	/**
	 * ����û�ѡ����ĳ����Դ���г�ʹ�ø���Դ�����мƻ���
	 */
	public void findAllEntryUseResource() {
		String name = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(name, department, date);
		if (materials.contains(material)) {
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
			return;
		}
		List<PlanningEntry<Material>> list = new ArrayList<>();
		Iterator<PlanningEntry<Material>> iter = board.iterator();
		while (iter.hasNext()) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) iter.next();
			if (((ActivityEntry<Material>) ae).getMaterial().equals(material))
				list.add(ae);
		}

		Object[][] str = new Object[list.size()][4];
		for (int i = 0; i < list.size(); i++) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) list.get(i);
			String[] s = { ae.getName(), ae.getTimeslot().toString(), ae.getLocation().getName(), ae.getState() };
			str[i] = s;
		}
		String[] title = { "Entry", "Time", "Location", "State" };
		board.visualize(str, title, "ʹ�ø���Դ�����мƻ���");
	}

	/**
	 * ѡ���ض�λ�ã����ӻ�չʾ��ǰ���ڸ�λ�õ���Ϣ��
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("�ص�����֣�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ڣ���ʽyyyy-MM-dd��");
		Location loc = new Location(name, true);
		if (!locations.contains(loc)) {
			JOptionPane.showMessageDialog(null, "�޴�λ�ã�");
			return;
		}
		board.allEntryAtLocation(loc, date);
	}

	/**
	 * ������
	 */
	public static void main(String[] args) {
		new ActivityScheduleApp();
	}
}
