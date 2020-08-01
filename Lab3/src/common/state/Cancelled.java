package common.state;

/**
 * 一个immutable类，表示已取消的状态
 */
public class Cancelled implements State {

	@Override
	public String state() {
		return "CANCELLED";
	}

	@Override
	public State nextState(String nextState) {
		return new Cancelled();
	}

	@Override
	public boolean isFinalState() {
		return true;
	}
}