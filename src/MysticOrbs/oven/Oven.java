package MysticOrbs.oven;

import MysticOrbs.factory.OrbFactory;
import MysticOrbs.orb.Orb;
import MysticOrbs.storage.ResourceStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Oven implements Comparable<Oven> {

    private String name;
    private ResourceStorage resourceStorage;

    private static final int MAX_BEFORE_BREAKING = 15;

    private boolean isBroken;

    protected List<Oven> brokenOvens = new ArrayList<>();


    private int numberOfTimesFixed = 0;

    private int producedOrbsAmount;

    private OrbFactory orbFactory;


    public List<Oven> getBrokenOvens() {
        return brokenOvens;
    }

    /**
     * instantiates oven
     *
     * @param name
     * @param resourcesStorage
     */
    public Oven(String name, ResourceStorage resourcesStorage) {
        this.name = name;
        this.resourceStorage = resourcesStorage;
        this.isBroken = false;
        this.brokenOvens = new ArrayList<>();


        this.producedOrbsAmount = 0;
        this.orbFactory = null;

    }

    /**
     * getter
     */
    public String getName() {
        return this.name;
    }

    /**
     * @returns reference to storage where that oven acquire resources
     */
    public ResourceStorage getResourceStorage() {
        return resourceStorage;
    }

    /**
     * @returns amount of orbs created by the oven
     */
    public int getCreatedOrbsAmount() {
        return producedOrbsAmount;
    }

    /**
     * @returns true if oven is broken, false otherwise
     */
    public boolean isBroken() {
        return isBroken;
    }

    public void setOrbFactory(OrbFactory factory) {
        this.orbFactory = factory;
    }

    /**
     * if oven is not broken and has enough resources
     * Kuuli loomiseks on vaja järnevaid ressursse: pearl x1, silver x1.
     * Peale loomist peavad need ressursid selles koguses hoidlast ära kaduma.
     * //Kuuli loomine tähendab seda, et luuakse kuul, määratakse talle looja nimi
     * // (ahju nimi) ja laetakse kasutatud ressurssidega.
     *
     * @return optional if not able to create orb
     */
    public Optional<Orb> craftOrb() {

        if (producedOrbsAmount == MAX_BEFORE_BREAKING) {
            isBroken = true;
            brokenOvens.add(this);
            return Optional.empty();
        }
            if (!isBroken) {
                if (resourceStorage.hasEnoughResource("pearl", 1)
                        && resourceStorage.hasEnoughResource("silver", 1)) {
                    resourceStorage.takeResource("pearl", 1);
                    resourceStorage.takeResource("silver", 1);
                    Orb orb = new Orb(this.name);


                    orb.charge("pearl", 1);

                    orb.charge("silver", 1);

                    incrementProducedOrbsAmount();


                    return Optional.of(orb);
                }
            }
        return Optional.empty();
        }

protected void incrementProducedOrbsAmount() {
        producedOrbsAmount++;
}


    /**
     * returns one of three values
     * broken oven is always smaller in priority
     * if both ovens are broken priority is as follows:
     * SpaceOven > InfinityMagicOven > MagicOven > Oven
     * bigger priority is for the oven produced least of orbs
     * @param o can be oven subclass , we can compare regular oven with the magic or space oven
     * @return 1 (o1 > o2), -1 (o1 < o2), 0 (o1 == o2)
     */
   public int compareTo(Oven o) {
       if (this.isBroken() && !o.isBroken()) {
           return -1;
       } else if (!this.isBroken() && o.isBroken()) {
           return 1;
       }

       // Rule 2: If both ovens are broken or both are not broken, compare by class type
       // SpaceOven > InfinityMagicOven > MagicOven > Oven
        String thisClassName = this.getClass().getSimpleName();
        String otherClassName = o.getClass().getSimpleName();



       if (!thisClassName.equals(otherClassName)) {
           return Integer.compare(getClassPriority(thisClassName), getClassPriority(otherClassName));

       }

       // Rule 3 : the furnace has produced fewer orbs has higher priority

       if (this.getCreatedOrbsAmount() != o.getCreatedOrbsAmount()) {
           return Integer.compare(o.getCreatedOrbsAmount(), this.getCreatedOrbsAmount());
       }

       // Rule 4: If equal on rules 1-3 compare names lexcographically
       // the oven with the lexicographiically larger name has higher priority
       int nameComparison = this.getName().compareTo(o.getName());

       if (nameComparison != 0) {
           return nameComparison;
       }


       // Rule 5 if names are also equal then ovens are equal
       return 0;
    }


    private int getClassPriority(String className) {
       switch (className) {
           case "SpaceOven":
               return 4;
           case "InfinityMagicOven":
               return 3;
           case "MagicOven":
               return 2;
           case "Oven":
           default:
               return 1;
       }
    }

    /**
     * what cant be fixed or what have been fixed for maximum
     * @return
     */

}


    // COMPARABLE - PART TWO

