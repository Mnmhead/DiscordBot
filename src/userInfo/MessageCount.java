// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class MessageCount extends BotCommand {

   public MessageCount() {
      description = "Shows the number of messages you've sent since your epoch.";
      name = "msg-count";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showMessageCount( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
