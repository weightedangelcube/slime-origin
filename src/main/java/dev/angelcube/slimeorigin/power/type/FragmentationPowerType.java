package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;

import java.util.Optional;

public class FragmentationPowerType extends PowerType {
    /**
     * Constructs a new instance of the Fragmentation PowerType.
     * 
     * @param condition Optional condition to pass to the superclass
     */
    public FragmentationPowerType(Optional<EntityCondition> condition) {
        super(condition);
    }

    @Override
    public PowerConfiguration<?> getConfig() {
        return SlimePowerTypes.FRAGMENTATION;
    }
}