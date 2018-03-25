// Copyright Gyorgy Wyatt Muntean 2018
package debug;

import commands.*;
import sx.blah.discord.handle.obj.IChannel;

/*
 * A singleton class that doesn't really need to be created.
 * Only useful method is debugPrint which prints to stdout when
 * debug flag is set.
 */
public class DebugUtil {
      
   private static final boolean DEBUG_FLAG = true;
   private static DebugUtil singleton;

   private DebugUtil() {
      super();
   }

   public DebugUtil Debug() {
      if( singleton != null ) {
         return singleton;
      } else {
         singleton = new DebugUtil();
         return singleton;
      }
   }

   public static void DEBUG( String s ) {
      if( DEBUG_FLAG ) {
         System.out.println( s );
      }
   }

}
