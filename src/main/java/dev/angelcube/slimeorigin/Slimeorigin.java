package dev.angelcube.slimeorigin;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.angelcube.slimeorigin.power.type.PowerTypes;

import net.minecraft.util.Identifier;

/**
 * The main entrypoint for the mod.
 **/
public class Slimeorigin implements ModInitializer {

    /**
     * Create a new String MOD_ID, for use in creating Identifiers.
     **/
    public static final String MOD_ID = "slimeorigin";

    /**
     * Completes tasks that must be done upon initialisation.
     **/
	@Override
	public void onInitialize() {
        
	}

    /**
     * Creates a new Identifier unique to the mod.
     * Identifiers are stored in ID:path format, for example slimeorigin:foo.
     *
     * @param path The path of the identifier.
     * @return The namespaced Identifier.
     **/
    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

}