// Copyright Gyorgy Wyatt Muntean 2017

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
         handleJoin( event );
      } else if( event instanceof UserVoiceChannelLeaveEvent ) {
         handleJoin( event );
      } else if( event instanceof UserVoiceChannelMoveEvent ) {
         handleMove( event );
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

      // build the string which the bot will send
      String content = userName + " has left " + vcName;
      buildTTSMessage( defaultChan, content );
   }

   /*
    *
    */    
   public void handleJoin( UserVoiceChannelJoinEvent event ) {
      IUser user = event.getUser(); 
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
      // remove non-ascii characters from the string
      content = content.replaceAll( "[^\\x00-\\x7F]", "" );  
      MessageBuilder mb = new MessageBuilder( BotMain.client ); 
      mb.withChannel( chan ).withContent( content ).withTTS().build();
   }

}
