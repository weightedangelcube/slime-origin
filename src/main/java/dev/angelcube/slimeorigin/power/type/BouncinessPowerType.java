package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class BouncinessPowerType extends PowerType {
    /**
     * The bounciness multiplier, or in other words, how bouncy the entity should be.
     */
    private final double bounceMultiplier;
    private final double minBounceHeight;

    /**
     * Constructs a new instance of the Bounciness PowerType.
     * 
     * @param bounceMultiplier The bounciness multiplier.
     */
    public BouncinessPowerType(double bounceMultiplier, double minBounceHeight) {
        this.bounceMultiplier = bounceMultiplier;
        this.minBounceHeight = minBounceHeight;
    }

    /**
     * This data factory comes from Calio—it sniffs the slimeorigin:bounciness JSON file for a field called
     * bounce_multiplier with a value of type double, and passes that into the class constructor.
     */
    public static final TypedDataObjectFactory<BouncinessPowerType> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add("bounce_multiplier", SerializableDataTypes.DOUBLE)
            .add("min_bounce_height", SerializableDataTypes.DOUBLE, 0.1),
        data -> new BouncinessPowerType(
            data.get("bounce_multiplier"),
            data.get("min_bounce_height")
        ),
        (powerType, serializableData) -> serializableData.instance()
            .set("bounce_multiplier", powerType.bounceMultiplier)
            .set("min_bounce_height", powerType.minBounceHeight)
        );

    /**
     * A simple accessor for the bounceMultiplier field.
     *
     * @return The bounceMultiplier field
     */
    public double getBounceMultiplier() {
        return bounceMultiplier;
    }

    public double getMinBounceHeight() {
        return minBounceHeight;
    }

    /**
     * Retrieves the PowerConfiguration of BOUNCINESS defined in PowerTypes.
     *
     * @return The BOUNCINESS PowerConfiguration
     */
    @Override
    public @NotNull PowerConfiguration<?> getConfig() {
        return SlimePowerTypes.BOUNCINESS;
    }
}