package common.location;

/**
 * һ����ʾ�ɱ���ĵ���λ�õĽӿ�
 */
public interface ModifiableSingleLocationEntry {

	/**
	 * ����λ��
	 * 
	 * @param location �����õ�λ�ã��ǿ�
	 * @return false ����ʧ�� true ���óɹ�
	 */
	public boolean setLocation(Location location);

	/**
	 * @return ���λ��
	 */
	public Location getLocation();
}