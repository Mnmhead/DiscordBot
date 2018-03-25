// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import static debug.DebugUtil.*;
import listeners.*;
import utils.UserX;
import java.util.*;
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
import sx.blah.discord.api.IShard;

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

      // poll until client is ready (this might need to be polling until all
      // shards are ready?)
      while( !client.isReady() ) {
         DEBUG( "waiting for client ready status to be true..." );
         try {
            Thread.sleep( 1000 );
         } catch( InterruptedException ex ) {
            DEBUG( "Thread interrupted..." );
            Thread.currentThread().interrupt();
         }
      }

      // init our BotMgr, which tracks all bot instances
      botMgr = new BotMgr();
      
      Iterator<IGuild> gIt = client.getGuilds().iterator();
      while( gIt.hasNext() ) {
         IGuild g = gIt.next();
         DEBUG( "Recognized guild: " + g.getName() );
         botMgr.addGuild( g );
      }

      // Add listeners to listen on messages, user voice channels, and
      // new guilds
      EventDispatcher dispatcher = client.getDispatcher();
      dispatcher.registerListener( new MessageListener( botMgr ) );
      dispatcher.registerListener( new UserVoiceChannelListener( botMgr ) );
      dispatcher.registerListener( new GuildCreateListener( botMgr ) );
      dispatcher.registerListener( new UserPresenceUpdateListener( botMgr ) );
      dispatcher.registerListener( new UserGuildJoinListener( botMgr ) );
      //dispatcher.registerListener( new UserSpeakingListener( botMgr ) );
   }

   /*
    * Gets the bot instance given the passed in guild name. 
    * Returns null if the guild does not exist
    */
   public static BotInstance getBotInstance( long guildId ) {
      return botMgr.getBotInstance( guildId );
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
