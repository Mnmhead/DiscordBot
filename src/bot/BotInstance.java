// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import commands.CmdMgr;
import utils.UserX;
import java.time.Instant;
import java.io.*;
import java.util.HashMap;
import sx.blah.discord.handle.obj.IUser;

/*
 * The main class and entry point for the bot.
 * Main funciton is housed here.
 */
public class BotInstance {

   // Bot state 
   public CmdMgr cmdMgr;
   private boolean muted;
   private long lastMute;
   private HashMap<Long,UserX> users;

   // Constructor for a bot instance object
   public BotInstance() {
      muted = false;
      lastMute = Instant.now().getEpochSecond();
      users = new HashMap<Long,UserX>();
      cmdMgr = new CmdMgr( this );
   }

   /*
    * Gets the mute status of this bot instance
    */
   public boolean isMuted() {
      return muted;
   }

   /*
    * Mutes this bot instance
    */
   public void mute() {
      muted = true;
   }

   /*
    * Mutes this bot instance
    */
   public void unmute() {
      muted = false;
   }

   /*
    * Sets the last mute time for this bot instance
    */
   public void setLastMute( long time ) {
      lastMute = time;
   } 

   /*
    * Gets the last mute time for this bot instance
    */
   public long getLastMute() {
      return lastMute;
   }

   /*
    * gets user by their id
    */
   public UserX getUser( long id ) {
      return users.get( id );
   }

   /*
    * Adds user to Bot's user map.
    */
   public void addUser( IUser user ) {
      long id = user.getLongID();
      UserX userX = new UserX( user );
      users.put( id, userX );
   }

   /*
    * Removes user from Bot's user map.
    */
   public void removeUser( IUser user ) {
      long id = user.getLongID();
      if( users.containsKey( id ) ) {
         users.remove( id );
      }
   }

}
