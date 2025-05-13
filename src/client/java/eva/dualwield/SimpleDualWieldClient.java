package eva.dualwield;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDualWieldClient implements ClientModInitializer {
	public static final String MOD_ID = "simple-dual-wield-client";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		LOGGER.info("yes tis i eva");
		LOGGER.info("will u b 2 wielding today sir/mam");
		LOGGER.info("i hop it gos wel");
	}
}