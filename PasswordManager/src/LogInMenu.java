
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class LogInMenu extends JFrame implements ActionListener
{
    private JLabel Label_UserName, Label_Password, Wrong_Password, Wrong_User_Name ;
    private JTextField Field_UserName ;
    private JPasswordField Field_Password ;
    private JButton LOG_IN, CLEAR ;
    private String IDENTIFIER_FINAL = null ;
    private int FAIL_COUNTER = 0 ;
    private static String PATHNAME_USER = null ;
    
     //Dhmiourgos
    public LogInMenu ( String PARAMETER_IDENTIFIER )
    {
        super  ( "Authenticate" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;   //Close Current Window
        setSize ( 600, 350 ) ;
        setLocationRelativeTo ( null ) ;
        setVisible ( true ) ;
        
        IDENTIFIER_FINAL = PARAMETER_IDENTIFIER ;  //Save value
         //
        //https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
        //In this technique a WindowListener interface implentation is added to the frame, where the listener has a method, windowClosing(), that is called when the frame is closed.
        //In practice, on overrides the windowClosing() method of WindowAdapter, a no-op implementation of WindowListener.   This way, one doesn't have to worry about all the other methods of the WindowListener.
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                      MainMenu a = new MainMenu ( ) ;
             }
        } ); 
        //
     
        // 1st Row
        Label_UserName = new JLabel ( "Enter User name : " ) ;
        Label_UserName.setForeground ( Color.BLACK ) ;
        Label_UserName.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Label_UserName.setBounds ( 10, 20, 900, 40 ) ;
        
        Field_UserName = new JTextField ( 20 ) ;
        Field_UserName.setForeground ( Color.BLACK ) ;
        Field_UserName.setBounds ( 218, 26, 220, 27 ) ;
        
        Wrong_Password = new JLabel ( "" ) ;
        Wrong_Password.setForeground ( Color.RED ) ;
        Wrong_Password.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Wrong_Password.setBounds ( 218, 120, 180, 40 ) ;
        
        Wrong_User_Name = new JLabel ( "" ) ;
        Wrong_User_Name.setForeground ( Color.RED ) ;
        Wrong_User_Name.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Wrong_User_Name.setBounds ( 218, 40, 180, 40 ) ;
        
        // 2nd row
        Label_Password = new JLabel ( " Enter password : " ) ;
        Label_Password.setForeground ( Color.BLACK ) ;
        Label_Password.setFont  (new Font ( "Courier New", Font.BOLD, 20 ) ) ;
        Label_Password.setBounds ( 10, 100, 900, 40 ) ;
        
        Field_Password = new JPasswordField ( 20 ) ;
        Field_Password.setForeground ( Color.BLACK ) ;
        Field_Password.setBounds ( 218, 106, 220, 27 ) ;
        
        // 3rd row
        LOG_IN = new JButton ( " Log in " ) ;
        LOG_IN.setForeground ( Color.BLACK ) ;
        LOG_IN.setBounds ( 150, 200, 140, 40 ) ;
        
        CLEAR = new JButton ( " Clear " ) ;
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 300, 200, 140, 40 ) ;
        
        // Manager Layout ΝΟ
        Container pane = getContentPane ( ) ;
        
        pane.setLayout ( null ) ;
        
        // Additions
        pane.add ( Label_UserName ) ;
        pane.add ( Label_Password ) ;
        pane.add ( Field_UserName ) ;
        pane.add ( Field_Password ) ;
        pane.add ( LOG_IN ) ;
        pane.add ( CLEAR ) ;
        pane.add ( Wrong_Password ) ;
        pane.add ( Wrong_User_Name ) ;

        setContentPane ( pane ) ;
        LOG_IN.addActionListener ( this ) ;
        CLEAR.addActionListener ( this ) ;
    }  //Telos dhmioyrgoy
    
    
    
    
    public static String FIND_PATHNAME ( )
    {
        return PATHNAME_USER ;
    }
    
    
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
      
       
        
        
        if ( source == CLEAR )
        {
            Field_UserName.setText ( null ) ;
            Field_Password.setText ( null ) ;
            Wrong_Password.setText ( null ) ;
            Wrong_User_Name.setText ( null ) ;
        }   // End of Clear-Action  
    }   // End of actionPerformed
} 
    
    

    

  
