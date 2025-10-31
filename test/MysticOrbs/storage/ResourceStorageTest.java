package MysticOrbs.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceStorageTest {
    @Test
    public void testAddResourcesToResourceStorageStorage() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("steel", 1);
        resourceStorage.addResource("steel", 1);
        resourceStorage.addResource("wood", 33);

        assertEquals(2, resourceStorage.getResourceAmount("steel"));
        assertEquals(33, resourceStorage.getResourceAmount("wood"));
        assertEquals(0, resourceStorage.getResourceAmount("other"));
    }

    @Test
    public void testMethodAddResourcesIgnoresResourceNameLettersCase() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("wood", 2);
        resourceStorage.addResource("wOod", 2);

        assertEquals(4, resourceStorage.getResourceAmount("wood"));
        assertEquals(4, resourceStorage.getResourceAmount("WOOd"));
    }

    @Test
    public void testAddNegativeAmountOfResourceToResourceStorage() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("wood", -2);
        resourceStorage.addResource("wood", 2);

        assertEquals(2, resourceStorage.getResourceAmount("wood"));
    }

    @Test
    public void testResourceStorageHasEnoughResources() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("wood", 64);

        assertFalse(resourceStorage.hasEnoughResource("wood", 65));
        assertTrue(resourceStorage.hasEnoughResource("wood", 64));
        assertTrue(resourceStorage.hasEnoughResource("wood", 63));

        assertFalse(resourceStorage.hasEnoughResource("wood", 0));
        assertFalse(resourceStorage.hasEnoughResource("wood", -5));
    }

    @Test
    public void testResourceStorageTakeResource() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("wood", 33);
        resourceStorage.addResource("wood", 34);
        resourceStorage.addResource("water", 444);

        assertTrue(resourceStorage.takeResource("water", 1));
        assertEquals(443, resourceStorage.getResourceAmount("water"));

        assertTrue(resourceStorage.takeResource("wood", 20));
        assertEquals(47, resourceStorage.getResourceAmount("wood"));
    }

    @Test
    public void testResourceStorageTakeMoreResourcesThanPossible() {
        ResourceStorage resourceStorage = new ResourceStorage();

        resourceStorage.addResource("wood", 1);
        resourceStorage.addResource("wood", 3);

        assertFalse(resourceStorage.takeResource("wood", 10));
        assertEquals(4, resourceStorage.getResourceAmount("wood"));
    }

    @Test
    public void testResourceStorageIsEmpty() {
        ResourceStorage resourceStorage = new ResourceStorage();
        resourceStorage.addResource("wood", 10);
        resourceStorage.addResource("steel", 2);
        resourceStorage.addResource("water", 66);

        assertFalse(resourceStorage.isEmpty());

        resourceStorage.takeResource("wood", 10);
        resourceStorage.takeResource("steel", 1);
        resourceStorage.takeResource("steel", 1);
        resourceStorage.takeResource("water", 65);
        resourceStorage.takeResource("water", 1);

        assertTrue(resourceStorage.isEmpty());
    }

}