package eva.dualwield;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.slf4j.Logger;

public class InteractHitter extends Minecraft {

    private static final Logger LOGGER = LogUtils.getLogger();
    public InteractHitter(GameConfig gameConfig) {
        super(gameConfig);
        Minecraft thisMinecraft = (Minecraft) (Object) this;
    }

    private boolean startOffhandAttack() {
        if (this.missTime > 0) {
            return false;
        } else if (this.hitResult == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (!this.gameMode.isDestroying()) {
                this.missTime = 10;
            }

            return false;
        } else if (this.player.isHandsBusy()) {
            return false;
        } else {
            ItemStack itemStack = this.player.getItemInHand(InteractionHand.OFF_HAND);
            if (!itemStack.isItemEnabled(this.level.enabledFeatures())) {
                return false;
            } else {

                boolean bl = false;

                switch (this.hitResult.getType()) {

                    case ENTITY:
                        this.gameMode.attack(this.player, ((EntityHitResult)this.hitResult).getEntity());
                        break;

                    case BLOCK:
                        BlockHitResult blockHitResult = (BlockHitResult)this.hitResult;
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        if (!this.level.getBlockState(blockPos).isAir()) {
                            this.gameMode.startDestroyBlock(blockPos, blockHitResult.getDirection());
                            if (this.level.getBlockState(blockPos).isAir()) {
                                bl = true;
                            }
                            break;
                        }

                    case MISS:
                        if (this.gameMode.hasMissTime()) {
                            this.missTime = 10;
                        }
                        this.player.resetAttackStrengthTicker();
                }

                this.player.swing(InteractionHand.OFF_HAND);
                return bl;
            }
        }
    }
}
