// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents the unique "help" command.
 */
public class Help extends BotCommand {
	
	/*
	 * Constructor for the Help command.
	 */
	public Help() {
		description = "displays helptext";
		name = "help";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      bot.cmdMgr.displayHelptext( chan );
	}
}
