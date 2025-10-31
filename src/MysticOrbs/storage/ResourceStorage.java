package MysticOrbs.storage;

import java.util.HashMap;

public class ResourceStorage {

    HashMap<String, Integer> resourceList = new HashMap<>();

    /**
     * checks if there are resources
     * returns false if quantity more than 0
     * otherwise true
     */
    public boolean isEmpty() {
        return resourceList.isEmpty();
    }

    /**
     * increment if resource exists
     *
     * @param resource ignore case, dont add if empty
     * @param amount
     */
    public void addResource(String resource, int amount) {

        String resourceFormatted = resource.toLowerCase().trim();

        if (resource.isEmpty() || amount < 0) {
            return;
        }



        if (resourceList.containsKey(resourceFormatted)) {
                int currentAmount = resourceList.get(resourceFormatted);
                resourceList.put(resourceFormatted, currentAmount + amount);
                return;



        }
        resourceList.put(resourceFormatted, amount);
    }

    /**
     * @param resource
     * @return returns resource quantity, returns 0 if does not exist
     */
    public int getResourceAmount(String resource) {

        if (resource.isEmpty()) {
            return 0;
        }

        String resourceFormatted = resource.toLowerCase();

        if (resourceList.containsKey(resourceFormatted)) {
            return resourceList.get(resourceFormatted);
        }
        return 0;
    }

/**
 *checks if amount resource is in the storage, return false if smaller than 1
 */
public boolean hasEnoughResource(String resource, int amount) {

    if (amount < 1) return false;

    String resourceKey = resource.toLowerCase();

    Integer currentAmount = resourceList.get(resourceKey);

    return currentAmount != null && currentAmount >= amount;
}

    /**
     *method to take resource out,
     * @param resource
     * @param amount
     * @return true if wished qunatity can be taken out, return false if it does not succeed
     */
    public boolean takeResource(String resource, int amount) {
        if (amount < 1) return false;

   if (hasEnoughResource(resource.toLowerCase(), amount)) {
       int currentAmount = resourceList.get(resource.toLowerCase());
       resourceList.put(resource.toLowerCase(), currentAmount - amount);
       if (resourceList.get(resource.toLowerCase()) == 0) {
           resourceList.remove(resource.toLowerCase());
       }
       return true;
   }
    return false;
}
}

