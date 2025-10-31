package MysticOrbs.oven;

import MysticOrbs.orb.Orb;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OvenTest {
    @Test
    public void testStandardOvenCreatesOrb() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 10);
        resourceStorage.addResource("silver", 12);

        Oven oven = new Oven("oven", resourceStorage);

        Optional<Orb> optionalOrb = oven.craftOrb();

        if (!optionalOrb.isPresent()) {
            fail("Oven did not create the orb!");
        }

        Orb orb = optionalOrb.get();
        assertEquals(11, orb.getEnergy());
        assertEquals("Orb by oven", orb.toString());

        assertEquals(9, resourceStorage.getResourceAmount("pearl"));
        assertEquals(11, resourceStorage.getResourceAmount("silver"));

        assertEquals(1, oven.getCreatedOrbsAmount());
    }

    @Test
    public void testStandardOvenCannotCreateOrbIfNotEnoughResources() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 12);
        resourceStorage.addResource("silver", 13);

        Oven oven = new Oven("oven-2", resourceStorage);
        for (int i = 0; i < 12; i++) { // can create orb 12 times
            Optional<Orb> optionalOrb = oven.craftOrb();
            if (!optionalOrb.isPresent()) {
                fail("Oven did not create the orb!");
            }
        }

        Optional<Orb> optionalOrb = oven.craftOrb();
        assertFalse(optionalOrb.isPresent());

        assertEquals(0, resourceStorage.getResourceAmount("pearl"));
        assertEquals(1, resourceStorage.getResourceAmount("silver"));
    }

    @Test
    public void testStandardOvenBreaksIfCreated15Orbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 222);
        resourceStorage.addResource("silver", 222);

        Oven oven = new Oven("oven-2", resourceStorage);
        for (int i = 0; i < 15; i++) {
            Optional<Orb> optionalOrb = oven.craftOrb();
            if (!optionalOrb.isPresent()) {
                fail("Oven did not create the orb!");
            }
            assertEquals(i + 1, oven.getCreatedOrbsAmount());
        }

        assertFalse(oven.craftOrb().isPresent());
        assertEquals(15, oven.getCreatedOrbsAmount());
        assertTrue(oven.isBroken());
    }

}