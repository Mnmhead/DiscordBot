// Copyright Gyorgy Wyatt Muntean 2017
package bot;

import static debug.DebugUtil.*;
import commands.CmdMgr;
import novelWriter.NovelWriter;
import userInfo.*;
import utils.UserX;
import java.time.Instant;
import java.io.*;
import java.util.*;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IVoiceState;
import sx.blah.discord.util.DiscordException;

/*
 * This class represents a bot which is a just a set of states and some
 * data.
 */
public class BotInstance {

   public CmdMgr cmdMgr;
   public NovelWriter novelWriter;
   public UserInfoCmds userInfoCmds;

   private boolean muted;
   private long lastMute;
   private HashMap<Long,UserX> users;
   private IGuild guild;
   private IChannel defaultChan;

   public BotInstance( IGuild guild ) {
      this.muted = false;
      this.lastMute = Instant.now().getEpochSecond();
      this.guild = guild;
      this.defaultChan = guild.getDefaultChannel();
      this.users = new HashMap<Long,UserX>();
      // populate users dict with guild's users 
      Iterator<IUser> uIt = guild.getUsers().iterator();
      while( uIt.hasNext() ) {
         IUser u = uIt.next();
         if( u.isBot() ) {
            continue;
         }
         UserX uX = new UserX( u );
         putUser( u.getLongID(), uX );
         DEBUG( "Putting user: " + uX.getName() + " in guild: " + guild.getName() );
         // log user in, if they are currently in a voice channel 
         if( u.getVoiceStateForGuild( guild ).getChannel() != null ) {
            DEBUG( "Detected  " + u.getName() + " in voice channel, logging them in." );
            loginUser( u );
         }
      }

      cmdMgr = new CmdMgr( this );
      novelWriter = new NovelWriter( this, guild.getName() );
      userInfoCmds = new UserInfoCmds( this );

      // send greeting message
      greetGuild();
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
   public UserX getUserByName( String name ) {
      DEBUG( "Getting user by name: " + name );
      Iterator<UserX> uIt = users.values().iterator();
      while( uIt.hasNext() ) {
         UserX u = uIt.next();
         DEBUG( "   " + u.getName() );
         if( u.getName().equals( name ) ) {
            return u;
         }
      }

      DEBUG( "Finished iterating over all users, did not find" );
      return null;
   }

   /*
    *
    */
   public void putUser( long id, UserX user ) {
      users.put( id, user );
   }

   /*
    * 
    */
   public void loginUser( IUser user ) {
      long id = user.getLongID();
      if( users.containsKey( id ) ) {
         users.get( id ).login();
      } else {
         DEBUG( "Attempted to login non-existant user: " + user.getName() );
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
         DEBUG( "Attempted to log out non-existant user: " + user.getName() );
      }
   }

   /*
    *
    */
   public IGuild getGuild() {
      return guild;
   }

   /*
    *
    */
   private void greetGuild() {
      // turns out this can throw an exception if the IShard is not ready
      try {
         defaultChan.sendMessage( "Hello ***" + guild.getName() + "***, thanks for having me." );
      } catch( DiscordException e ) {
         DEBUG( "Couldn't greet guild " + guild.getName() + " becuase of discord exception" );
         e.printStackTrace();
      }
   }

}
