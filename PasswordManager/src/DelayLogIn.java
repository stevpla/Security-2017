/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LU$er
 */
public class DelayLogIn extends Thread
{
    private int delayCounter = 0 ;
    private int temp = 0 ;
    
    
    public DelayLogIn ( int delayCounter) 
    {
        if ( delayCounter == 1 ) temp = 4000 ;
        if ( delayCounter == 2 ) temp = 8000 ;
        if ( delayCounter == 3 ) temp = 12000 ;
        if ( delayCounter == 4 ) temp = 16000 ;
        if ( delayCounter == 5 ) temp = 20000 ;
        if ( delayCounter == 6 ) temp = 24000 ;
        if ( delayCounter == 7 ) temp = 28000 ;
        if ( delayCounter == 8 ) temp = 32000 ;
        if ( delayCounter == 9 ) temp = 3600 ;
        if ( delayCounter == 10 ) temp = 40000 ;
        this.start ( ) ;
    }
    
    
    public void run ( )
    {
        while ( LogInMenu.flagDelayA == true )
        {
            long a = System.currentTimeMillis ( ) ;
            long b = System.currentTimeMillis ( ) ;
            while ( (b-a)< temp )
            {
                b = System.currentTimeMillis ( ) ; 
            }
            //make flag false again, User can try again
            LogInMenu.flagDelayA = false ;
        }
    }
}
