package MysticOrbs.orb;

public class SpaceOrb extends Orb {

    private String creator;
    private int energy;

    /**
     * constructor
     */
    public SpaceOrb(String creator) {
        super(creator);
        this.energy = 100;
    }
    // kuul mida ei saa laadida ,aga tal on oskus neelata n6rgemaid kuule

    // sama konstruktor mis klassils orb, m22rab energiale v22rtuse 100



    @Override
/**
 * does nothing
 */
    public void charge(String resource, int amount) {
        return;
    }
    /**
     * Orb by [creator]
     * creator is oven name what has produced the orb
     * @return
     */
    public String toString() {
        return "SpaceOrb by " + getCreator();
    }

    /**
     * absorbs the orb
     * can only absorb an orb that has less energy than the absorber
     * absorb orb energy is 0
     * absorber increases energy by absorbed orb
     * @param orb
     * @returns true if absorbed, false if not
     */
    public boolean absorb(Orb orb) {

        if (orb == null) {
            return false;
        }

        if (orb.getEnergy() < this.energy) {
            this.energy += orb.getEnergy();
            orb.setEnergy(0);
            return true;
        }
        return false;
    }

    @Override
    public int getEnergy() {
        return energy;
    }
}
