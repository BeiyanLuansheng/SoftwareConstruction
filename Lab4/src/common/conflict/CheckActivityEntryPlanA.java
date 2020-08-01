package common.conflict;

import java.util.ArrayList;
import java.util.List;

import activity.ActivityEntry;
import common.location.Location;
import common.plan.PlanningEntry;
import common.time.Timeslot;

/**
 * 检查活动计划项是否存在位置独占冲突
 * 
 * @param <R> 资源类型
 */
public class CheckActivityEntryPlanA<R> implements CheckActivityEntry<R> {

	@Override
	public boolean checkLocationConflict(List<PlanningEntry<R>> entries) {
		if (entries == null || entries.size() < 2)
			return false;
		List<ActivityEntry<R>> activityEntries = new ArrayList<>();
		for (PlanningEntry<R> pe : entries)
			activityEntries.add((ActivityEntry<R>) pe);
		List<Location> locations = new ArrayList<>();
		List<Timeslot> times = new ArrayList<>();
		for (ActivityEntry<R> ae : activityEntries) {
			Location location = ae.getLocation();
			Timeslot time = ae.getTimeslot();
			if (locations.contains(location))
				for (Location loc : locations)
					if (loc.equals(location)) {
						int index = locations.indexOf(loc);
						Timeslot t = times.get(index);
						if (!(t.isBefore(time) || time.isBefore(t)))
							return true;
					}
			locations.add(location);
			times.add(time);
		}
		return false;
	}
}
