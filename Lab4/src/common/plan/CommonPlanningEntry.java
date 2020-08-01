package common.plan;

import common.state.State;
import common.state.Waiting;

/**
 * 一个mutable的抽象类，表示一个通用的计划项
 * 
 * @param <R> 计划项使用的资源类型
 */
public abstract class CommonPlanningEntry<R> implements PlanningEntry<R> {

	protected State state;
	private final String name;

	/**
	 * 创建一个通用计划项
	 * 
	 * @param name 计划项的名字，非空
	 */
	public CommonPlanningEntry(String name) {
		if (name == null)
			throw new IllegalArgumentException("姓名为空");
		state = new Waiting();
		this.name = name;
		checkRep();
	}

	/**
	 * 检查不变性
	 */
	private void checkRep() {
		assert this.state != null;
		assert this.name != null;
	}

	@Override
	public boolean allocate() {
		state = state.nextState("ALLOCATED");
		checkRep();
		if (state.state().equals("ALLOCATED"))
			return true;
		else
			return false;
	}

	@Override
	public boolean running() {
		state = state.nextState("RUNNING");
		checkRep();
		if (state.state().equals("RUNNING"))
			return true;
		return false;
	}

	@Override
	public boolean cancel() {
		state = state.nextState("CANCELLED");
		checkRep();
		if (state.state().equals("CANCELLED"))
			return true;
		return false;
	}

	@Override
	public boolean end() {
		state = state.nextState("ENDED");
		checkRep();
		if (state.state().equals("ENDED"))
			return true;
		return false;
	}

	@Override
	public String getName() {
		checkRep();
		return name;
	}

	@Override
	public String getState() {
		checkRep();
		return state.state();
	}
}
