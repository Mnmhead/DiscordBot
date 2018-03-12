// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class CurrentSessionTime extends BotCommand {

   public CurrentSessionTime() {
      description = "Shows your current session time.";
      name = "current-time";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showCurrentSessionTime( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
