package dev.angelcube.slimeorigin.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.angelcube.slimeorigin.power.type.BouncinessPowerType;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {

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
    * Doubles the normal bounciness on Slime blocks specifically.
    *
    * @see BlockMixin
    * @param entity The entity to apply bounciness to
    * @param original The original method
    */
    @WrapOperation(method = "bounce", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(DDD)V"))
    public void bounce(Entity entity, double x, double y, double z, Operation<Void> original) {
        Vec3d entityVelocity = entity.getVelocity();
        if (!PowerHolderComponent.hasPowerType(entity, BouncinessPowerType.class) || entity.bypassesLandingEffects()) {
            original.call(entity, x, y, z);
            return;
        }

        BouncinessPowerType bouncePower = PowerHolderComponent.getPowerTypes(entity, BouncinessPowerType.class).getFirst();
        double bounceMultiplier = bouncePower.getBounceMultiplier() * 2;
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