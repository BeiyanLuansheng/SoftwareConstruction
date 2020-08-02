package common.plan;

import java.util.List;

import activity.ActivityEntry;
import common.location.Location;
import common.location.ModifiableSingleLocation;
import common.location.ModifiableSingleLocationEntry;
import common.location.MultipleLocation;
import common.location.MultipleLocationEntry;
import common.location.TwoLocationEntry;
import common.location.TwoLocationWithTemp;
import common.time.PresetMultipleTimeslot;
import common.time.PresetMultipleTimeslotEntry;
import common.time.PresetSingleTimeslot;
import common.time.PresetSingleTimeslotEntry;
import flight.FlightEntry;
import train.TrainEntry;

/**
 * ��ʾһ���ƻ���
 * 
 * @param <R> �ƻ���ʹ�õ���Դ������
 */
public interface PlanningEntry<R> extends Comparable<PlanningEntry<R>> {

	/**
	 * ��������ƻ���
	 * 
	 * @param <R>       ��Դ����
	 * @param name      ��Ŀ�����ǿ�
	 * @param departure �����ص㣬�ǿ�
	 * @param temp      ��ת������null��ʾû����ת
	 * @param arrival   Ŀ�ĵص㣬�ǿ�
	 * @param start     ����ʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @param end       ����ʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @param tempStart ��ת�Ľ���ʱ�䣬��ʽΪyyyy-MM-dd HH:mm
	 * @param tempEnd   ��ת�����ʱ�䣬��ʽΪyyyy-MM-dd HH:mm
	 * @return һ���µĺ���ƻ���
	 */
	public static <R> PlanningEntry<R> flightEntry(String name, Location departure, Location temp, Location arrival,
			String start, String end, String tempStart, String tempEnd) {
		TwoLocationEntry twoLocations = new TwoLocationWithTemp(departure, temp, arrival);
		PresetSingleTimeslotEntry oneTimeslot = new PresetSingleTimeslot(start, end);
		PresetSingleTimeslotEntry tempTimeslot = new PresetSingleTimeslot(start, end);
		return new FlightEntry<>(name, twoLocations, oneTimeslot, tempTimeslot);
	}

	/**
	 * �����������μƻ���
	 * 
	 * @param <R>          ��Դ����
	 * @param name         ��Ŀ�����ǿ�
	 * @param locationList λ���б��ǿ�
	 * @param times        ʱ���б��ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @return һ���µĸ������μƻ���
	 */
	public static <R> PlanningEntry<R> trainEntry(String name, List<Location> locationList, List<String> times) {
		MultipleLocationEntry locations = new MultipleLocation(locationList);
		PresetMultipleTimeslotEntry timeslot = new PresetMultipleTimeslot(times);
		return new TrainEntry<>(name, locations, timeslot);
	}

	/**
	 * ����ѧϰ��ƻ���
	 * 
	 * @param <R>   ��Դ����
	 * @param name  ��Ŀ�����ǿ�
	 * @param start ��ʼʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @param end   ����ʱ�䣬�ǿգ���ʽΪyyyy-MM-dd HH:mm
	 * @return һ���µ�ѧϰ��ƻ���
	 */
	public static <R> PlanningEntry<R> activityEntry(String name, String start, String end) {
		ModifiableSingleLocationEntry modifiableLocation = new ModifiableSingleLocation();
		PresetSingleTimeslotEntry timeslot = new PresetSingleTimeslot(start, end);
		return new ActivityEntry<>(name, modifiableLocation, timeslot);
	}

	/**
	 * ��״̬����Ϊ�ѷ���״̬
	 * 
	 * @return false ����ʧ�� true ���óɹ�
	 */
	public boolean allocate();

	/**
	 * ��״̬����Ϊ����״̬
	 * 
	 * @return false ����ʧ�� true ���óɹ�
	 */
	public boolean running();

	/**
	 * ��״̬����Ϊ��ȡ��״̬
	 * 
	 * @return false ����ʧ�� true ���óɹ�
	 */
	public boolean cancel();

	/**
	 * ��״̬����Ϊ���״̬
	 * 
	 * @return false ����ʧ�� true ���óɹ�
	 */
	public boolean end();

	/**
	 * ��ȡ�ƻ��������
	 * 
	 * @return ��ʾ���ֵ��ַ���
	 */
	public String getName();

	/**
	 * ��üƻ���״̬
	 * 
	 * @return ��ʾ״̬���ַ���
	 */
	public String getState();
}
