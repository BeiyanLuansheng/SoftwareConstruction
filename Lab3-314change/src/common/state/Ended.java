package common.state;

/**
 * һ��immutable�࣬��ʾ�ѽ�����״̬
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