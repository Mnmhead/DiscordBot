// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import main.BotMain;
import java.time.Instant;
import sx.blah.discord.handle.obj.IChannel;

/*
 * This class represents a mute command.
 */
public class Mute extends BotCommand {
	
	/*
	 * Constructor for the Mute command.
	 */
	public Mute() {
		description = "suppresses //tts and speech output";
		name = "mute";
		maxNumArgs = 0;
	}
	
	public void doCmd( IChannel chan, String[] parameters ) {
		if( BotMain.muted ) {
			CommandManager.sendMessage( chan, "Bot is already muted." );
			return;
		}
		Instant inst = Instant.now();
		BotMain.lastMute = inst.getEpochSecond();
		BotMain.muted = true;
	}
}
