// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RateLimitException;

/*
 * This class, the command manager, manages the available bot commands.
 */
public class CmdMgr {

	public Map<String, BotCommand> commands;  // a map from command names to commands
	public Pattern regexForParams;
   private BotInstance bot;

	/*
	 * Constructor for a CmdMgr.
	 */
	public CmdMgr( BotInstance bot ) {
      this.bot = bot;
		commands =  new HashMap<String, BotCommand>();

		// Add all desired commands to the CmdMgr.
		Mute mute = new Mute();
		commands.put( mute.name, mute );
		Unmute unmute = new Unmute();
		commands.put( unmute.name, unmute );
		CreateBasicOutputCommand cboc = new CreateBasicOutputCommand();
		commands.put( cboc.name, cboc );
        
      List<BasicOutputCommand> basicOutputCommands = getBasicOutputCommands();
      for (BasicOutputCommand boc : basicOutputCommands) {
         commands.put(boc.name, boc);
      }

      /*
      Compiles a regex for parameter matching in manageCommand.
      This grabs sequences of characters that aren't spaces or double-quotes,
      and sequences of characters that begin and end with a double-quote, with 
      no double-quotes in between. There is a caputing group for the later
      to disclude the double quotes being in the output. 
      */
      regexForParams = Pattern.compile("[^\\s\"]+|\"([^\"]*)\"");
	}

	/*
	 * Function to poll incoming messages for valid commands.
	 * Valid commands then trigger a call to the proper function.
	 */
	public void manageCommand( IMessage message, String prefix ) {
		IChannel channel = message.getChannel();
		String msg = message.getContent().toLowerCase();
		// if the message is empty, return
		if( msg.equals( "" ) ) {
			return;
		}
		String potentialPrefix = msg.substring( 0, 1 );
		
		// if the first character in the user's message is not the command
		// prefix, return silently
		if( !potentialPrefix.equals( prefix ) ) {
			return;
		} 
		
		// strip off the prefix from the command
		String strippedMsg = msg.replaceFirst( prefix, "" );

		// split command by a regex to extract command name and parameters
		Matcher regexMatcher = regexForParams.matcher(strippedMsg);
		List<String> matchList = new ArrayList<String>();

		while(regexMatcher.find()) {
			if (regexMatcher.group(1) != null)
				// add double-quoted string without quotes
				matchList.add(regexMatcher.group(1));
			else
				// add unquoted string
				matchList.add(regexMatcher.group());
		}

		// The message was just the command prefix with 0 or more spaces..
		if (matchList.size() == 0) 
			return;

		String cmd = matchList.get(0);
		List<String> parameters = matchList.subList(1, matchList.size());
		
		// help command is a special case
		if( cmd.equals( "help" ) ) {
			displayHelptext( channel );
			return;
		}

      if( cmd.equals( "printguilds" ) ) {
         BotRunner.botMgr.printGuilds();
         return;
      }
		
		// grab the bot command from the dictionary of commands
		BotCommand botcmd = commands.get( cmd );
		if( botcmd == null ) {
			sendMessage( channel, "Command \'" + cmd + "\'" + "not supported." );
			return;
		}
		
		// perform the command
		botcmd.doCmd( bot, channel, parameters );
	}
	
	/*
	 * Lists all commands in the passed text channel.
	 */
	public void displayHelptext( IChannel chan ) {
		String helptext = "Type '#help' for a list of available commands.\n\n";
		Iterator<String> it = commands.keySet().iterator();
		while( it.hasNext() ) {
			String cmd = it.next();
			String description = commands.get( cmd ).description;
			helptext += cmd + "\t-\t" + description + "\n";
		}
		sendMessage( chan, helptext );
	}
	
	/*
	 * Wrapper function for sending messages through a channel.
	 */
	public static void sendMessage( IChannel chan, String msg ) {
		try {
			chan.sendMessage( msg );
		} catch( RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
			System.err.print( "Rate-limited, exception thrown." );
			e.printStackTrace();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
    /* Method used by commands to dynamically add new commands to the manager
     * TODO persist added commands to disk
     */
    protected void addBotCommand(BotCommand botCommand) {
    	this.commands.put( botCommand.name, botCommand );
    }
	
    /*
     * Returns a list of basic output commands for this command manager
     * TODO Make this take basic output commands from a file 
     */
    private List<BasicOutputCommand> getBasicOutputCommands() {
      ArrayList<BasicOutputCommand> basicOutputCommands = new ArrayList<BasicOutputCommand>();
      basicOutputCommands.add( new BasicOutputCommand( "kill", 
                                                       "this command kills", 
                                                       "I will kill you") );
      basicOutputCommands.add( new BasicOutputCommand( "poke_chris", 
                                                       "pokes Chris", 
                                                       "Hey Christopher!...Chris responds, \"YEAH!!???!\"") );
      return basicOutputCommands;
    }
}
