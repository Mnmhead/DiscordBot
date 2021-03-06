package commands;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a basic output command.
 * A basic command is one that simply prints a static message
 * in response to the command. A basic command accepts zero
 * arguments.
 */
public class BasicOutputCommand extends BotCommand {
   private String output;

   public BasicOutputCommand( String name, String description, String output ) {
      this.name = name;
      this.description = description;
      this.output = output;
   }

   public void doCmd() {
      bot.cmdMgr.sendMessage( chan, output ); 
   }
}
