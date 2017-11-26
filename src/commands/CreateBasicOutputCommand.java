package commands;

import main.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * 
 */
public class CreateBasicOutputCommand extends BotCommand {

    public CreateBasicOutputCommand() {
        description = "creates a basic output command";
        name = "create";
    }

    public void doCmd( BotInstance bot, IChannel chan, List<String> parameters  ) {
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
      Set<String> existingCmds = bot.cmdMgr.getExistingCmdNames();
      if( existingCmds.contains( name ) ) {
         String errMsg = String.format(
                                 "Error: command \"%s\" already exists.",
                                 name );
         bot.cmdMgr.sendMessage( chan, errMsg );   
      }

      BasicOutputCommand basicCmd = new BasicOutputCommand( name,
                                                            description, 
                                                            output );
      bot.cmdMgr.addBotCommand( basicCmd );
      String createMsg = String.format( "New command \"%s\" created with description \"%s\" and output \"%s\"",
                                        name, 
                                        description, 
                                        output );
      bot.cmdMgr.sendMessage( chan, createMsg );
    }
}
