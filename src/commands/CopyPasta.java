// Copyright Gyorgy Wyatt Muntean 2017
package commands;

import bot.BotInstance;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import sx.blah.discord.handle.obj.IChannel;

/*
 *
 */
public class CopyPasta extends BotCommand {

   // This is the directory in which the raw copypasta textfiles are stored
   private static final String COPY_PASTA_DIR = "copypastas";
	
	/*
	 * Constructor for the CopyPasta command.
	 */
	public CopyPasta() {
		description = "calls upon the lord of copy pasterino";
		name = "copypasta";
	}
	
	public void doCmd( BotInstance bot, IChannel chan, List<String> parameters ) {
      
      String copyPasta = getRandFileName( COPY_PASTA_DIR );
      copyPasta = COPY_PASTA_DIR + "/" + copyPasta;
      
      if( copyPasta == null ) {
         return;
      }
      String content = readFile( copyPasta );
      if( content == null ) {
         return;
      }

	   bot.cmdMgr.sendMessage( chan, content );
	}
     
   /*
    *
    */ 
   private String getRandFileName( String fileName ) {
      File dir = new File( fileName );
      if( dir == null ) {
         return null;
      }
      File[] files = dir.listFiles();
      ArrayList<String> copyPastas = new ArrayList<String>(); 
      
      for( int i = 0; i < files.length; i++ ) {
         File curFile = files[ i ];
         if( curFile.isFile() ) {
            copyPastas.add( curFile.getName() );
         }
      }
      
      // generate a random index and return file name from that index
      int randIndex = new Random().nextInt( copyPastas.size() );
      return copyPastas.get( randIndex );
   }

   /*
    * Reads the contents of the passed in file and returns them
    * as a String.
    */
   private String readFile( String fileName ) {
      File file = null;
      BufferedReader br = null;
      try {
         file = new File( fileName );
         br = new BufferedReader( new FileReader( file ) );

         String copyP = "";
         String cur;
         while( ( cur = br.readLine() ) != null ) {
            copyP += cur;
         }

         return copyP;
      } catch( IOException e ) {
         System.err.println( "Could not read line of file: " + fileName + "." );
         return null;
      }
   }
}
