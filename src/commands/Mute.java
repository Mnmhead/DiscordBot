// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.BotInstance;
import java.time.Instant;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a mute command.
 */
public class Mute extends BotCommand {
	
	/*
	 * Constructor for the Mute command.
	 */
	public Mute() {
		description = "suppresses text-to-speech output";
		name = "mute";
	}
	
	public void doCmd() {
		if( bot.isMuted() ) {
			bot.cmdMgr.sendMessage( chan, "Bot is already muted." );
			return;
		}
		bot.setLastMute( Instant.now().getEpochSecond() );
		bot.mute();
	}
}
