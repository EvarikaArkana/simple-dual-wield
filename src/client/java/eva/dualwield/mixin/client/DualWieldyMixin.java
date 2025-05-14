package eva.dualwield.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.InteractionHand.*;

@Debug(export = true)
@Mixin(Minecraft.class)
public class DualWieldyMixin{



	@Inject(method = "startUseItem",
			at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/InteractionResult;consumesAction()Z",
			ordinal = 0, shift = At.Shift.BEFORE))

	private void offHandAttack(CallbackInfo ci,
			@Local InteractionHand interactionHand, @Local Entity entity,
			@Local EntityHitResult entityHitResult) {
		Minecraft thisMinecraft = (Minecraft) (Object) this;
		InteractionResult interactionResult = thisMinecraft.gameMode.interactAt(thisMinecraft.player, entity, entityHitResult, interactionHand);
		if (!interactionResult.consumesAction()) {
			interactionResult = thisMinecraft.gameMode.interact(thisMinecraft.player, entity, interactionHand);
		}
		if (!(interactionResult instanceof InteractionResult.Success success)) {
			if (thisMinecraft.rightClickDelay > 0) {
				return;
			} else if (thisMinecraft.player.isHandsBusy()) {
				return;
			} else {
				ItemStack itemStack = thisMinecraft.player.getItemInHand(InteractionHand.OFF_HAND);
				if (!itemStack.isItemEnabled(thisMinecraft.level.enabledFeatures())) {
					return;
				} else {
					thisMinecraft.gameMode.attack(thisMinecraft.player, ((EntityHitResult) thisMinecraft.hitResult).getEntity());
					}
				}
			}
		}
	


	@Inject(method = "startUseItem",
			at = @At(value = "CONSTANT",
			args = "classValue=net.minecraft/world/InteractionResult$Fail",
			shift = At.Shift.BEFORE))
	public void offHandMine(CallbackInfo ci, @Local InteractionHand interactionHand){
		Minecraft thisMinecraft = (Minecraft) (Object) this;
		if (interactionHand == OFF_HAND) {

			if (thisMinecraft.rightClickDelay > 0) {
				return;
			} else if (thisMinecraft.player.isHandsBusy()) {
				return;
			} else {
				ItemStack itemStack = thisMinecraft.player.getItemInHand(InteractionHand.OFF_HAND);
				if (!itemStack.isItemEnabled(thisMinecraft.level.enabledFeatures())) {
					return;
				} else {
					BlockHitResult blockHitResult = (BlockHitResult) thisMinecraft.hitResult;
					BlockPos blockPos = blockHitResult.getBlockPos();
					if (!thisMinecraft.level.getBlockState(blockPos).isAir()) {
						thisMinecraft.gameMode.startDestroyBlock(blockPos, blockHitResult.getDirection());
						if (thisMinecraft.level.getBlockState(blockPos).isAir()) {
						}
					}
				}
			}

		}
	}
	
//	private boolean offAttacker(Minecraft minecraft) {
//		if (thisMinecraft.rightClickDelay > 0) {
//			return;
//		} else if (thisMinecraft.player.isHandsBusy()) {
//			return;
//		} else {
//			ItemStack itemStack = thisMinecraft.player.getItemInHand(InteractionHand.OFF_HAND);
//			if (!itemStack.isItemEnabled(thisMinecraft.level.enabledFeatures())) {
//				return;
//			} else {
//				boolean bl = false;
//				switch (thisMinecraft.hitResult.getType()) {
//					case ENTITY:
//						thisMinecraft.gameMode.attack(thisMinecraft.player, ((EntityHitResult) thisMinecraft.hitResult).getEntity());
//						break;
//					case BLOCK:
//						BlockHitResult blockHitResult = (BlockHitResult)thisMinecraft.hitResult;
//						BlockPos blockPos = blockHitResult.getBlockPos();
//						if (!thisMinecraft.level.getBlockState(blockPos).isAir()) {
//							 thisMinecraft.gameMode.startDestroyBlock(blockPos, blockHitResult.getDirection());
//							if (thisMinecraft.level.getBlockState(blockPos).isAir()) {
//								bl = true;
//							}
//							break;
//						}
//					case MISS:
//						if (thisMinecraft.gameMode.hasMissTime()) {
//							thisMinecraft.rightClickDelay = 10;
//						}
//
//						thisMinecraft.player.resetAttackStrengthTicker();
//				}
//
//				thisMinecraft.player.swing(InteractionHand.OFF_HAND);
//				return bl;
//			}
//		}
//	}
}
			
		
	
