package MysticOrbs.orb;

public class MagicOrb extends Orb {

    private String creator;
    private int energy;

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    /**
     * constructor
     */
    public MagicOrb(String creator) {
        super(creator);
        this.energy = 0;
    }

    /**
     * orb energy increment amount which equals resource symbols times amount
     * if resource dust OR resource is empty nothing happens
     * @param resource
     * @param amount
     */

    @Override
    public void charge(String resource, int amount) {

        if (amount < 0) {
            return;
        }

        String resourceFormatted = resource.trim();

        if (resourceFormatted.isEmpty() || resourceFormatted.equalsIgnoreCase("dust")) {
            return;
        }

        if (!resourceFormatted.isEmpty()) {

            this.energy += (resourceFormatted.length() * amount) * 2;
        }
    }
    @Override
    /**
     * Orb by [creator]
     * creator is oven name what has produced the orb
     * @return
     */
    public String toString() {
        return "MagicOrb by " + getCreator();
    }
}
