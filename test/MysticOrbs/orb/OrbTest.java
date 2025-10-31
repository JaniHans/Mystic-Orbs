package MysticOrbs.orb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrbTest {
    @Test
    public void testChargeOrbUsingResources() {
        Orb orb = new Orb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("magic wind", 2);
        assertEquals(20, orb.getEnergy());
    }

    @Test
    public void testCannotChargeOrbUsingResourcesWithEmptyName() {
        Orb orb = new Orb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("   ", 2);
        assertEquals(0, orb.getEnergy());
    }

    @Test
    public void testCannotChargeOrbUsingDust() {
        Orb orb = new Orb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("DUST", 2);
        assertEquals(0, orb.getEnergy());

        orb.charge("dust", 2);
        assertEquals(0, orb.getEnergy());
    }
}