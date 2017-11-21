// Copyright Gyorgy Wyatt Muntean 2017
package main;

import java.util.HashMap;
import java.util.Iterator;
import sx.blah.discord.handle.obj.IGuild;
//import sx.blah.discord.handle.impl.events.guild.*;

/*
 * The main class and entry point for the bot.
 * Main funciton is housed here.
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

   public void addGuild( String name, BotInstance bot ) {
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
