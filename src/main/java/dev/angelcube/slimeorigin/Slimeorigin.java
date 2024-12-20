package dev.angelcube.slimeorigin;

import dev.angelcube.slimeorigin.power.type.SlimePowerTypes;
import dev.angelcube.slimeorigin.util.TextFileResourceListener;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
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

    public static final RegistryEntry<EntityAttribute> GENERIC_SLIME_SIZE = Registry.registerReference(Registries.ATTRIBUTE, identifier("slime_size"), new ClampedEntityAttribute("attribute.name.generic.slimeorigin.slime_size", 0, 0, 3).setTracked(true));
    
    /**
	 * Completes tasks that must be done upon initialisation, such as initialising all the PowerTypes.
	 */
	@Override
	public void onInitialize() {
        SlimePowerTypes.register();
        LOGGER.info("Initialising Slime Origin, enjoy!");
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new TextFileResourceListener());
	}

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }
}