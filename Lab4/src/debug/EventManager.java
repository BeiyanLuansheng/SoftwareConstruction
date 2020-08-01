package debug;

import java.util.TreeMap;

/**
 * 
 * ʵ��һ��EventManager������������ճ̣�ͨ�������һ������
 * 
 * book(int day, int start, int end)
 * 
 * ��������¼�
 * 
 * ����ӵ����¼�������day������һ����������ʾһ����ĵ�day��
 * 
 * start��ʾ�¼�����ʼʱ�䣬Ϊ��day��ĵ�startСʱ
 * 
 * end��ʾ���¼��Ľ���ʱ�䣬Ϊ��day��ĵ�endСʱ��
 * 
 * ���磺
 * book(1,8,10)��ʾ���һ����1��1�գ���1�죩��8�㿪ʼ��10��������¼���
 * book(1, 0, 1)��ʾ�ڵ�1���0:00-1:00���¼� ? 
 * book(1, 22,24)��ʾ�ڵ�1���22:00-24:00���¼�
 * 
 * �¼��ĳ��ȵ�λ��Сʱ������Ҫ���Ƿ��ӡ�
 * 
 * Լ��������1<=day<=365�����迼������֮������⣩��0<=start<end<=24��

 * ��k-�ص�����ָ����k���¼���ʱ�䷶Χ��ĳ��ʱ����ڴ��ڽ���������k���¼���ĳ��Сʱ�ڶ��Ѿ���������δ������
 * book(��)�����ķ���ֵ�ǣ������ε��ý���������kֵ��
 * 
 * ���磺
 * 
 * EventManager.book(1, 10, 20); 	// returns 1
 * EventManager.book(1, 1, 7); 		// returns 1
 * EventManager.book(1, 10, 22); 	// returns 2
 * EventManager.book(1, 5,15); 		// returns 3
 * EventManager.book(1, 5, 12); 	// returns 4
 * EventManager.book(1, 7, 10); 	// returns 4
 * 
 * ������´�����е��Ժ��޸ģ�ʹ����������ȷ����׳������������󣬵���Ҫ�ı�ô���������߼���
 *
 * 
 */
public class EventManager {

	static TreeMap<Integer, Integer> temp = new TreeMap<>();

	/**
	 * 
	 * @param start start time of the event to be added, should be in [0, 24)
	 * @param end   end time of the event to be added, should be in (0, 24]
	 * @return 		the max number of concurrent events in the same hour
	 */
	public static int book(int day, int start, int end) {
		//�����õ�key����λ��ʾday������λ��ʾhour
		if(temp.containsKey(day*100+start)) //���ж�key��Map�У�����value+1
			temp.put(day*100+start, temp.get(day*100+start)+1);
		else
			temp.put(day*100+start, 1); //���������ó�ֵΪ1
		if(temp.containsKey(day*100+end)) //���ж�key��Map�У�����value-1
			temp.put(day*100+end, temp.get(day*100+end)-1);
		else
			temp.put(day*100+end, -1);//���������ó�ֵΪ-1

		int active = 0, ans = 0;
		for (int d : temp.values()) { 
			active += d;
			if (active >= ans)
				ans = active;
		}
		return ans;
	}

}