package common.state;

/**
 * 一个immutable类，表示已分配的状态
 */
public class Allocated implements State {

	@Override
	public String state() {
		return "ALLOCATED";
	}

	@Override
	public State nextState(String nextState) {
		if (nextState.equals("RUNNING"))
			return new Running();
		if (nextState.equals("CANCELLED"))
			return new Cancelled();
		return new Allocated();
	}

	@Override
	public boolean isFinalState() {
		return false;
	}
}