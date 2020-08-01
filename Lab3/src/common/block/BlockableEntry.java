package common.block;

/**
 * 具有阻塞功能的接口
 */
public interface BlockableEntry {

	/**
	 * 把计划项的状态设置为阻塞
	 * 
	 * @return false 设置失败 true 设置成功
	 */
	public boolean block();
}