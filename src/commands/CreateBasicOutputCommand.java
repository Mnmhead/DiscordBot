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
        maxNumArgs = 3;
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

      if( parameters.get( 0 ).contains( " " ) ) {
         String errMsg = String.format( "Error: command name \"%s\" contains spaces.", parameters.get( 0 ) ); 
	      bot.cmdMgr.sendMessage( chan, errMsg );
			return;
		}

      BasicOutputCommand basicCmd = new BasicOutputCommand(
                                             parameters.get( 0 ), 
                                             parameters.get( 1 ), 
                                             parameters.get( 2 ) );
      bot.cmdMgr.addBotCommand( basicCmd );
      String createMsg = String.format( "New command \"%s\" created with description \"%s\" and output \"%s\"",
                                   parameters.get( 0 ), parameters.get( 1 ), parameters.get( 2 ) );
      bot.cmdMgr.sendMessage( chan, createMsg );
    }
}
