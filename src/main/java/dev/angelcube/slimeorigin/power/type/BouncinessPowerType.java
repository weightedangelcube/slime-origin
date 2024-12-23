package dev.angelcube.slimeorigin.power.type;

import io.github.apace100.apoli.data.TypedDataObjectFactory;
import io.github.apace100.apoli.power.PowerConfiguration;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;

import org.jetbrains.annotations.NotNull;

public class BouncinessPowerType extends PowerType {
    /**
     * The bounciness multiplier, or in other words, how bouncy the entity should be.
     */
    private double bounceMultiplier;

    /**
     * Constructs a new instance of the Bounciness PowerType.
     * 
     * @param bounceMultiplier The bounciness multiplier.
     */
    public BouncinessPowerType(double bounceMultiplier) {
        this.bounceMultiplier = bounceMultiplier;
    }

    /**
     * This data factory comes from Calio—it sniffs the slimeorigin:bounciness JSON file for a field called
     * bounce_multiplier with a value of type double, and passes that into the class constructor. It's
     * super cool!
     */
    public static final TypedDataObjectFactory<BouncinessPowerType> DATA_FACTORY = TypedDataObjectFactory.simple(
        new SerializableData()
            .add("bounce_multiplier", SerializableDataTypes.DOUBLE),
        data -> new BouncinessPowerType(
            data.get("bounce_multiplier")
        ),
        (powerType, serializableData) -> serializableData.instance()
            .set("bounce_multiplier", powerType.bounceMultiplier)
        );

    /**
     * A simple accessor for the bounceMultiplier field.
     *
     * @return The bounceMultiplier field
     */
    public double getBounceMultiplier() {
        return bounceMultiplier;
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