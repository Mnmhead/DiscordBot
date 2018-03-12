// Copyright Gyorgy Wyatt Muntean 2018
package debug;

import commands.*;
import sx.blah.discord.handle.obj.IChannel;

public class DebugUtils {
      
   public static boolean DEBUG = false;

   private IChannel chan;

   public DebugUtils( IChannel chan ) {
      this.chan = chan;
   }

   public void debugMessage( String s ) {
      if( DEBUG ) {
         CmdMgr.sendMessage( chan, s );
      }
   }

}
