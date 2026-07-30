package com.horrortricks.fakeachievementtoast;

public final class FakeAchievementToastEntries {
   private static final FakeAchievementToastEntries.Entry[] ENTRIES = new FakeAchievementToastEntries.Entry[]{
      new FakeAchievementToastEntries.Entry("A New Feeling", "Something else moved when you did."),
      new FakeAchievementToastEntries.Entry("Recognized", "It already knew your name."),
      new FakeAchievementToastEntries.Entry("Uninvited Guest", "You weren't the only one who heard that."),
      new FakeAchievementToastEntries.Entry("Borrowed Time", "That wasn't the first time you died here."),
      new FakeAchievementToastEntries.Entry("Familiar Face", "It's wearing something you lost."),
      new FakeAchievementToastEntries.Entry("Still Watching", "It hasn't blinked since you arrived."),
      new FakeAchievementToastEntries.Entry("Quiet Company", "There were two sets of footprints."),
      new FakeAchievementToastEntries.Entry("Almost Yours", "The name was spelled almost right."),
      new FakeAchievementToastEntries.Entry("Longer Than You Think", "You've been here before. You just don't remember."),
      new FakeAchievementToastEntries.Entry("Not Alone Tonight", "Someone counted your steps.")
   };

   private FakeAchievementToastEntries() {
   }

   public static FakeAchievementToastEntries.Entry[] entries() {
      return ENTRIES;
   }

   public record Entry(String title, String description) {
   }
}
