// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import static debug.DebugUtil.*;
import bot.*;
import utils.UserX;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserSpeakingEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageBuilder;

/*
 * This class represents the listener for Voice channel events.
 * The events supported are: join, leave and move events.
 */
public class UserVoiceChannelListener implements IListener<UserVoiceChannelEvent> {

   private BotMgr botMgr;

   public UserVoiceChannelListener( BotMgr botMgr ) {
      super();
      this.botMgr = botMgr;
   }

   /*
    * Main event handler for UserVoiceChannelEvents.
    * Dispatches the event to the correct handler.
    */ 
   public void handle( UserVoiceChannelEvent event ) {
      if( event instanceof UserVoiceChannelJoinEvent ) {
         handleJoin( (UserVoiceChannelJoinEvent) event );
      } else if( event instanceof UserVoiceChannelLeaveEvent ) {
         handleLeave( (UserVoiceChannelLeaveEvent) event );
      } else if( event instanceof UserVoiceChannelMoveEvent ) {
         handleMove( (UserVoiceChannelMoveEvent) event );
      } else if( event instanceof UserSpeakingEvent ) {
         DEBUG( "UserSpeakingEvent received" );
      }
   }

   /*
    * Function to handle handle a leave event
    */ 
   public void handleLeave( UserVoiceChannelLeaveEvent event ) {
      // get BotInstance this event cooresponds to
      BotInstance bot = botMgr.getBotInstance( event.getGuild().getLongID() );
      if( bot == null ) {
         return;
      }

      // get user, channel, and other related information
      IUser user = event.getUser(); 
      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getVoiceChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // Get session time
      long id = user.getLongID();
      UserX userX = bot.getUser( id );
      if( userX == null ) {
         buildTTSMessage( bot, defaultChan, userName + " has left " + vcName );
         return;
      }
      String sessionTimeStr = milliToContentStr( userX.sessionTime() );
      sessionTimeStr = userName + ", " + sessionTimeStr;

      // Delete user
      bot.logoutUser( user );

      // build the string which the bot will send
      String content = userName + " has left " + vcName;
      buildTTSMessage( bot, defaultChan, content );
      showSessionTime( bot, defaultChan, sessionTimeStr );
   }

   /*
    *
    */    
   public void handleJoin( UserVoiceChannelJoinEvent event ) {
      // get BotInstance this event cooresponds to
      BotInstance bot = botMgr.getBotInstance( event.getGuild().getLongID() );
      if( bot == null ) {
         return;
      }

      // Add user to Bot's map
      IUser user = event.getUser(); 
      bot.loginUser( user );

      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getVoiceChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // build the string which the bot will send
      String content = userName + " has joined " + vcName;
      buildTTSMessage( bot, defaultChan, content );
   }
   
   /*
    *
    */ 
   public void handleMove( UserVoiceChannelMoveEvent event ) {
      // get BotInstance this event cooresponds to
      BotInstance bot = botMgr.getBotInstance( event.getGuild().getLongID() );
      if( bot == null ) {
         return;
      }

      IUser user = event.getUser();
      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getNewChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // build the string which the bot will send
      String content = userName + " has moved to " + vcName;
      buildTTSMessage( bot, defaultChan, content );
   }

   /*
    * Provate helper function to send a TTS message through the channel.
    */ 
   private static void buildTTSMessage( BotInstance bot, IChannel chan, String content ) {
      if( bot.isMuted() ) {
         return;
      }
      // remove non-ascii characters from the string
      content = content.replaceAll( "[^\\x00-\\x7F]", "" );  
                                             // somehow need to get rid of this refernce to BotRunner
      MessageBuilder mb = new MessageBuilder( BotRunner.client ); 
      mb.withChannel( chan ).withContent( content ).withTTS().build();
   }

   /*
    * Builds a message to show user's session time.
    */
   private static void showSessionTime( BotInstance bot, IChannel chan, String content ) {
      if( bot.isMuted() ) {
         return;
      }
      MessageBuilder mb = new MessageBuilder( BotRunner.client ); 
      mb.withChannel( chan ).withContent( content ).build();
   }

  /*
   * Converts milliseconds to human readable time string
   */
   private static String milliToContentStr( long millis ) {
      long second = (millis / 1000) % 60;
      long minute = (millis / (1000 * 60)) % 60;
      long hour = (millis / (1000 * 60 * 60)) % 24;
      
      if( ( millis / (1000*60*60) ) >= 24 ) {
         return "what the hell...you were online for more than a full day!?";
      }
 
      return "session time: " + String.format("%02d:%02d:%02d", hour, minute, second );
   }

}
