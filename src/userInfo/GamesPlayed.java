// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;
import sx.blah.discord.handle.obj.IChannel;

public class GamesPlayed extends BotCommand {

   public GamesPlayed() {
      description = "Shows the games you play.";
      name = "games";
   }

   public void doCmd() {
      String response = bot.userInfoCmds.showGamesPlayed( user );
      bot.cmdMgr.sendMessage( chan, response );
   }

}
