// Copyright Gyorgy Wyatt Muntean 2017
package main;

import commands.CommandManager;
import listeners.MessageListener;
import listeners.UserVoiceChannelListener;
import utils.UserX;
import java.time.Instant;
import java.io.*;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

/*
 * The main class and entry point for the bot.
 * Main funciton is housed here.
 */
public class BotMain {
 
   // Bot's token should exist in a file in the same directory as the user
   // plans to run this bot. The file should be one line, being the 
   // Discord token string. 
   private static final String TOKEN_FILE = "token.txt";   

   // Discord related fields
   public static String token;
   public static IDiscordClient client;
   public static CommandManager cm;

   // Bot state 
   public static boolean muted;
   public static long lastMute;
   public static HashMap<Long,UserX> users;

   // Main entry point to the bot. 
   public static void main( String args[] ) {
      token = readToken();
      client = createClient( token, true );
      cm = new CommandManager();
      muted = false;
      lastMute = Instant.now().getEpochSecond();
      users =  new HashMap<Long,UserX>();

      // Get the event dispatcher associated with this client
      EventDispatcher dispatcher = client.getDispatcher();
      dispatcher.registerListener(new MessageListener());
      dispatcher.registerListener(new UserVoiceChannelListener());
   }

   // Creates a Client object and returns a client instance
   public static IDiscordClient createClient(String token, boolean login) { 
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
               // Creates the client instance but it doesn't log the client in yet, you would have 
               // to call client.login() yourself
               return clientBuilder.build();
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }

   /*
    * Adds user to Bot's user map.
    */
   public static void addUser( IUser user ) {
      long id = user.getLongID();
      UserX userX = new UserX( user );
      BotMain.users.put( id, userX );
   }

   /*
    * Removes user from Bot's user map.
    */
   public static void removeUser( IUser user ) {
      long id = user.getLongID();
      if( BotMain.users.containsKey( id ) ) {
         BotMain.users.remove( id );
      }
   }

   // Function to read in token from file.
   private static String readToken() {
      FileInputStream fs;
      String token;

      try {
         fs = new FileInputStream( TOKEN_FILE );
         BufferedReader br = new BufferedReader( new InputStreamReader( fs ) );
         token = br.readLine();
      } catch( IOException e ) {
         fs = null;
         token = null;
         System.err.println( "File not found: please provide token.txt within the run directory." );
         System.exit( 1 );
      }

      return token;
   }
}
