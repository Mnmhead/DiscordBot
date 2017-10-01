// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import java.time.Instant;

import Discord.Main.BotMain;
import sx.blah.discord.handle.obj.IChannel;

public class Mute extends BotCommand {
	
	/*
	 * Constructor for the Mute command.
	 */
	public Mute() {
		description = "suppresses TTS and speach output";
		name = "mute";
		maxNumArgs = 0;
	}
	
	public void doCmd( IChannel chan ) {
		if( BotMain.muted ) {
			CommandManager.sendMessage( chan, "Bot is already muted." );
			return;
		}
		Instant inst = Instant.now();
		BotMain.lastMute = inst.getEpochSecond();
		BotMain.muted = true;
	}
}
