package common.resource;

import java.util.List;

/**
 * 表示一个有序的资源组的接口
 * 
 * @param <R> 资源类型你
 */
public interface MultipleSortedResourceEntry<R> {

	/**
	 * 获得资源组
	 * 
	 * @return 一个资源组的列表
	 */
	public List<R> getResources();
}
