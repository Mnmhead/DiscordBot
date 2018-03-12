// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import commands.CmdMgr;
import novelWriter.NovelWriter;
import userInfo.*;
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
   public NovelWriter novelWriter;
   public UserInfoCmds userInfoCmds;
   private boolean muted;
   private long lastMute;
   private HashMap<Long,UserX> users;
   private String guildName;

   // Constructor for a bot instance object
   public BotInstance( String guildName ) {
      muted = false;
      lastMute = Instant.now().getEpochSecond();
      this.guildName = guildName;
      users = new HashMap<Long,UserX>();
      cmdMgr = new CmdMgr( this );
      novelWriter = new NovelWriter( this, guildName );
      userInfoCmds = new UserInfoCmds( this );
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
    * 
    */
   public void loginUser( IUser user ) {
      long id = user.getLongID();
      if( users.containsKey( id ) ) {
         users.get( id ).login();
      } else {
         users.put( id, new UserX( user, true ) );    
      }
   }

   /*
    *
    */
   public void logoutUser( IUser user ) {
      long id = user.getLongID();
      if( users.containsKey( id ) ) {
         users.get( id ).logout();
      } else {
         // print some debug TODO
      }
   }

}
