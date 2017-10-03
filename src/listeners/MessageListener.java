// Copyright Gyorgy Wyatt Muntean 2017
package main;

import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/*
 * This class represnets a listener which listens for recieved messages
 * on all discord text channels.
 */
public class MessageListener implements IListener<MessageReceivedEvent> {

   private static final String prefix = "#";

   /*
    * Constructor for the MessageLIstener object.
    */ 
   public MessageListener() {
      super();
   }

   /*
    * Handles the recipt of a message along any text channel.
    */ 
   public void handle( MessageReceivedEvent event ) {
      BotMain.cm.manageCommand( event.getMessage(), prefix ); 
   }

}
