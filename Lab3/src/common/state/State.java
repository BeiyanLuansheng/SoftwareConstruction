package common.state;

/**
 * 表示状态的接口
 */
public interface State {

	/**
	 * 获取状态
	 * 
	 * @return 一个表示状态的字符串
	 */
	public String state();

	/**
	 * 状态转移函数，转移到下一个状态
	 * 
	 * @param nextState 输入为以下五种之一 已分配：ALLOCATED 正在运行： RUNNING 阻塞/挂起： BLOCKED 结束：
	 *                  ENDED 取消： CANCELLED
	 * @return 新的状态，如果输入的参数符合状态转移图，则返回一个新的状态 否则返回原来的状态
	 */
	public State nextState(String nextState);

	/**
	 * 判断当前状态是否为最终状态
	 * 
	 * @return false 不是终态 true 是终态
	 */
	public boolean isFinalState();
}