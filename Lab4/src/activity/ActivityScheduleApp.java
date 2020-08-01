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
 * 学习活动管理应用
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
	 * 操作面板
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
	 * 添加可用学习资料
	 */
	public void addMaterrial() {
		String name = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(name, department, date);
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		if (addMaterial(material)) {
			log.info("AddMaterrial;" + materialInfo + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddMaterrial;" + materialInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此材料已存在！");
		}
	}

	/**
	 * 添加学习资料的功能实现
	 * 
	 * @param material 资料
	 * @return true 成功; false 已存在，失败
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
	 * 删除不可用学习资料
	 */
	public void removeMaterial() {
		String name = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(name, department, date);
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		try {
			if (removeMaterial(material)) {
				log.info("RemoveMaterial;" + materialInfo + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemoveMaterial;" + materialInfo + ";fail");
				JOptionPane.showMessageDialog(null, "此材料不存在！");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveMaterial;" + materialInfo + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定资源？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveMaterial;EntryUseResourceOrLocationException:ActivityScheduleApp.removeMaterial(material);reselect resource");
				removeMaterial();
			} else
				log.warning(
						"RemoveMaterial;EntryUseResourceOrLocationException:ActivityScheduleApp.removeMaterial(material);end");
		}

	}

	/**
	 * 删除不可用资源的功能实现部分
	 * 
	 * @param material 待删除的资源
	 * @return true 删除成功;false 不存在
	 * @throws EntryUseResourceOrLocationException 资源被使用中
	 */
	public boolean removeMaterial(Material material) throws EntryUseResourceOrLocationException {
		if (materials.contains(material)) {
			for (PlanningEntry<Material> pe : board.getActivityEntries())
				if (((ActivityEntry<Material>) pe).getMaterial().equals(material) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该资源，不能删除");
			materials.remove(material);
			return true;
		} else
			return false;
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("地点名称：");
		Location loc = new Location(name, false);
		if (addLocation(loc)) {
			log.info("AddLocation;Name:" + name + ";success");
			JOptionPane.showMessageDialog(null, "添加成功！");
		} else {
			log.info("AddLocation;Name:" + name + ";fail");
			JOptionPane.showMessageDialog(null, "此地点已存在！");
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
		String name = JOptionPane.showInputDialog("地点名称：");
		Location loc = new Location(name, true);
		try {
			if (removeLocation(loc)) {
				log.info("RemoveLocation;Name:" + name + ";success");
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				log.info("RemoveLocation;Name:" + name + ";fail");
				JOptionPane.showMessageDialog(null, "此地点不存在！");
			}
		} catch (EntryUseResourceOrLocationException e) {
			log.info("RemoveLocation;Name:" + name + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新指定位置？") == JOptionPane.YES_OPTION) {
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:ActivityScheduleApp.removeLocation(loc);reselect location");
				removeLocation();
			} else
				log.warning(
						"RemoveLocation;EntryUseResourceOrLocationException:ActivityScheduleApp.removeLocation(loc);end");
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
			for (PlanningEntry<Material> pe : board.getActivityEntries())
				if (((ActivityEntry<Material>) pe).getLocation().equals(loc) && !pe.getState().equals("ENDED"))
					throw new EntryUseResourceOrLocationException("计划项" + pe.getName() + "正在使用该位置，不可删除");
			locations.remove(loc);
			return true;
		} else
			return false;
	}

	/**
	 * 找到指定的计划项
	 * 
	 * @return null 没有相应的计划项 非空 相应的计划项
	 */
	private PlanningEntry<Material> findActivityEntry(StringBuilder ActivityInfo) {
		String name = JOptionPane.showInputDialog("活动主题：");
		String location = JOptionPane.showInputDialog("活动地点：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("开始时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("结束时间，格式yyyy-MM-dd HH:mm：");
		ActivityInfo.append("Entry:" + name);
		ActivityInfo.append(",Departure" + location);
		ActivityInfo.append(",StartTime:" + start);
		ActivityInfo.append(",EndTime:" + end);

		return findActivityEntry(name, location, start, end);
	}

	/**
	 * 找到指定的计划项
	 * @param name 计划项名
	 * @param location 位置
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return null 没有相应的计划项 非空 相应的计划项
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
	 * 增加一条新的计划项
	 */
	public void addActivity() {
		String name = JOptionPane.showInputDialog("活动主题：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("开始时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("结束时间，格式yyyy-MM-dd HH:mm：");

		while (true) {
			String location = JOptionPane.showInputDialog("活动地点：");
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
					JOptionPane.showMessageDialog(null, "无位置，添加失败！");
					return;
				} else if (addActivity(name, start, end, loc)) {
					log.info("AddActivity;" + activityInfo + ";success");
					JOptionPane.showMessageDialog(null, "添加成功！");
					return;
				} else {
					log.info("AddActivity;" + activityInfo + ";fail");
					JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
					return;
				}
			} catch (ResourceOrLocationConflictException e) {
				log.info("AddActivity;" + activityInfo + ";fail");
				JOptionPane.showMessageDialog(null, e.getMessage() + "\n请重新输入地点");
				log.warning(
						"AddActivity;ResourceOrLocationConflictException:ActivityScheduleApp.addActivity;reset location");
				continue;
			}
		}
	}

	/**
	 * 增加一条新的计划项的功能实现部分
	 * 
	 * @param name  活动名称，非空
	 * @param start 开始时间，非空
	 * @param end   结束时间，非空
	 * @param loc   活动位置，非空
	 * @return 添加成功;false 计划项已存在
	 * @throws ResourceOrLocationConflictException 设置位置后与已有计划项存在位置独占冲突
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
			throw new ResourceOrLocationConflictException("设置后导致与其他计划项产生位置独占冲突");
		board.addActivityEntry(activityEntry);
		return true;
	}

	/**
	 * 取消某个计划项
	 */
	public void cancelActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		try {
			if (cancelActivity(ae)) {
				log.info("CancelActivity;" + activityInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "取消成功！");
			} else {
				log.info("CancelActivity;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "没有该计划项，取消失败！");
			}
		} catch (CannotCancelledException e) {
			log.info("CancelActivity;" + activityInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新选择取消的计划项？") == JOptionPane.YES_OPTION) {
				log.warning(
						"CancelActivity;CannotCancelledException:ActivityScheduleApp.cancelActivity(ae);reselect entry");
				cancelActivity();
			} else
				log.warning("CancelActivity;CannotCancelledException:ActivityScheduleApp.cancelActivity(ae);end");
		}
	}

	/**
	 * 取消计划项功能实现部分
	 * 
	 * @param ae 取消的计划项
	 * @return true 取消成功;false 取消失败，计划项为空
	 * @throws CannotCancelledException 当前计划项状态不可取消
	 */
	public boolean cancelActivity(PlanningEntry<Material> ae) throws CannotCancelledException {
		if (ae == null)
			return false;
		if (ae.cancel())
			return true;
		else
			throw new CannotCancelledException("当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		String materialName = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(materialName, department, date);
		if (ae == null) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
		} else if (!materials.contains(material)) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
		} else if (allocateActivity(ae, material)) {
			log.info("AllocateActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "资源分配成功！");
		} else {
			log.info("AllocateActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
		}
	}

	/**
	 * 为计划项分配资源的功能实现部分
	 * 
	 * @param ae       待分配的计划项
	 * @param material 待分配的资源
	 * @return true 分配成功; false 分配失败，计划项状态不能分配资源
	 */
	public boolean allocateActivity(PlanningEntry<Material> ae, Material material) {
		if (((ActivityEntry<Material>) ae).allocateMaterial(material))
			return true;
		else
			return false;
	}

	/**
	 * 启动某个计划项
	 */
	public void runActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("RunActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，启动失败！");
		} else if (ae.running()) {
			log.info("RunActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "启动成功！");
		} else {
			log.info("RunActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
		}
	}

	/**
	 * 变更某个已存在的计划项的位置
	 */
	public void setLocation() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("SetLocation;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，设置失败！");
			return;
		}

		Location oldLoc = ((ActivityEntry<Material>) ae).getLocation();
		String newLocation = JOptionPane.showInputDialog("新活动地点：");
		Location loc = null;
		for (Location l : locations)
			if (l.getName().equals(newLocation)) {
				loc = l;
				break;
			}
		try {
			if (loc == null) {
				log.info("SetLocation;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "无新位置，设置失败！");
			} else if (setLocation(ae, loc)) {
				log.info("SetLocation;" + activityInfo.toString() + ";success");
				JOptionPane.showMessageDialog(null, "设置成功！");
			} else {
				log.info("SetLocation;" + activityInfo.toString() + ";fail");
				JOptionPane.showMessageDialog(null, "当前状态不能被设置位置！");
			}
		} catch (ResourceOrLocationConflictException e) {
			((ActivityEntry<Material>) ae).setLocation(oldLoc);
			log.info("SetLocation;" + activityInfo.toString() + ";fail");
			if (JOptionPane.showConfirmDialog(null, e.getMessage() + "\n重新设置位置？") == JOptionPane.YES_OPTION) {
				log.warning(
						"SetLocation;ResourceOrLocationConflictException:ActivityScheduleApp.setLocation(ae, loc);reset location");
				setLocation();
			} else
				log.warning(
						"SetLocation;ResourceOrLocationConflictException:ActivityScheduleApp.setLocation(ae, loc);end");
		}
	}

	/**
	 * 设置位置的功能实现部分
	 * 
	 * @param ae  计划项，非空
	 * @param loc 位置，非空
	 * @return true 设置成功;false 设置失败
	 * @throws ResourceOrLocationConflictException 设置后导致与其他计划项产生位置独占冲突
	 */
	public boolean setLocation(PlanningEntry<Material> ae, Location loc) throws ResourceOrLocationConflictException {
		if (((ActivityEntry<Material>) ae).setLocation(loc)) {
			if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries()))
				throw new ResourceOrLocationConflictException("设置后导致与其他计划项产生位置独占冲突");
			return true;
		} else
			return false;
	}

	/**
	 * 结束某个计划项
	 */
	public void endActivity() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("EndActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		} else if (ae.end()) {
			log.info("EndActivity;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "结束成功！");
		} else {
			log.info("EndActivity;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
		}
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		StringBuilder activityInfo = new StringBuilder();
		PlanningEntry<Material> ae = findActivityEntry(activityInfo);
		if (ae == null) {
			log.info("GetState;" + activityInfo.toString() + ";fail");
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		} else {
			log.info("GetState;" + activityInfo.toString() + ";success");
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + ae.getState());
		}
	}

	/**
	 * 用A方式检查是否存在位置冲突
	 */
	public void checkConflictPlanA() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries())) {
			log.info("CheckLocationConflictPlanA;existence;success");
			JOptionPane.showMessageDialog(null, "存在位置独占冲突！");
		} else {
			log.info("CheckResourcConflictPlanA;inexistence;success");
			JOptionPane.showMessageDialog(null, "不存在位置独占冲突！");
		}
	}

	/**
	 * 用B方式检查是否存在位置冲突
	 */
	public void checkConflictPlanB() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanB<Material>(), board.getActivityEntries())) {
			log.info("CheckLocationConflictPlanB;existence;success");
			JOptionPane.showMessageDialog(null, "存在位置独占冲突！");
		} else {
			log.info("CheckResourcConflictPlanB;inexistence;success");
			JOptionPane.showMessageDialog(null, "不存在位置独占冲突！");
		}
	}

	/**
	 * 针对用户选定的某个资源，列出使用该资源的所有计划项
	 */
	public void findAllEntryUseResource() {
		String name = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(name, department, date);
		String materialInfo = "Name:" + name + ",Department:" + department + ",Date:" + date;
		if (materials.contains(material)) {
			log.info("FindAllEntryUseResource;" + materialInfo + ";fail");
			JOptionPane.showMessageDialog(null, "此资源不存在！");
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
		board.visualize(str, title, "使用该资源的所有计划项");
	}

	/**
	 * 选定特定位置，可视化展示当前日期该位置的信息板
	 */
	public void findLocation() {
		String name = JOptionPane.showInputDialog("地点的名字：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("日期，格式yyyy-MM-dd：");
		Location loc = new Location(name, true);
		if (!locations.contains(loc)) {
			log.info("FindLocation;Name:" + name + ",Date:" + date + ";fail");
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		log.info("FindLocation;Name:" + name + ",Date:" + date + ";success");
		board.allEntryAtLocation(loc, date);
	}
	
	/**
	 * 显示日志
	 */
	public void readLog() {
		Scanner input = null;
		try {
			input = new Scanner(new File("src/log/activitylog.txt"));
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
	 * 主程序
	 */
	public static void main(String[] args) {
		new ActivityScheduleApp().run();;
	}
}
