package common.state;

/**
 * 一个immutable类，表示已结束的状态
 */
public class Ended implements State {

	@Override
	public String state() {
		return "ENDED";
	}

	@Override
	public State nextState(String nextState) {
		return new Ended();
	}

	@Override
	public boolean isFinalState() {
		return true;
	}
}