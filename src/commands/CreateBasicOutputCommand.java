package commands;

import bot.BotInstance;
import java.util.List;
import java.util.Set;
import sx.blah.discord.handle.obj.IChannel;

/*
 * 
 */
public class CreateBasicOutputCommand extends BotCommand {

    public CreateBasicOutputCommand() {
        description = "creates a basic output command.\n" +
                      "usage:   #create [name] [description] [output].\n" +
                      "use quotes for a multi-word description and/or ouput.";
        name = "create";
    }

    public void doCmd() {
      if( parameters.size() != 3 ) {
         String usage = String.format( 
                              "Incorrect parameters. Use %s [name] [description] [output]", 
                              this.name );
         bot.cmdMgr.sendMessage( chan, usage );
         return;
      }

      String name = parameters.get( 0 );
      String description = parameters.get( 1 );
      String output = parameters.get( 2 );

      // check parameters
      if( name.contains( " " ) ) {
         String errMsg = String.format( 
                                 "Error: command name \"%s\" contains spaces.", 
                                 name ); 
	      bot.cmdMgr.sendMessage( chan, errMsg );
			return;
		}

      // check command-overwriting
      Set<String> existingCmds = bot.cmdMgr.getExistingCmdNames();
      if( existingCmds.contains( name ) ) {
         String errMsg = String.format(
                                 "Error: command \"%s\" already exists.",
                                 name );
         bot.cmdMgr.sendMessage( chan, errMsg );
         return;
      }

      BasicOutputCommand basicCmd = new BasicOutputCommand( name,
                                                            description, 
                                                            output );
      bot.cmdMgr.addBotCommand( basicCmd );
      String createMsg = String.format( "Command \"%s\" created with description \"%s\" and output \"%s\"",
                                        name, 
                                        description, 
                                        output );
      bot.cmdMgr.sendMessage( chan, createMsg );
    }
}
