// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class UserEpoch extends BotCommand {

   public UserEpoch() {
      description = "Shows when you sprung into existance (from this bot's perspective).";
      name = "epoch";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showUserEpoch( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
