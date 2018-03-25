// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.BotCommand;

public class ActiveStatus extends BotCommand {

   public ActiveStatus() {
      description = "Debugging tool";
      name = "show-active";
   }

   public void doCmd() {
      String response = "";
      if( parameters.size() == 0 ) {
         response = bot.userInfoCmds.showActiveStatus( user );
      } else if( parameters.size() == 1 ) {
         response = bot.userInfoCmds.showActiveStatus( parameters.get( 0 ) );
      } else {
         String usage = String.format( 
                              "Incorrect parameters. Use %s [name] [description] [output]", 
                              this.name );
         bot.cmdMgr.sendMessage( chan, usage );
         return;
      }

      bot.cmdMgr.sendMessage( chan, response );
   }

}
