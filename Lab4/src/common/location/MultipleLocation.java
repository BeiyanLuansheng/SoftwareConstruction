package common.location;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * һ��immutable���ͣ� ��ʾ������ɸ��ĵ�λ��
 */
public class MultipleLocation implements MultipleLocationEntry {

	private final List<Location> locations;

	// Abstraction function:
	// AF(locations)=��locations�еĶ���Ϊλ���Ұ�˳�����е�һ��λ��
	// Representation invariant:
	// locations!=null
	// locations�в������ظ��ĳ�վ
	// Safety from rep exposure:
	// ���е���������˽�е���ʹ��final�޶�
	// ����λ���б�ʱʹ��Collections.unmodifiableList()����һ�������޸ĵ��б�

	/**
	 * ����һ��;����λ��
	 * 
	 * @param locations ;��λ�õ��б��ǿգ��������ظ��ĳ�վ
	 */
	public MultipleLocation(List<Location> locations) {
		// ������ǰ���������׳��쳣
		if (locations == null || locations.size() < 2)
			throw new IllegalArgumentException("�б��е�λ����������");
		Set<Location> locSet = new HashSet<>();
		for (Location loc : locations) {
			if (locSet.contains(loc))
				throw new IllegalArgumentException("λ���б����ظ�λ��");
			locSet.add(loc);
		}
		this.locations = locations;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.locations != null;
		assert this.locations.size() >= 2;
		Set<Location> locSet = new HashSet<>();
		for (Location loc : this.locations) {
			if (locSet.contains(loc))
				assert false;
			locSet.add(loc);
		}
	}

	@Override
	public List<Location> getLocations() {
		checkRep();
		return Collections.unmodifiableList(locations);
	}

}
