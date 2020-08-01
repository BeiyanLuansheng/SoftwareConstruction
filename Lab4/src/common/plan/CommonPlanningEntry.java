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
		if (name == null)
			throw new IllegalArgumentException("����Ϊ��");
		state = new Waiting();
		this.name = name;
		checkRep();
	}

	/**
	 * ��鲻����
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
