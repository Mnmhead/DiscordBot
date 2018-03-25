// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import static debug.DebugUtil.*;
import bot.BotInstance;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This calss represnts the unmute command.
 */
public class UsersToBePruned extends BotCommand {

		/*
       *
		 */
		public UsersToBePruned() {
			description = "Displays users who have been inactive for x days";
			name = "show-pruned";
		}
		
		public void doCmd() {
         if( parameters.size() != 1 ) {
            String usage = "Incorrect parameters. Provide a number of days of inactivity.";
            bot.cmdMgr.sendMessage( chan, usage );
            return;
         }

         int days = Integer.parseInt( parameters.get( 0 ) );
         DEBUG( "" + bot );
         DEBUG( "calling user prune: " + bot.getGuild().getName() );
         String msg = "";
         // String msg = bot.getGuild().getUsersToBePruned( days ) + " users would be pruned.";
         DEBUG( bot.getGuild().getUsersToBePruned( days ) + "" );
         bot.cmdMgr.sendMessage( chan, msg );
		}
}
