// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import commands.BotCommand;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

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
	
	public void doCmd() {
      IUser next = bot.novelWriter.getNextContributor();
      if( next == null ) {
         bot.cmdMgr.sendMessage( chan, "No users registered. " +
                                       "Use #register to register yourself." );
         return;
      }
      String username = next.getName(); 
      bot.cmdMgr.sendMessage( chan, username + ", you are the next writer." );
	}
}
