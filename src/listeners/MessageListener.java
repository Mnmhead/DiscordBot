// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import static debug.DebugUtil.*;
import bot.*;
import utils.*;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/*
 * This class represnets a listener which listens for recieved messages
 * on all discord text channels.
 */
public class MessageListener implements IListener<MessageReceivedEvent> {

   private static final String prefix = "#";   
   private BotMgr botMgr;

   /*
    * Constructor for the MessageLIstener object.
    */ 
   public MessageListener( BotMgr botMgr ) {
      super();
      this.botMgr = botMgr;
   }

   /*
    * Handles the recipt of a message along any text channel.
    */ 
   public void handle( MessageReceivedEvent event ) {
      BotInstance bot = botMgr.getBotInstance( event.getGuild().getLongID() );
      if( bot == null ) {
         return;
      }

      UserX author = bot.getUser( event.getAuthor().getLongID() );
      if( author == null ) {
         DEBUG( "Unknown user sent message: " + author.getName() );
         return;
      }

      author.incMessageCount();
      bot.cmdMgr.manageCommand( event.getMessage(), prefix ); 
   }

}
