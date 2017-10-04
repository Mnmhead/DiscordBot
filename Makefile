CLASSES = \
        BotMain.java \
        Mute.java \
		  Unmute.java \
		  MessageListener.java \
		  UserVoiceChannelListener.java \
		  BotCommand.java \
		  PokeChris.java \
		  CommandManager.java
		  
default: classes

classes: $(CLASSES:.java=.class)
	javac -d bin -sourcepath src -cp lib/Discord4j-2.9-shaded.jar src/main/BotMain.java	

clean:
	rm -rf *.class
