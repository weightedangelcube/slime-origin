package dev.angelcube.slimeorigin.mixin;

import dev.angelcube.slimeorigin.power.type.BouncinessPowerType;

import io.github.apace100.apoli.component.PowerHolderComponent;

import net.minecraft.block.Block;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Vec3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {
    double bounceMultiplier;
    /**
    * Makes the Slime block super bouncy.
    * Instead of having bounce happen twice on Slime blocks, we need to override it to be extra bouncy.
    *
    * @see BlockMixin
    * @param entity The entity to apply bounciness to
    * @param ci The callback info
    */
    @Inject(method = "bounce", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(DDD)V", ordinal = 0), cancellable = true)
    public void bounce(Entity entity, CallbackInfo ci) {
        for (BouncinessPowerType bouncePower : PowerHolderComponent.getPowerTypes(entity, BouncinessPowerType.class)) {
            bounceMultiplier = bouncePower.getBounceMultiplier() * 2;
        }
        Vec3d entityVelocity = entity.getVelocity();
        if (!entity.bypassesLandingEffects() && PowerHolderComponent.hasPowerType(entity, BouncinessPowerType.class)) {
            entity.setVelocity(entityVelocity.x, -entityVelocity.y * bounceMultiplier, entityVelocity.z);
            BlockSoundGroup slimeSounds = BlockSoundGroup.SLIME;
            entity.playSound(slimeSounds.getStepSound(), slimeSounds.getVolume() * 0.1F, slimeSounds.getPitch());
            ci.cancel();
        }
    }
}