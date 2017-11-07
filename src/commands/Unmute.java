// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.BotMain;
import java.time.Instant;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This calss represnts the unmute command.
 */
public class Unmute extends BotCommand {
		private static final int unmuteTimer = 1;  // number of minutes until unmute is possible
	
		/*
		 * Constructor for unmute command.
		 */
		public Unmute() {
			description = "unsuppresses bot //tts and speech output";
			name = "unmute";
			maxNumArgs = 0;
		}
		
		public void doCmd( IChannel chan, String[] parameters  ) {
			if( !BotMain.muted ) {
            // bot is alreadu unmuted, no-op
				CommandManager.sendMessage( chan, "Bot is already unmuted.");
				return; 
			}
			Instant inst = Instant.now();
			long curSec = inst.getEpochSecond();
			long timeSinceLastMute = curSec - BotMain.lastMute;
			if(  timeSinceLastMute >= unmuteTimer*60 ) {
				// timer has expired, its ok to unmute
				BotMain.muted = false;
			} else {
				long wait = unmuteTimer*60 - timeSinceLastMute;
				CommandManager.sendMessage( chan, "Sorry you must wait " + wait + " more seconds to unmute." );
			}
		}
}
