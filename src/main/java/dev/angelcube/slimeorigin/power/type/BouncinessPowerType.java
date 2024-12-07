package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.condition.EntityCondition;
import io.github.apace100.apoli.power.PowerConfiguration;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * This power allows an entity to bounce on all blocks as if they were slime blocks.
 * 
 * See also BlockMixin.
 **/
public class BouncinessPowerType extends PowerType {
    /**
     * Constructs a new instance of the bounciness power.
     *
     * @param condition
     **/
    public BouncinessPowerType(Optional<EntityCondition> condition) {
        super(condition);
    }

    /**
     * Retrieves the PowerConfiguration of BOUNCINESS defined in PowerTypes.
     *
     * @return The BOUNCINESS PowerConfiguration.
     **/
    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return PowerTypes.BOUNCINESS;
    }
}