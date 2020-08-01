package common.location;

/**
 * 表示出发和抵达两个地点的接口
 */
public interface TwoLocationEntry {

	/**
	 * 获得出发地点
	 * 
	 * @return 出发地点
	 */
	public Location getDeparture();

	/**
	 * 获得目的地点
	 * 
	 * @return 目的地点
	 */
	public Location getArrival();
}