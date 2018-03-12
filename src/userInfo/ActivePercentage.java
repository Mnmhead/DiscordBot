// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class ActivePercentage extends BotCommand {

   public ActivePercentage() {
      description = "Shows the percentage of your life you spend here, as of late.";
      name = "lifetime";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showPercentageSpentHere( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
