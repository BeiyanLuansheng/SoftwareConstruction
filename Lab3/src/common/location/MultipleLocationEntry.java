package common.location;

import java.util.List;

/**
 * 一个表示多个不可更改的位置的接口
 */
public interface MultipleLocationEntry {

	/**
	 * 获得途径的所有地点
	 * 
	 * @return 途经地点的list
	 */
	public List<Location> getLocations();
}