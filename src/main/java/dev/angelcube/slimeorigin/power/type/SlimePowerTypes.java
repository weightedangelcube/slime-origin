package dev.angelcube.slimeorigin.power.type;

import dev.angelcube.slimeorigin.Slimeorigin;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerTypes;

/**
 * This class registers all the PowerTypes we have to Apoli's registry.
 */
public class SlimePowerTypes {
    /**
     * Creates new PowerConfigurations BOUNCINESS and FRAGMENTATION, and registers them to Apoli's
     * registry.
     */
    public static final PowerConfiguration<BouncinessPowerType> BOUNCINESS = PowerTypes.register(
        PowerConfiguration.of(Slimeorigin.identifier("bounciness"), BouncinessPowerType.DATA_FACTORY)
    );
    public static final PowerConfiguration<FragmentationPowerType> FRAGMENTATION = PowerTypes.register(
        PowerConfiguration.simple(Slimeorigin.identifier("fragmentation"), FragmentationPowerType::new)
    );


    /**
     * Registers all the PowerTypes defined in this class. Apoli does this for us, so we can just
     * leave this method blank.
     */
    public static void register() {

    }

}