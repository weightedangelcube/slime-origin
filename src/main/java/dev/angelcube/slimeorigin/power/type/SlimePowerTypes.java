package dev.angelcube.slimeorigin.power.type;

import dev.angelcube.slimeorigin.Slimeorigin;

import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.registry.ApoliRegistries;

import net.minecraft.registry.Registry;

/**
 * This class registers all the PowerTypes we have to Apoli's registry.
 */
public class SlimePowerTypes {
    /**
     * Creates new PowerConfigurations BOUNCINESS and FRAGMENTATION, and registers them to Apoli's
     * registry.
     */
    public static final PowerConfiguration<BouncinessPowerType> BOUNCINESS = register(
        PowerConfiguration.dataFactory(Slimeorigin.identifier("bounciness"), BouncinessPowerType.DATA_FACTORY)
    );
    public static final PowerConfiguration<FragmentationPowerType> FRAGMENTATION = register(
        PowerConfiguration.simple(Slimeorigin.identifier("fragmentation"), FragmentationPowerType::new)
    );

    /**
     * Registers a provided PowerConfiguration to Apoli's registry.
     *
     * @param configuration The configuration to register
     * @return The registered configuration
     */
    @SuppressWarnings("unchecked")
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