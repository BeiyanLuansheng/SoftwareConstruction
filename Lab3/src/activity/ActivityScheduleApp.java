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
 * 学习活动管理应用
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
	 * 添加可用学习资料
	 */
	public void addMaterrial() {
		String name = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(name, department, date);
		if (materials.contains(material))
			JOptionPane.showMessageDialog(null, "此材料已存在！");
		else {
			materials.add(material);
			JOptionPane.showMessageDialog(null, "添加成功！");
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
		if (materials.contains(material)) {
			materials.remove(material);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此材料不存在！");
	}

	/**
	 * 添加可用位置
	 */
	public void addLocation() {
		String name = JOptionPane.showInputDialog("地点名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc))
			JOptionPane.showMessageDialog(null, "此地点已存在！");
		else {
			locations.add(loc);
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 删除不可用的位置
	 */
	public void removeLocation() {
		String name = JOptionPane.showInputDialog("地点名称：");
		Location loc = new Location(name, true);
		if (locations.contains(loc)) {
			locations.remove(loc);
			JOptionPane.showMessageDialog(null, "删除成功！");
		} else
			JOptionPane.showMessageDialog(null, "此地点不存在！");
	}

	/**
	 * 找到指定的计划项
	 * 
	 * @return null 没有相应的计划项 非空 相应的计划项
	 */
	private PlanningEntry<Material> findActivityEntry() {
		String name = JOptionPane.showInputDialog("活动主题：");
		String location = JOptionPane.showInputDialog("活动地点：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("开始时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("结束时间，格式yyyy-MM-dd HH:mm：");

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
		String location = JOptionPane.showInputDialog("活动地点：");
		String regex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		String start = "";
		String end = "";
		while (!start.matches(regex))
			start = JOptionPane.showInputDialog("开始时间，格式yyyy-MM-dd HH:mm：");
		while (!end.matches(regex))
			end = JOptionPane.showInputDialog("结束时间，格式yyyy-MM-dd HH:mm：");

		for (PlanningEntry<Material> pe : board.getActivityEntries()) {
			ActivityEntry<Material> ae = (ActivityEntry<Material>) pe;
			if (ae.getName().equals(name) && ae.getLocation().getName().equals(location)
					&& ae.getTimeslot().equals(new Timeslot(start, end))) {
				JOptionPane.showMessageDialog(null, "已有该计划项，添加失败！");
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
			JOptionPane.showMessageDialog(null, "无位置，添加失败！");
		else {
			PlanningEntry<Material> activityEntry = PlanningEntry.activityEntry(name, start, end);
			((ActivityEntry<Material>) activityEntry).setLocation(loc);
			board.addActivityEntry(activityEntry);
			JOptionPane.showMessageDialog(null, "添加成功！");
		}
	}

	/**
	 * 取消某个计划项
	 */
	public void cancelActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，取消失败！");
		else if (ae.cancel())
			JOptionPane.showMessageDialog(null, "取消成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可取消！");
	}

	/**
	 * 为某个计划项分配资源
	 */
	public void allocateActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		String materialName = JOptionPane.showInputDialog("材料的名字：");
		String department = JOptionPane.showInputDialog("材料的发布部门：");
		String date = "";
		while (!date.matches("\\d{4}-\\d{2}-\\d{2}"))
			date = JOptionPane.showInputDialog("材料的发布日期,格式yyyy-MM-dd：");
		Material material = new Material(materialName, department, date);
		if (ae == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，资源分配失败！");
		else if (!materials.contains(material))
			JOptionPane.showMessageDialog(null, "没有该资源，资源分配失败！");
		else if (((ActivityEntry<Material>) ae).allocateMaterial(material))
			JOptionPane.showMessageDialog(null, "资源分配成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可分配资源！");
	}

	/**
	 * 启动某个计划项
	 */
	public void runActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，启动失败！");
		else if (ae.running())
			JOptionPane.showMessageDialog(null, "启动成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被启动！");
	}

	/**
	 * 变更某个已存在的计划项的位置
	 */
	public void setLocation() {
		PlanningEntry<Material> ae = findActivityEntry();
		String newLocation = JOptionPane.showInputDialog("新活动地点：");
		Location loc = null;
		for (Location l : locations)
			if (l.getName().equals(newLocation)) {
				loc = l;
				break;
			}
		if (loc == null)
			JOptionPane.showMessageDialog(null, "无新位置，设置失败！");
		else if (ae == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，设置失败！");
		else if (((ActivityEntry<Material>) ae).setLocation(loc))
			JOptionPane.showMessageDialog(null, "设置成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不能被设置位置！");
	}

	/**
	 * 结束某个计划项
	 */
	public void endActivity() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "没有该计划项，结束失败！");
		else if (ae.end())
			JOptionPane.showMessageDialog(null, "结束成功！");
		else
			JOptionPane.showMessageDialog(null, "当前状态不可被结束！");
	}

	/**
	 * 选定一个计划项，查看它的当前状态
	 */
	public void getState() {
		PlanningEntry<Material> ae = findActivityEntry();
		if (ae == null)
			JOptionPane.showMessageDialog(null, "此计划项不存在！");
		else
			JOptionPane.showMessageDialog(null, "此计划项状态为：" + ae.getState());
	}

	/**
	 * 用A方式检查是否存在位置冲突
	 */
	public void checkConflictPlanA() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanA<Material>(), board.getActivityEntries()))
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		else
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
	}

	/**
	 * 用B方式检查是否存在位置冲突
	 */
	public void checkConflictPlanB() {
		if (checkConf.checkLocationConflict(new CheckActivityEntryPlanB<Material>(), board.getActivityEntries()))
			JOptionPane.showMessageDialog(null, "存在资源独占冲突！");
		else
			JOptionPane.showMessageDialog(null, "不存在资源独占冲突！");
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
		if (materials.contains(material)) {
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
			JOptionPane.showMessageDialog(null, "无此位置！");
			return;
		}
		board.allEntryAtLocation(loc, date);
	}

	/**
	 * 主程序
	 */
	public static void main(String[] args) {
		new ActivityScheduleApp();
	}
}
