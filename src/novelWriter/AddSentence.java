// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a command to add a sentence to the novel
 */
public class AddSentence extends BotCommand {
	
	/*
	 * Constructor for the AddSentence command
	 */
	public AddSentence() {
		description = "writes the given sentence as the next line in the novel.\n" +
                           "usage:  #write [sentence]\n" +
                           "surround sentence in quotes, i.e. \"hello world\"";
		name = "write";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      if( parameters.size() != 1 ) {
         String usage = String.format( "Incorrect parameters. Use %s [sentence]",
                                       this.name );
         return;
      }

      String sentence = parameters.get( 0 );
      bot.novelWriter.writeSentence( sentence );
	}
}
