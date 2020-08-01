package debug;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Note that this class may use the other two class: Flight and Plane.
 * 
 * Debug and fix errors. DON'T change the initial logic of the code.
 *
 */
public class FlightClient {
	
	/**
	 * Given a list of flights and a list of planes, suppose each flight has not yet been
	 * allocated a plane to, this method tries to allocate a plane to each flight and ensures that
	 * there're no any time conflicts between all the allocations. 
	 * For example:
	 *  Flight 1 (2020-01-01 8:00-10:00) and Flight 2 (2020-01-01 9:50-10:40) are all allocated 
	 *  the same plane B0001, then there's conflict because from 9:50 to 10:00 the plane B0001
	 *  cannot serve for the two flights simultaneously.
	 *  
	 * @param planes a list of planes
	 * @param flights a list of flights each of which has no plane allocated
	 * @return false if there's no allocation solution that could avoid any conflicts
	 */
	
	public boolean planeAllocation(List<Plane> planes, List<Flight> flights) {
		boolean bFeasible = true;
		//Random r = new Random(); //删除了无用的随机变量
		
		Collections.sort(flights);
		for (Flight f : flights) {
			boolean bAllocated = false;
			int i=0; //新增飞机标识量
			while (!bAllocated) {
				//Plane p = planes.get(r.nextInt(planes.size()));
				Plane p = planes.get(i);//将随机取飞机改为顺序取飞机
				Calendar fStart = f.getDepartTime();
				Calendar fEnd = f.getArrivalTime();
				boolean bConflict = false;
				
				for (Flight t : flights) {
					Plane q = t.getPlane();
					if (q == null || ! q.equals(p)) //先判断空资源
						continue;
					
					Calendar tStart = t.getDepartTime();
					Calendar tEnd = t.getArrivalTime();
					//运算符改为方法，并且增加了开始时间点相等的判断
					if (((fStart.equals(tStart) || fStart.after(tStart)) && fStart.before(tEnd)) 
							|| ((tStart.equals(fStart) || tStart.after(fStart)) && tStart.before(fEnd))) {
						bConflict = true;
						break;
					}
				}
				
				if (!bConflict) {
					f.setPlane(p);
					break;
				}
				if(i == planes.size()-1 && !bAllocated)
					return false;//如果运行到这里说明资源已被分配完，没有不冲突的资源了
				i++; //判断下一台飞机是否满足
			}
		}
		return bFeasible;
	}
}
