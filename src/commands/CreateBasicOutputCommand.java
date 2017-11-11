package commands;
import main.BotMain;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;

public class CreateBasicOutputCommand extends BotCommand {

    public CreateBasicOutputCommand() {
        description = "creates a basic output command";
        maxNumArgs = 3;
        name = "create";
    }

    public void doCmd( IChannel chan, List<String> parameters  ) {
        if (parameters.size() != 3) {
        	CommandManager.sendMessage( chan, String.format("Incorrect parameters. Use %s [name] [description] [output]", this.name ) );
        	return;
        }

        if (parameters.get(0).contains(" ")) {
			CommandManager.sendMessage( chan, String.format("Command name \"%s\" contains spaces, which is not supported", parameters.get(0) ) );
			return;
		}


        BotMain.cm.addBotCommand( new BasicOutputCommand(parameters.get(0), parameters.get(1), parameters.get(2)) );
        CommandManager.sendMessage( chan, String.format("New command \"%s\" created with description \"%s\" and output \"%s\"", parameters.get(0), parameters.get(1), parameters.get(2) ) );
    }
}
