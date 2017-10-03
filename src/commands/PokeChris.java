// Copyright Gyorgy Wyatt Muntean 2017
package main;

import sx.blah.discord.handle.obj.IChannel;

public class PokeChris extends BotCommand {

	/*
	 * Constructor for a PokeChris command.
	 */
	public PokeChris() {
		description = "pokes Chris";
		maxNumArgs = 0;
		name = "poke_chris";
	}
	
	public void doCmd( IChannel chan ) {
		CommandManager.sendMessage( chan, "Hey Christopher!...Chris responds, \"YEAH!!???!\"" );
	}

}
