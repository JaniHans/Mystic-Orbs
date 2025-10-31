package MysticOrbs.oven;

public interface Fixable {

    // if magic oven breaks it can be repaired
    // Parandamiseks on vaja järgnevaid ressursse: clay x25 ja freezing powder x100
    // next time doubles

    /**
     * constructor
     */
    void fix();
    /**
     * constructor
     */
    int getTimesFixed();
        // returns how many times the oven has been repaired

}
