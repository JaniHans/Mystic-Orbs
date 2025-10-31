package MysticOrbs.oven;

import MysticOrbs.exceptions.CannotFixException;
import MysticOrbs.factory.OrbFactory;
import MysticOrbs.orb.Orb;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FixableTest {
    @Test
    public void testCannotFixOvenIfItIsNotBroken() {
        Fixable oven = new MagicOven("ss", new ResourceStorage());
        try {
            oven.fix();
            fail("Why did not fix method throw exception? It is not broken.");
        } catch (CannotFixException ex) {
            assertEquals(oven, ex.getOven());
            assertEquals(CannotFixException.Reason.IS_NOT_BROKEN, ex.getReason());
        }
    }

    @Test
    public void testCannotFixSpaceOvenIfItIsNotBroken() {
        Fixable oven = new SpaceOven("ss", new ResourceStorage());
        try {
            oven.fix();
            fail("Why did not fix method throw exception? It is not broken.");
        } catch (CannotFixException ex) {
            assertEquals(oven, ex.getOven());
            assertEquals(CannotFixException.Reason.IS_NOT_BROKEN, ex.getReason());
        }
    }

    @Test
    public void testCannotFixMagicOvenIfNotEnoughResources() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("gold", Integer.MAX_VALUE);
        resourceStorage.addResource("dust", Integer.MAX_VALUE);
        resourceStorage.addResource("clay", 24);
        resourceStorage.addResource("freezing powder", 100);

        Fixable oven = new MagicOven("someFixableOven", resourceStorage);

        assertFalse(((MagicOven) oven).isBroken());
        for (int e = 0; e < 5; e++) ((MagicOven) oven).craftOrb();
        assertTrue(((MagicOven) oven).isBroken());

        try {
            oven.fix();
            fail("How did you fix the oven, if there is not enough resources?");
        } catch (CannotFixException e) {
            assertEquals(CannotFixException.Reason.NOT_ENOUGH_RESOURCES, e.getReason());
        }

        assertTrue(((MagicOven) oven).isBroken());
        assertEquals(0, oven.getTimesFixed());
    }
    // write similar for spaceovens by yourself

    @Test
    public void testFixSpaceOvenUseEssenceIfNoSilver() throws CannotFixException {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 10999999);
        resourceStorage.addResource("silver", 1999990);
        resourceStorage.addResource("meteorite stone", 999999);
        resourceStorage.addResource("star fragment", 999999);
        resourceStorage.addResource("liquid silver", 39);
        resourceStorage.addResource("star essence", 10);

        Fixable oven = new SpaceOven("SpaceOven", resourceStorage);

        assertFalse(((SpaceOven) oven).isBroken());
        for (int e = 0; e < 25; e++) ((SpaceOven) oven).craftOrb();
        assertTrue(((SpaceOven) oven).isBroken());

        oven.fix(); // should be ok

        assertFalse(((SpaceOven) oven).isBroken());

        assertEquals(39, resourceStorage.getResourceAmount("liquid silver"));
        assertEquals(0, resourceStorage.getResourceAmount("star essence"));
    }

    @Test
    public void testOrbFactoryFixingOvensWhenProducingOrbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", Integer.MAX_VALUE);
        resourceStorage.addResource("silver", Integer.MAX_VALUE);
        resourceStorage.addResource("gold", Integer.MAX_VALUE);
        resourceStorage.addResource("dust", Integer.MAX_VALUE);
        resourceStorage.addResource("meteorite stone", Integer.MAX_VALUE);
        resourceStorage.addResource("star fragment", Integer.MAX_VALUE);
        resourceStorage.addResource("liquid silver", Integer.MAX_VALUE);
        resourceStorage.addResource("star essence", Integer.MAX_VALUE);
        resourceStorage.addResource("clay", Integer.MAX_VALUE);
        resourceStorage.addResource("freezing powder", Integer.MAX_VALUE);

        Fixable magicOven = new MagicOven("MagicOven", resourceStorage);
        Fixable spaceOven = new SpaceOven("SpaceOven", resourceStorage);

        assertEquals(0, magicOven.getTimesFixed());
        assertEquals(0, spaceOven.getTimesFixed());

        OrbFactory orbFactory = new OrbFactory(resourceStorage);

        orbFactory.addOven((Oven) magicOven);
        orbFactory.addOven((Oven) spaceOven);

        orbFactory.produceOrbs(29);

        assertEquals(5, magicOven.getTimesFixed());
        assertEquals(1, spaceOven.getTimesFixed());
    }

    @Test
    public void testOrbFactoryProduction() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 1000);
        resourceStorage.addResource("silver", 300);
        resourceStorage.addResource("gold", 100);
        resourceStorage.addResource("dust", 500);
        resourceStorage.addResource("meteorite stone", 400);
        resourceStorage.addResource("star fragment", 400);
        resourceStorage.addResource("liquid silver", 200);
        resourceStorage.addResource("star essence", 70);
        resourceStorage.addResource("clay", 250);
        resourceStorage.addResource("freezing powder", 199);

        OrbFactory orbFactory = new OrbFactory(resourceStorage);

        List<Oven> ovenList = new ArrayList<>();
        ovenList.add(new Oven("StandardOven1", resourceStorage));
        ovenList.add(new MagicOven("MagicOven2", resourceStorage));
        ovenList.add(new SpaceOven("SpaceOven3", resourceStorage));
        ovenList.add(new InfinityMagicOven("InfMagicOven4", resourceStorage));
        ovenList.add(new MagicOven("MagicOven5", resourceStorage));
        ovenList.forEach(orbFactory::addOven);

        assertEquals(45, orbFactory.produceOrbs(10));

        List<Orb> actualOrbs = orbFactory.getAndClearProducedOrbsList();

        String[] expected = new String[]{
                "Orb by StandardOven1", "Orb by MagicOven2", "SpaceOrb by SpaceOven3", "Orb by InfMagicOven4",
                "Orb by MagicOven5", "Orb by StandardOven1", "MagicOrb by MagicOven2", "SpaceOrb by SpaceOven3",
                "MagicOrb by InfMagicOven4", "MagicOrb by MagicOven5", "Orb by StandardOven1", "Orb by MagicOven2",
                "SpaceOrb by SpaceOven3", "Orb by InfMagicOven4", "Orb by MagicOven5", "Orb by StandardOven1",
                "MagicOrb by MagicOven2", "SpaceOrb by SpaceOven3", "MagicOrb by InfMagicOven4", "MagicOrb by MagicOven5",
                "Orb by StandardOven1", "Orb by MagicOven2", "SpaceOrb by SpaceOven3", "Orb by InfMagicOven4",
                "Orb by MagicOven5", "Orb by StandardOven1", "MagicOrb by MagicOven2", "SpaceOrb by SpaceOven3",
                "MagicOrb by InfMagicOven4", "Orb by StandardOven1", "Orb by MagicOven2", "SpaceOrb by SpaceOven3",
                "Orb by InfMagicOven4", "Orb by StandardOven1", "MagicOrb by MagicOven2", "SpaceOrb by SpaceOven3",
                "MagicOrb by InfMagicOven4", "Orb by StandardOven1", "Orb by MagicOven2", "SpaceOrb by SpaceOven3",
                "Orb by InfMagicOven4", "Orb by StandardOven1", "MagicOrb by MagicOven2", "SpaceOrb by SpaceOven3",
                "MagicOrb by InfMagicOven4"
        };

        for (int i = 0; i < expected.length; i++) {
            assertEquals(actualOrbs.get(i).toString(), expected[i]);
        }
    }

    @Test
    public void testOvensGetRidOfOvensThatCannotBeFixed() throws CannotFixException {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", Integer.MAX_VALUE);
        resourceStorage.addResource("silver", Integer.MAX_VALUE);
        resourceStorage.addResource("gold", Integer.MAX_VALUE);
        resourceStorage.addResource("dust", Integer.MAX_VALUE);
        resourceStorage.addResource("meteorite stone", Integer.MAX_VALUE);
        resourceStorage.addResource("star fragment", Integer.MAX_VALUE);
        resourceStorage.addResource("liquid silver", Integer.MAX_VALUE);
        resourceStorage.addResource("star essence", Integer.MAX_VALUE);
        resourceStorage.addResource("clay", Integer.MAX_VALUE);
        resourceStorage.addResource("freezing powder", Integer.MAX_VALUE);

        OrbFactory orbFactory = new OrbFactory(resourceStorage);

        Oven oven1 = new Oven("Oven1", resourceStorage);
        Oven oven2 = new Oven("Oven2", resourceStorage);
        for (int i = 0; i < 20; i++) {
            oven1.craftOrb();
            oven2.craftOrb();
        }
        assertTrue(oven1.isBroken());
        assertTrue(oven2.isBroken());
        Oven oven3 = new SpaceOven("SpaceOven", resourceStorage);
        Oven oven4 = new MagicOven("MagicOven", resourceStorage);
        for (int i = 0; i < 11; i++) { // break 5 times
            for (int j = 0; j < 6; j++) {
                oven4.craftOrb();
            }
            assertTrue(oven4.isBroken());
            if (i < 10) { // can fix only 10 times
                ((Fixable) oven4).fix(); // should be ok
            }
        }

        orbFactory.addOven(oven1);
        orbFactory.addOven(oven2);
        orbFactory.addOven(oven3);
        orbFactory.addOven(oven4);

        orbFactory.produceOrbs();

        List<Oven> brokenOvens = orbFactory.getOvensThatCannotBeFixed();
        assertEquals(3, brokenOvens.size());
        assertTrue(brokenOvens.containsAll(Arrays.asList(oven1, oven2, oven4)));

        orbFactory.getRidOfOvensThatCannotBeFixed();

        List<Oven> allOvens = orbFactory.getOvens();
        assertEquals(1, allOvens.size());
        assertEquals(oven3, allOvens.get(0));
    }
}