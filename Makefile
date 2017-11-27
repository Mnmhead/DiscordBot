CLASSES = \
        BotMain.java \
        Mute.java \
		  Unmute.java \
		  MessageListener.java \
		  UserVoiceChannelListener.java \
		  BotCommand.java \
		  PokeChris.java \
		  CmdMgr.java \
        UserX.java

# one clean option: find . -name '*.class' -delete		  
#
default: classes

classes:
	mkdir -p bin
	javac -d bin -sourcepath src -cp lib/Discord4j-2.9-shaded.jar src/bot/BotRunner.java	

clean:
	rm -rf bin
