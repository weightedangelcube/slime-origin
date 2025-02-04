package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import org.jetbrains.annotations.NotNull;

public class FragmentationPowerType extends PowerType {
    /**
     * Constructs a new instance of the Fragmentation PowerType.
     */
    public FragmentationPowerType() {

    }

    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return SlimePowerTypes.FRAGMENTATION;
    }
}