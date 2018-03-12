// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class TotalSessionTime extends BotCommand {

   public TotalSessionTime() {
      description = "Shows your cumulative time spent in voice chat on this channel.";
      name = "total-time";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showTotalSessionTime( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
