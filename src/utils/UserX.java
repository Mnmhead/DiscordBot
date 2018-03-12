package utils;

import java.util.HashMap;
import sx.blah.discord.handle.obj.IUser;

/*
 * This class serves as a wrapper for a Discord IUser obejct.
 * Some extra state info is kept in this object.
 */
public class UserX {
   
   private boolean active;
   private long loginTime;
   private long recordSessionTime;
   private long lifetimeSessionTime;
   private String lastMessage;
   private int lifetimeMessageCount;
   private long userBirth;
   private HashMap<String,Long> gameActivityMap;
   
   public IUser user;

   /*
    * Constructor for a UserX object.
    */
   public UserX( IUser user, boolean login ) {
      this.user = user;
      this.active = false;
      this.recordSessionTime = 0;
      this.lifetimeSessionTime = 0;
      this.lastMessage = "";
      this.lifetimeMessageCount = 0;
      this.userBirth = System.currentTimeMillis();

      // since we are creating this user, it must have come from somewhere, so we
      // log them in
      this.login();
   }

   /*
    *
    */
   public void login() {
      if( !active ) {
         active = true;
         loginTime = System.currentTimeMillis(); 
      }
   }

   /*
    *
    */
   public void logout() {
      if( active ) {
         active = false;
         long sessionTime = System.currentTimeMillis() - loginTime;
         updateSessionData( sessionTime );
      }
   }

   /*
    *
    */
   public void incMessageCount() {
      lifetimeMessageCount += 1;
   }


   /*
    *
    */
   public long age() {
      return System.currentTimeMillis() - userBirth;
   }


   /*
    * Gets the user's total time they have been in their
    * current session. Returns time in milliseconds.
    */
   public long sessionTime() {
      long now = System.currentTimeMillis();
      return now - loginTime;
   }

   /*
    *
    */
   public long lifetimeSessionTime() {
      return lifetimeSessionTime;
   }

   /*
    *
    */
   public long recordSessionTime() {
      return recordSessionTime;
   }

   /*
    *
    */
   public int lifetimeMessageCount() {
      return lifetimeMessageCount;
   }

   /*
    *
    */ 
   private void updateSessionData( long sessionTime ) {
      if( sessionTime > recordSessionTime ) {
         recordSessionTime = sessionTime;
      }
      
      lifetimeSessionTime += sessionTime;      
   }

}
