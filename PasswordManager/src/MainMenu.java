
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.HashMap;
import javax.crypto.SealedObject;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;




public class MainMenu extends JFrame implements ActionListener
{
    
    
    //Components
    private JButton ADDPASSWORD, DISPLAYHIDE, PASSWORDDELETE, PASSWORDMODIFY, SIGNUP, LOGIN, LOGOUT ;
    private JLabel USER_LABEL ;
    private static JLabel IMAGE_LABEL ; //Mas eikozizei to onoma tou xrhsth pou einai LOG ON
    private static JLabel USER_NAME  = new JLabel ( "-" ) ;  //Replace by User Name
    private KeyPairGenerator OBJECT_KEY = null ;
    private KeyPair KEY_PAIR = null ;
    public static PrivateKey PRIVATE_KEY = null ;
    private PublicKey PUBLIC_KEY = null ;
    private static boolean CHECK_LOG_FLAG = false  ; //For authentication
    private FileOutputStream OUTPUT_STREAM = null ;
    private ObjectOutputStream OUTPUT_STREAM_2 = null ;
    public static PrivateKey  PRIVATE_KEY_APP = null ;
    private String IDENTIFIER [ ] = null ;
    private FileInputStream INPUT_STREAM = null ;
    private ObjectInputStream INPUT_STREAM_2 = null ;
    private Signature Digital_Sign = null ;
    private MessageDigest Message_Digest = null ;
    private byte [ ] File_Bytes = new byte [ 1024 ], MY_BYTES = null, TOTAL_HASH_FROM_3HASHES = null ;
    private HashMap < String, byte [ ] > HASH_MAP = null ;
    private JLabel Sign_In , Info;
  
 
    
    
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
        
        //
        IDENTIFIER = new String [ ] { "IFR", "IFE","MFR","MFE","SMR" } ;
        
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
        SIGNUP.setBounds ( 500, 430, 180, 50 ) ;
        
        LOGIN = new JButton ( "Log In" ) ;
        LOGIN.setForeground ( Color.BLACK ) ;
        LOGIN.setBounds ( 300, 430, 180, 50 ) ;
        
    
        //=======
        
        //Labels
        USER_LABEL = new JLabel ( "User : " ) ;
        USER_LABEL.setForeground ( Color.DARK_GRAY ) ;
        USER_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        USER_LABEL.setBounds ( 20, 05, 900, 40 ) ;
        
        USER_NAME.setForeground ( Color.DARK_GRAY ) ;
        USER_NAME.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        USER_NAME.setBounds ( 105, 05, 550, 40 ) ;  
        
        
        IMAGE_LABEL = new JLabel ( ) ;
        IMAGE_LABEL.setIcon ( new ImageIcon ( this .getClass ( ). getResource ("pm.jpg") ) ) ;
        IMAGE_LABEL.setBounds ( 182, 10, 700, 345 ) ; 
        
        //Prosthetontas ta koympia
        ADDPASSWORD.addActionListener ( this ) ;
        DISPLAYHIDE.addActionListener ( this ) ;
        PASSWORDDELETE.addActionListener ( this ) ;
        PASSWORDMODIFY.addActionListener ( this ) ;
        SIGNUP.addActionListener ( this ) ;
        LOGIN.addActionListener ( this ) ;
        LOGOUT.addActionListener ( this ) ;
        
        Container pane = getContentPane ( ) ;  //Diaxeiristis
        pane.setLayout ( null ) ;   //Deactivate Manager Layout
    
        //
        pane.add ( ADDPASSWORD ) ;
        pane.add ( DISPLAYHIDE ) ;
        pane.add ( PASSWORDDELETE ) ;
        pane.add ( PASSWORDMODIFY ) ;
        pane.add ( LOGOUT ) ;
        pane.add ( SIGNUP ) ;
        pane.add ( LOGIN ) ;
        pane.add ( IMAGE_LABEL ) ;
        pane.add ( USER_LABEL ) ;
        
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
            
        
     }//Telos methodou ACTION
    
    
  
    
    
    
}//END CLASS
        
        
  
    


    

