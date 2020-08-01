package common.state;

/**
 * һ��immutable�࣬��ʾ��ȡ����״̬
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