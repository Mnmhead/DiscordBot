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
      cmds.addBotCommand( new ActivePercentage() );
      bot.cmdMgr.registerCmdGroup( "User Info", cmds );
   }

   public String showTotalSessionTime( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      long total = userX.lifetimeSessionTime();
      if( total == 0 ) {
         return "You must end a session first";
      } else {
         return "Total time spent: " + millisToTimeStr( total );
      }
   } 

   public String showPercentageSpentHere( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      double age = (double) userX.age();
      double totalTime = (double) userX.lifetimeSessionTime();
      double pcent = ( ( age - totalTime ) / age ) * 100;
      if( totalTime == 0 ) {
         return "You haven't spent any time here yet.";
      } else {
         return "You spend " + pcent + "% of your life here";
      }
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
      long record = userX.recordSessionTime();
      if( record == 0 ) {
         return "You must end a session first";
      } else {
         return "Longest session time for " + user.getName() + ": "
                + millisToTimeStr( userX.recordSessionTime() );
      }
   }

   public String showUserEpoch( IUser user ) {
      UserX userX = bot.getUser( user.getLongID() );
      long epoch = userX.age();
      if( epoch == 0 ) {
         return "You were just born! Congratulations!";
      } else {
         return user.getName() + " has existed for " + millisToTimeStr( userX.age() );
      }
   }

   private String millisToTimeStr( long millis ) {
      long second = (millis / 1000) % 60; 
      long minute = (millis / (1000 * 60)) % 60; 
      long hour = (millis / (1000 * 60 * 60)) % 24; 
      long day = (millis / (1000 * 60 * 60 * 24)) % 365; 
      long year = (millis / (1000 * 60 * 60 * 24 * 365));

      if( year != 0 ) {
         return year + " year(s) " +
                day + " day(s) " + 
                hour + " hour(s) " +
                minute + " minute(s) " +
                second + " second(s)";
      } else if( day != 0 ) {
         return day + " day(s) " + 
                hour + " hour(s) " +
                minute + " minute(s) " +
                second + " second(s)";
      } else if( hour != 0 ) {
         return hour + " hour(s) " +
                minute + " minute(s) " +
                second + " second(s)";
      } else if( minute != 0 ) {
         return minute + " minute(s) " +
                second + " second(s)";
      } else {
         return second + " second(s)";
      }
   }

}
