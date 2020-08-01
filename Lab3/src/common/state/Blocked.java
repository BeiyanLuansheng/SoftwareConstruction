package common.state;

/**
 * 一个immutable类，表示阻塞或挂起的状态
 */
public class Blocked implements State {

	@Override
	public String state() {
		return "BLOCKED";
	}

	@Override
	public State nextState(String nextState) {
		if (nextState.equals("RUNNING"))
			return new Running();
		if (nextState.equals("CANCELLED"))
			return new Cancelled();
		return new Blocked();
	}

	@Override
	public boolean isFinalState() {
		return false;
	}
}