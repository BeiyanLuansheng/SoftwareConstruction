package common.resource;

import java.util.List;

/**
 * ��ʾһ���������Դ��Ľӿ�
 * 
 * @param <R> ��Դ������
 */
public interface MultipleSortedResourceEntry<R> {

	/**
	 * �����Դ��
	 * 
	 * @return һ����Դ����б�
	 */
	public List<R> getResources();
}
