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
 * 高铁车次管理应用
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
	 * 操作面板
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
	 * 添加可用车厢资源
	 */
	public void addCarriage() {
		String id = JOptionPane.showInputDialog("车厢的编号：");
		String type = JOptionPane.showInputDialog("车厢的型号：");
		String seats = JOptionPane.showInputDialog("车厢的定员数：");
		String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		if (addCarriage(carriage)) {
			log.info("AddCarriage;" + carriageInfo + ";success");
			JOptionPane.showMessageDialog(null, "此车厢已存在！");
		} else {
			log.info("AddCarriage;" + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 添加可用车厢的功能实现
	 * 
	 * @param carriage 车厢
	 * @return true 成功;false 已存在，失败
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
	 * 删除不可用车厢资源
	 */
	public void removeCarriage() {
		String id = JOptionPane.showInputDialog("车厢的编号：");
		String type = JOptionPane.showInputDialog("车厢的型号：");
		String seats = JOptionPane.showInputDialog("车厢的定员数：");
		String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		try {
			if (removeCarriage(carriage)) {
				log.info("RemoveCarriage;" + carriageInfo + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemoveCarriage;" + carriageInfo + ";fail");
				JOptionPane.showMessageDialog(null, "此车厢不存在！");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveCarriage;" + carriageInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定资源？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveCarriage;EntryUseResourceOrLocationException:TrainScheduleApp.removeCarriage(carriage);reselect resource");
				removeCarriage();
			} else
				log.warning(
						"RemoveCarriage;EntryUseResourceOrLocationException:TrainScheduleApp.removeCarriage(carriage);end");
		}
	}

	/**
	 * 删除不可用资源的功能实现部分
	 * 
	 * @param carriage 待删除的资源
	 * @return true 删除成功;false 不存在
	 * @throws EntryUseResourceOrLocationException 资源被使用中
	 */
	public boolean removeCarriage(Carriage carriage) throws EntryUseResourceOrLocationException {
		if (carriages.contains(carriage)) {
			for (PlanningEntry<Carriage> pe : board.getTrainEntries())
				if (((TrainEntry<Carriage>) pe).getResources().contains(carriage) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该资源，不能删除");
			carriages.remove(carriage);
			return true;
		} else
			return false;
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("车站名称：");
		Location loc = new Location(name, true);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "此车站已存在！");
		}
	}

	/**
	 * 添加可用位置的功能实现部分
	 * 
	 * @param loc 待添加的位置
	 * @return true 添加成功;false 位置已存在
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
	 * 删除不可用的位置
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("车站名称：");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "此车站不存在！");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定位置？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:TrainScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:TrainScheduleApp.removeLocation(loc);end");
		}

	}

	/**
	 * 删除不可用的位置的功能实现部分
	 * 
	 * @param loc 待删除的位置
	 * @return true 删除成功;false 不存在位置
	 * @throws EntryUseResourceOrLocationException 有计划项在使用该位置
	 */
	public boolean removeLocation(Location loc) throws EntryUseResourceOrLocationException {
		if (locations.contains(loc)) {
			for (PlanningEntry<Carriage> pe : board.getTrainEntries())
				if (((TrainEntry<Carriage>) pe).getLocations().contains(loc) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该位置，不可删除");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * 找到指定的车次计划项
	 * 
	 * @return 非空 相应的车次计划项 null 没有相应的车次计划项
	 */
	private PlanningEntry<Carriage> findTrainEntry(StringBuilder trainInfo) {
		String name = JOptionPane.showInputDialog("车次号：");
		List<String> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String num = JOptionPane.showInputDialog("经过的车站个数:");
		for (int i = 1; i < Integer.parseInt(num); i++) {
			String info = JOptionPane.showInputDialog("经过的第" + i + "个车站:");
			locations.add(info);
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("出站时间，格式yyyy-MM-dd HH:mm：");
			times.add(info);
			info = "";
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("下一站进站站时间，格式yyyy-MM-dd HH:mm：");
			times.add(info);
		}
		String info = JOptionPane.showInputDialog("经过的最后一个车站:");
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
	 * 找到指定的车次计划项
	 * @param name 计划项名
	 * @param locs 位置列表
	 * @param times 时间表
	 * @return 非空 相应的车次计划项 null 没有相应的车次计划项
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
	 * 增加一条新的计划项
	 */
	public void addTrain() {
		String name = JOptionPane.showInputDialog("车次号：");
		List<String> locations = new ArrayList<>();
		List<String> times = new ArrayList<>();
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String num = JOptionPane.showInputDialog("经过的车站个数:");
		for (int i = 1; i < Integer.parseInt(num); i++) {
			String info = JOptionPane.showInputDialog("经过的第" + i + "个车站:");
			locations.add(info);
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("出站时间，格式yyyy-MM-dd HH:mm：");
			times.add(info);
			info = "";
			while (!info.matches(regex))
				info = JOptionPane.showInputDialog("下一站进站站时间，格式yyyy-MM-dd HH:mm：");
			times.add(info);
		}
		String info = JOptionPane.showInputDialog("经过的最后一个车站:");
		locations.add(info);
		String trainInfo = "Entry:" + name + ",Departure" + locations.get(0) + ",Arrival:"
				+ locations.get(locations.size() - 1) + ",StartTime:" + times.get(0) + ",EndTime:"
				+ times.get(times.size() - 1);
		// 在可用位置列表中寻找位置
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
				JOptionPane.showMessageDialog(null, "无位置，添加失败！");
				return;
			}
		}

		if (addTrain(name, locationList, times)) {
			log.info("AddTrain;" + trainInfo + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddTrain;" + trainInfo + ";fail");
			JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
		}
	}

	/**
	 * 增加一条新的计划项的功能实现部分
	 * 
	 * @param name      车次，非空
	 * @param locations 车站列表，非空
	 * @param times     时间表，非空
	 * @return true 添加成功;false 计划项已存在
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
	 * 取消某个计划项
	 */
	public void cancelTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		try {
			if (cancelTrain(te)) {
				log.info("CancelTrain;" + trainInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "取消成功！");
			} else {
				log.info("CancelTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "取消失败！");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelTrain;" + trainInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新选择取消的计划项？") == JOptionPane.YES_OPTION) {
				log.warning("CancelTrain;CannotCancelledException:TrainScheduleApp.cancelTrain(te);reselect entry");
				cancelTrain();
			} else
				log.warning("CancelTrain;CannotCancelledException:TrainScheduleApp.cancelTrain(te);end");
		}
	}

	/**
	 * 取消计划项功能实现部分
	 * 
	 * @param te 取消的计划项
	 * @return true 取消成功;false 取消失败，计划项为空
	 * @throws CannotCancelledException 当前计划项状态不可取消
	 */
	public boolean cancelTrain(PlanningEntry<Carriage> te) throws CannotCancelledException {
		if (te == null)
			return false;
		if (te.cancel())
			return true;
		else
			throw new CannotCancelledException("当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		// 车厢资源
		List<Carriage> train = new ArrayList<>();
		String carriageNum = JOptionPane.showInputDialog("车厢节数：");
		for (int i = 0; i < Integer.parseInt(carriageNum); i++) {
			String id = JOptionPane.showInputDialog("车厢的编号：");
			String type = JOptionPane.showInputDialog("车厢的型号：");
			String seats = JOptionPane.showInputDialog("车厢的定员数：");
			String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
			Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
					Integer.parseInt(manufactureYear));
			train.add(carriage);
		}
		// 确保车厢资源都是可用的
		for (Carriage carriage : train)
			if (!this.carriages.contains(carriage)) {
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
				return;
			}
		try {
			if (te == null) {
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
			} else if (allocateTrain(te, train)) {
				log.info("AllocateTrain;" + trainInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "资源分配成功！");
			} else {
				log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
			}
		} catch (ResourceOrLocationConflictException e) {
			log.info("AllocateTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, e.getMessage() + "\n请重新分配资源");
			log.warning(
					"AllocateTrain;ResourceOrLocationConflictException:TrainScheduleApp.allocateTrain(te,train);reallocate");
			allocateTrain();
		}
	}

	/**
	 * 为计划项分配资源的功能实现部分
	 * 
	 * @param te    待分配的计划项
	 * @param train 待分配的资源
	 * @return 分配成功; false 分配失败，计划项状态不能分配资源
	 * @throws ResourceOrLocationConflictException 分配后导致与其他计划项产生资源独占冲突
	 */
	public boolean allocateTrain(PlanningEntry<Carriage> te, List<Carriage> train)
			throws ResourceOrLocationConflictException {
		if (((TrainEntry<Carriage>) te).allocateTrain(train)) {
			if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries()))
				throw new ResourceOrLocationConflictException("分配后导致与其他计划项产生资源独占冲突");
			return true;
		} else
			return false;
	}

	/**
	 * 启动某个计划项
	 */
	public void runTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("RunTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，启动失败！");
		}
		else if (te.running()) {
			log.info("RunTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "启动成功！");
		}
		else {
			log.info("RunTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
		}
	}

	/**
	 * 阻塞某个计划项
	 */
	public void blockTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("BlockTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，阻塞失败！");
		}
		else if (((TrainEntry<Carriage>) te).block()) {
			log.info("BlockTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "阻塞成功！");
		}
		else {
			log.info("BlockTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可阻塞！");
		}
	}

	/**
	 * 结束某个计划项
	 */
	public void endTrain() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null) {
			log.info("EndTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		}
		else if (te.end()) {
			log.info("EndTrain;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "结束成功！");
		}
		else{
			log.info("EndTrain;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
		}
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (te == null){
			log.info("GetState;" + trainInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		}
		else{
			log.info("GetState;" + trainInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + te.getState());
		}
	}

	/**
	 * 检测当前的计划项集合中可能存在的资源独占冲突
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries())) {
			log.info("CheckResourceConflict;existence;success");
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		}
		else {
			log.info("CheckResourcConflict;inexistence;success");
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
		}
	}

	/**
	 * 针对用户选定的某个资源，列出使用该资源的所有计划项
	 */
	public void findAllEntryUseResource() {
		String id = JOptionPane.showInputDialog("车厢的编号：");
		String type = JOptionPane.showInputDialog("车厢的型号：");
		String seats = JOptionPane.showInputDialog("车厢的定员数：");
		String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		String carriageInfo = "Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		if (!carriages.contains(carriage)) {
			log.info("FindAllEntryUseResource;" + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此资源不存在！");
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
		board.visualize(str, title, "使用该资源的所有计划项");
	}

	/**
	 * 针对用户选定的某个资源和某个计划项之后，找出它的前序计划项
	 */
	public void findPreEntryUseResource() {
		String id = JOptionPane.showInputDialog("车厢的编号：");
		String type = JOptionPane.showInputDialog("车厢的型号：");
		String seats = JOptionPane.showInputDialog("车厢的定员数：");
		String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		String carriageInfo = ",Code:" + id + ",Type:" + type + ",Seats:" + seats + ",ManufactureYear:"
				+ manufactureYear;
		StringBuilder trainInfo = new StringBuilder();
		PlanningEntry<Carriage> te = findTrainEntry(trainInfo);
		if (!carriages.contains(carriage)) {
			log.info("FindPreEntryUseResource;" + trainInfo + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此资源不存在！");
		}
		else if (te == null) {
			log.info("FindPreEntryUseResource;" + trainInfo + carriageInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
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
	 * 选定特定位置，可视化展示当前时刻该位置的信息板
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("车站的名字:");
		String time = JOptionPane.showInputDialog("查询时间，格式yyyy-MM-dd HH:mm：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		while (!time.matches(regex))
			time = JOptionPane.showInputDialog("查询时间，格式yyyy-MM-dd HH:mm：");
		Location loc = new Location(name, true);
		if (!locations.contains(loc)) {
			log.info("FindLocation;Name:" + name + ",Time:" + time + ";fail");
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Time:" + time + ";success");
		board.allEntryAtLocation(loc, time);
	}
	
	/**
	 * 显示日志
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/trainlog.txt"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "日志文件错误！");
			return;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = null, end = null;
		JOptionPane.showMessageDialog(null, "以下四项内容不填或格式不符均视为该项无限制");
		String startTime = JOptionPane.showInputDialog("查询时间段的开始时间\n格式yyyy-MM-dd HH:mm：");
		if(startTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			start = LocalDateTime.parse(startTime, df);
		String endTime = JOptionPane.showInputDialog("查询时间段的开始时间\n格式yyyy-MM-dd HH:mm：");
		if(endTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}-\\{2}"))
			end = LocalDateTime.parse(endTime, df);
		String actionType = JOptionPane.showInputDialog("查询操作类型：");
		String entryName = JOptionPane.showInputDialog("查询计划项名：");
		
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
	 * 主程序
	 */
	public static void main(String[] args) {
		new TrainScheduleApp().run();
	}
}
