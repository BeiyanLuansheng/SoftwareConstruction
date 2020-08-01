package train;

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

import common.conflict.PlanningEntryAPIs;
import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;
import exceptions.*;

/**
 * �������ι���Ӧ��
 */
public class TrainScheduleApp {
	private final JFrame frame = new JFrame();
	private final JPanel buttonPanel = new JPanel();
	private final Set<Location> locations = new HashSet<>();
	private final Set<Carriage> carriages = new HashSet<>();
	private final Board<Carriage> board = new Board<>();
	private final PlanningEntryAPIs<Carriage> checkConf = new PlanningEntryAPIs<>();
	private final Logger log = Logger.getLogger(TrainScheduleApp.class.getName());

	public TrainScheduleApp() {
		try {
			new FileWriter("src/log/trainlog.txt").close();
			Locale.setDefault(new Locale("en", "EN"));
			log.setLevel(Level.INFO);
			FileHandler fh = new FileHandler("src/log/trainlog.txt", true);
			fh.setFormatter(new SimpleFormatter());
			log.addHandler(fh);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������
	 */
	public void run() {
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
	 * ��ӿ��ó�����Դ
	 */
	public void addCarriage() {
		String id = JOptionPane.showInputDialog("����ı�ţ�");
		String type = JOptionPane.showInputDialog("������ͺţ�");
		String seats = JOptionPane.showInputDialog("����Ķ�Ա����");
		String manufactureYear = JOptionPane.showInputDialog("�����������ݣ�");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		if (addCarriage(carriage)) {
			log.info("AddCarriage;" + carriageInfo + ";success");
			JOptionPane.showMessageDialog(null, "�˳����Ѵ��ڣ�");
		} else {
			log.info("AddCarriage;" + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		}
	}

	/**
	 * ��ӿ��ó���Ĺ���ʵ��
	 * 
	 * @param carriage ����
	 * @return true �ɹ�;false �Ѵ��ڣ�ʧ��
	 */
	public boolean addCarriage(Carriage carriage) {
		if (carriages.contains(carriage))
			return false;
		else {
			carriages.add(carriage);
			return true;
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
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		try {
			if (removeCarriage(carriage)) {
				log.info("RemoveCarriage;" + carriageInfo + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemoveCarriage;" + carriageInfo + ";fail");
				JOptionPane.showMessageDialog(null, "�˳��᲻���ڣ�");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveCarriage;" + carriageInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ����Դ��") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveCarriage;EntryUseResourceOrLocationException:TrainScheduleApp.removeCarriage(carriage);reselect resource");
				removeCarriage();
			} else
				log.warning(
						"RemoveCarriage;EntryUseResourceOrLocationException:TrainScheduleApp.removeCarriage(carriage);end");
		}
	}

	/**
	 * ɾ����������Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param carriage ��ɾ������Դ
	 * @return true ɾ���ɹ�;false ������
	 * @throws EntryUseResourceOrLocationException ��Դ��ʹ����
	 */
	public boolean removeCarriage(Carriage carriage) throws EntryUseResourceOrLocationException {
		if (carriages.contains(carriage)) {
			for (PlanningEntry<Carriage> pe : board.getTrainEntries())
				if (((TrainEntry<Carriage>) pe).getResources().contains(carriage) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø���Դ������ɾ��");
			carriages.remove(carriage);
			return true;
		} else
			return false;
	}

	/**
	 * ��ӿ���λ��
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("��վ���ƣ�");
		Location loc = new Location(name, true);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "�˳�վ�Ѵ��ڣ�");
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
		String name = JOptionPane.showInputDialog("��վ���ƣ�");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "�˳�վ�����ڣ�");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ָ��λ�ã�") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:TrainScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:TrainScheduleApp.removeLocation(loc);end");
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
			for (PlanningEntry<Carriage> pe : board.getTrainEntries())
				if (((TrainEntry<Carriage>) pe).getLocations().contains(loc) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("�ƻ���" + pe.getName() + "����ʹ�ø�λ�ã�����ɾ��");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * �ҵ�ָ���ĳ��μƻ���
	 * 
	 * @return �ǿ� ��Ӧ�ĳ��μƻ��� null û����Ӧ�ĳ��μƻ���
	 */
	private PlanningEntry<Carriage> findTrainEntry(StringBuilder trainInfo) {
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
		trainInfo.append("Entry:" + name);
		trainInfo.append(",Departure" + locations.get(0));
		trainInfo.append(",Arrival:" + locations.get(locations.size() - 1));
		trainInfo.append(",StartTime:" + times.get(0));
		trainInfo.append(",EndTime:" + times.get(times.size() - 1));

		List<Location> locs = new ArrayList<>();
		for (String l : locations)
			locs.add(new Location(l, true));
		
		return findTrainEntry(name, locs, times);
	}

	/**
	 * �ҵ�ָ���ĳ��μƻ���
	 * @param name �ƻ�����
	 * @param locs λ���б�
	 * @param times ʱ���
	 * @return �ǿ� ��Ӧ�ĳ��μƻ��� null û����Ӧ�ĳ��μƻ���
	 */
	public PlanningEntry<Carriage> findTrainEntry(String name, List<Location> locs, List<String> times){
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
		String trainInfo = "Entry:" + name + ",Departure" + locations.get(0) + ",Arrival:"
				+ locations.get(locations.size() - 1) + ",StartTime:" + times.get(0) + ",EndTime:"
				+ times.get(times.size() - 1);
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
				log.info("AddTrain;" + trainInfo + ";fail");
				JOptionPane.showMessageDialog(null, "��λ�ã����ʧ�ܣ�");
				return;
			}
		}

		if (addTrain(name, locationList, times)) {
			log.info("AddTrain;" + trainInfo + ";success");
			JOptionPane.showMessageDialog(null, "��ӳɹ���");
		} else {
			log.info("AddTrain;" + trainInfo + ";fail");
			JOptionPane.showMessageDialog(null, "���иüƻ�����ʧ�ܣ�");
		}
	}

	/**
	 * ����һ���µļƻ���Ĺ���ʵ�ֲ���
	 * 
	 * @param name      ���Σ��ǿ�
	 * @param locations ��վ�б��ǿ�
	 * @param times     ʱ����ǿ�
	 * @return true ��ӳɹ�;false �ƻ����Ѵ���
	 */
	public boolean addTrain(String name, List<Location> locations, List<String> times) {
		List<Timeslot> t = new ArrayList<>();
		for (int i = 0; i < times.size(); i += 2)
			t.add(new Timeslot(times.get(i), times.get(i + 1)));
		Iterator<PlanningEntry<Carriage>> iter = board.iterator();
		while (iter.hasNext()) {
			TrainEntry<Carriage> te = (TrainEntry<Carriage>) iter.next();
			if (te.getName().equals(name) && te.getLocations().equals(locations) && te.getTimeslot().equals(t)) {
				return false;
			}
		}
		PlanningEntry<Carriage> trainEntry = PlanningEntry.trainEntry(name, locations, times);
		board.addTrainEntry(trainEntry);
		return true;
	}

	/**
	 * ȡ��ĳ���ƻ���
	 */
	public void cancelTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		try {
			if (cancelTrain(te)) {
				log.info("CancelTrain;" + trainInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "ȡ���ɹ���");
			} else {
				log.info("CancelTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "ȡ��ʧ�ܣ�");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelTrain;" + trainInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n����ѡ��ȡ���ļƻ��") == JOptionPane.YES_OPTION) {
				log.warning("CancelTrain;CannotCancelledException:TrainScheduleApp.cancelTrain(te);reselect entry");
				cancelTrain();
			} else
				log.warning("CancelTrain;CannotCancelledException:TrainScheduleApp.cancelTrain(te);end");
		}
	}

	/**
	 * ȡ���ƻ����ʵ�ֲ���
	 * 
	 * @param te ȡ���ļƻ���
	 * @return true ȡ���ɹ�;false ȡ��ʧ�ܣ��ƻ���Ϊ��
	 * @throws CannotCancelledException ��ǰ�ƻ���״̬����ȡ��
	 */
	public boolean cancelTrain(PlanningEntry<Carriage> te) throws CannotCancelledException {
		if (te == null)
			return false;
		if (te.cancel())
			return true;
		else
			throw new CannotCancelledException("��ǰ״̬����ȡ����");
	}

	/**
	 * Ϊĳ���ƻ��������Դ
	 */
	public void allocateTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
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
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "û�и���Դ����Դ����ʧ�ܣ�");
				return;
			}
		try {
			if (te == null) {
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "û�иüƻ����Դ����ʧ�ܣ�");
			} else if (allocateTrain(te, train)) {
				log.info("AllocateTrain;" + trainInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "��Դ����ɹ���");
			} else {
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "��ǰ״̬���ɷ�����Դ��");
			}
		} catch (ResourceOrLocationConflictException e) {
			log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n�����·�����Դ");
			log.warning(
					"AllocateTrain;ResourceOrLocationConflictException:TrainScheduleApp.allocateTrain(te,train);reallocate");
			allocateTrain();
		}
	}

	/**
	 * Ϊ�ƻ��������Դ�Ĺ���ʵ�ֲ���
	 * 
	 * @param te    ������ļƻ���
	 * @param train ���������Դ
	 * @return ����ɹ�; false ����ʧ�ܣ��ƻ���״̬���ܷ�����Դ
	 * @throws ResourceOrLocationConflictException ��������������ƻ��������Դ��ռ��ͻ
	 */
	public boolean allocateTrain(PlanningEntry<Carriage> te, List<Carriage> train)
			throws ResourceOrLocationConflictException {
		if (((TrainEntry<Carriage>) te).allocateTrain(train)) {
			if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries()))
				throw new ResourceOrLocationConflictException("��������������ƻ��������Դ��ռ��ͻ");
			return true;
		} else
			return false;
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void runTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("RunTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		}
		else if (te.running()) {
			log.info("RunTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		}
		else {
			log.info("RunTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void blockTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("BlockTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		}
		else if (((TrainEntry<Carriage>) te).block()) {
			log.info("BlockTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		}
		else {
			log.info("BlockTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬����������");
		}
	}

	/**
	 * ����ĳ���ƻ���
	 */
	public void endTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("EndTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "û�иüƻ������ʧ�ܣ�");
		}
		else if (te.end()) {
			log.info("EndTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�����ɹ���");
		}
		else{
			log.info("EndTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "��ǰ״̬���ɱ�������");
		}
	}

	/**
	 * ѡ��һ���ƻ���鿴���ĵ�ǰ״̬
	 */
	public void getState() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null){
			log.info("GetState;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		}
		else{
			log.info("GetState;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "�˼ƻ���״̬Ϊ��" + te.getState());
		}
	}

	/**
	 * ��⵱ǰ�ļƻ�����п��ܴ��ڵ���Դ��ռ��ͻ
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries())) {
			log.info("CheckResourceConflict;existence;success");
			JOptionPane.showMessageDialog(null, "������Դ��ռ��ͻ��");
		}
		else {
			log.info("CheckResourcConflict;inexistence;success");
			JOptionPane.showMessageDialog(null, "��������Դ��ռ��ͻ��");
		}
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
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		if (!carriages.contains(carriage)) {
			log.info("FindAllEntryUseResource;" + carriageInfo + ";fail");
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
		log.info("FindAllEntryUseResource;" + carriageInfo + ";success");
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
		String carriageInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (!carriages.contains(carriage)) {
			log.info("FindPreEntryUseResource;" + trainInfo + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "����Դ�����ڣ�");
		}
		else if (te == null) {
			log.info("FindPreEntryUseResource;" + trainInfo + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "�˼ƻ�����ڣ�");
		}
		else {
			TrainEntry<Carriage> prete = (TrainEntry<Carriage>) checkConf.findPreEntryPerResource(carriage, te,
					board.getTrainEntries());
			JOptionPane.showMessageDialog(null,
					"Entry:" + prete.getName() + "\nTime:" + prete.getTimeslot().toString() + "\nFrom:"
							+ prete.getLocations().get(0).getName() + "\nTo:"
							+ prete.getLocations().get(prete.getLocations().size() - 1).getName() + "\nState:"
							+ prete.getState());
			log.info("FindPreEntryUseResource;" + trainInfo + carriageInfo + ";success");
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
			log.info("FindLocation;Name:" + name + ",Time:" + time + ";fail");
			JOptionPane.showMessageDialog(null, "�޴�λ�ã�");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Time:" + time + ";success");
		board.allEntryAtLocation(loc, time);
	}
	
	/**
	 * ��ʾ��־
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/trainlog.txt"));
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
			int pos = line1.indexOf("train");
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
		new TrainScheduleApp().run();
	}
}
