package common.state;

/**
 * һ��immutable�࣬��ʾ�������е�״̬
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