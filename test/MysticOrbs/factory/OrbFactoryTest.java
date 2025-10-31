package MysticOrbs.factory;

import MysticOrbs.orb.MagicOrb;
import MysticOrbs.orb.Orb;
import MysticOrbs.orb.SpaceOrb;
import MysticOrbs.oven.MagicOven;
import MysticOrbs.oven.Oven;
import MysticOrbs.oven.SpaceOven;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrbFactoryTest {
    @Test
    public void testAddOvensToOrbFactory() {
        ResourceStorage resourceStorage = new ResourceStorage();
        OrbFactory orbFactory = new OrbFactory(resourceStorage);

        Oven oven1 = new Oven("Oven1", resourceStorage);
        Oven oven2 = new SpaceOven("Oven2", resourceStorage);
        Oven oven3 = new Oven("Oven3", resourceStorage);

        orbFactory.addOven(oven1);
        orbFactory.addOven(oven2);
        orbFactory.addOven(oven3);

        assertEquals(Arrays.asList(oven1, oven2, oven3), orbFactory.getOvens());
    }

    @Test
    public void testProduceOrbs() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("pearl", 999999);
        resourceStorage.addResource("silver", 999999);
        resourceStorage.addResource("gold", 999999);
        resourceStorage.addResource("dust", 999999);
        resourceStorage.addResource("meteorite stone", 999999);
        resourceStorage.addResource("star fragment", 999999);

        OrbFactory orbFactory = new OrbFactory(resourceStorage);

        Oven oven1 = new Oven("Oven1", resourceStorage);
        for (int i = 0; i < 16; i++) { // break oven1
            oven1.craftOrb();
        }
        Oven oven2 = new SpaceOven("SpaceOven1", resourceStorage);
        Oven oven3 = new MagicOven("MagicOven1", resourceStorage);
        oven3.craftOrb();

        orbFactory.addOven(oven1);
        orbFactory.addOven(oven2);
        orbFactory.addOven(oven3);

        assertEquals(2, orbFactory.produceOrbs());
        List<Orb> createdOrbs = orbFactory.getAndClearProducedOrbsList();
        assertEquals(2, createdOrbs.size());

        assertTrue(createdOrbs.get(0) instanceof SpaceOrb);
        assertTrue(createdOrbs.get(1) instanceof MagicOrb);
    }
}