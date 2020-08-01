package activity;

import static org.junit.Assert.*;

import org.junit.Test;

import common.location.Location;
import common.plan.PlanningEntry;
import common.plan.PlanningEntryInstanceTest;
import common.time.Timeslot;

public class ActivityEntryTest extends PlanningEntryInstanceTest<Material> {

    // Test strategy
    // 测试allocateMaterial()
    // 状态可分配资源，状态不可分配资源
    // 测试getMaterial()
    // 测试得到的资源是否与期望的相同
    // 测试getTimeslot()
    // 测试得到的时间表是否与期望的相同
    // 测试setLocation()
    // 位置为空，位置不为空
	// 状态可更改位置，状态不可更改位置
    // 测试getLocation()
    // 测试得到的位置是否与期望的相同
    // 测试compareTo()
    // 在pe之前开始，在pe之后开始，在pe同时开始
    
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
        //状态可分配资源
        assertTrue(((ActivityEntry<Material>) pe).allocateMaterial(material));
        //状态不可分配资源
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
        //位置为空
        assertFalse(((ActivityEntry<Material>) pe).setLocation(null));
        //位置不为空，状态可更改位置
        assertTrue(((ActivityEntry<Material>) pe).setLocation(loc));
        //状态不可更改位置
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
        //在pe之前开始
        PlanningEntry<Material> beforePe = PlanningEntry.activityEntry(name, "2020-04-26 09:00", "2020-04-26 10:00");
        assertEquals(1, pe.compareTo(beforePe));
        //在pe之后开始
        PlanningEntry<Material> afterPe = PlanningEntry.activityEntry(name, "2020-04-26 11:00", "2020-04-26 12:00");
        assertEquals(-1, pe.compareTo(afterPe));
        //与pe同时开始
        PlanningEntry<Material> equalPe = PlanningEntry.activityEntry(name, "2020-04-26 10:00", "2020-04-26 11:00");
        assertEquals(0, pe.compareTo(equalPe));
    }
}
