// Copyright Gyorgy Wyatt Muntean 2017
package commands;

/*
 * This class represents a bot command. Every command is made up of a description
 * of what the command perfoms. A name for the command which is what a user would enter
 * to execute said command. Lastly, a command has a maximum number of arguments that
 * can follow the command name.
 */
public abstract class BotCommand {
	public String description;  // description of the commands function
	public String name;  // the name of the command (what the user must type)
	public int maxNumArgs;  // the maximum number of arguments the command can take
	
	/*
	 * The function the command will perform
	 */
	public abstract void doCmd( IChannel chan );
	
}
