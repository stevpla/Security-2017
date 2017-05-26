
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;



public class LogInMenu extends JFrame implements ActionListener
{
    private JLabel Label_UserName, Label_Password ;
    private JTextField Field_UserName ;
    private JPasswordField Field_Password ;
    private JButton LOG_IN, CLEAR ;
    private String IDENTIFIER_FINAL = null ;
    private int FAIL_COUNTER = 0 ;
    private static String PATHNAME_USER = null ;
    private JCheckBox Checkbox = null ;
    private File selectedFile = null ;
    public static boolean flagDelayA = false ;
    private static int banCounter = 0 ;
    public static byte [ ] SymmetricKey = null ;
     
    public LogInMenu ( String PARAMETER_IDENTIFIER )
    {
        super  ( "Authenticate" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;   
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
        
        Checkbox = new JCheckBox ( "Upload Certificate" ) ;
        Checkbox.setBounds ( 30, 140, 160, 50 ) ;
        Checkbox.addActionListener ( new ActionListener ( ) 
        {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                JCheckBox cb = ( JCheckBox ) event.getSource ( ) ;
                if ( cb.isSelected ( ) ) 
                {
                    // do something if check box is selected
                    //JFileChooser to uplaod the Certificate
                    JFileChooser fileChooser = new JFileChooser ( ) ;
                    int returnValue = fileChooser.showOpenDialog ( rootPane ) ;
                    if ( returnValue == JFileChooser.APPROVE_OPTION ) 
                    {
                         selectedFile = fileChooser.getSelectedFile ( ) ;
                    } 
                    if ( returnValue == JFileChooser.CANCEL_OPTION ) 
                    {
                        Checkbox.setEnabled ( false ) ;
                    }
                }
                else 
                {
                    // check box is unselected, do something else
                }
            }
        });
        
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
        pane.add ( Checkbox ) ;

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
        
        if ( source == LOG_IN )
        {
          if ( flagDelayA == false )
          { if ( ! ( Field_UserName.getText ( ).equals ( "" ) ) )
            {
                String temp = Field_Password.getText ( ) ;
                if ( ! ( temp.equals ( "" ) ) )
                {
                    if ( Checkbox.isSelected ( ) )
                    {
                        X509Certificate cert = null ;
                        try 
                        {
                            //1st Check Certificate for validility
                            byte [] CertBytes = Files.readAllBytes ( selectedFile.toPath ( ) ) ;
                            //Open Stream to read the cert into App RAM
                            InputStream in = new ByteArrayInputStream ( CertBytes ) ;
                            CertificateFactory certFactory = CertificateFactory.getInstance ( "X.509" ) ;
                            cert = (X509Certificate)certFactory.generateCertificate ( in ) ;
                        } 
                        catch ( CertificateException CE ) 
                        {
                            //Logger.getLogger(LogInMenu.class.getName()).log(Level.SEVERE, null, CE);
                        }
                        catch (IOException ex) 
                        {
                            //Logger.getLogger(LogInMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //LOAD JKS and find Public Key
                        try
                        {
                          KeyStore YO = KeyStore.getInstance("JKS");
                          // get user password and file input stream
                          FileInputStream RE = null;
                          try 
                          {
                              RE = new FileInputStream("PM.jks");
                              YO.load(RE, "password".toCharArray() );
                          } 
                          finally 
                          {
                              if (RE != null) 
                              {
                                  RE.close();
                              }
                          }
                          //retrieve
                          X509Certificate fdr = (X509Certificate) YO.getCertificate("CertApp");
                          PublicKey pubKey = fdr.getPublicKey ( ) ;
                          //Verify Certificate
                          cert.checkValidity ( ) ;
                          cert.verify ( pubKey ) ;
                          //=====================
                          //Now check if UserName exists
                          //See if the UserName trully exists
                          boolean localFlag = false ;
                          File folder = new File ( "USERS" ) ;
                          File [ ] listOfFiles = folder.listFiles ( ) ;
                          final String userName = Field_UserName.getText ( ) ;
                          for  ( int i = 0 ; i < listOfFiles.length ; i++ ) 
                          {
                              if ( ( listOfFiles [ i ].getName ( ) ).equals ( userName ) ) 
                              {
                                  localFlag = true ;
                                  break ;
                              }
                          }
                          //
                          if ( localFlag == true )
                          {
                              //Then USERNAME is correct but lets check is pass is the right one
                              PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator ( ) ;
                              String pass = Field_Password.getText ( ) ;
                              gen.init ( pass.getBytes(), Field_UserName.getText().getBytes(), 2000 ) ;
                              byte [ ] sKey = ( ( KeyParameter ) gen.generateDerivedParameters ( 128 ) ).getKey ( ) ;   // n bits. 16 bytes -> 
                              //Produce AuthHash
                              PKCS5S2ParametersGenerator forauth = new PKCS5S2ParametersGenerator ( ) ;
                              gen.init ( sKey, pass.getBytes(), 1000 ) ;
                              byte [ ] authHash = ( ( KeyParameter ) gen.generateDerivedParameters(128) ).getKey ( );   // n bits. 16 bytes -> 
                              //=============COMPARE THEM
                              try 
                              {
                                  ObjectInputStream ois = new ObjectInputStream ( new FileInputStream ( "USERS\\" + Field_UserName.getText ( ) + "\\" + Field_UserName.getText ( ) + ".auth" ) )  ;
                                  UserNameAuthHash Unah = ( UserNameAuthHash ) ois.readObject ( ) ;
                                  String UserName = Unah.GetUserName ( ) ;
                                  byte [ ] tempByte = Unah.GetHashedValue ( ) ;
                                  //COMPARE
                                  if ( ( UserName.equals ( Field_UserName.getText ( ) ) )   &&   ( Arrays.areEqual ( authHash, tempByte ) ) )
                                  {    
                                       SymmetricKey = sKey ;
                                       String g = Field_UserName.getText ( ) ; 
                                       MainMenu.Note_User ( g ) ; //Note in Panel the name of user which is logged at currrent time
                                       MainMenu.Set_CHECK_LOG_FLAG ( ) ;  //Flag to check the authentication flag
                                       PATHNAME_USER = Field_UserName.getText ( ) ;
                                       this.dispose ( ) ;
                                       if (IDENTIFIER_FINAL.equals("CR")) 
                                       {
                                          CreatePasswordMenu OBJ = new CreatePasswordMenu ( ) ;
                                       }
                                       else if (IDENTIFIER_FINAL.equals("DSP")) 
                                       {
                                          DisplayHideMenu OBJ = new DisplayHideMenu ( ) ;
                                       }
                                       else if (IDENTIFIER_FINAL.equals("MOD")) 
                                       {
                                          //Insert_Financial_Expenses_Window OBJ = new Insert_Financial_Expenses_Window();
                                       } 
                                       else if (IDENTIFIER_FINAL.equals("DEL")) 
                                       {
                                          DeletePasswordMenu op = new DeletePasswordMenu ( ) ;
                                       }
                                  }
                                  else
                                  {
                                      JOptionPane.showMessageDialog ( rootPane,"Please try again....","Wrong Info's",JOptionPane.ERROR_MESSAGE ) ;
                                      banCounter ++ ;
                                      flagDelayA = true ;
                                      int u = banCounter ;
                                      new DelayLogIn ( u ) ;
                                  }
                              } 
                              catch (Exception ex) 
                              {
                                  ex.printStackTrace();
                              }
                          }
                          else
                          {
                              JOptionPane.showMessageDialog ( rootPane,"This UserName doesnt exist..","Warning",JOptionPane.ERROR_MESSAGE ) ;
                              banCounter ++ ;
                              flagDelayA = true ;
                              int e = banCounter ;
                              //System.out.println ( " mpijes ewdo epeidh den uparxeo to u sername pou eodes..to e einai " + e ) ;
                              new DelayLogIn ( e ) ;
                              ///Start Thread Delay
                          }
                        }
                        catch ( Exception E )
                        {
                            JOptionPane.showMessageDialog ( rootPane,"Invalid certificate","Warning",JOptionPane.ERROR_MESSAGE ) ;
                            if ( Checkbox.isSelected ( ) )
                            {
                                Checkbox.setEnabled ( false ) ;
                                Checkbox.setEnabled ( true ) ;
                            }
                            //Start Thread Delay
                            banCounter ++ ;
                            flagDelayA = true ;
                            int t = banCounter ;
                            new DelayLogIn ( t ) ;
                            //exit actionPerformed
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( rootPane,"Enter certificate","Warning",JOptionPane.WARNING_MESSAGE ) ;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog ( rootPane,"Enter Password","Warning",JOptionPane.INFORMATION_MESSAGE ) ;
                }
            }
            else
            {
                JOptionPane.showMessageDialog ( rootPane,"Enter UserName","Warning",JOptionPane.INFORMATION_MESSAGE ) ;
            }
          }
          else // if is true then you have entered at least one time some info wrong
          {
              JOptionPane.showMessageDialog ( rootPane,"Wait before try again","Time Ban",JOptionPane.INFORMATION_MESSAGE ) ;
          }
        }   
       
        
        
        if ( source == CLEAR )
        {
            Field_UserName.setText ( null ) ;
            Field_Password.setText ( null ) ;
            Checkbox.setEnabled ( true ) ;
        }   // End of Clear-Action  
    }   // End of actionPerformed
} 
    
    

    

  
