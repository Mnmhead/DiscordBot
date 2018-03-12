// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class RecordSessionTime extends BotCommand {

   public RecordSessionTime() {
      description = "Shows your longest session time ever.";
      name = "record-time";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showRecordSessionTime( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
