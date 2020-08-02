package activity;

import static org.junit.Assert.*;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryInstanceTest;
import common.time.Timeslot;

public class ActivityEntryTest extends PlanningEntryInstanceTest<Material> {

    // Test strategy
    // ����allocateMaterial()
    // ״̬�ɷ�����Դ��״̬���ɷ�����Դ
    // ����getMaterial()
    // ���Եõ�����Դ�Ƿ�����������ͬ
    // ����getTimeslot()
    // ���Եõ���ʱ����Ƿ�����������ͬ
    // ����setLocation()
    // λ��Ϊ�գ�λ�ò�Ϊ��
	// ״̬�ɸ���λ�ã�״̬���ɸ���λ��
    // ����getLocation()
    // ���Եõ���λ���Ƿ�����������ͬ
    // ����compareTo()
    // ��pe֮ǰ��ʼ����pe֮��ʼ����peͬʱ��ʼ
    
    @Override
    public PlanningEntry<Material> entryInstance() {
        String start = "2020-04-26 10:00";
        String end = "2020-04-26 12:00";
        return PlanningEntry.activityEntry(name, start, end);
    }

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testAllocateMaterial() {
        PlanningEntry<Material> pe = entryInstance();
        Material material = new Material("material", "Learning department", "2020-04-26");
        //״̬�ɷ�����Դ
        assertTrue(((ActivityEntry<Material>) pe).allocateMaterial(material));
        //״̬���ɷ�����Դ
        pe.cancel();
        assertFalse(((ActivityEntry<Material>) pe).allocateMaterial(material));
    }

    @Test
    public void testGetMaterial() {
        PlanningEntry<Material> pe = entryInstance();
        Material material = new Material("material", "Learning department", "2020-04-26");
        ((ActivityEntry<Material>) pe).allocateMaterial(material);
        assertEquals(material, ((ActivityEntry<Material>) pe).getMaterial());
    }

    @Test
    public void testGetTimeslot() {
        PlanningEntry<Material> pe = entryInstance();
        Timeslot t = new Timeslot("2020-04-26 10:00", "2020-04-26 12:00");
        assertEquals(t, ((ActivityEntry<Material>) pe).getTimeslot());
    }

    @Test
    public void testSetLocation() {
        Location loc = new Location("place", false);
        PlanningEntry<Material> pe = entryInstance();
        //λ��Ϊ��
        assertFalse(((ActivityEntry<Material>) pe).setLocation(null));
        //λ�ò�Ϊ�գ�״̬�ɸ���λ��
        assertTrue(((ActivityEntry<Material>) pe).setLocation(loc));
        //״̬���ɸ���λ��
        pe.cancel();
        assertFalse(((ActivityEntry<Material>) pe).setLocation(loc));
    }

    @Test
    public void testGetLocation() {
        Location loc = new Location("place", false);
        PlanningEntry<Material> pe = entryInstance();
        ((ActivityEntry<Material>) pe).setLocation(loc);
        assertEquals(loc, ((ActivityEntry<Material>) pe).getLocation());
    }

    @Test
    public void testCompareTo() {
        PlanningEntry<Material> pe = entryInstance();
        //��pe֮ǰ��ʼ
        PlanningEntry<Material> beforePe = PlanningEntry.activityEntry(name, "2020-04-26 09:00", "2020-04-26 10:00");
        assertEquals(1, pe.compareTo(beforePe));
        //��pe֮��ʼ
        PlanningEntry<Material> afterPe = PlanningEntry.activityEntry(name, "2020-04-26 11:00", "2020-04-26 12:00");
        assertEquals(-1, pe.compareTo(afterPe));
        //��peͬʱ��ʼ
        PlanningEntry<Material> equalPe = PlanningEntry.activityEntry(name, "2020-04-26 10:00", "2020-04-26 11:00");
        assertEquals(0, pe.compareTo(equalPe));
    }
}
