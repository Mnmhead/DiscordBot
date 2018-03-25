// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import static debug.DebugUtil.*;
import bot.*;
import java.util.HashMap;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.impl.events.guild.*;

/*
 * This class represnets a listener which listens for guild create events.
 * In other words this event listener triggers when the bot connects to a new
 * guild.
 */
public class GuildCreateListener implements IListener<GuildCreateEvent> {

   private BotMgr botMgr;

   /*
    * Constructor for the MessageLIstener object.
    */ 
   public GuildCreateListener( BotMgr botMgr ) {
      super();
      this.botMgr = botMgr;
   }

   /*
    * Handles the recipt of a message along any text channel.
    */ 
   public void handle( GuildCreateEvent event ) {
      IGuild guild = event.getGuild();
      DEBUG( "Handling guild create event for guild: " + guild.getName() );
      botMgr.addGuild( guild );
   }

}
