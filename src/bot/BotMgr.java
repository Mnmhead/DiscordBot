// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import static debug.DebugUtil.*;
import java.util.*;
import sx.blah.discord.handle.obj.IGuild;

/*
 * This class manages all running bot instances.
 * Each guild in the scope of the BotMgr is assigned one bot instance.
 */
public class BotMgr {
 
   // Map from guild snowflakeID to the botinstance assigned to that guild
   private static HashMap<Long,BotInstance> guilds;

   public BotMgr() {
      guilds = new HashMap<Long,BotInstance>();
   } 

   public void printGuilds() {
      Iterator<Long> it = guilds.keySet().iterator();
      DEBUG( "Managed guilds:" );
      while( it.hasNext() ) {
         long id = it.next();
         DEBUG( "   " + id );
      }
      DEBUG( "" );
   }

   /*
    *
    */
   public Collection<BotInstance> getBots() {
      return guilds.values();
   }

   /*
    *
    */
   public void addGuild( IGuild guild ) {
      BotInstance bot = new BotInstance( guild );
      guilds.put( guild.getLongID(), bot );
   }

   /*
    * Gets the bot instance given the passed in guild name. 
    * Returns null if the guild does not exist
    */
   public BotInstance getBotInstance( long guildId ) {
      return guilds.get( guildId );
   }
}
