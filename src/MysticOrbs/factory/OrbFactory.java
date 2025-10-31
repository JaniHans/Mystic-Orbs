package MysticOrbs.factory;

import MysticOrbs.exceptions.CannotFixException;
import MysticOrbs.orb.Orb;
import MysticOrbs.oven.MagicOven;
import MysticOrbs.oven.Oven;
import MysticOrbs.oven.SpaceOven;
import MysticOrbs.storage.ResourceStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

public class OrbFactory {


    private ResourceStorage resourceStorage;

    private LinkedHashSet<Oven> ovenList;

    private List<Orb> producedOrbs;

    private int producedOrbsAmount = 0;




    /**
     * constructors
     * @param resourceStorage
     */
    public OrbFactory(ResourceStorage resourceStorage) {
        this.resourceStorage = resourceStorage;
        this.ovenList = new LinkedHashSet<>();
        this.producedOrbs = new ArrayList<>();
    }

    /**
     * adds oven to the factory listi
     * only add those that have resource storage as the factory and what
     * is not in the ovens list
     * @param oven
     */
    public void addOven(Oven oven) {
    if (!ovenList.contains(oven)) {
        if (oven.getResourceStorage().equals(resourceStorage)) {
            ovenList.add(oven);
            oven.setOrbFactory(this);
        }
    }
    }

    /**
     * returns all list of ovens in the factory
     * list have to have the same order
     * @return
     */
    public List<Oven> getOvens() {
        return ovenList.stream().toList();
    }

    /**
     * returns and empties the list where there are orbs, if no orbs
     * returns empty list
     * @return
     */
    public List<Orb> getAndClearProducedOrbsList() {
       if (!producedOrbs.isEmpty()) {

           List<Orb> result = new ArrayList<>(producedOrbs);
           producedOrbs.clear();
           return result;
       }
       return producedOrbs;
    }

    /**
     * adds orbs to the produced orbs list
     */
    public void addOrb(Orb orb) {
        if (orb != null) {
            producedOrbs.add(orb);
        }
    }

    /**
     * all ovens ONLINE
     * OVEN MUST WORK AS ORDERED
     * OVEN BROKE, try fixing
     * all produced orbs to put into list, that can be retrieved with getandclearproduce
     * listis ordered,
     * returns how many orbs were produced
     * @return
     */
    public int produceOrbs() {

        int cycleProduced = 0;

        for (Oven oven : ovenList) {
        if (oven.isBroken()) {
            if (oven instanceof MagicOven) {
                try {
                    ((MagicOven) oven).fix();
                } catch (CannotFixException e) {

                    continue;
                }
            } else if (oven instanceof SpaceOven) {
                try {
                    ((SpaceOven) oven).fix();
                } catch (CannotFixException e) {
                    continue;
                }
            } else {
                //regular oven that cant be fixed
                continue;
            }
        }



            Optional<Orb> possibleOrb =  oven.craftOrb();
            if (possibleOrb.isPresent()) {
                producedOrbsAmount++;
                addOrb(possibleOrb.get());
            }
        }


        return producedOrbsAmount;
    }


    /**
     * cycles as above
     * @param cycles
     * @return
     */
    public int produceOrbs(int cycles) {

        if (cycles == 0) {
            return 0;
        }
        int totalProduced = 0;



           while (cycles > 0) {
               produceOrbs();
               totalProduced += producedOrbsAmount;
               producedOrbsAmount = 0;
               cycles--;
           }

       return totalProduced;
    }

    // PART TWO

    /**
     * @return ovens that cant be fixed
     */
    public List<Oven> getOvensThatCannotBeFixed() {

       List<Oven> result = new ArrayList<>();

        for (Oven oven: ovenList) {
            if (!oven.getBrokenOvens().isEmpty()) {
                result.add(oven);
            }
        }
        return result;
    }

    /**
     * removes ovens from the factory list of ovens that cant be fixed
     */
    public void getRidOfOvensThatCannotBeFixed() {
        ovenList.removeIf(oven -> getOvensThatCannotBeFixed().contains(oven));
    }


    /**
     * orders ovens that priority ones are first
     *
     */
    public void optimizeOvensOrder() {
        List<Oven> ovens = new ArrayList<>(ovenList);

        Collections.sort(ovens);
        Collections.reverse(ovens);

        ovenList.clear();

        ovenList.addAll(ovens);

    }
}
