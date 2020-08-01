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
	// Safety from rep exposure:
	// locations,timeslot��������˽�е���final�޶�
	// train��˽�е�

	/**
	 * ����һ���������μƻ���
	 * 
	 * @param name      ���ε����ƣ��ǿ�
	 * @param locations �����ξ�����λ�ã��ǿ�
	 * @param timeslot  �����ε�ʱ�̱��ǿ�
	 */
	public TrainEntry(String name, MultipleLocationEntry locations, PresetMultipleTimeslotEntry timeslot) {
		super(name);
		this.locations = locations;
		this.timeslot = timeslot;
	}

	/**
	 * Ϊ�����η�����Դ
	 * 
	 * @param train ���������Դ���ǿ�
	 * @return false ����ʧ�� true ����ɹ�
	 */
	public boolean allocateTrain(List<R> train) {
		if (super.allocate()) {
			this.train = new MultipleSortedResource<>(train);
			return true;
		}
		return false;
	}

	@Override
	public List<Location> getLocations() {
		return locations.getLocations();
	}

	@Override
	public List<R> getResources() {
		return train.getResources();
	}

	@Override
	public List<Timeslot> getTimeslot() {
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
