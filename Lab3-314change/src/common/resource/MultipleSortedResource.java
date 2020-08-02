package common.resource;

import java.util.Collections;
import java.util.List;

/**
 * һ��immutable�����ͣ���ʾ�����һ����Դ
 * 
 * @param <R> ��Դ����
 */
public class MultipleSortedResource<R> implements MultipleSortedResourceEntry<R> {

	private final List<R> train;

	// Abstraction function:
	// ��train�е�˳���ź���ģ���train.size()�������һ����Դ
	// Representation invariant:
	// train!=null
	// train.size()>0
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// �����Դʱ��Collections.unmodifiableList()ת��Ϊ���ɱ��List���

	/**
	 * ����һ����Դ��
	 * 
	 * @param train ָ������Դ�飬�ǿգ�Ԫ�ظ�������0
	 */
	public MultipleSortedResource(List<R> train) {
		this.train = train;
	}

	@Override
	public List<R> getResources() {
		return Collections.unmodifiableList(train);
	}
}
