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
 * 高铁车次管理应用
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
	 * 添加可用车厢资源
	 */
	public void addCarriage() {
		String id = JOptionPane.showInputDialog("车厢的编号：");
		String type = JOptionPane.showInputDialog("车厢的型号：");
		String seats = JOptionPane.showInputDialog("车厢的定员数：");
		String manufactureYear = JOptionPane.showInputDialog("车厢的生产年份：");
		Carriage carriage = new Carriage(Integer.parseInt(id), type, Integer.parseInt(seats),
				Integer.parseInt(manufactureYear));
		if (carriages.contains(carriage))
			JOptionPane.showMessageDialog(null, "此车厢已存在！");
		else {
			carriages.add(carriage);
			JOptionPane.showMessageDialog(null, "添加成功！");
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
		if (carriages.contains(carriage)) {
			carriages.remove(carriage);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此车厢不存在！");
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("车站名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "此车站已存在！");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 删除不可用的位置
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("车站名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此车站不存在！");
	}

	/**
	 * 找到指定的车次计划项
	 * 
	 * @return 非空 相应的车次计划项 null 没有相应的车次计划项
	 */
	private PlanningEntry<Carriage> findTrainEntry() {
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
				JOptionPane.showMessageDialog(null, "无位置，添加失败！");
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
				JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
				return;
			}
		}
		PlanningEntry<Carriage> trainEntry = PlanningEntry.trainEntry(name, locationList, times);
		board.addTrainEntry(trainEntry);
		JOptionPane.showMessageDialog(null, "添加成功！");
	}

	/**
	 * 取消某个计划项
	 */
	public void cancelTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "取消失败！");
		else if (te.cancel())
			JOptionPane.showMessageDialog(null, "取消成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
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
				JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
				return;
			}
		if (te == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
		else if (((TrainEntry<Carriage>) te).allocateTrain(train))
			JOptionPane.showMessageDialog(null, "资源分配成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
	}

	/**
	 * 启动某个计划项
	 */
	public void runTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "启动失败！");
		else if (te.running())
			JOptionPane.showMessageDialog(null, "启动成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
	}

	/**
	 * 阻塞某个计划项
	 */
	public void blockTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，阻塞失败！");
		else if (((TrainEntry<Carriage>) te).block())
			JOptionPane.showMessageDialog(null, "阻塞成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可阻塞！");
	}

	/**
	 * 结束某个计划项
	 */
	public void endTrain() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		else if (te.end())
			JOptionPane.showMessageDialog(null, "结束成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		PlanningEntry<Carriage> te = findTrainEntry();
		if (te == null)
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		else
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + te.getState());
	}

	/**
	 * 检测当前的计划项集合中可能存在的资源独占冲突
	 */
	public void checkConflict() {
		if (checkConf.checkResourceExclusiveConflict(board.getTrainEntries()))
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		else
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
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

		if (!carriages.contains(carriage)) {
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

		PlanningEntry<Carriage> te = findTrainEntry();
		if (!carriages.contains(carriage))
			JOptionPane.showMessageDialog(null, "此资源不存在！");
		else if (te == null)
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
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
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		board.allEntryAtLocation(loc, time);
	}

	/**
	 * 主程序
	 */
	public static void main(String[] args) {
		new TrainScheduleApp();
	}
}
