package common.state;

/**
 * һ��immutable�࣬��ʾ�������״̬
 */
public class Waiting implements State {

	@Override
	public String state() {
		return "WAITING";
	}

	@Override
	public State nextState(String nextState) {
		if (nextState.equals("CANCELLED"))
			return new Cancelled();
		if (nextState.equals("ALLOCATED"))
			return new Allocated();
		return new Waiting();
	}

	@Override
	public boolean isFinalState() {
		return false;
	}
}