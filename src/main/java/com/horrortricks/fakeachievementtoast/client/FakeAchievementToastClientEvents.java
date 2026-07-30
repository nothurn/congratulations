package com.horrortricks.fakeachievementtoast.client;

import com.horrortricks.fakeachievementtoast.FakeAchievementToastConfig;
import com.horrortricks.fakeachievementtoast.FakeAchievementToastEntries;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.critereon.ImpossibleTrigger.TriggerInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import org.slf4j.Logger;

@EventBusSubscriber(modid = "fakeachievementtoast", value = Dist.CLIENT)
public final class FakeAchievementToastClientEvents {
   private static final int DEBUG_COMMAND_PERMISSION_LEVEL = 0;
   private static final Logger LOGGER = LogUtils.getLogger();
   private static long tickCounter = 0L;
   private static long nextToastTick = -1L;
   private static int spawnedCount = 0;

   private FakeAchievementToastClientEvents() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && minecraft.level != null && minecraft.screen == null) {
         if ((Boolean)FakeAchievementToastConfig.ENABLED.get()) {
            tickCounter++;
            RandomSource random = minecraft.level.random;
            if (nextToastTick < 0L) {
               scheduleNext(random);
            } else {
               if (tickCounter >= nextToastTick) {
                  showFakeToast(minecraft, random);
                  scheduleNext(random);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLoggingIn(LoggingIn event) {
      tickCounter = 0L;
      nextToastTick = -1L;
   }

   @SubscribeEvent
   public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
      register(event.getDispatcher());
   }

   private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fakeachievementtoast").requires(source -> source.hasPermission(0)))
            .then(Commands.literal("trigger").executes(context -> triggerNow((CommandSourceStack)context.getSource())))
      );
   }

   private static int triggerNow(CommandSourceStack source) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level == null) {
         LOGGER.info("[fakeachievementtoast debug] No world loaded - nothing to trigger.");
         return 0;
      } else {
         showFakeToast(minecraft, minecraft.level.random);
         LOGGER.info("[fakeachievementtoast debug] Triggered a fake achievement toast.");
         return 1;
      }
   }

   private static void scheduleNext(RandomSource random) {
      int min = (Integer)FakeAchievementToastConfig.MIN_INTERVAL_TICKS.get();
      int max = Math.max(min, (Integer)FakeAchievementToastConfig.MAX_INTERVAL_TICKS.get());
      int delay = min + random.nextInt(max - min + 1);
      nextToastTick = tickCounter + delay;
   }

   private static void showFakeToast(Minecraft minecraft, RandomSource random) {
      FakeAchievementToastEntries.Entry[] entries = FakeAchievementToastEntries.entries();
      FakeAchievementToastEntries.Entry entry = entries[random.nextInt(entries.length)];
      DisplayInfo display = new DisplayInfo(
         new ItemStack(Items.SKELETON_SKULL),
         Component.literal(entry.title()).withStyle(ChatFormatting.DARK_RED),
         Component.literal(entry.description()),
         Optional.empty(),
         AdvancementType.TASK,
         true,
         false,
         false
      );
      Builder builder = Builder.advancement().display(display).addCriterion("impossible", CriteriaTriggers.IMPOSSIBLE.createCriterion(new TriggerInstance()));
      AdvancementHolder holder = builder.build(ResourceLocation.fromNamespaceAndPath("fakeachievementtoast", "fake_toast_" + spawnedCount++));
      minecraft.getToasts().addToast(new AdvancementToast(holder));
   }
}
