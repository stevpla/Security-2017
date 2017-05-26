
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;




public class MainMenu extends JFrame implements ActionListener
{
    
    
    //Components
    private JButton ADDPASSWORD, DISPLAYHIDE, PASSWORDDELETE, PASSWORDMODIFY, SIGNUP, LOGOUT ;
    private JLabel USER_LABEL ;
    private static JLabel IMAGE_LABEL ; //Mas eikozizei to onoma tou xrhsth pou einai LOG ON
    private static JLabel USER_NAME  = new JLabel ( "-" ) ;  //Replace by User Name
    private static boolean CHECK_LOG_FLAG = false  ; //For authentication
    public static PrivateKey  PRIVATE_KEY_APP = null ;
    private static String [ ] IDENTIFIER = null ;
    
    
    //Default Constructor
    public MainMenu ( )  
    {
        super ( "Welcome to PasswordManager" ) ;
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE ) ;      //If you press X the red one icon then terminate this Window
        //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                       int Answer = JOptionPane.showConfirmDialog ( rootPane,  "Are you sure to close this window?", "Sure for Exit ?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE ) ;
                               if ( Answer == JOptionPane.YES_OPTION)
                               {  
                                   //Now Check for Revenue FILE and later for the Expenses File and then Exit..but the signatures keep them in a file
                                   System.exit( 1 ) ;
                               }
                               else
                               {
                                    setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE ) ;
                               }
             }
        } ); 
        //
        
        setSize ( 1000, 650 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;

        
        //Initialize Components
        //Buttons
        ADDPASSWORD = new JButton ( "Add password" ) ;
        ADDPASSWORD.setForeground ( Color.BLACK ) ;
        ADDPASSWORD.setBounds ( 100, 370, 180, 50 ) ;
        
        DISPLAYHIDE = new JButton ( "Display/Hide" ) ;
        DISPLAYHIDE.setForeground ( Color.BLACK ) ;
        DISPLAYHIDE.setBounds ( 300, 370, 180, 50 ) ;
        
        PASSWORDMODIFY = new JButton ( "Password Modification" ) ;
        PASSWORDMODIFY.setForeground ( Color.BLACK ) ;
        PASSWORDMODIFY.setBounds ( 500, 370, 180, 50 ) ;
        
        PASSWORDDELETE = new JButton ( "Password Delete" ) ;
        PASSWORDDELETE.setForeground ( Color.BLACK ) ;
        PASSWORDDELETE.setBounds ( 700, 370, 180, 50 ) ;
        
        LOGOUT = new JButton ( "Logout" ) ;
        LOGOUT.setForeground ( Color.RED ) ;
        LOGOUT.setBounds ( 830, 10, 130, 30 ) ;
        
        SIGNUP = new JButton ( "Sign Up" ) ;
        SIGNUP.setForeground ( Color.BLACK ) ;
        SIGNUP.setBounds ( 400, 430, 180, 50 ) ;
    
        //=======
        
        //Labels
        USER_LABEL = new JLabel ( "User :" ) ;
        USER_LABEL.setForeground ( Color.DARK_GRAY ) ;
        USER_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        USER_LABEL.setBounds ( 20, 05, 150, 40 ) ;
        
        USER_NAME.setForeground ( Color.BLUE ) ;
        USER_NAME.setFont  (new Font ( "Courier New", Font.BOLD, 18 ) ) ;
        USER_NAME.setBounds ( 84, 04, 110, 45 ) ;  
        
        
        IMAGE_LABEL = new JLabel ( ) ;
        IMAGE_LABEL.setIcon ( new ImageIcon ( this .getClass ( ). getResource ("pm.jpg") ) ) ;
        IMAGE_LABEL.setBounds ( 182, 10, 700, 345 ) ; 
        
        //
        ADDPASSWORD.addActionListener ( this ) ;
        DISPLAYHIDE.addActionListener ( this ) ;
        PASSWORDDELETE.addActionListener ( this ) ;
        PASSWORDMODIFY.addActionListener ( this ) ;
        SIGNUP.addActionListener ( this ) ;
        LOGOUT.addActionListener ( this ) ;
        
        IDENTIFIER = new String [ ] { "CR", "DSP","MOD","DEL" } ;
        
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
        //
        pane.add ( ADDPASSWORD ) ;
        pane.add ( DISPLAYHIDE ) ;
        pane.add ( PASSWORDDELETE ) ;
        pane.add ( PASSWORDMODIFY ) ;
        pane.add ( LOGOUT ) ;
        pane.add ( SIGNUP ) ;
        pane.add ( IMAGE_LABEL ) ;
        pane.add ( USER_LABEL ) ;
        pane.add ( USER_NAME ) ;
        
        setContentPane ( pane ) ;
        
    } //End of Constructor
    
    //Static method because we change a static variable, and notes the name of the USER which is LOG ON at the current moment
    public static void Note_User ( String x )
    {
        USER_NAME.setText ( "" ) ;
        USER_NAME.setText ( " " + x ) ;
    }
    
    //In Authentication Window, when a user loged on then the flag turns in true
    public static void Set_CHECK_LOG_FLAG ( )
    {
        CHECK_LOG_FLAG = true ;
    }
    
    // Actions
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( SIGNUP == source )
        {
            this.dispose();
            SignUpMenu SUM = new SignUpMenu ( ) ;
        }
        
        if ( ADDPASSWORD == source )
        {
            this.dispose ( ) ;
            if ( CHECK_LOG_FLAG == false )
            {
                LogInMenu AW = new LogInMenu ( IDENTIFIER [ 0 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                CreatePasswordMenu cpm = new CreatePasswordMenu ( ) ;
            }
        }
        
        if ( DISPLAYHIDE == source )
        {
            this.dispose ( ) ;
            if ( CHECK_LOG_FLAG == false )
            {
                LogInMenu AW = new LogInMenu ( IDENTIFIER [ 1 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                try 
                {
                    DisplayHideMenu p = new DisplayHideMenu ( ) ;
                } 
                catch (NoSuchAlgorithmException ex) 
                {
                } 
                catch (NoSuchPaddingException ex) 
                {
                } 
                catch (InvalidKeyException ex) 
                {
                } 
                catch (IllegalBlockSizeException ex) 
                {
                } 
                catch (BadPaddingException ex) 
                {
                }
            }
        }
        
        if ( PASSWORDMODIFY == source )
        {
            this.dispose ( ) ;
            if ( CHECK_LOG_FLAG == false )
            {
                LogInMenu AW = new LogInMenu ( IDENTIFIER [ 2 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                //Insert_Financial_Expenses_Window IFE = new Insert_Financial_Expenses_Window ( ) ;
            }
        }
        
        if ( PASSWORDDELETE == source )
        {
            this.dispose ( ) ;
            if ( CHECK_LOG_FLAG == false )
            {
                LogInMenu AW = new LogInMenu ( IDENTIFIER [ 3 ] ) ;
            }//You alread logged on, and app <remember you>
            else  
            {
                try 
                {
                    DeletePasswordMenu dpm = new DeletePasswordMenu ( ) ;
                } 
                catch (NoSuchAlgorithmException ex) 
                {
                } 
                catch (NoSuchPaddingException ex) 
                {
                } 
                catch (InvalidKeyException ex) 
                {
                } 
                catch (IllegalBlockSizeException ex) 
                {
                }
                catch (BadPaddingException ex) 
                {
                }
            }
        }
        
        if ( LOGOUT == source)
        {
            if ( CHECK_LOG_FLAG == false )
            {
                JOptionPane.showMessageDialog ( null, "There is no Log On User Right Now..", "Info", JOptionPane.INFORMATION_MESSAGE, null ) ;
            }
            else
            {
                ( LogInMenu.SymmetricKey ) = null ;
               //Call method that signatures
               //Total_FILES_Digital_Signature ( ) ;
               CHECK_LOG_FLAG = false ;  //Make it false so, authentication required, next time a user wants to do an operation
               USER_NAME.setText ( "" ) ;  //Clear name because you logouted
               JOptionPane.showMessageDialog ( null, "Successfull Logout", "Logout", JOptionPane.INFORMATION_MESSAGE, null ) ;
            }
        }
     }//Telos methodou ACTION
}//END CLASS
        
        
  
    


    

