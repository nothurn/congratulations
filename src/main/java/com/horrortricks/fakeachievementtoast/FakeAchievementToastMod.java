package com.horrortricks.fakeachievementtoast;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import org.slf4j.Logger;

@Mod("fakeachievementtoast")
public class FakeAchievementToastMod {
   public static final String MOD_ID = "fakeachievementtoast";
   private static final Logger LOGGER = LogUtils.getLogger();

   public FakeAchievementToastMod(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.CLIENT, FakeAchievementToastConfig.SPEC);
      LOGGER.info("Fake Achievement Toast initialized.");
   }
}
