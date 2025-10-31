package MysticOrbs.oven;

import MysticOrbs.exceptions.CannotFixException;
import MysticOrbs.orb.Orb;
import MysticOrbs.orb.SpaceOrb;
import MysticOrbs.storage.ResourceStorage;

import java.util.Optional;

public class SpaceOven extends Oven implements Fixable {



    private boolean isBroken;

    private ResourceStorage resourceStorage;

    //Unikaalne ahi, mis toodab kosmosekuule.
    // Kui on katki või ei ole piisavalt ressursse kosmosekuuli tootmiseks, siis toodab tavalisi kuule.
    // Kui aga tavalise kuuli jaoks pole piisavalt ressursse, siis ei saa ahi midagi toota.
    // Läheb katki pärast kahekümne viiendat loodud kuuli.

    private static final int MAX_ORBS_PRODUCED_BEFORE_BREAKING = 25;
    private static final int INVINCIBLE = 5;
    private static final int STAR = 15;

    private int producedOrbsAmount;

    private int orbsBeforeBreaking;

    private int numberOfTimesFixed = 0;

    private static final int LIQUID_SILVER = 40;
    private static final int STAR_ESSENCE = 10;


    /**
     * constructs
     * @param name
     */

    public SpaceOven(String name, ResourceStorage resourceStorage) {
        super(name, resourceStorage);
        this.resourceStorage = resourceStorage;
        this.producedOrbsAmount = 0;
        this.orbsBeforeBreaking = 0;
        this.isBroken = false;

    }

    @Override
    /**
     * @returns true if oven is broken, false otherwise
     */
    public boolean isBroken() {

        if (numberOfTimesFixed >= INVINCIBLE) {
            return false;
        }

        return isBroken;
    }
    @Override
    /**
     * if oven is not broken and has enough resources
     * Kuuli loomiseks on vaja järnevaid ressursse: pearl x1, silver x1.
     * Peale loomist peavad need ressursid selles koguses hoidlast ära kaduma.
     * //Kuuli loomine tähendab seda, et luuakse kuul, määratakse talle looja nimi
     * // (ahju nimi) ja laetakse kasutatud ressurssidega.
     * @return optional if not able to create orb
     */
    public Optional<Orb> craftOrb() {



        if (!isBroken()) {
            if (resourceStorage.hasEnoughResource("meteorite stone", 1)
                    && resourceStorage.hasEnoughResource("star fragment", STAR)
            ) {
                resourceStorage.takeResource("meteorite stone", 1);
                resourceStorage.takeResource("star fragment", STAR);

            }
            Orb orb = new SpaceOrb(getName());
            producedOrbsAmount++;
            orbsBeforeBreaking++;
            incrementProducedOrbsAmount();

            if (producedOrbsAmount == MAX_ORBS_PRODUCED_BEFORE_BREAKING) {
                isBroken = true;
                orbsBeforeBreaking = 0;

            }

            return Optional.of(orb);


        }
        if (resourceStorage.hasEnoughResource("pearl", 1)
                && resourceStorage.hasEnoughResource("silver", 1)
        ) {
            resourceStorage.takeResource("pearl", 1);
            resourceStorage.takeResource("silver", 1);
            Orb orb = new Orb(getName());
            producedOrbsAmount++;
            orbsBeforeBreaking++;
            incrementProducedOrbsAmount();
            if (orbsBeforeBreaking == MAX_ORBS_PRODUCED_BEFORE_BREAKING) {
                isBroken = true;
                orbsBeforeBreaking = 0;

            }
            return Optional.of(orb);
        }



        return Optional.empty();
    }
    @Override
    /**
     * check if isbroken first
     * check how many times have been fixed
     * to fix need clay x25 and freezing powder x100
     * resources increase with each fix
     */
    public void fix() throws CannotFixException {
        if (!isBroken()) {
            throw new CannotFixException(this, CannotFixException.Reason.IS_NOT_BROKEN);
        } else if (numberOfTimesFixed >= INVINCIBLE) {
            throw new CannotFixException(this, CannotFixException.Reason.FIXED_MAXIMUM_TIMES);
        } else if ((!resourceStorage.hasEnoughResource("liquid silver", LIQUID_SILVER)
                && !resourceStorage.hasEnoughResource("star essence", STAR_ESSENCE))) {
            throw new CannotFixException(this, CannotFixException.Reason.NOT_ENOUGH_RESOURCES);
        }

        if (numberOfTimesFixed >= INVINCIBLE) {
            isBroken = false;
            return;
        }

        if (isBroken()) {

            if (resourceStorage.hasEnoughResource("liquid silver", LIQUID_SILVER)) {
                resourceStorage.takeResource("liquid silver", LIQUID_SILVER);
                isBroken = false;

                numberOfTimesFixed++;

            } else if (resourceStorage.hasEnoughResource("star essence", STAR_ESSENCE)) {
                resourceStorage.takeResource("star essence", STAR_ESSENCE);
                isBroken = false;

                numberOfTimesFixed++;
            }

        }
    }

    @Override
    public int getTimesFixed() {
        return numberOfTimesFixed;
    }
}
