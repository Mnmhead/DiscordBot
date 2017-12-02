// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a command to expose the next
 * contributor in line to write to the novel
 */
public class NextContributor extends BotCommand {
	
	/*
	 * Constructor for the NextContributor command
	 */
	public NextContributor() {
		description = "shows the next user in line to write the novel.";
		name = "next_writer";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      String username = bot.novelWriter.getNextContributor().getName(); 
      bot.cmdMgr.sendMessage( chan, "The next writer is: " + username + "." );
	}
}
