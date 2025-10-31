package MysticOrbs.oven;

import MysticOrbs.orb.MagicOrb;
import MysticOrbs.orb.Orb;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MagicOvenTest {
    @Test
    public void testMagicOvenCraftsOrbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 999999);
        resourceStorage.addResource("silver", 999999);
        resourceStorage.addResource("gold", 999999);
        resourceStorage.addResource("dust", 999999);

        Oven magicOven = new MagicOven("MagicOven", resourceStorage);

        Optional<Orb> orbOptional = magicOven.craftOrb();
        if (!orbOptional.isPresent()) {
            fail("Oven did not create orb!");
        }
        Optional<Orb> magicOrbOptional = magicOven.craftOrb();
        if (!magicOrbOptional.isPresent()) {
            fail("Oven did not create orb!");
        }

        assertFalse(orbOptional.get() instanceof MagicOrb);
        assertTrue(magicOrbOptional.get() instanceof MagicOrb);

        assertEquals(999993, resourceStorage.getResourceAmount("dust"));
        assertEquals(999997, resourceStorage.getResourceAmount("gold"));
    }

    @Test
    public void testMagicOvenBreaksIfCreated5rbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("gold", 999999);
        resourceStorage.addResource("dust", 999999);

        Oven magicOven = new MagicOven("magic oven", resourceStorage);

        for (int i = 0; i < 5; i++) {
            Optional<Orb> orbOptional = magicOven.craftOrb();
            if (!orbOptional.isPresent()) {
                fail("Oven did not create orb!");
            }
        }

        assertTrue(magicOven.isBroken());

        if (magicOven.craftOrb().isPresent()) {
            fail("Oven could not create orb, because is is broken.");
        }
    }

    @Test
    public void testInfinityMagicOvenNeverBreaks() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("gold", 999999);
        resourceStorage.addResource("dust", 999999);

        Oven infinityOven = new InfinityMagicOven("Infinity magic oven.", resourceStorage);

        for (int i = 0; i < 1000; i++) {
            infinityOven.craftOrb();
        }

        assertFalse(infinityOven.isBroken());
    }

    @Test
    public void testMagicOvenCannotCraftIfNotEnoughResources() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("gold", 2);
        resourceStorage.addResource("dust", 1);

        Oven magicOven = new MagicOven("MagicOven", resourceStorage);
        assertFalse(magicOven.craftOrb().isPresent());
        assertEquals(1, resourceStorage.getResourceAmount("dust"));
        assertEquals(2, resourceStorage.getResourceAmount("gold"));

        // same for infinity magic oven
        Oven infinityOven = new InfinityMagicOven("aaa", resourceStorage);
        assertFalse(infinityOven.craftOrb().isPresent());
        assertEquals(1, resourceStorage.getResourceAmount("dust"));
        assertEquals(2, resourceStorage.getResourceAmount("gold"));
    }

}