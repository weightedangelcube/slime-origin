package angelcube.dev.slimeorigin.mixin;


// import dev.angelcube.slimeorigin.power.type.PowerTypes;
import dev.angelcube.slimeorigin.power.type.BouncinessPowerType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin targeting the Block class.
 *
 * @see Block
 **/
@Mixin(Block.class)
public class BlockMixin {
    /**
     * Modifies all blocks to be bouncy such as the slime block.
     * If the PowerType BOUNCY is active, and the entity in question does not bypass landing effects,
     * then get the velocity of said entity. If the entity is falling downwards, invert the velocity once
     * the ground is hit to get a bounce. 
     *
     * @param world The state of blocks, fluids, and their entities
     * @param entity The entity to apply bounciness to
     * @param ci 
     **/

    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        if (new BouncinessPowerType(null).isActive() && !entity.bypassesLandingEffects()) {
            Vec3d entityVelocity = entity.getVelocity();
            if (entityVelocity.y < -0.1) {
                double d;
                if (entity instanceof LivingEntity) {
                    d = 1.0D;
                } else {
                    d = 0.8D;
                }
                entity.setVelocity(entityVelocity.x, -entityVelocity.y * d, entityVelocity.z);
                
                BlockSoundGroup blockSoundGroup = BlockSoundGroup.SLIME;
                entity.playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.1F, blockSoundGroup.getPitch());

            } else {
                entity.setVelocity(entityVelocity.x, entityVelocity.y * 0.0D, entityVelocity.z);
            }
            ci.cancel();
            return;
        }
    }
}