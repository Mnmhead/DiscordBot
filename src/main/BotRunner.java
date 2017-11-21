// Copyright Gyorgy Wyatt Muntean 2017
package main;

import listeners.*;
import utils.UserX;
import java.time.Instant;
import java.io.*;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.impl.events.guild.*;

/*
 * The main class and entry point for the bot.
 * Main funciton is housed here.
 */
public class BotRunner {
 
   // Bot's token should exist in a file in the same directory as the user
   // plans to run this bot. The file should be one line, being the 
   // Discord token string. 
   private static final String TOKEN_FILE = "token.txt";   

   // Discord related fields
   public static String token;
   public static IDiscordClient client;

   // Mangement related fields
   public static BotMgr botMgr;

   // Main entry point to the bot. 
   public static void main( String args[] ) {
      token = readToken();
      client = createClient( token );
      botMgr = new BotMgr();

      // Get the event dispatcher associated with this client
      EventDispatcher dispatcher = client.getDispatcher();
      // Add listeners to listen on messages, user voice channels, and
      // new guilds
      dispatcher.registerListener( new MessageListener( botMgr ) );
      dispatcher.registerListener( new UserVoiceChannelListener( botMgr ) );
      dispatcher.registerListener( new GuildCreateListener( botMgr ) );
   }

   /*
    * Gets the bot instance given the passed in guild name. 
    * Returns null if the guild does not exist
    */
   public static BotInstance getBotInstance( String guildName ) {
      return botMgr.getBotInstance( guildName );
   }

   // Creates a Client object and returns a client instance
   private static IDiscordClient createClient( String token ) { 
      ClientBuilder clientBuilder = new ClientBuilder();
      clientBuilder.withToken( token ); // Adds the login info to the builder
      try {
         return clientBuilder.login(); // Creates the client instance and logs the client in
      } catch ( DiscordException e ) { // This is thrown if there was a problem building the client
         e.printStackTrace();
         return null;
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
