// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RateLimitException;

/*
 * This class manages the available bot commands.
 */
public class CommandManager {

	public Map<String, BotCommand> commands;  // a map from command names to commands
	
	/*
	 * Constructor for a CommandManager.
	 */
	public CommandManager() {
		// Add all desired commands to the CommandManager.
		commands =  new HashMap<String, BotCommand>();
		PokeChris pc = new PokeChris();
		Mute mute = new Mute();
		Unmute unmute = new Unmute();
		CreateBasicOutputCommand cboc = new CreateBasicOutputCommand();
		commands.put( pc.name, pc );
		commands.put( mute.name, mute );
		commands.put( unmute.name, unmute );
		commands.put( cboc.name, cboc );
        
        List<BasicOutputCommand> basicOutputCommands = getBasicOutputCommands();
        for (BasicOutputCommand boc : basicOutputCommands) {
            commands.put(boc.name, boc);
        }
	}
	
    /*
     * Returns a list of basic output commands for this command manager
     * TODO Make this take basic output commands from a file 
     */
    private List<BasicOutputCommand> getBasicOutputCommands() {
        ArrayList<BasicOutputCommand> basicOutputCommands = new ArrayList<BasicOutputCommand>();
        basicOutputCommands.add(new BasicOutputCommand("kill", "this command kills", "I will kill you"));
        return basicOutputCommands;
    }

    /* Method used by commands to dynamically add new commands to the manager
     * TODO persist added commands to disk
     */
    protected void addBotCommand(BotCommand botCommand) {
    	this.commands.put( botCommand.name, botCommand );
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
		
		// strip off the prefix from the command, split command by whitespace
		// to extract command name and parameters
		String[] tokens = msg.replaceFirst( prefix, "" ).split( " " );
		String cmd = tokens[0];
		String[] parameters = Arrays.copyOfRange( tokens, 1, tokens.length );
		
		// help command is a special case
		if( cmd.equals( "help" ) ) {
			displayHelptext( channel );
			return;
		}
		
		// grab the bot command from the dictionary of commands
		BotCommand botcmd = commands.get( cmd );
		if( botcmd == null ) {
			sendMessage( channel, "Command \'" + cmd + "\'" + "not supported." );
			return;
		}
		
		// perform the command
		botcmd.doCmd( channel, parameters );
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
	
}
