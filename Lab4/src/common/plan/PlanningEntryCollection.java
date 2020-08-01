package common.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * һ��mutable���࣬PlanningEntry�ļ�����
 * 
 * @param <R> ��Դ����
 */
public class PlanningEntryCollection<R> {

	private final List<PlanningEntry<R>> entries = new ArrayList<>();

	// Abstraction function:
	// AF(entries)=��ʵ�еļƻ����嵥
	// Representation invariant:
	// entries!=null
	// Safety from rep exposure:
	// ���е���������˽�е���final�޶�
	// ���entriesʱʹ��Collections.unmodifiableList()ת��Ϊ���ɸ��ĵ�list����

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.entries != null;
	}

	/**
	 * ����һ���ƻ���
	 * 
	 * @param entry �����ӵļƻ���
	 * @return false ���ʧ�ܣ��ƻ���Ϊ�� true ��ӳɹ�
	 */
	public boolean addEntries(PlanningEntry<R> entry) {
		if (entry == null)
			return false;
		entries.add(entry);
		checkRep();
		return true;
	}

	/**
	 * ������еļƻ����б�
	 * 
	 * @return �ƻ����б�
	 */
	public List<PlanningEntry<R>> getEntries() {
		checkRep();
		return Collections.unmodifiableList(entries);
	}

	/**
	 * �ƻ��������
	 * 
	 * @return ������
	 */
	public Iterator<PlanningEntry<R>> iterator() {
		checkRep();
		return entries.iterator();
	}

	/**
	 * �Ѽƻ����ʼʱ����絽������
	 */
	public void sort() {
		entries.sort(null);
		checkRep();
	}
}
