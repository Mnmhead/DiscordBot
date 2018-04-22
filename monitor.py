#!/usr/bin/env python
# Author: Gyorgy Wyatt Muntean
# Wrapper script for the bot.  Runs the bot in a subprocess and pipes both
# stdout and stderr to a log file.  If the bot crashes, it is restarted by 
# this script after printing a custom message to the log file.
# This script limits the number of crashes allowed before giving up.
import subprocess
import os, sys, time

base_filename_ = 'bot.log'
maxcrash_ = 10

def startProcess( num ):
   filename = base_filename_
   logfile = None
   if( os.path.exists( filename ) ):
      logfile = open( filename, 'a' ) 
   else:
      logfile = open( filename, 'w' )
   return ( subprocess.Popen( [ 'java', 'Fail' ], stdout=logfile, stderr=logfile ),
            logfile )

def cleanup( logfile ):
   logfile.close() 

def monitor():
   procCount = 0
   ( p, log ) = startProcess( procCount )
   while( 1 ):
      if( p.poll() is None ):
         # keep looping, everything is running smoothly
         continue
      else:
         # program crashed
         ret = p.returncode
         log.write( "PROGRAM CRASH ITERATION %s, RETURNCODE %s\n" % ( procCount, ret ) )
         if( procCount >= maxcrash_ ):
            log.write( "CRASH LIMIT %s REACHED, GIVING UP\n" % maxcrash_ )
            sys.exit( 0 )

         # restart the process
         cleanup( log )
         #time.sleep( 10 )
         procCount += 1
         ( p, log ) = startProcess( procCount )

         
if __name__ == '__main__':
   monitor()
