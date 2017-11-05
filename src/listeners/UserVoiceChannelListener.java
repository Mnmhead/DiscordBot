// Copyright Gyorgy Wyatt Muntean 2017
package listeners;

import main.BotMain;
import utils.UserX;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelJoinEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.voice.user.UserVoiceChannelMoveEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageBuilder;

/*
 * This class represents the listener for Voice channel events.
 * The events supported are: join, leave and move events.
 */
public class UserVoiceChannelListener implements IListener<UserVoiceChannelEvent> {

   public UserVoiceChannelListener() {
      super();
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
      } else {
         System.err.println( "Encountered unkown VoiceChannelEvent." );
         return;
      }
   }

   /*
    *
    */ 
   public void handleLeave( UserVoiceChannelLeaveEvent event ) {
      IUser user = event.getUser(); 
      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getVoiceChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // Get session time
      long id = user.getLongID();
      UserX userX = BotMain.users.get( id );
      if( userX == null ) {
         buildTTSMessage( defaultChan, "Someone left, but I missed who it was." );
      }
      String sessionTimeStr = milliToContentStr( userX.getSessionTime() );
      sessionTimeStr = userName + ", " + sessionTimeStr;

      // Delete user
      BotMain.removeUser( user );

      // build the string which the bot will send
      String content = userName + " has left " + vcName;
      buildTTSMessage( defaultChan, content );
      showSessionTime( defaultChan, sessionTimeStr );
   }

   /*
    *
    */    
   public void handleJoin( UserVoiceChannelJoinEvent event ) {
      // Add user to Bot's map
      IUser user = event.getUser(); 
      BotMain.addUser( user );

      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getVoiceChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // build the string which the bot will send
      String content = userName + " has joined " + vcName;
      buildTTSMessage( defaultChan, content );
   }
   
   /*
    *
    */ 
   public void handleMove( UserVoiceChannelMoveEvent event ) {
      IUser user = event.getUser();
      IChannel defaultChan = event.getGuild().getDefaultChannel();
      String vcName = event.getNewChannel().toString();
      String userName = user.getName();  // could do getDisplayName() lol

      // build the string which the bot will send
      String content = userName + " has moved to " + vcName;
      buildTTSMessage( defaultChan, content );
   }

   /*
    * Provate helper function to send a TTS message through the channel.
    */ 
   private static void buildTTSMessage( IChannel chan, String content ) {
      if( BotMain.muted ) {
         return;
      }
      // remove non-ascii characters from the string
      content = content.replaceAll( "[^\\x00-\\x7F]", "" );  
      MessageBuilder mb = new MessageBuilder( BotMain.client ); 
      mb.withChannel( chan ).withContent( content ).withTTS().build();
   }

   /*
    * Adds user to Bot's user map.
    */
   private static void addUser( IUser user ) {
      long id = user.getLongID();
      UserX userX = new UserX( user );
      BotMain.users.put( id, userX );
   }

   /*
    * Removes user from Bot's user map.
    */
   private static void removeUser( IUser user ) {
      long id = user.getLongID();
      if( BotMain.users.containsKey( id ) ) {
         BotMain.users.remove( id );
      }
   }  

   /*
    * Builds a message to show user's session time.
    */
   private static void showSessionTime( IChannel chan, String content ) {
      if( BotMain.muted ) {
         return;
      }
      MessageBuilder mb = new MessageBuilder( BotMain.client ); 
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
         return "what the hell, you were on more than a full day!?";
      }
 
      return "session time: " + String.format("%02d:%02d:%02d", hour, minute, second );
   }

}
