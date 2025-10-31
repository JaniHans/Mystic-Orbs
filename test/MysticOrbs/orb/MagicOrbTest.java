package MysticOrbs.orb;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MagicOrbTest {
    @Test
    public void testMagicOrbGetsDoubleEnergyAfterCharging() {
        Orb orb = new MagicOrb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("magic wind", 2);
        assertEquals(40, orb.getEnergy());
    }

    @Test
    public void testCannotChargeOrbUsingResourcesWithEmptyName() {
        Orb orb = new MagicOrb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("   ", 2);
        assertEquals(0, orb.getEnergy());
    }

    @Test
    public void testCannotChargeOrbUsingDust() {
        Orb orb = new MagicOrb("CreatorHere");
        assertEquals(0, orb.getEnergy());

        orb.charge("DUST", 2);
        assertEquals(0, orb.getEnergy());

        orb.charge("dust", 2);
        assertEquals(0, orb.getEnergy());
    }
}