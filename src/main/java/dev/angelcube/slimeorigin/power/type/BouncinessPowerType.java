package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;

import java.util.Optional;

public class BouncinessPowerType extends PowerType {
    /**
     * Constructs a new instance of the Bounciness PowerType.
     * 
     * @param condition Optional condition to pass to the superclass
     */
    public BouncinessPowerType(Optional<EntityCondition> condition) {
        super(condition);
    }

    /**
     * Retrieves the PowerConfiguration of BOUNCINESS defined in PowerTypes.
     *
     * @return The BOUNCINESS PowerConfiguration
     */
    @Override
    public PowerConfiguration<?> getConfig() {
        return SlimePowerTypes.BOUNCINESS;
    }
}