package common.location;

import java.util.Collections;
import java.util.List;

/**
 * һ��immutable���ͣ� ��ʾ������ɸ��ĵ�λ��
 */
public class MultipleLocation implements MultipleLocationEntry {

	private final List<Location> locations;

	// Abstraction function:
	// AF(locations)=��locations�еĶ���Ϊλ���Ұ�˳�����е�һ��λ��
	// Representation invariant:
	// locations!=null
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// ����λ���б�ʱʹ��Collections.unmodifiableList()����һ�������޸ĵ��б�

	/**
	 * ����һ��;����λ��
	 * 
	 * @param locations ;��λ�õ��б��ǿ�
	 */
	public MultipleLocation(List<Location> locations) {
		this.locations = locations;
	}

	@Override
	public List<Location> getLocations() {
		return Collections.unmodifiableList(locations);
	}

}
