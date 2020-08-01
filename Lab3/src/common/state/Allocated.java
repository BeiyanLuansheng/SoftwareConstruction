package common.state;

/**
 * һ��immutable�࣬��ʾ�ѷ����״̬
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