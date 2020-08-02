package common.plan;

import common.state.State;
import common.state.Waiting;

/**
 * һ��mutable�ĳ����࣬��ʾһ��ͨ�õļƻ���
 * 
 * @param <R> �ƻ���ʹ�õ���Դ����
 */
public abstract class CommonPlanningEntry<R> implements PlanningEntry<R> {

	protected State state;
	private final String name;

	/**
	 * ����һ��ͨ�üƻ���
	 * 
	 * @param name �ƻ�������֣��ǿ�
	 */
	public CommonPlanningEntry(String name) {
		state = new Waiting();
		this.name = name;
	}

	@Override
	public boolean allocate() {
		state = state.nextState("ALLOCATED");
		if (state.state().equals("ALLOCATED"))
			return true;
		else
			return false;
	}

	@Override
	public boolean running() {
		state = state.nextState("RUNNING");
		if (state.state().equals("RUNNING"))
			return true;
		return false;
	}

	@Override
	public boolean cancel() {
		state = state.nextState("CANCELLED");
		if (state.state().equals("CANCELLED"))
			return true;
		return false;
	}

	@Override
	public boolean end() {
		state = state.nextState("ENDED");
		if (state.state().equals("ENDED"))
			return true;
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getState() {
		return state.state();
	}
}
