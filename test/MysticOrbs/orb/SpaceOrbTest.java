package MysticOrbs.orb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpaceOrbTest {
    @Test
    public void testSpaceOrbInitialEnergyIs100() {
        SpaceOrb spaceOrb = new SpaceOrb("Creator");

        assertEquals(100, spaceOrb.getEnergy());
    }

    @Test
    public void testCannotChargeSpaceOrb() {
        SpaceOrb spaceOrb = new SpaceOrb("Creator");
        assertEquals(100, spaceOrb.getEnergy());

        spaceOrb.charge("wood", 100);
        spaceOrb.charge("steel", 999);
        spaceOrb.charge("wood", -100);
        spaceOrb.charge("wateR", 0);

        assertEquals(100, spaceOrb.getEnergy());
    }

    @Test
    public void testSpaceOrbAbsorbsAnotherOrb() {
        SpaceOrb spaceOrb = new SpaceOrb("Creator");

        assertEquals(100, spaceOrb.getEnergy());

        // create and charge orb to absorb
        Orb orbToAbsorb = new Orb("OtherCreator");
        orbToAbsorb.charge("candies", 10);
        assertEquals(70, orbToAbsorb.getEnergy());

        assertTrue(spaceOrb.absorb(orbToAbsorb));

        assertEquals(0, orbToAbsorb.getEnergy());
        assertEquals(170, spaceOrb.getEnergy());
    }

    @Test
    public void testSpaceOrbCannotAbsorbsAnotherOrbIfItHasMoreEnergy() {
        SpaceOrb spaceOrb = new SpaceOrb("Creator");

        assertEquals(100, spaceOrb.getEnergy());

        // create and charge orb to absorb
        Orb orbToAbsorb = new Orb("OtherCreator");
        orbToAbsorb.charge("candies", 100);
        assertEquals(700, orbToAbsorb.getEnergy());

        assertFalse(spaceOrb.absorb(orbToAbsorb));

        assertEquals(700, orbToAbsorb.getEnergy());
        assertEquals(100, spaceOrb.getEnergy());
    }
}