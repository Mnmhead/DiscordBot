// Copyright Gyorgy Wyatt Muntean 2017
package main;

import commands.CommandManager;
import listeners.MessageListener;
import listeners.UserVoiceChannelListener;
import java.time.Instant;
import java.io.*;
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
   private static final TOKEN_FILE = "token.txt";   

   public static String token;
   public static IDiscordClient client;
   public static CommandManager cm;
   public static boolean muted;
   public static long lastMute;

   // Main entry point to the bot. 
   public static void main( String args[] ) {
      token = readToken();
      client = createClient( token, true );
      cm = new CommandManager();
      muted = false;
      lastMute = Instant.now().getEpochSecond();

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

   // Function to read in token from file.
   private static String readToken() {
      FileInputStream fs;

      try {
         fs = new FileInputStream( TOKEN_FILE );
         br = new BufferedReader( new InputStreamReader( fs ) );
         return br.readLine();
      } catch( FileNotFoundException e ) {
         fs = null;
         System.err.println( "File not found: please provide token.txt within the run directory." );
         System.exit( 1 );
      }
   }
}
