// Copyright Gyorgy Wyatt Muntean 2018
package userInfo;

import bot.BotInstance;
import commands.*;
import utils.*;
import sx.blah.discord.handle.obj.IUser;

public class UserInfoCmds {

   private BotInstance bot;
   private CmdGroup cmds;
   
   public UserInfoCmds( BotInstance bot ) {
      this.bot = bot;
      this.cmds = new CmdGroup();
      cmds.addBotCommand( new CurrentSessionTime() );  
      cmds.addBotCommand( new RecordSessionTime() );  
      cmds.addBotCommand( new UserEpoch() );  
      cmds.addBotCommand( new TotalSessionTime() );  
      cmds.addBotCommand( new MessageCount() );  
      bot.cmdMgr.registerCmdGroup( cmds );
   }

   public String showTotalSessionTime( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      return "Total time spent: " + millisToTimeStr( userX.lifetimeSessionTime() );
   } 

   public String showCurrentSessionTime( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      return "Current session time: " + millisToTimeStr( userX.sessionTime() );
   }

   public String showMessageCount( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      return "Total number of messages: " + userX.lifetimeMessageCount();
   }

   public String showRecordSessionTime( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      return "Longest session time for " + user.getName() + ": "
             + millisToTimeStr( userX.recordSessionTime() );
   }

   public String showUserEpoch( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      return user.getName() + " has existed for: " + millisToTimeStr( userX.age() );
   }

   private String millisToTimeStr( long millis ) {
      long second = (millis / 1000) % 60; 
      long minute = (millis / (1000 * 60)) % 60; 
      long hour = (millis / (1000 * 60 * 60)) % 24; 
      long day = (millis / (1000 * 60 * 60 * 24)) % 365; 
      long year = (millis / (1000 * 60 * 60 * 24 * 365));

      String res = "session time: ";
      if( year == 0 ) {
         return res + String.format( "%02d years %03d days %02d hours %02d minutes %02d seconds",
                                     year, day, hour, minute, second );
      } else if( day == 0 ) {
         return res + String.format( "%03d days %02d hours %02d minutes %02d seconds",
                                     day, hour, minute, second );
      } else if( hour == 0 ) {
         return res + String.format( "%02d hours %02d minutes %02d seconds",
                                     hour, minute, second );
      } else if( minute == 0 ) {
         return res + String.format( "%02d minutes %02d seconds", minute, second );
      } else {
         return res + String.format( "%02d seconds", second );
      }
   }

}
