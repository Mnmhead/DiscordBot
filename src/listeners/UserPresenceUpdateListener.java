// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import static debug.DebugUtil.*;
import bot.*;
import java.util.*;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IPresence;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.user.PresenceUpdateEvent;

/*
 * This class represnets a listener which listens for guild create events.
 * In other words this event listener triggers when the bot connects to a new
 * guild.
 */
public class UserPresenceUpdateListener implements IListener<PresenceUpdateEvent> {

   private BotMgr botMgr;

   /*
    * Constructor for the MessageLIstener object.
    */ 
   public UserPresenceUpdateListener( BotMgr botMgr ) {
      super();
      this.botMgr = botMgr;
   }

   /*
    * Handles the recipt of a message along any text channel.
    */ 
   public void handle( PresenceUpdateEvent event ) {
      IUser user = event.getUser();
      IPresence newPres = event.getNewPresence();
      DEBUG( "Handling presence update event for user: " + user.getName() );
      if( newPres.getPlayingText().isPresent() ) {
         String gameText = newPres.getPlayingText().get();
         DEBUG( user.getName() + " now playing " + gameText );
         Iterator<BotInstance> bIt = botMgr.getBots().iterator();
         while( bIt.hasNext() ) {
            BotInstance bot = bIt.next();
            if( bot.getUser( user.getLongID() ) != null ) {
               bot.getUser( user.getLongID() ).putGame( gameText, 0 );
            }
         }
      }
   }
}
