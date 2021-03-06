// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import java.util.*;

/*
 * This class expresses a grouping of commands. Some commands
 * are not stand-alone and have some relation to each other. 
 * A CmdGroup should be used in the case that a relationship 
 * between commands needs to be expressed. Any CmdGroup must
 * belong to a CmdMgr.
 */
public class CmdGroup {

	private Map<String, BotCommand> commands;  // a map from command names to commands

	/*
	 * Constructor for a CmdMgr.
	 */
	public CmdGroup() {
		commands = new HashMap<String, BotCommand>();
	}
			
   /* 
    * Adds a bot command to this CmdGroup
    */
   public void addBotCommand( BotCommand botCommand ) {
      commands.put( botCommand.name, botCommand );
   }

   /* 
    * Removes a bot command from this CmdGroup
    */
   public void removeBotCommand( String name ) {
      commands.remove( name );
   }

   /*
    * Retuns a list of commands in this group by name
    */
   public Set<String> getCmdNames() {
      return commands.keySet();
   } 

   /*
    * Gets bot command by name
    */
   public BotCommand getCmd( String name ) {
      return commands.get( name );
   }
}
