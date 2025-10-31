package MysticOrbs.oven;

import MysticOrbs.exceptions.CannotFixException;
import MysticOrbs.storage.ResourceStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparableTest {

    private ResourceStorage infinityResourceStorage;

    @BeforeEach
    public void createAndFillResourceStorage() {
        infinityResourceStorage = new ResourceStorage();
        infinityResourceStorage.addResource("pearl", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("silver", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("gold", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("dust", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("meteorite stone", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("star fragment", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("liquid silver", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("star essence", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("clay", Integer.MAX_VALUE);
        infinityResourceStorage.addResource("freezing powder", Integer.MAX_VALUE);
    }

    @Test
    public void testCompareSpaceAndStandardOven() throws CannotFixException {
        Oven oven1 = createStandardOven("Oven1", 10);
        Oven oven2 = createSpaceOven("Oven2", 10, 0);

        // oven1 < oven2
        assertEquals(-1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareSpaceAndMagicOven() throws CannotFixException {
        Oven oven1 = createStandardOven("Oven1", 3);
        Oven oven2 = createMagicOven("Oven2", 3, 0);

        // oven1 < oven2
        assertEquals(-1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareMagicAndStandardOven() throws CannotFixException {
        Oven oven1 = createMagicOven("Oven1", 3, 0);
        Oven oven2 = createStandardOven("Oven2", 3);

        // oven1 > oven2
        assertEquals(1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareBrokenSpaceAndWorkingStandardOvens() throws CannotFixException {
        Oven oven1 = createStandardOven("Oven1", 4);
        Oven oven2 = createSpaceOven("Oven2", 26, 0); // should be broken

        // oven1 > oven2
        assertEquals(1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareMagicOvens() throws CannotFixException {
        Oven oven1 = createMagicOven("Oven1", 3, 0); // next orb is magic orb
        Oven oven2 = createMagicOven("Oven2", 2, 0);

        // oven1 > oven2
        assertEquals(1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareMagicOvensOneIsInfinity() throws CannotFixException {
        Oven oven1 = createMagicOven("Oven1", 3, 0); // next orb is magic orb
        Oven oven2 = createInfinityMagicOven("Oven2", 3); // next orb is magic orb

        // oven1 < oven2
        assertEquals(-1, oven1.compareTo(oven2));
    }

    @Test
    public void testCompareTwoOvensByCreatedOrbsAmount() {
        Oven oven1 = createStandardOven("Oven1", 4);
        Oven oven2 = createStandardOven("Oven2", 3); // less orbs

        // oven1 < oven2
        assertEquals(-1, oven1.compareTo(oven2));
    }

    private Oven createStandardOven(String name, int createOrbs) {
        Oven o = new Oven(name, infinityResourceStorage);

        for (int i = 0; i < createOrbs; i++) {
            o.craftOrb();
        }

        return o;
    }

    private Oven createInfinityMagicOven(String name, int createOrbs) {
        Oven o = new InfinityMagicOven(name, infinityResourceStorage);

        for (int i = 0; i < createOrbs; i++) {
            o.craftOrb();
        }

        return o;
    }

    private Oven createMagicOven(String name, int createOrbs, int fix) throws CannotFixException {
        Oven o = new MagicOven(name, infinityResourceStorage);

        for (int i = 0; i < createOrbs; i++) {
            o.craftOrb();
            if (o.isBroken() && fix > 0) {
                ((MagicOven) o).fix();
                fix--;
            }
        }

        return o;
    }

    private Oven createSpaceOven(String name, int createOrbs, int fix) throws CannotFixException {
        Oven o = new SpaceOven(name, infinityResourceStorage);

        for (int i = 0; i < createOrbs; i++) {
            o.craftOrb();
            if (o.isBroken() && fix > 0) {
                ((SpaceOven) o).fix();
                fix--;
            }
        }

        return o;
    }
}