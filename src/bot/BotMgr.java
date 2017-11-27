// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import java.util.HashMap;
import java.util.Iterator;
import sx.blah.discord.handle.obj.IGuild;
//import sx.blah.discord.handle.impl.events.guild.*;

/*
 * This class manages all running bot instances.
 * Each guild in the scope of the BotMgr is assigned one bot instance.
 */
public class BotMgr {
 
   // Mangement related fields
   private static HashMap<String,BotInstance> guilds;

   public BotMgr() {
      guilds = new HashMap<String,BotInstance>();
   } 

   public void printGuilds() {
      Iterator<String> it = guilds.keySet().iterator();
      while( it.hasNext() ) {
         String name = it.next();
         System.out.println( name );
      }
   }

   public void addGuild( String name ) {
      BotInstance bot = new BotInstance();
      guilds.put( name, bot );
   }

   /*
    * Gets the bot instance given the passed in guild name. 
    * Returns null if the guild does not exist
    */
   public BotInstance getBotInstance( String guildName ) {
      return guilds.get( guildName );
   }
}
