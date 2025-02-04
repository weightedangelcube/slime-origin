package dev.angelcube.slimeorigin.mixin;

import dev.angelcube.slimeorigin.power.type.BouncinessPowerType;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

/**
 * A mixin targeting the Block class.
 *
 * @see Block
 */
@Mixin(Block.class)
public class BlockMixin {
    @Unique
    double bounceMultiplier;
    /**
     * Modifies all blocks to be bouncy such as the Slime block. 
     * If the PowerType BOUNCINESS is active, and the entity in question does not bypass landing effects,
     * then retrieve the velocity of said entity. If the entity is falling downwards with significant
     * velocity, invert the velocity once the ground is hit to get a "bounce," and play the Slime bounce
     * sound.
     *
     * @param world The state of blocks, fluids, and their entities, unused
     * @param entity The entity to apply bounciness to
     * @param ci The callback info
     */
    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        for (BouncinessPowerType bouncePower : PowerHolderComponent.getPowerTypes(entity, BouncinessPowerType.class)) {
            bounceMultiplier = bouncePower.getBounceMultiplier();
        }

        if (!entity.bypassesLandingEffects() && PowerHolderComponent.hasPowerType(entity, BouncinessPowerType.class)) {
            Vec3d entityVelocity = entity.getVelocity();
            if (entityVelocity.y < -0.1) {
                if (!(entity instanceof LivingEntity)) {
                    bounceMultiplier = 0.8D;
                }
                entity.setVelocity(entityVelocity.x, -entityVelocity.y * bounceMultiplier, entityVelocity.z);
                BlockSoundGroup slimeSounds = BlockSoundGroup.SLIME;
                entity.playSound(slimeSounds.getStepSound(), slimeSounds.getVolume() * 0.1F, slimeSounds.getPitch());
            } else {
                entity.setVelocity(entityVelocity.x, entityVelocity.y * 0.0D, entityVelocity.z);
            }
            ci.cancel();
        }
    }
}