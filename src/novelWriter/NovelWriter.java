// Copyright Gyorgy Wyatt Muntean 2017
package novelWriter;

import java.util.Random;
import bot.BotInstance;
import java.util.ArrayList;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

/*
 * This class 
 */

// lets design some code to facillitate the writing of a novel.
// how should things work...
//
// there should be a collection of users who are currently participating in the novel
// maybe there should be a set of commands existing within the novel writer...
// Should I make a subCmdMgr? One that more complex command-based entities can use?
//
// for now, lets go with the following:
// 1. command to register a user as a participator in the writing of the novel
// 2. command to ask who is the next contributor
// 3. command to write the next line in the novel
//    a. this will give you an error message if you are not the next participator
//    b. otherwise this will write the line to the file and advance to the next participator
//       - a line cannot be greater than 140 characters? whatever a tweet is?
// 4. 
//
// list of nouns, adjectives. Randomly generate a theme based on these noun and
// adjectives.
public class NovelWriter {

   private static final String NOVEL_FILE = "xxx.txt";

   // novel writer fixed fields
   private BotInstance bot;
   private CmdGroup cmds;

   // novel writer state
   private File novel;
   private ArrayList<IUser> contributors;
   private IUser nextContributor;

	/*
	 * Constructor for a NovelWriter 
	 */
	public NovelWriter( BotInstance bot ) {
      // initial setup
      this.novel = new File( NOVEL_FILE );
      this.contributors = new ArrayList<IUser>();
      this.bot = bot;

      // instantiate commands associated with the NovelWriter
      // and register them with the bot's command manager
      this.cmds = new CmdGroup();
      cmds.addBotCommand( new Register() );
      cmds.addBotCommand( new NextContributor() );
      cmds.addBotCommand( new AddSentence() );
      bot.cmdMgr.registerCmdGroup( cmds );

      // the first contributor is null
      this.nextContributor = null;
	}

   /*
    *
    */
   public void writeSentence( String sentence ) {
      // 1. write sentence to next line in file
      // 2. advance to the next contributor
      
      return;
   } 

   /*
    *
    */
   public void registerContributer( IUser user ) {
      if( user == null ) {
         return;
      }
      contributors.add( user );
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

}
