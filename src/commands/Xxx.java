// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.BotInstance;
import java.time.Instant;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class 
 */
public class Xxx extends BotCommand {
	
	/*
	 * Constructor for the Xxx command
	 */
	public Xxx() {
		description = "Adds following line to erotica novel.";
		name = "erotica";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      
      // lets design some code to facillitate the writing of a novel.
      // how should things work...
      //
      // there should be a collection of users who are currently participating in the novel
      // maybe there should be a set of commands existing within the novel writer...
      // Should I make a subCmdMgr? One that more complex command-based entities can use?
      //
      // for now, lets go with the following:
      // 1. command to register a user as a participator in the writing of the novel
      // 2. command to ask who is the next contributor
      // 3. command to write the next line in the novel
      //    a. this will give you an error message if you are not the next participator
      //    b. otherwise this will write the line to the file and advance to the next participator
      //       - a line cannot be greater than 140 characters? whatever a tweet is?
      // 4. 
	}
}
