package dev.angelcube.slimeorigin.power.type;

import dev.angelcube.slimeorigin.Slimeorigin;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;

import net.minecraft.registry.Registry;

// register all powertypes
public class SlimePowerTypes {
    /**
     * Creates a new PowerConfiguration BOUNCINESS and registers it to Apoli's registry.
     */
    public static final PowerConfiguration<BouncinessPowerType> BOUNCINESS = register(
        PowerConfiguration.conditionedSimple(Slimeorigin.identifier("bounciness"), BouncinessPowerType::new)
    );

    /**
     * Registers a provided PowerConfiguration to Apoli's registry.
     *
     * @param configuration The configuration to register
     * @return The registered configuration
     */
    public static <T extends PowerType> PowerConfiguration<T> register(PowerConfiguration<T> configuration) {
        PowerConfiguration<PowerType> casted = (PowerConfiguration<PowerType>) configuration;
        Registry.register(ApoliRegistries.POWER_TYPE, casted.id(), casted);
        return configuration;
    }

    /**
     * Registers all the PowerTypes defined in this class. Apoli does this for us, so we can just
     * leave this method blank.
     */
    public static void register() {

    }

}