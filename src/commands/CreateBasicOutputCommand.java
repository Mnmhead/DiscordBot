package commands;
import main.BotMain;
import sx.blah.discord.handle.obj.IChannel;

public class CreateBasicOutputCommand extends BotCommand {

    public CreateBasicOutputCommand() {
        description = "creates a basic output command";
        maxNumArgs = 3;
        name = "create";
    }

    public void doCmd( IChannel chan, String[] parameters  ) {
        if (parameters.length != 3) {
        	CommandManager.sendMessage( chan, String.format("Incorrect parameters. Use %s [name] [description] [output]", this.name ) );
        	return;
        } 

        BotMain.cm.addBotCommand( new BasicOutputCommand(parameters[0], parameters[1], parameters[2]) );
        CommandManager.sendMessage( chan, String.format("New command \"%s\" created with description \"%s\" and output \"%s\"", parameters[0], parameters[1], parameters[2] ) );
    }
}
