package MysticOrbs.oven;

import MysticOrbs.orb.Orb;
import MysticOrbs.orb.SpaceOrb;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceOvenTest {
    @Test
    public void testSpaceOvenCreatesSpaceOrbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("meteorite stone", 999999);
        resourceStorage.addResource("star fragment", 999999);

        Oven spaceOven = new SpaceOven("space-oven-1", resourceStorage);

        for (int i = 0; i < 10; i++) {
            Optional<Orb> optionalOrb = spaceOven.craftOrb();
            if (!optionalOrb.isPresent()) {
                fail("Space oven did not create any orb");
            }
            Orb orb = optionalOrb.get();
            assertTrue(orb instanceof SpaceOrb);
        }

        assertEquals(999989, resourceStorage.getResourceAmount("meteorite stone"));
        assertEquals(999849, resourceStorage.getResourceAmount("star fragment"));
    }

    @Test
    public void testSpaceOvenCreatesStandardOrbsIfItIsBroken() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 10);
        resourceStorage.addResource("silver", 10);
        resourceStorage.addResource("meteorite stone", 999999);
        resourceStorage.addResource("star fragment", 999999);

        Oven spaceOven = new SpaceOven("space-oven-2", resourceStorage);

        // break oven
        for (int i = 0; i < 25; i++) {
            spaceOven.craftOrb();
        }
        assertTrue(spaceOven.isBroken());

        Optional<Orb> optionalOrb = spaceOven.craftOrb();
        if (!optionalOrb.isPresent()) {
            fail("Space oven did not create any orb");
        }
        Orb orb = optionalOrb.get();
        assertFalse(orb instanceof SpaceOrb);
        assertEquals("Orb by space-oven-2", orb.toString());

        resourceStorage.addResource("meteorite stone", 999974);
        resourceStorage.addResource("star fragment", 999624);
        assertEquals(9, resourceStorage.getResourceAmount("pearl"));
        assertEquals(9, resourceStorage.getResourceAmount("silver"));
    }

    @Test
    public void testSpaceOvenCreatesStandardOrbIfNotEnoughResourcesForSpaceOrb() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 10);
        resourceStorage.addResource("silver", 10);
        resourceStorage.addResource("meteorite stone", 2);
        resourceStorage.addResource("star fragment", 30);

        Oven spaceOven = new SpaceOven("space-oven-3", resourceStorage);

        for (int i = 0; i < 2; i++) {
            Optional<Orb> optionalOrb = spaceOven.craftOrb();
            if (!optionalOrb.isPresent()) {
                fail("Space oven did not create any orb");
            }
            Orb orb = optionalOrb.get();
            assertTrue(orb instanceof SpaceOrb);
        }

        Optional<Orb> optionalOrb = spaceOven.craftOrb();
        if (!optionalOrb.isPresent()) {
            fail("Space oven did not create any orb");
        }
        Orb orb = optionalOrb.get();
        assertFalse(orb instanceof SpaceOrb);
    }
}