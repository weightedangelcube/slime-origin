package dev.angelcube.slimeorigin.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.angelcube.slimeorigin.power.type.BouncinessPowerType;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin targeting the Block class.
 *
 * @see Block
 */
@Mixin(Block.class)
public class BlockMixin {
    @Unique
    float lastFallDistance;

    @Inject(method = "onLandedUpon", at = @At(value = "HEAD"))
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity.bypassesLandingEffects()) {
            lastFallDistance = 0; // if crouching or otherwise, set the last fall distance to 0 so bounce doesn't fire
            return;
        }
        lastFallDistance = fallDistance;
        // FIXME: kinda jank
    }

    /**
     * Modifies all blocks to be bouncy such as the Slime block.
     *
     * @param entity The entity to apply bounciness to
     * @param original The original method
     */
    @WrapOperation(method = "onEntityLand", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    public void onEntityLand(Entity entity, Vec3d velocity, Operation<Void> original) {
        Vec3d entityVelocity = entity.getVelocity();
        if (!PowerHolderComponent.hasPowerType(entity, BouncinessPowerType.class) || entity.bypassesLandingEffects()) {
            original.call(entity, velocity);
            return;
        }

        BouncinessPowerType bouncePower = PowerHolderComponent.getPowerTypes(entity, BouncinessPowerType.class).getFirst();
        double bounceMultiplier = bouncePower.getBounceMultiplier();
        double minBounceHeight = bouncePower.getMinBounceHeight();

        if (lastFallDistance > minBounceHeight) {
            entity.setVelocity(entityVelocity.x, -entityVelocity.y * bounceMultiplier, entityVelocity.z);
            BlockSoundGroup slimeSounds = BlockSoundGroup.SLIME;
            entity.playSound(slimeSounds.getStepSound(), slimeSounds.getVolume() * 0.1F, slimeSounds.getPitch());

        } else {
            entity.setVelocity(entityVelocity.x, entityVelocity.y * 0.0D, entityVelocity.z);
        }
    }
}