// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import commands.BotCommand;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

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
	
	public void doCmd() {
      if( parameters.size() != 1 ) {
         String usage = String.format( "Incorrect parameters. Use %s [sentence]",
                                       this.name );
         bot.cmdMgr.sendMessage( chan, usage );
         return;
      }

      IUser cur = bot.novelWriter.getNextContributor(); 
      if( cur == null ) {
         bot.cmdMgr.sendMessage( chan, "You must register first, sorry." );
         return;
      }
      if( !user.equals( cur ) ) {
         bot.cmdMgr.sendMessage( chan, "Sorry " + user.getName() +
                                       ", you are not the next writer, " +
                                       cur.getName() + " is the next writer." );
         return;
      }

      String sentence = parameters.get( 0 );
      boolean status = bot.novelWriter.writeSentence( sentence );
      if( status ) {
         IUser next = bot.novelWriter.getNextContributor(); 
         bot.cmdMgr.sendMessage( chan, "Thanks, " + cur.getName() + ". " + next.getName() +
                                 " has been chosen as the next writer." );
      } else {
         bot.cmdMgr.sendMessage( chan, "Sorry, write failed." );
      }
	}

}
