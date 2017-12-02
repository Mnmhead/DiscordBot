package commands;

import bot.BotInstance;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import java.util.List;

/*
 * This class represents a request to execute a command.
 * The fields can be thought of as parameters to an
 * arbitrary bot command.
 */
public class CmdRequest {

   public BotInstance bot;
   public IChannel chan;
   public IUser user;
   public List<String> parameters;

   /*
    * Constructor for a CmdRequest
    */
   public CmdRequest( BotInstance bot,
                      IChannel chan,
                      IUser user,
                      List<String> parameters ) {
      this.bot = bot;
      this.chan = chan;
      this.user = user;
      this.parameters = parameters;
   }
}
