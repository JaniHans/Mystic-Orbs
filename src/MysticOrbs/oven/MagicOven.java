package MysticOrbs.oven;

import MysticOrbs.exceptions.CannotFixException;
import MysticOrbs.orb.MagicOrb;
import MysticOrbs.orb.Orb;
import MysticOrbs.storage.ResourceStorage;

import java.util.Optional;

public class MagicOven extends Oven implements Fixable {


    private ResourceStorage resourceStorage;

    private boolean isBroken;
    private static final int MAX_ORBS_PRODUCED_BEFORE_BREAKING = 5;
    private static final int MAXIMUM_REPAIR_COUNT = 10;

    private int orbsBeforeBreaking;

    private int everySecondIsOrb = 1;
    // every second orb is magic , breaks down after 5th orb is created

    private int numberOfTimesFixed = 0;

    private static final int CLAY = 25;
    private static final int FREEZING_POWDER = 100;

    /**
     * constructor
     */
    public MagicOven(String name, ResourceStorage resourceStorage) {
        super(name, resourceStorage);
        this.orbsBeforeBreaking = 0;
        this.resourceStorage = resourceStorage;
        this.isBroken = false;

    }
    // tuleb üle kirjutada meetodid isBroken ja craftOrb

    // Nii tavalise kui ka maagilise kuuli loomiseks on vaja järgnevaid ressursse: gold x1 ja dust x3

    @Override
    /**
     * @returns true if oven is broken, false otherwise
     */
    public boolean isBroken() {
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
            if (resourceStorage.hasEnoughResource("gold", 1) && resourceStorage.hasEnoughResource("dust", 3)) {
                resourceStorage.takeResource("gold", 1);
                resourceStorage.takeResource("dust", 3);

                if (everySecondIsOrb % 2 != 0) {

                    Orb orb = new Orb(getName());
                    orb.charge("gold", 1);
                    orb.charge("dust", 3);

                    everySecondIsOrb++;
                    orbsBeforeBreaking++;
                    incrementProducedOrbsAmount();

                    if (orbsBeforeBreaking == MAX_ORBS_PRODUCED_BEFORE_BREAKING) {
                        isBroken = true;
                        orbsBeforeBreaking = 0;

                    }

                    if (numberOfTimesFixed == MAXIMUM_REPAIR_COUNT) {
                        brokenOvens.add(this);
                    }

                    return Optional.of(orb);


                } else {
                    Orb orb = new MagicOrb(getName());
                    orb.charge("gold", 1);
                    orb.charge("gold", 3);

                    everySecondIsOrb++;
                    orbsBeforeBreaking++;
                    incrementProducedOrbsAmount();

                    if (orbsBeforeBreaking == MAX_ORBS_PRODUCED_BEFORE_BREAKING) {
                        isBroken = true;
                        orbsBeforeBreaking = 0;


                    }
                    if (numberOfTimesFixed == MAXIMUM_REPAIR_COUNT) {
                        brokenOvens.add(this);
                    }

                    return Optional.of(orb);



                }
            }
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
        } else if (numberOfTimesFixed >= MAXIMUM_REPAIR_COUNT) {
            brokenOvens.add(this);
            throw new CannotFixException(this, CannotFixException.Reason.FIXED_MAXIMUM_TIMES);
        } else if ((!resourceStorage.hasEnoughResource("clay", CLAY * (numberOfTimesFixed + 1))
                || !resourceStorage.hasEnoughResource("freezing powder", FREEZING_POWDER * (numberOfTimesFixed + 1)))) {

            throw new CannotFixException(this, CannotFixException.Reason.NOT_ENOUGH_RESOURCES);
        }

        if (isBroken()) {


            if (numberOfTimesFixed != 10) {

                if (resourceStorage.hasEnoughResource("clay", CLAY * (numberOfTimesFixed + 1))
                        &&
                        resourceStorage.hasEnoughResource("freezing powder", FREEZING_POWDER * (numberOfTimesFixed + 1))
                ) {

                    resourceStorage.takeResource("clay", CLAY * (numberOfTimesFixed + 1));
                    resourceStorage.takeResource("freezing powder", FREEZING_POWDER * (numberOfTimesFixed + 1));
                    isBroken = false;

                    numberOfTimesFixed++;

                }
            }
        }
    }

    @Override
    public int getTimesFixed() {
        return numberOfTimesFixed;
    }
}
