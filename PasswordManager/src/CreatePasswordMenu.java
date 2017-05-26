

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;




public class CreatePasswordMenu  extends JFrame implements ActionListener
{
    
    private JLabel LDomain, JUsername, Jpassword, Jcomment ;
    private JTextField TDomain, TUsername ;
    private JTextArea Tcomment ;
    private JPasswordField Tpassword ;
    private JButton BSavePass, CLEAR ;
    private Cipher CIPHER = null ;
    
    
    //Constructor
    public CreatePasswordMenu ( )
    {
        super ( "Create Password" ) ;
       
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                 MainMenu mm = new MainMenu ( ) ; 
             }
        } ) ;
        //Windows Closing
        setSize ( 800, 650 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        
        
        // 
        LDomain = new JLabel ( "Domain :" ) ;
        LDomain.setForeground ( Color.BLACK ) ;
        LDomain.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        LDomain.setBounds ( 35, 80, 400, 40 ) ;
        
        TDomain = new JTextField ( 40 ) ;
        TDomain.setForeground ( Color.BLACK ) ;
        TDomain.setBounds ( 150, 85, 300, 33 ) ;
        
        // 
        JUsername = new JLabel ( "UserName : " ) ;
        JUsername.setForeground ( Color.BLACK ) ;
        JUsername.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        JUsername.setBounds ( 35, 140, 450, 40 ) ;
        
        TUsername = new JTextField ( 20 ) ;
        TUsername.setForeground ( Color.BLACK ) ;
        TUsername.setBounds ( 180, 147, 300, 27 ) ;
        
        //
        Jpassword = new JLabel ( "Password :" ) ;
        Jpassword.setForeground ( Color.BLACK ) ;
        Jpassword.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        Jpassword.setBounds ( 35, 200, 400, 40 ) ;
        
        Tpassword = new JPasswordField ( 20 ) ;
        Tpassword.setForeground ( Color.BLACK ) ;
        Tpassword.setBounds ( 190, 210, 220, 27 ) ;
        
        Jcomment = new JLabel ( "Comment : " ) ;
        Jcomment.setForeground ( Color.BLACK ) ;
        Jcomment.setFont  (new Font ( "Courier New", Font.BOLD, 23 ) ) ;
        Jcomment.setBounds ( 35, 260, 400, 40 ) ;
        
        Tcomment = new JTextArea ( 10,20 ) ;
        Tcomment.setForeground ( Color.BLACK ) ;
        Tcomment.setBounds ( 210, 270, 400, 150 ) ;
        
       
        //Buttons
        BSavePass = new JButton ( "Save_Password" )  ;
        BSavePass.setForeground ( Color.BLACK ) ;
        BSavePass.setBounds ( 100, 500, 200, 40 ) ;

        CLEAR = new JButton ( "Clear" ) ;   // Just go previous Window
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 540, 500, 200, 40 ) ;
        
        // Manager Layout
        Container pane = getContentPane ( ) ;
        pane.setLayout ( null ) ;
        
        BSavePass.addActionListener ( this ) ;
        CLEAR.addActionListener ( this ) ;
        // Additions Components
        pane.add ( LDomain ) ;
        pane.add ( JUsername ) ;
        pane.add ( Jpassword ) ;
        pane.add ( CLEAR ) ;
        pane.add ( Jcomment ) ;
        pane.add ( TDomain ) ;
        pane.add ( TUsername ) ;
        pane.add ( CLEAR ) ;
        pane.add ( Tpassword ) ;
        pane.add ( Tcomment ) ;
        pane.add ( BSavePass ) ;
        setContentPane ( pane ) ;
    } // End of Constructor
    
    
    
    
    //Method Actions
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        
        if ( source == CLEAR )
        {
            TDomain.setText ( "" ) ;
            Tcomment.setText ( "" ) ;
            Tpassword.setText ( "" ) ;
            TUsername.setText ( "" ) ;
        }
        
        if ( source == BSavePass)
        { System.out.println ( "yolosss") ;
            if ( ! ( TDomain.getText ( ).equals ( "" ) ) )
            {
                if ( ! ( Tpassword.getText ( ).equals ( "" ) ) )
                {
                    if ( ! ( Tcomment.getText ( ).equals ( "" ) ) )
                    {
                        if ( ! ( TUsername.getText ( ).equals ( "" ) ) )
                        {
                            String pass = Tpassword.getText ( ) ;
                            PasswordRecord Pr = new PasswordRecord ( TUsername.getText ( ), Tcomment.getText ( ), TDomain.getText ( ), pass ) ;
                            byte [ ] temp = LogInMenu.SymmetricKey ;
                            SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                            try
                            {
                              CIPHER = null ;
                              CIPHER = Cipher.getInstance ( "AES" ) ;
                              CIPHER.init ( Cipher.ENCRYPT_MODE, key2 ) ;      //ENCRYPT_MODE MODE static inT ,Constant used to initialize cipher to ENCRYPT_MODE mode.
                              //What is SealedObject?
                              //SealedObject encapsulate the original java object(it should implements Serializable). It use the cryptographic algorithm to seals the serialized content of the object.
                              SealedObject SEALEDOBJECT = new SealedObject ( Pr, CIPHER ) ;
                              FileOutputStream f = new FileOutputStream ( ( "USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" ), true ) ;
                              ObjectOutputStream o = new ObjectOutputStream ( f ) ;
                              // Write objects to file
                              o.writeObject ( SEALEDOBJECT ) ;
                              o.close ( ) ;
                              //
                              this.dispose ( ) ;
                              MainMenu mm = new MainMenu ( ) ;
                            }
                            catch ( NoSuchAlgorithmException NSAE )
                            {
                                JOptionPane.showMessageDialog ( rootPane,"CR - NoSuchAlgorithmException","Exception ",JOptionPane.WARNING_MESSAGE ) ;
                            }
                            catch ( NoSuchPaddingException NSPE )
                            {
                                JOptionPane.showMessageDialog ( rootPane,"CR - NoSuchPaddingException","Exception ",JOptionPane.WARNING_MESSAGE ) ;
                            }
                            catch ( InvalidKeyException IKE )
                            {
                                JOptionPane.showMessageDialog ( rootPane,"CR - InvalidKeyException","Exception ",JOptionPane.WARNING_MESSAGE ) ;
                            }
                            catch ( IllegalBlockSizeException IBSE )
                            {
                                JOptionPane.showMessageDialog ( rootPane,"CR - IllegalBlockSizeException","Exception ",JOptionPane.WARNING_MESSAGE ) ;
                                IBSE.printStackTrace();
                            }
                            catch ( IOException IOE )
                            {
                                JOptionPane.showMessageDialog ( rootPane,"CR - IOException","Exception ",JOptionPane.WARNING_MESSAGE ) ;
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog ( rootPane,"Enter a username","Blank Field ",JOptionPane.WARNING_MESSAGE ) ;
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( rootPane,"Enter a comment","Blank Field ",JOptionPane.WARNING_MESSAGE ) ;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog ( rootPane,"Enter a password","Blank Field ",JOptionPane.WARNING_MESSAGE ) ;
                }
            }
            else
            {
                JOptionPane.showMessageDialog ( rootPane,"Enter a domain","Blank Field ",JOptionPane.WARNING_MESSAGE ) ;
            }
        }   
    }//End 
}//End of method


    

