// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import bot.BotInstance;
import commands.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/*
 * This class 
 */
public class NovelWriter {

   private static final String PATH = "novels/";
   private static final int CHAR_LIMIT = 140;

   // novel writer fixed fields
   private BotInstance bot;
   private CmdGroup cmds;

   // novel writer state
   private String novelPath;
   private ArrayList<IUser> contributors;
   private IUser nextContributor;

	/*
	 * Constructor for a NovelWriter 
	 */
	public NovelWriter( BotInstance bot, String filename ) {
      // initial setup
      this.novelPath = PATH + filename;
      this.contributors = new ArrayList<IUser>();
      this.bot = bot;

      // instantiate commands associated with the NovelWriter
      // and register them with the bot's command manager
      this.cmds = new CmdGroup();
      cmds.addBotCommand( new RegisterUser() );
      cmds.addBotCommand( new NextContributor() );
      cmds.addBotCommand( new AddSentence() );
      bot.cmdMgr.registerCmdGroup( cmds );

      // the first contributor is null
      this.nextContributor = null;
	}

   /*
    *
    */
   public boolean writeSentence( String sentence ) {
      if( sentence.length() > CHAR_LIMIT ) {
         return false;
      }

      try {
         BufferedWriter bw = new BufferedWriter( new FileWriter( novelPath, true ) );
    
         bw.append( sentence );
         bw.newLine();
         bw.close();

         advanceNextContributor();
      } catch( IOException e ) {
         System.err.println( "Failed to write to file: " + novelPath );
         return false;
      }

      return true;
   } 

   /*
    *
    */
   public String registerContributer( IUser user ) {
      if( user == null ) {
         return "Error, user does not exist?";
      }

      String success = "Hello " + user.getName() + ", you are now a contributor.";
      
      // if no-one is registered yet, set the next contributor
      if( contributors.isEmpty() ) {
         contributors.add( user );
         nextContributor = user;
         return success;
      }

      if( contributors.contains( user ) ) {
         return user.getName() + ", you are already registered.";
      }

      contributors.add( user );
      return success;
   } 

   /*
    * 
    */
   public IUser getNextContributor() {
      return nextContributor;
   } 

   /*
    * Randomly chooses the next contributor from the list
    * of registered contributors
    */
   private void advanceNextContributor() {
      int randIndex = new Random().nextInt( contributors.size() );
      nextContributor = contributors.get( randIndex );
   }

   private void printContributors() {
      for( int i = 0; i < contributors.size(); i++ ) {
         System.out.println( contributors.get( i ).getName() );
      }
   }
   
}
