// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.BotInstance;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/*
 * This class represents a bot command. Every command is made up of a description
 * of what the command perfoms. A name for the command which is what a user would enter
 * to execute said command.
 */
public abstract class BotCommand {
	public String description;  // description of the commands function
	public String name;  // the name of the command (what the user must type)

   // params to the command
   public BotInstance bot;  // the bot this command is directed at
   public IChannel chan;  // channel this command can interact with
   public IUser user;   // author of the command request
   public List<String> parameters;  // parameters to this command

   public final void invoke( CmdRequest cmdReq ) {
      extractParams( cmdReq );
      doCmd();
   }
	
	/*
	 * The function the command will perform
	 */
	protected abstract void doCmd();

   /*
    * Unpacks a command request into this object
    */
   public void extractParams( CmdRequest cmdReq ) {
      this.bot = cmdReq.bot;
      this.chan = cmdReq.chan;
      this.user = cmdReq.user;
      this.parameters = cmdReq.parameters;
   }
}
