package commands;

import sx.blah.discord.handle.obj.IChannel;

public class BasicOutputCommand extends BotCommand {
    private String output;

    public BasicOutputCommand(String name, String description, String output) {
        this.name = name;
        this.description = description;
        this.output = output;
        this.maxNumArgs = 0; 
    }

    public void doCmd( IChannel chan, String[] parameters ) {
        CommandManager.sendMessage( chan, output ); 
    }
}
