package utils;

import sx.blah.discord.handle.obj.IUser;

/*
 * This class serves as a wrapper for a Discord IUser obejct.
 * Some extra state info is kept in this object.
 */
public class UserX {
   
   private long logInTime;
   public IUser user;

   /*
    * Constructor for a UserX object.
    */
   public UserX( IUser user ) {
      this.user = user;
      this.logInTime = System.currentTimeMillis(); 
   }

   /*
    * Gets the user's total time they have been in their
    * current session. Returns time in milliseconds.
    */
   public long getSessionTime() {
      long now = System.currentTimeMillis();
      return now - logInTime;
   }

   /*
    * Sets seesion time to now.
    */
   public void resetSession() {
      logInTime = System.currentTimeMillis();
   }

}
