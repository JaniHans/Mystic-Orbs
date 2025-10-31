package MysticOrbs.orb;

public class Orb {


    private String creator;
    private int energy;



    /**
     * constructor that assigns oven name to the orb that it was produced
     * @param creator
     */
    public Orb(String creator) {
        this.creator = creator;
        this.energy = 0;
    }

    /**
     * orb energy increment amount which equals resource symbols times amount
     * if resource dust OR resource is empty nothing happens
     * @param resource
     * @param amount
     */
    public void charge(String resource, int amount) {

        if (amount < 0) {
            return;
        }

        String resourceFormatted = resource.trim();

        if (resourceFormatted.isEmpty() || resourceFormatted.equalsIgnoreCase("dust")) {
            return;
        }

        if (!resourceFormatted.isEmpty()) {
            this.energy += resourceFormatted.length() * amount;
        }
    }
    /**
     * energy getter
     */
    public int getEnergy() {
        return this.energy;
    }
    /**
     * energy getter
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    /**
     * Orb by [creator]
     * creator is oven name what has produced the orb
     * @return
     */
    public String toString() {
        return "Orb by " + creator;
    }
    public String getCreator() {
        return creator;
    }

}
