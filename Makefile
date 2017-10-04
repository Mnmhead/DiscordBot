CLASSES = \
        BotMain.java \
        Mute.java \
		  Unmute.java \
		  MessageListener.java \
		  UserVoiceChannelListener.java \
		  BotCommand.java \
		  PokeChris.java \
		  CommandManager.java

# one clean option: find . -name '*.class' -delete		  
#
default: classes

classes:
	javac -d bin -sourcepath src -cp lib/Discord4j-2.9-shaded.jar src/main/BotMain.java	

clean:
	rm -rf bin
	mkdir bin
