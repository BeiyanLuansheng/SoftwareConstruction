package common.location;

/**
 * ��ʾ�����͵ִ������ص�Ľӿ�
 */
public interface TwoLocationEntry {

	/**
	 * ��ó����ص�
	 * 
	 * @return �����ص�
	 */
	public Location getDeparture();

	/**
	 * ���Ŀ�ĵص�
	 * 
	 * @return Ŀ�ĵص�
	 */
	public Location getArrival();
}