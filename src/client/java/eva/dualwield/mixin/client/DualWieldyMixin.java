package eva.dualwield.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static eva.dualwield.SimpleDualWieldClient.MOD_ID;
import static net.minecraft.world.InteractionHand.*;

@Debug(export = true)
@Mixin(Minecraft.class)
public class DualWieldyMixin{

	Minecraft thisMinecraft = (Minecraft) (Object) this;

	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	InteractionHand interactionHand = OFF_HAND;

	@Inject(method = "startUseItem",
			at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;",
			shift = At.Shift.AFTER))
	private void interactionHandTracker(CallbackInfo ci) {interactionHand = (interactionHand == MAIN_HAND) ? OFF_HAND : MAIN_HAND;}

	@Inject(method = "startUseItem",
			at = @At(value = "RETURN",
					ordinal = 5, shift = At.Shift.BY, by = 2))

	private void offHandAttack(CallbackInfo info) {
		// This code is injected into the start of Minecraft.run()V
		if (interactionHand == OFF_HAND) {

//			EntityHitResult entityHitResult = (EntityHitResult) thisMinecraft.hitResult;
//			Entity entity = entityHitResult.getEntity();
//			InteractionResult interactionResult =
//					thisMinecraft.gameMode.interactAt(thisMinecraft.player, entity,
//							entityHitResult, interactionHand);
//			if (!interactionResult.consumesAction()) {
//				interactionResult = thisMinecraft.gameMode.interact(thisMinecraft.player, entity, interactionHand);
//			}
//
//			if (interactionResult instanceof InteractionResult.Success) {
//				InteractionResult.Success success = (InteractionResult.Success)interactionResult;
//				if (success.swingSource() == InteractionResult.SwingSource.CLIENT) {
//					thisMinecraft.player.swing(interactionHand);

//				}
//			}
		}
	}

	@Inject(method = "startUseItem",
			at = @At(value = "RETURN",
					ordinal = 5, shift = At.Shift.BY, by = 2))
	public void offHandMine(CallbackInfo ci){
		if (interactionHand == OFF_HAND) {

		}
	}
}