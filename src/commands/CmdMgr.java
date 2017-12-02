// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
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
		commands = new HashMap<String, BotCommand>();

		// Add commands to the CmdMgr.
      registerStaticCmds();	
 
      // add existing basic output commands 
      List<BasicOutputCommand> basicOutputCommands = getBasicOutputCommands();
      for (BasicOutputCommand boc : basicOutputCommands) {
         commands.put(boc.name, boc);
      }

      // Compiles a regex for parameter matching in manageCommand.
      // This grabs sequences of characters that aren't spaces or double-quotes,
      // and sequences of characters that begin and end with a double-quote, with 
      // no double-quotes in between. There is a caputing group for the later
      // to disclude the double quotes being in the output. 
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
		
		// if the first character in the user's message is not the command
		// prefix, return silently
		String potentialPrefix = msg.substring( 0, 1 );
		if( !potentialPrefix.equals( prefix ) ) {
			return;
		} 
		
		// strip off the prefix from the command
		String strippedMsg = msg.replaceFirst( prefix, "" );

		// split command by a regex to extract command name and parameters
		Matcher regexMatcher = regexForParams.matcher( strippedMsg );
		List<String> matchList = new ArrayList<String>();

		while( regexMatcher.find() ) {
			if( regexMatcher.group(1) != null ) {
				// add double-quoted string without quotes
				matchList.add( regexMatcher.group(1) );
			} else {
				// add unquoted string
				matchList.add( regexMatcher.group() );
         }
		}

		// The message was just the command prefix with 0 or more spaces..
		if( matchList.size() == 0 ) {
			return;
      }

		String cmd = matchList.get( 0 );
		List<String> parameters = matchList.subList( 1, matchList.size() );
		
		// grab the bot command from the dictionary of commands
		BotCommand botcmd = commands.get( cmd );
		if( botcmd == null ) {
			sendMessage( channel, "Command \'" + cmd + "\'" + " not supported." );
			return;
		}

      CmdRequest cmdReq = buildCmdRequest( bot, 
                                           channel,
                                           message.getAuthor(),
                                           parameters );
		
		// perform the command
		botcmd.invoke( cmdReq );
	}
	
	/*
	 * Lists all commands in the passed text channel.
	 */
	public void displayHelptext( IChannel chan ) {
		String helptext = "List of available commands:\n\n";

      // iterate to find the longest command. This helps with
      // formatting
      Iterator<String> it = commands.keySet().iterator();
      int longestCmd = 0;
      while( it.hasNext() ) {
         String cmd = it.next();
         int len = cmd.length();
         if( len > longestCmd ) {
            longestCmd = len;
         }
      }

		it = commands.keySet().iterator();
		while( it.hasNext() ) {
			String cmd = it.next();
			String description = commands.get( cmd ).description;
         // compute padding
			helptext += formatHelptextLine( longestCmd, cmd, description );
		}
		sendMessage( chan, "``" + helptext + "``" );
	}

	/*
	 * Wrapper function for sending messages through a channel.
	 */
	public static void sendMessage( IChannel chan, String msg ) {
      // format message by surrounding it with backticks
      msg = "`" + msg + "`";
		try {
			chan.sendMessage( msg );
		} catch( RateLimitException e) { // RateLimitException thrown. The bot is sending messages too quickly!
			System.err.print( "Rate-limited, exception thrown." );
			e.printStackTrace();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
   /* 
    * Method used by commands to dynamically add new commands to the manager
    * TODO persist added commands to disk
    */
   protected void addBotCommand( BotCommand botCommand ) {
      commands.put( botCommand.name, botCommand );
   }

   /* 
    * Method used by commands to dynamically add new commands to the manager
    * TODO persist added commands to disk
    */
   protected void removeBotCommand( String name ) {
      commands.remove( name );
   }

   /*
    * Retuns a list of existing commands by name
    */
   protected Set<String> getExistingCmdNames() {
      return commands.keySet();
   } 

   /*
    * Helper function to add static commands to the command manager.
    * When a new BotCommand is defined, add instantiate it here.
    */
   private void registerStaticCmds() {
	   Mute mute = new Mute();
		commands.put( mute.name, mute );
		Unmute unmute = new Unmute();
		commands.put( unmute.name, unmute );
		CreateBasicOutputCommand cboc = new CreateBasicOutputCommand();
		commands.put( cboc.name, cboc );
      Help help = new Help();
      commands.put( help.name, help );
      CopyPasta copyP = new CopyPasta();
      commands.put( copyP.name, copyP );
   }

   /*
    *
    */
   private CmdRequest buildCmdRequest( BotInstance bot,
                                       IChannel chan,
                                       IUser user,
                                       List<String> parameters ) {
      return new CmdRequest( bot, chan, user, parameters );
   }
   
   /*
    * Returns a list of basic output commands for this command manager
    * TODO Make this take basic output commands from a file 
    */
   private List<BasicOutputCommand> getBasicOutputCommands() {
      ArrayList<BasicOutputCommand> basicOutputCommands = new ArrayList<BasicOutputCommand>();
      basicOutputCommands.add( new BasicOutputCommand( "kill", 
                                                       "this command kills", 
                                                       "I will kill you" ) );
      basicOutputCommands.add( new BasicOutputCommand( "poke_chris", 
                                                       "pokes Chris", 
                                                       "Hey Christopher!...Chris responds, \"YEAH!!???!\"" ) );
      return basicOutputCommands;
   }

   /*
    * Helper function to format a line of helptext
    */
   private String formatHelptextLine( int spacing,
                                      String cmd,
                                      String description ) {
      // compute padding before cmd
      int padLen = spacing - cmd.length();
      String padding = "";
      for( int i = 0; i < padLen; i++ ) {
         padding += " ";
      }

      // build description padding string, 3 extra spaces
      // for " - ".
      String descPadding = "";
      for( int i = 0; i < spacing + 3; i++ ) {
         descPadding += " ";
      }

      // pad the description
      String[] descLines = description.split( "\n" );
      String formatDesc = "";
      for( int i = 0; i < descLines.length; i++ ) {
         if( i == descLines.length - 1 ) {
            // if this is last line, print with no padding
            formatDesc += descLines[ i ];
         } else {
            // otherwise, print padding
            formatDesc += descLines[ i ] + "\n" + descPadding;
         }
      }

      return cmd + padding + " - " + formatDesc + "\n";
   }
	
}
