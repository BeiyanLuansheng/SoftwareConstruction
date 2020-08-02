package common.state;

/**
 * ��ʾ״̬�Ľӿ�
 */
public interface State {

	/**
	 * ��ȡ״̬
	 * 
	 * @return һ����ʾ״̬���ַ���
	 */
	public String state();

	/**
	 * ״̬ת�ƺ�����ת�Ƶ���һ��״̬
	 * 
	 * @param nextState ����Ϊ��������֮һ �ѷ��䣺ALLOCATED �������У� RUNNING ����/���� BLOCKED ������
	 *                  ENDED ȡ���� CANCELLED
	 * @return �µ�״̬���������Ĳ�������״̬ת��ͼ���򷵻�һ���µ�״̬ ���򷵻�ԭ����״̬
	 */
	public State nextState(String nextState);

	/**
	 * �жϵ�ǰ״̬�Ƿ�Ϊ����״̬
	 * 
	 * @return false ������̬ true ����̬
	 */
	public boolean isFinalState();
}