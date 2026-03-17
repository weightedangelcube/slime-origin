package dev.angelcube.slimeorigin.power.type;

import dev.angelcube.slimeorigin.SlimeOrigin;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerTypes;
import net.minecraft.util.Identifier;

/**
 * This class registers all the PowerTypes we have to Apoli's registry.
 */
public class SlimePowerTypes {
    /**
     * Creates new PowerConfigurations BOUNCINESS and FRAGMENTATION, and registers them to Apoli's
     * registry.
     */
    public static final PowerConfiguration<BouncinessPowerType> BOUNCINESS = PowerTypes.register(
        PowerConfiguration.of(
                Identifier.of("angelcube","slime-origin/bounciness"),
                BouncinessPowerType.DATA_FACTORY
        )
    );

    public static final PowerConfiguration<FragmentationPowerType> FRAGMENTATION = PowerTypes.register(
        PowerConfiguration.simple(
                Identifier.of("angelcube","slime-origin/fragmentation"),
                FragmentationPowerType::new
        )
    );


    /**
     * Registers all the PowerTypes defined in this class. Apoli does this for us, so we can just
     * leave this method blank.
     */
    public static void register() { }

}