package dev.angelcube;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.type.PowerType;
import io.github.apace100.apoli.power.type.PowerReference;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Slimeorigin implements ModInitializer {
    public static final String MOD_ID = "slimeorigin";

    public static final PowerType<Power> BOUNCY = new PowerReference<Power>(new Identifier(MOD_ID, "bouncy"));
    public static final PowerType<Power> FRAGMENTATION = new PowerReference<Power>(new Identifier(MOD_ID, "fragmentation"));

    public static final EntityAttribute SLIME_SIZE = make("slime_size", 0, 0 ,3);

    public static double getSlimeSize(final LivingEntity entity){
        final EntityAttributeInstance size = entity.getAttributeInstance(SLIME_SIZE);
        return size.getValue();
    }


    private static EntityAttribute make(final String name, final double base, final double min, final double max) {
        return new ClampedEntityAttribute("attribute.name.generic." + MOD_ID + '.'  + name, base, min, max).setTracked(true);
    }


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

	}
}