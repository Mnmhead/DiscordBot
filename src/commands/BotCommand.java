// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a bot command. Every command is made up of a description
 * of what the command perfoms. A name for the command which is what a user would enter
 * to execute said command. Lastly, a command has a maximum number of arguments that
 * can follow the command name.
 */
public interface BotCommand {
	public String description;  // description of the commands function
	public String name;  // the name of the command (what the user must type)
	
	/*
	 * The function the command will perform
	 */
	public abstract void doCmd( BotInstance bot, IChannel chan, List<String> parameters );
}
