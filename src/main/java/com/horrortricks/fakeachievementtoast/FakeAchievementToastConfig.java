package com.horrortricks.fakeachievementtoast;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class FakeAchievementToastConfig {
   private static final Builder BUILDER = new Builder();
   public static final BooleanValue ENABLED = BUILDER.comment("Master switch for the fake achievement toast effect.").define("enabled", true);
   public static final IntValue MIN_INTERVAL_TICKS = BUILDER.comment(
         new String[]{"Minimum ticks (20 ticks/second) between fake achievement toasts.", "Default of 18000 is 15 minutes."}
      )
      .defineInRange("minIntervalTicks", 18000, 20, Integer.MAX_VALUE);
   public static final IntValue MAX_INTERVAL_TICKS = BUILDER.comment(
         new String[]{"Maximum ticks (20 ticks/second) between fake achievement toasts.", "Default of 36000 is 30 minutes."}
      )
      .defineInRange("maxIntervalTicks", 36000, 20, Integer.MAX_VALUE);
   public static final ModConfigSpec SPEC = BUILDER.build();

   private FakeAchievementToastConfig() {
   }

   static {
      BUILDER.push("general");
      BUILDER.pop();
   }
}
