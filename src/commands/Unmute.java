// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.BotInstance;
import java.time.Instant;
import java.util.List;
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
		
		public void doCmd( BotInstance bot, IChannel chan, List<String> parameters  ) {
			if( !bot.isMuted() ) {
            // bot is alreadu unmuted, no-op
				bot.cmdMgr.sendMessage( chan, "Bot is already unmuted.");
				return; 
			}
			Instant inst = Instant.now();
			long curSec = inst.getEpochSecond();
			long timeSinceLastMute = curSec - bot.getLastMute();
			if(  timeSinceLastMute >= unmuteTimer*60 ) {
				// timer has expired, its ok to unmute
				bot.unmute();
			} else {
				long wait = unmuteTimer*60 - timeSinceLastMute;
				bot.cmdMgr.sendMessage( chan, "Sorry you must wait " + wait + " more seconds to unmute." );
			}
		}
}
