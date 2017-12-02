// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a command to register users in the creation
 * of a novel.
 */
public class RegisterUser extends BotCommand {
	
	/*
	 * Constructor for the RegisterUser command
	 */
	public RegisterUser() {
		description = "Adds this user to list of novel contributors.";
		name = "register";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      bot.novelWriter.registerContributer();
	}
}
