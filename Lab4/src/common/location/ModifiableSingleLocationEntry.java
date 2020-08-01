package common.location;

/**
 * 一个表示可变更的单个位置的接口
 */
public interface ModifiableSingleLocationEntry {

	/**
	 * 设置位置
	 * 
	 * @param location 待设置的位置，非空
	 * @return false 设置失败 true 设置成功
	 */
	public boolean setLocation(Location location);

	/**
	 * @return 获得位置
	 */
	public Location getLocation();
}