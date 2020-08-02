package common.time;

import java.util.List;

public class PresetMultipleTimeslotTest extends PresetMultipleTimeslotEntryInstanceTest {

	@Override
	public PresetMultipleTimeslotEntry createPMTE(List<String> times) {
		return new PresetMultipleTimeslot(times) ;
	}

}
