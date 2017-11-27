package commands;

import bot.BotInstance;
import sx.blah.discord.handle.obj.IChannel;

/*
 * 
 */
public class CreateBasicOutputCommand extends BotCommand {

   public CreateBasicOutputCommand() {
      description = "deletes a basic output command";
      name = "delete";
   }

   public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      if( parameters.size() != 1 ) {
         String usage = String.format( 
                              "Incorrect parameters. Use %s [name]", 
                              this.name );
         bot.cmdMgr.sendMessage( chan, usage );
         return;
      }

      String name = parameters.get( 0 );

      // check parameters
      // TODO: existing output commands only returns statically assigned cmds at this point.
      // eventually this will return all created commands. This function will also have to 
      // remove the command from the place it resides on disk.
      //
      // A temporary workaround could be to get all existing commands and only delete ones
      // that are instanceof( BasicOutputCommand ).
      Set<String> existingCmds = bot.cmdMgr.getBasicOutputCommands();
      if( !existingCmds.contains( name ) ) {
         String errMsg = String.format(
                                 "Error: command \"%s\" does not exist or is not removable.",
                                 name );
         bot.cmdMgr.sendMessage( chan, errMsg );   
      }

      bot.cmdMgr.removeBotCommand( basicCmd );
      String deleteMsg = String.format( "command \"%s\" deleted successfully",
                                        name );
      bot.cmdMgr.sendMessage( chan, deleteMsg );
    }
}
