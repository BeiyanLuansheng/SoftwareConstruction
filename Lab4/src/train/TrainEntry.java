package train;

import java.util.List;

import common.block.BlockableEntry;
import common.location.Location;
import common.location.MultipleLocationEntry;
import common.plan.CommonPlanningEntry;
import common.plan.PlanningEntry;
import common.resource.MultipleSortedResource;
import common.resource.MultipleSortedResourceEntry;
import common.time.PresetMultipleTimeslotEntry;
import common.time.Timeslot;

/**
 * ����һ����ʾ�������μƻ���
 * 
 * @param <R> ��Դ����
 */
public class TrainEntry<R> extends CommonPlanningEntry<R>
		implements MultipleLocationEntry, PresetMultipleTimeslotEntry, MultipleSortedResourceEntry<R>, BlockableEntry {

	private final MultipleLocationEntry locations;
	private final PresetMultipleTimeslotEntry timeslot;
	private MultipleSortedResourceEntry<R> train;

	// Abstraction function:
	// AF(locations, timeslot, train)=һ����timeslotΪʱ�̱�
	// ����locations�еĳ�վ��ʹ��train�еĳ�����Դ�ĸ�������
	// Representation invariant:
	// locations!=null
	// timeslot!=null
	// ʱ�̱�������ȳ�վ������1
	// locations.getLocations().size() == timeslot.getTimeslot().size()+1
	// Safety from rep exposure:
	// locations,timeslot��������˽�е���final�޶�
	// train��˽�е�

	/**
	 * ����һ���������μƻ���
	 * 
	 * @param name      ���ε����ƣ��ǿ�
	 * @param locations �����ξ�����λ�ã��ǿ�
	 * @param timeslot  �����ε�ʱ�̱��ǿգ�ʱ�̱�������ȳ�վ������1
	 */
	public TrainEntry(String name, MultipleLocationEntry locations, PresetMultipleTimeslotEntry timeslot) {
		super(name);
		if (locations == null || timeslot == null
				|| locations.getLocations().size() != timeslot.getTimeslot().size() + 1)
			throw new IllegalArgumentException("�������Ϸ�");
		this.locations = locations;
		this.timeslot = timeslot;
		checkRep();
	}

	/**
	 * ��鲻����
	 */
	private void checkRep() {
		assert this.locations != null;
		assert this.timeslot != null;
		assert this.locations.getLocations().size() == this.timeslot.getTimeslot().size() + 1;
	}

	/**
	 * Ϊ�����η�����Դ
	 * 
	 * @param train ���������Դ���ǿ�
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocateTrain(List<R> train) {
		// ������ǰ���������׳��쳣
		if (train == null || train.size() == 0)
			throw new IllegalArgumentException("����Դ");
		if (super.allocate()) {
			this.train = new MultipleSortedResource<>(train);
			assert getState().equals("ALLOCATED"); // ȷ�����ѷ���״̬
			assert this.train != null;
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}

	@Override
	public List<Location> getLocations() {
		checkRep();
		return locations.getLocations();
	}

	@Override
	public List<R> getResources() {
		checkRep();
		return train.getResources();
	}

	@Override
	public List<Timeslot> getTimeslot() {
		checkRep();
		return this.timeslot.getTimeslot();
	}

	@Override
	public boolean block() {
		state = state.nextState("BLOCKED");
		if (state.state().equals("BLOCKED"))
			return true;
		return false;
	}

	@Override
	public int compareTo(PlanningEntry<R> entry) {
		TrainEntry<R> te = (TrainEntry<R>) entry;
		if (this.getTimeslot().get(0).getStartTime().isBefore(te.getTimeslot().get(0).getStartTime()))
			return -1; // this��entry֮ǰ��ʼ
		else if (this.getTimeslot().get(0).getStartTime().isAfter(te.getTimeslot().get(0).getStartTime()))
			return 1; // entry��this֮ǰ��ʼ
		else
			return 0;
	}
}
