package MysticOrbs.oven;

import MysticOrbs.storage.ResourceStorage;

public class InfinityMagicOven extends MagicOven {

    /**
     * constructor
     */
    public InfinityMagicOven(String name, ResourceStorage resourcesStorage) {
        super(name, resourcesStorage);
    }

    @Override

    public boolean isBroken() {
        return false;
    }
}
