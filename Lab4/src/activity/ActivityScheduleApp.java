package activity;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
import exceptions.*;

/**
 * ѧϰ�����Ӧ��
 */
public class ActivityScheduleApp {
	private final JFrame frame = new JFrame();
	private final JPanel buttonPanel = new JPanel();
	private final Set<Location> locations = new HashSet<>();
	private final Set<Material> materials = new HashSet<>();
	private final Board<Material> board = new Board<>();
	private final PlanningEntryAPIs<Material> checkConf = new PlanningEntryAPIs<>();
	private final Logger log = Logger.getLogger(ActivityScheduleApp.class.getName());

	public ActivityScheduleApp() {
		try {
			new FileWriter("src/log/activitylog.txt").close();
			Locale.setDefault(new Locale("en", "EN"));
			log.setLevel(Level.INFO);
			FileHandler fh = new FileHandler("src/log/activitylog.txt", true);
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������
	 */
	public void run() {
		
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
	 * ��ӿ���ѧϰ����
	 */
	public void addMaterrial() {
		String name = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(name, department, date);
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		if (addMaterial(material)) {
			log.info("AddMaterrial;" + materialInfo + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddMaterrial;" + materialInfo + ";fail");
			JOptionPane.showMessageDialog(null, "�˲����Ѵ��ڣ�");
		}
	}

	/**
	 * ���ѧϰ���ϵĹ���ʵ��
	 * 
	 * @param material ����
	 * @return true �ɹ�; false �Ѵ��ڣ�ʧ��
	 */
	public boolean addMaterial(Material material) {
		if (materials.contains(material))
			return false;
		else {
			materials.add(material);
			return true;
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
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		try {
			if (removeMaterial(material)) {
				log.info("RemoveMaterial;" + materialInfo + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemoveMaterial;" + materialInfo + ";fail");
				JOptionPane.showMessageDialog(null, "�˲��ϲ����ڣ�");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveMaterial;" + materialInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ����Դ��") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveMaterial;EntryUseResourceOrLocationException:ActivityScheduleApp.removeMaterial(material);reselect resource");
				removeMaterial();
			} else
				log.warning(
						"RemoveMaterial;EntryUseResourceOrLocationException:ActivityScheduleApp.removeMaterial(material);end");
		}

	}

	/**
	 * ɾ����������Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param material ��ɾ������Դ
	 * @return true ɾ���ɹ�;false ������
	 * @throws EntryUseResourceOrLocationException ��Դ��ʹ����
	 */
	public boolean removeMaterial(Material material) throws EntryUseResourceOrLocationException {
		if (materials.contains(material)) {
			for (PlanningEntry<Material> pe : board.getActivityEntries())
				if (((ActivityEntry<Material>) pe).getMaterial().equals(material) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø���Դ������ɾ��");
			materials.remove(material);
			return true;
		} else
			return false;
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("�ص����ƣ�");
		Location loc = new Location(name, false);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "�˵ص��Ѵ��ڣ�");
		}
	}

	/**
	 * ��ӿ���λ�õĹ���ʵ�ֲ���
	 * 
	 * @param loc ����ӵ�λ��
	 * @return true ��ӳɹ�;false λ���Ѵ���
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
	 * ɾ�������õ�λ��
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("�ص����ƣ�");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "�˵ص㲻���ڣ�");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ��λ�ã�") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:ActivityScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:ActivityScheduleApp.removeLocation(loc);end");
		}

	}

	/**
	 * ɾ�������õ�λ�õĹ���ʵ�ֲ���
	 * 
	 * @param loc ��ɾ����λ��
	 * @return true ɾ���ɹ�;false ������λ��
	 * @throws EntryUseResourceOrLocationException �мƻ�����ʹ�ø�λ��
	 */
	public boolean removeLocation(Location loc) throws EntryUseResourceOrLocationException {
		if (locations.contains(loc)) {
			for (PlanningEntry<Material> pe : board.getActivityEntries())
				if (((ActivityEntry<Material>) pe).getLocation().equals(loc) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø�λ�ã�����ɾ��");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * �ҵ�ָ���ļƻ���
	 * 
	 * @return null û����Ӧ�ļƻ��� �ǿ� ��Ӧ�ļƻ���
	 */
	private PlanningEntry<Material> findActivityEntry(StringBuilder ActivityInfo) {
		String name = JOptionPane.showInputDialog("����⣺");
		String location = JOptionPane.showInputDialog("��ص㣺");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("��ʼʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		ActivityInfo.append("Entry:" + name);
		ActivityInfo.append(",Departure" + location);
		ActivityInfo.append(",StartTime:" + start);
		ActivityInfo.append(",EndTime:" + end);

		return findActivityEntry(name, location, start, end);
	}

	/**
	 * �ҵ�ָ���ļƻ���
	 * @param name �ƻ�����
	 * @param location λ��
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @return null û����Ӧ�ļƻ��� �ǿ� ��Ӧ�ļƻ���
	 */
	public PlanningEntry<Material> findActivityEntry(String name, String location, String start, String end){
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
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("��ʼʱ�䣬��ʽyyyy-MM-dd HH:mm��");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("����ʱ�䣬��ʽyyyy-MM-dd HH:mm��");

		while (true) {
			String location = JOptionPane.showInputDialog("��ص㣺");
			String activityInfo = "Entry:" + name + ",Departure" + location + ",StartTime:" + start + ",EndTime:" + end;
			Location loc = null;
			for (Location l : locations)
				if (l.getName().equals(location)) {
					loc = l;
					break;
				}
			try {
				if (loc == null) {
					log.info("AddActivity;" + activityInfo + ";fail");
					JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
					return;
				} else if (addActivity(name, start, end, loc)) {
					log.info("AddActivity;" + activityInfo + ";success");
					JOptionPane.showMessageDialog(null, "��ӳɹ���");
					return;
				} else {
					log.info("AddActivity;" + activityInfo + ";fail");
					JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
					return;
				}
			} catch (ResourceOrLocationConflictException e) {
				log.info("AddActivity;" + activityInfo + ";fail");
				JOptionPane.showMessageDialog(null, e.getMessage() + "\n����������ص�");
				log.warning(
						"AddActivity;ResourceOrLocationConflictException:ActivityScheduleApp.addActivity;reset location");
				continue;
			}
		}
	}

	/**
	 * ����һ���µļƻ���Ĺ���ʵ�ֲ���
	 * 
	 * @param name  ����ƣ��ǿ�
	 * @param start ��ʼʱ�䣬�ǿ�
	 * @param end   ����ʱ�䣬�ǿ�
	 * @param loc   �λ�ã��ǿ�
	 * @return ��ӳɹ�;false �ƻ����Ѵ���
	 * @throws ResourceOrLocationConflictException ����λ�ú������мƻ������λ�ö�ռ��ͻ
	 */
	public boolean addActivity(String name, String start, String end, Location loc)
			throws ResourceOrLocationConflictException {
		for (PlanningEntry<Material> pe : board.getActivityEntries()) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) pe;
			if (ae.getName().equals(name) && ae.getTimeslot().equals(new Timeslot(start, end)))
				return false;
		}
		PlanningEntry<Material> activityEntry = PlanningEntry.activityEntry(name, start, end);
		List<PlanningEntry<Material>> list = new ArrayList<>();
		for(PlanningEntry<Material> ae: board.getActivityEntries())
			list.add(ae);
		list.add(activityEntry);
		((ActivityEntry<Material>) activityEntry).setLocation(loc);
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), list))
			throw new ResourceOrLocationConflictException("���ú����������ƻ������λ�ö�ռ��ͻ");
		board.addActivityEntry(activityEntry);
		return true;
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		try {
			if (cancelActivity(ae)) {
				log.info("CancelActivity;" + activityInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
			} else {
				log.info("CancelActivity;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "û�иüƻ��ȡ��ʧ�ܣ�");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelActivity;" + activityInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ѡ��ȡ���ļƻ��") == JOptionPane.YES_OPTION) {
				log.warning(
						"CancelActivity;CannotCancelledException:ActivityScheduleApp.cancelActivity(ae);reselect entry");
				cancelActivity();
			} else
				log.warning("CancelActivity;CannotCancelledException:ActivityScheduleApp.cancelActivity(ae);end");
		}
	}

	/**
	 * ȡ���ƻ����ʵ�ֲ���
	 * 
	 * @param ae ȡ���ļƻ���
	 * @return true ȡ���ɹ�;false ȡ��ʧ�ܣ��ƻ���Ϊ��
	 * @throws CannotCancelledException ��ǰ�ƻ���״̬����ȡ��
	 */
	public boolean cancelActivity(PlanningEntry<Material> ae) throws CannotCancelledException {
		if (ae == null)
			return false;
		if (ae.cancel())
			return true;
		else
			throw new CannotCancelledException("��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		String materialName = JOptionPane.showInputDialog("���ϵ����֣�");
		String department = JOptionPane.showInputDialog("���ϵķ������ţ�");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("���ϵķ�������,��ʽyyyy-MM-dd��");
		Material material = new Material(materialName, department, date);
		if (ae == null) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
		} else if (!materials.contains(material)) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
		} else if (allocateActivity(ae, material)) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
		} else {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
		}
	}

	/**
	 * Ϊ�ƻ��������Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param ae       ������ļƻ���
	 * @param material ���������Դ
	 * @return true ����ɹ�; false ����ʧ�ܣ��ƻ���״̬���ܷ�����Դ
	 */
	public boolean allocateActivity(PlanningEntry<Material> ae, Material material) {
		if (((ActivityEntry<Material>) ae).allocateMaterial(material))
			return true;
		else
			return false;
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("RunActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		} else if (ae.running()) {
			log.info("RunActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		} else {
			log.info("RunActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ���ĳ���Ѵ��ڵļƻ����λ��
	 */
	public void setLocation() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("SetLocation;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
			return;
		}

		Location oldLoc = ((ActivityEntry<Material>) ae).getLocation();
		String newLocation = JOptionPane.showInputDialog("�»�ص㣺");
		Location loc = null;
		for (Location l : locations)
			if (l.getName().equals(newLocation)) {
				loc = l;
				break;
			}
		try {
			if (loc == null) {
				log.info("SetLocation;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "����λ�ã�����ʧ�ܣ�");
			} else if (setLocation(ae, loc)) {
				log.info("SetLocation;" + activityInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "���óɹ���");
			} else {
				log.info("SetLocation;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "��ǰ״̬���ܱ�����λ�ã�");
			}
		} catch (ResourceOrLocationConflictException e) {
			((ActivityEntry<Material>) ae).setLocation(oldLoc);
			log.info("SetLocation;" + activityInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n��������λ�ã�") == JOptionPane.YES_OPTION) {
				log.warning(
						"SetLocation;ResourceOrLocationConflictException:ActivityScheduleApp.setLocation(ae, loc);reset location");
				setLocation();
			} else
				log.warning(
						"SetLocation;ResourceOrLocationConflictException:ActivityScheduleApp.setLocation(ae, loc);end");
		}
	}

	/**
	 * ����λ�õĹ���ʵ�ֲ���
	 * 
	 * @param ae  �ƻ���ǿ�
	 * @param loc λ�ã��ǿ�
	 * @return true ���óɹ�;false ����ʧ��
	 * @throws ResourceOrLocationConflictException ���ú����������ƻ������λ�ö�ռ��ͻ
	 */
	public boolean setLocation(PlanningEntry<Material> ae, Location loc) throws ResourceOrLocationConflictException {
		if (((ActivityEntry<Material>) ae).setLocation(loc)) {
			if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries()))
				throw new ResourceOrLocationConflictException("���ú����������ƻ������λ�ö�ռ��ͻ");
			return true;
		} else
			return false;
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("EndActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		} else if (ae.end()) {
			log.info("EndActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		} else {
			log.info("EndActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("GetState;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		} else {
			log.info("GetState;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + ae.getState());
		}
	}

	/**
	 * ��A��ʽ����Ƿ����λ�ó�ͻ
	 */
	public void checkConflictPlanA() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries())) {
			log.info("CheckLocationConflictPlanA;existence;success");
			JOptionPane.showMessageDialog(null, "����λ�ö�ռ��ͻ��");
		} else {
			log.info("CheckResourcConflictPlanA;inexistence;success");
			JOptionPane.showMessageDialog(null, "������λ�ö�ռ��ͻ��");
		}
	}

	/**
	 * ��B��ʽ����Ƿ����λ�ó�ͻ
	 */
	public void checkConflictPlanB() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanB<Material>(), board.getActivityEntries())) {
			log.info("CheckLocationConflictPlanB;existence;success");
			JOptionPane.showMessageDialog(null, "����λ�ö�ռ��ͻ��");
		} else {
			log.info("CheckResourcConflictPlanB;inexistence;success");
			JOptionPane.showMessageDialog(null, "������λ�ö�ռ��ͻ��");
		}
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
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		if (materials.contains(material)) {
			log.info("FindAllEntryUseResource;" + materialInfo + ";fail");
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
		log.info("FindAllEntryUseResource;" + materialInfo + ";success");
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
			log.info("FindLocation;Name:" + name + ",Date:" + date + ";fail");
			JOptionPane.showMessageDialog(null, "�޴�λ�ã�");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Date:" + date + ";success");
		board.allEntryAtLocation(loc, date);
	}
	
	/**
	 * ��ʾ��־
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/activitylog.txt"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��־�ļ�����");
			return;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = null, end = null;
		JOptionPane.showMessageDialog(null, "�����������ݲ�����ʽ��������Ϊ����������");
		String startTime = JOptionPane.showInputDialog("��ѯʱ��εĿ�ʼʱ��\n��ʽyyyy-MM-dd HH:mm��");
		if(startTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			start = LocalDateTime.parse(startTime, df);
		String endTime = JOptionPane.showInputDialog("��ѯʱ��εĿ�ʼʱ��\n��ʽyyyy-MM-dd HH:mm��");
		if(endTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			end = LocalDateTime.parse(endTime, df);
		String actionType = JOptionPane.showInputDialog("��ѯ�������ͣ�");
		String entryName = JOptionPane.showInputDialog("��ѯ�ƻ�������");
		
		List<String[]> list = new ArrayList<>();
		DateFormat formatFrom = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss aa", Locale.ENGLISH);
		DateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter df2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		try {
			while(input.hasNext()) {
			String[] logline = new String[5];
			String line1 = input.nextLine();
			String line2 = input.nextLine();
			int pos = line1.indexOf("activity");
			logline[0] = line1.substring(0, pos-1);
			Date date = formatFrom.parse(logline[0]);
	        LocalDateTime time = LocalDateTime.parse(formatTo.format(date), df2);
			pos = line2.indexOf(":");
			logline[1] = line2.substring(0, pos);
			String[] message = line2.substring(pos).split(";");
			logline[2] = message[0];
			logline[3] = message[1];
			logline[4] = message[2];
			if((start != null && start.isAfter(time)) || (end != null && end.isBefore(time)))
				continue;
			if(!actionType.equals("") && actionType.equals(logline[2]))
				continue;
			if(!entryName.equals("") && entryName.equals(logline[4]))
				continue;
			list.add(logline);
			}
		} catch(ParseException e) {
			return;
		}
		input.close();
		String[] title = {"Time", "Type", "Option", "Message", "Result"};
		Object[][] content = new Object[list.size()][5];
		for(int i = 0; i < list.size(); i++)
			content[i] = list.get(i);
		board.visualize(content, title, "log");
	}

	/**
	 * ������
	 */
	public static void main(String[] args) {
		new ActivityScheduleApp().run();;
	}
}
