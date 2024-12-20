package dev.angelcube.slimeorigin.mixin;

import dev.angelcube.slimeorigin.Slimeorigin;
import dev.angelcube.slimeorigin.power.type.FragmentationPowerType;

import io.github.apace100.apoli.component.PowerHolderComponent;

import java.util.Set;

import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * A mixin targeting the LivingEntity class.
 *
 * @see LivingEntity
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    /**
     * The constructor for the LivingEntity class.
     *
     * @param type The type of the targeted LivingEntity
     * @param world The current world
     */
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // These are a couple of shadowed methods we retrieve from the original LivingEntity class.
    @Shadow public abstract @Nullable EntityAttributeInstance getAttributeInstance(RegistryEntry<EntityAttribute> attribute);
    @Shadow public abstract void setHealth(float health);
    @Shadow public abstract boolean clearStatusEffects();
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    /**
     * Adds our new attribute GENERIC_SLIME_SIZE to the targeted LivingEntity.
     *
     * @param cir The returned value
     */
    @Inject(method = "createLivingAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", require = 1, allow = 1, at = @At("RETURN"))
    private static void createLivingAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        final DefaultAttributeContainer.Builder attributeBuilder = cir.getReturnValue();
        attributeBuilder.add(Slimeorigin.GENERIC_SLIME_SIZE);
    }

    /**
     * Fragmentation code.
     * <p>
     * Every time an entity dies, the tryUseTotem method is called; we bypass the check whether the entity
     * is actually holding the Totem of Undying if the current entity has the Fragmentation power type.
     * If so, we first increment the GENERIC_SLIME_SIZE attribute, then retrieve its value. If it's greater
     * than or equal to one, we create new modifiers for the size and max health of the entity. We reset
     * the entity's health and status effects, and add new status effects Regenration, Resistance, and
     * Absorption, as is standard when a Totem is used.
     * <p>
     * If the entity doesn't have the Fragmentation power
     * type, we sort through a Set of modifiers for each EntityAttribute and attempt to remove these
     * modifiers, and reset the health and size of the entity.
     *
     * @param source The source of the damage killing the entity
     * @param cir The returned value
     */
    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (PowerHolderComponent.hasPowerType(this, FragmentationPowerType.class)) {
            this.getAttributeInstance(Slimeorigin.GENERIC_SLIME_SIZE).setBaseValue(this.getAttributeInstance(Slimeorigin.GENERIC_SLIME_SIZE).getBaseValue() + 1);
            int flooredSlimeSize = (int) Math.floor(this.getAttributeInstance(Slimeorigin.GENERIC_SLIME_SIZE).getValue());
            if (flooredSlimeSize >= 1) {
                EntityAttributeModifier slimeScaleModifier = new EntityAttributeModifier(Slimeorigin.identifier(String.format("fragmentation_scale_%d", flooredSlimeSize)), -0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                EntityAttributeModifier slimeMaxHealthModifier = new EntityAttributeModifier(Slimeorigin.identifier(String.format("fragmentation_max_health_%d", flooredSlimeSize)), -0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                this.getAttributeInstance(EntityAttributes.GENERIC_SCALE).addPersistentModifier(slimeScaleModifier);
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(slimeMaxHealthModifier);
                
                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 800, 0));

                cir.setReturnValue(true);
                cir.cancel();
            }
        } else {
            Set<EntityAttributeModifier> maxHealthModifiers = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getModifiers();
            for (EntityAttributeModifier modifier : maxHealthModifiers) {
                if (modifier.id().toString().contains("fragmentation_max_health")) {
                    this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).removeModifier(modifier.id());
                }
            }
            Set<EntityAttributeModifier> scaleModifiers = this.getAttributeInstance(EntityAttributes.GENERIC_SCALE).getModifiers();
            for (EntityAttributeModifier modifier : scaleModifiers) {
                if (modifier.id().toString().contains("fragmentation_scale")) {
                    this.getAttributeInstance(EntityAttributes.GENERIC_SCALE).removeModifier(modifier.id());
                }
            }
        }
    }
}