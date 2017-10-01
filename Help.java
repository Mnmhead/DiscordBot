package commands;

import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents the command to display help text.
 * This command lists all other commands and thier descriptions. 
 */
public class Help extends BotCommand {

	/*
	 * Constructor for a Help command.
	 */
	public Help() {
		description = "Type help to list all available commands";
		name = "help";
		maxNumArgs = 0;  // command takes no arguments
	}
	
	public void doCmd( IChannel chan ) {
		
	}
}
