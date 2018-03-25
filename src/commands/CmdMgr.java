// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.*;
import static debug.DebugUtil.*;
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

   private static final String DEBUG_ON_STR = "debug-on";
   private static final String DEBUG_OFF_STR = "debug-off";

	public Map<String, BotCommand> commands;  // a map from command names to commands
	public Pattern regexForParams;
   private BotInstance bot;
   private Map<String, List<String>> cmdGroups;

	/*
	 * Constructor for a CmdMgr.
	 */
	public CmdMgr( BotInstance bot ) {
      this.bot = bot;
		commands = new HashMap<String, BotCommand>();
      cmdGroups = new HashMap<String, List<String>>();

		// Add commands to the CmdMgr.
      registerDefaultCmds();	
 
      // add existing basic output commands 
      CmdGroup basicCmds = getBasicOutputCommands();
      registerCmdGroup( "Basic", basicCmds );

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
		String msg = message.getContent();
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

		String cmd = matchList.get( 0 ).toLowerCase();
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

   public void displayHelptext( IChannel chan ) {
      String helptext = "List of available commands:\n\n";

      // calculate the longest string
      Iterator<String> it = cmdGroups.keySet().iterator();
      int longestCmd = 0;
      while( it.hasNext() ) {
         String groupName = it.next();
         Iterator<String> cmdIt = cmdGroups.get( groupName ).iterator();

         while( cmdIt.hasNext() ) {
            String cmd = cmdIt.next();
            int len = cmd.length();
            if( len > longestCmd ) {
               longestCmd = len;
            }
         }
      }
      
      // print the helptext
      it = cmdGroups.keySet().iterator();
      while( it.hasNext() ) {
         String groupName = it.next();
         Iterator<String> cmdIt = cmdGroups.get( groupName ).iterator();
         helptext += groupName + ":\n";
         helptext += "---------\n";  // an arbitrary amount of dashes
         
         while( cmdIt.hasNext() ) {
            String cmd = cmdIt.next();
            String description = commands.get( cmd ).description;
            // add padding
            helptext += formatHelptextLine( longestCmd, cmd, description );
         }

         helptext += "\n";
      }

      sendMessage( chan, "``\n" + helptext + "``" );
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
    *
    */
   public void registerCmdGroup( String groupName, CmdGroup cmdGrp ) {
      Iterator<String> it = cmdGrp.getCmdNames().iterator();
      List<String> cmdNames = new ArrayList<String>();
      while( it.hasNext() ) {
         BotCommand cmd = cmdGrp.getCmd( it.next() );
         addBotCommand( cmd );
         cmdNames.add( cmd.name );
      }

      cmdGroups.put( groupName, cmdNames );
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
   private void registerDefaultCmds() {
      CmdGroup defaults = new CmdGroup();
      defaults.addBotCommand( new Mute() );
		defaults.addBotCommand( new Unmute() );
		defaults.addBotCommand( new CreateBasicOutputCommand() );
      defaults.addBotCommand( new Help() );  
      defaults.addBotCommand( new CopyPasta() );
      //defaults.addBotCommand( new UsersToBePruned() );
      registerCmdGroup( "Default", defaults );
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
   private CmdGroup getBasicOutputCommands() {
      CmdGroup basicCmds = new CmdGroup();
      //basicCmds.addBotCommand( new BasicOutputCommand( "kill", 
      //                                                 "this command kills", 
      //                                                 "I will kill you" ) );
      //basicCmds.addBotCommand( new BasicOutputCommand( "poke-chris", 
      //                                                 "pokes Chris", 
      //                                                 "Hey Christopher!...Chris responds, \"YEAH!!???!\"" ) );
      return basicCmds;
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
