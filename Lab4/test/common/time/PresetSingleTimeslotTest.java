package common.time;

public class PresetSingleTimeslotTest extends PresetSingleTimeslotEntryInstanceTest {

	@Override
	public PresetSingleTimeslotEntry createPSTE(String start, String end) {
		return new PresetSingleTimeslot(start, end);
	}

}
