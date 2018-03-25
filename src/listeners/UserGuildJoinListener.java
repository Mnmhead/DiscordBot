// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import static debug.DebugUtil.*;
import bot.*;
import utils.*;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;

/*
 *
 */
public class UserGuildJoinListener implements IListener<UserJoinEvent> {

   private BotMgr botMgr;

   /*
    * Constructor for the UserGuildJoinListener object.
    */ 
   public UserGuildJoinListener( BotMgr botMgr ) {
      super();
      this.botMgr = botMgr;
   }

   public void handle( UserJoinEvent event ) {
      // A user has been added to a guild. Place them in the right
      // BotInstance and wrap them in a UserX object
      IUser user = event.getUser();
      UserX uX = new UserX( user );
      botMgr.getBotInstance( event.getGuild().getLongID() ).putUser( user.getLongID(), uX );
   }
}
