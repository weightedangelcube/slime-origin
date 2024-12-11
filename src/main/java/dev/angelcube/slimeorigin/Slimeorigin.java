package dev.angelcube.slimeorigin;

import dev.angelcube.slimeorigin.power.type.SlimePowerTypes;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main entrypoint for the mod.
 */
public class Slimeorigin implements ModInitializer {
	/**
	 * Create a new String MOD_ID, for use in Loggers and Identifiers.
	 */
	public static final String MOD_ID = "slimeorigin";
  
	/**
	 * Create a new LOGGER, to print information to the console.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * Completes tasks that must be done upon initialisation, such as initialising all the PowerTypes.
	 */
	@Override
	public void onInitialize() {
        SlimePowerTypes.register();
        LOGGER.info("Initialising Slime Origin, enjoy!");
	}

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }
}