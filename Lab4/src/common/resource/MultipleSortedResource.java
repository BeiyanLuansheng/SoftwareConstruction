package common.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	// train�в������ظ��ĳ���
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// �����Դʱ��Collections.unmodifiableList()ת��Ϊ���ɱ��List���

	/**
	 * ����һ����Դ��
	 * 
	 * @param train ָ������Դ�飬�ǿգ�Ԫ�ظ�������0
	 */
	public MultipleSortedResource(List<R> train) {
		// ������ǿջ�Ԫ�ظ�������0��ǰ���������׳��쳣
		if (train == null || train.size() == 0)
			throw new IllegalArgumentException("��Ԫ��");
		Set<R> carrigeSet = new HashSet<>();
		for (R carriage : train) {
			if (carrigeSet.contains(carriage))
				throw new IllegalArgumentException("������Դ���ظ�");
			carrigeSet.add(carriage);
		}
		this.train = train;
		checkRep();
	}

	private void checkRep() {
		assert this.train != null;
		assert this.train.size() > 0;
		Set<R> carrigeSet = new HashSet<>();
		for (R carriage : this.train) {
			if (carrigeSet.contains(carriage))
				assert false;
			carrigeSet.add(carriage);
		}
	}

	@Override
	public List<R> getResources() {
		checkRep();
		return Collections.unmodifiableList(train);
	}
}
