package common.state;

/**
 * 一个immutable类，表示正在运行的状态
 */
public class Running implements State {

	@Override
	public String state() {
		return "RUNNING";
	}

	@Override
	public State nextState(String nextState) {
		if (nextState.equals("ENDED"))
			return new Ended();
		if (nextState.equals("BLOCKED"))
			return new Blocked();
		return new Running();
	}

	@Override
	public boolean isFinalState() {
		return false;
	}
}