
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V1CertificateGenerator;




public class SignUpMenu extends JFrame implements ActionListener
{
    
    private JLabel L1, L2, L3, L4, NAME_LABEL, EmailLabel, SURNAME_LABEL ;
    private JButton Sign_Up, CLEAR ;
    private JTextField LOGIN_NAME_FIELD, NAME_SUR_FIELD, EMAILFIELD, SURNAME_FIELD ;
    private JPasswordField Password_1, Password_2 ;
   
    
    
    
    public SignUpMenu ( )
    {
        super  ( "Sign Up" ) ;
        setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE ) ;   //Close Current Window
        setSize ( 800, 550 ) ;
        setLocationRelativeTo ( null ) ;
        setVisible ( true ) ;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); //----
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
        //Locating and Initializing Components
        
        L1 = new JLabel ( "Please Enter your Info to Sign Up  " ) ;
        L1.setForeground ( Color.BLACK ) ;
        L1.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L1.setBounds ( 100, 50, 900, 40 ) ;
        
        L2 = new JLabel ( "Enter Name: " ) ;
        L2.setForeground ( Color.BLACK ) ;
        L2.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L2.setBounds ( 40, 100, 900, 40 ) ;
        
        NAME_LABEL = new JLabel ( "Enter Surname : " ) ;
        NAME_LABEL.setForeground ( Color.BLACK ) ;
        NAME_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        NAME_LABEL.setBounds ( 40, 160, 900, 40 ) ;
        
        SURNAME_LABEL = new JLabel ( "Enter Email : " ) ;
        SURNAME_LABEL.setForeground ( Color.BLACK ) ;
        SURNAME_LABEL.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        SURNAME_LABEL.setBounds ( 40, 220, 900, 40 ) ;
        
        EmailLabel = new JLabel ( "Enter UserName : " ) ;
        EmailLabel.setForeground ( Color.BLACK ) ;
        EmailLabel.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        EmailLabel.setBounds ( 40, 280, 900, 40 ) ;
        
        L3 = new JLabel ( "Enter Password : " ) ;
        L3.setForeground ( Color.BLACK ) ;
        L3.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L3.setBounds ( 40, 340, 900, 40 ) ;
        
        L4 = new JLabel ( "Re-enter Password : " ) ;
        L4.setForeground ( Color.BLACK ) ;
        L4.setFont  (new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        L4.setBounds ( 40, 400, 900, 40 ) ;
        
        //Buttons
        Sign_Up = new JButton ( "Sign_Up" ) ;
        Sign_Up.setForeground ( Color.BLACK ) ;
        Sign_Up.setBounds ( 200, 440, 120, 50 ) ;
        
        CLEAR = new JButton ( "Clear" ) ;
        CLEAR.setForeground ( Color.BLACK ) ;
        CLEAR.setBounds ( 350, 440, 120, 50 ) ;
        
        //Fields
        LOGIN_NAME_FIELD = new JTextField ( 20 ) ;
        LOGIN_NAME_FIELD.setForeground ( Color.BLACK ) ;
        LOGIN_NAME_FIELD.setBounds ( 210, 105, 220, 27 ) ;
        
        NAME_SUR_FIELD  = new JTextField ( 40 ) ;
        NAME_SUR_FIELD.setForeground ( Color.BLACK ) ;
        NAME_SUR_FIELD.setBounds ( 270, 167, 220, 27 ) ;
        
        SURNAME_FIELD  = new JTextField ( 40 ) ;
        SURNAME_FIELD.setForeground ( Color.BLACK ) ;
        SURNAME_FIELD.setBounds ( 245, 226, 220, 27 ) ;
        
        EMAILFIELD  = new JTextField ( 40 ) ;
        EMAILFIELD.setForeground ( Color.BLACK ) ;
        EMAILFIELD.setBounds ( 290, 289, 220, 27 ) ;
        
        Password_1 = new JPasswordField ( 20 ) ;
        Password_1.setForeground ( Color.BLACK ) ;
        Password_1.setBounds ( 285, 342, 220, 27 ) ;
        
        Password_2 = new JPasswordField ( 20 ) ;
        Password_2.setForeground ( Color.BLACK ) ;
        Password_2.setBounds ( 325, 404, 220, 27 ) ;
        
        
        CLEAR.addActionListener ( this ) ;
        Sign_Up.addActionListener ( this ) ;
        
        //Prosthetontas sto PANE
        Container pane = getContentPane ( ) ;
        
        pane.setLayout ( null ) ;   //Apenergopoioume ton diaxeiristi
        
        // Additions Components
        pane.add ( LOGIN_NAME_FIELD ) ;
        pane.add ( NAME_LABEL ) ;
        pane.add ( NAME_SUR_FIELD ) ;
        pane.add ( Password_1 ) ;
        pane.add ( Password_2 ) ;
        pane.add ( Sign_Up ) ;
        pane.add ( CLEAR ) ;
        pane.add ( L1 ) ;
        pane.add ( L2 ) ;
        pane.add ( L3 ) ;
        pane.add ( L4 ) ;
        pane.add ( SURNAME_LABEL ) ;
        pane.add ( EmailLabel ) ;
        pane.add ( SURNAME_FIELD ) ;
        pane.add ( EMAILFIELD ) ;
        
        setContentPane ( pane ) ;
        
    }//TELOS Constructor
    
    
   
    
    
    // Actions
    
    public void actionPerformed ( ActionEvent evt )
    {
        Object source = evt.getSource ( ) ;
        //CANCEL
        if ( source == CLEAR )
        {
            LOGIN_NAME_FIELD.setText ( "" ) ;
            NAME_SUR_FIELD.setText ( "" ) ;
            Password_1.setText ( "" ) ;
            Password_2.setText ( "" ) ;
            EMAILFIELD.setText ( "" ) ;
            SURNAME_FIELD.setText ( "" ) ;
        }
        
        
        //SIGN_UP
        if ( source == Sign_Up )
        {
            //1
            if ( ! ( LOGIN_NAME_FIELD.getText ( ).equals ( "" ) ) )
            {
                if ( ! ( NAME_SUR_FIELD.getText ( ).equals ( "" ) ) )
                {
                    if ( ! ( SURNAME_FIELD.getText ( ).equals ( "" ) ) )
                    {
                        if ( ! ( EMAILFIELD.getText ( ).equals ( "" ) ) )
                        {
                            if ( ! ( Password_1.getText ( ).equals ( "" ) ) )
                            {
                                if ( ! ( Password_2.getText ( ).equals ( "" ) ) )
                                {
                                    //Then all fields have been written
                                    //1
                                    //Check for email validility
                                     final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$" ;
                                     Boolean b = SURNAME_FIELD.getText ( ).matches ( EMAIL_REGEX ) ;
                                     if ( b == true )
                                     {
                                         //2
                                         //Check  if 2 passwords are the same
                                         String pass1 = Password_1.getText ( ) ;
                                         String pass2 = Password_2.getText ( ) ;
                                         if ( pass1.equals ( pass2 ) )
                                         {
                                             boolean localFlag = false ;
                                             //3
                                             //Then  see if there is User with same USERNAME
                                             File folder = new File ( "USERS" ) ;
                                             File [ ] listOfFiles = folder.listFiles ( ) ;
                                             final String userName = EMAILFIELD.getText ( ) ;
                                             for ( int i = 0 ; i < listOfFiles.length ; i ++ ) 
                                             {
                                                 if ( ( listOfFiles [ i ].getName ( ) ).equals ( userName ) )
                                                 {
                                                     localFlag = true ;
                                                     break ;
                                                 }
                                             }
                                             //ASo here, we are ready to say if User can sign up or must try with other UserName
                                             if ( localFlag == false )
                                             {
                                                 //A
                                                 //Create Key Pair
                                                 try   
                                                 {
                                                    KeyPairGenerator OBJECT_KEY = null;
                                                    KeyPair KEY_PAIR = null ;
                                                    PrivateKey PRIVATE_KEY = null ;
                                                    PublicKey PUBLIC_KEY = null ;
                                                    OBJECT_KEY = KeyPairGenerator.getInstance ( "RSA" ) ;  //Επιστρέφουμε το αντικείμενο που παράγει τα κλειδιά με τον RSA
                                                    OBJECT_KEY.initialize ( 2048 ) ; //Αρχικοποιούμε την γεννήτρια
                                                    KEY_PAIR = OBJECT_KEY.genKeyPair ( ); //Παράγουμε το ζευγάρι κλειδιών και το αποθηκεύουμε στο KEY_PAIR
                                                    PRIVATE_KEY = KEY_PAIR.getPrivate ( ); //Αποκτούμε την αναφορά στο ιδιωτικό κλειδί
                                                    //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                                                    PUBLIC_KEY = KEY_PAIR.getPublic ( ) ; //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                                                    //A
                                                    
                                                    //B
                                                    // create the extension value
                                                    // GeneralNames subjectAltName = new GeneralNames ( new GeneralName ( GeneralName.dNSName, SURNAME_FIELD.getText ( ) ) ) ;
                                                    // create the extensions object and add it as an attribute
                                                    Hashtable  attrs = new Hashtable();
                                                    attrs.put(X509Principal.C, "GR");
                                                    attrs.put(X509Principal.O,LOGIN_NAME_FIELD.getText ( ) + NAME_SUR_FIELD.getText ( ) );
                                                    attrs.put(X509Principal.EmailAddress, SURNAME_FIELD.getText ( ) );
                                                    Vector  order = new Vector ( ) ;
                                                    order.addElement ( X509Principal.C ) ;
                                                    order.addElement ( X509Principal.O ) ;
                                                    order.addElement ( X509Principal.EmailAddress ) ;
                                                    X509Name   subject = new X509Name ( order, attrs ) ;
                                                    PKCS10CertificationRequest req1 = new PKCS10CertificationRequest ( "SHA1withRSA", subject, KEY_PAIR.getPublic ( ), null, KEY_PAIR.getPrivate ( ) ) ;
                                                    ///B. CSR Ready
                                                    
                                                    //C
                                                    //Verify the CSR
                                                    //If the request is successful, the certificate authority will send back an identity certificate that has been digitally signed using the private key of the 
                                                    //certificate authority.
                                                    boolean VerifiedCSR = req1.verify ( PUBLIC_KEY, "BC" ) ;  //will be caught if false
                                                    //C
                                                    
                                                    //D
                                                    //Create the X509 Certificate of User now
                                                     Date startDate = new Date ( ) ;             // time from which certificate is valid
                                                     Calendar cal = Calendar.getInstance ( ) ;
                                                     cal.set ( 2017, Calendar.DECEMBER, 1 ) ;         //Year, month and day of month
                                                     Date expiryDate = cal.getTime ( ) ;          // time after which certificate is not valid
                                                     BigInteger serialNumber = BigInteger.valueOf ( (int) ( new Date ( ).getTime ( ) / 1000 ) ) ;    // serial number for certificate
                                                     X509V1CertificateGenerator certGen = new X509V1CertificateGenerator ( ) ;
                                                     X509Principal dnName = new X509Principal ( "CN=Test CA Certificate" ) ;
                                                     certGen.setSerialNumber (  serialNumber  ) ;
                                                     certGen.setIssuerDN ( dnName ) ;
                                                     certGen.setNotBefore ( startDate ) ;
                                                     certGen.setNotAfter ( expiryDate ) ;
                                                     certGen.setSubjectDN ( dnName ) ;       // note: same as issuer
                                                     certGen.setPublicKey ( PUBLIC_KEY ) ;
                                                     certGen.setSignatureAlgorithm ( "SHA1withRSA" ) ;
                                                     SecureRandom sr = SecureRandom.getInstance ( "SHA1PRNG" ) ;
                                                     //===============================================================================
                                                     KeyStore KSSpace = KeyStore.getInstance("JKS");
                                                     // get user password and file input stream
                                                     FileInputStream Fis = null;
                                                     try 
                                                     {
                                                         Fis = new FileInputStream ( "PM.jks" ) ;
                                                         KSSpace.load ( Fis, "password".toCharArray(  ) );
                                                     } 
                                                     finally 
                                                     {
                                                         if ( Fis != null ) 
                                                         {
                                                             Fis.close ( ) ;
                                                         } 
                                                     }
                                                     //retrieve
                                                     PrivateKey pkkp = ( PrivateKey ) KSSpace.getKey ( "Priv", "password".toCharArray ( ) ) ;
                                                     //==================================================================================
                                                     X509Certificate cert = certGen.generate ( pkkp, sr ) ;   //signed with PRIVATE KEY OF APP (CA)
                                                     //D
                                                   
                                                     //E
                                                     //Create a file .auth, that contains the record < Username, Authhash >
                                                     //Create sKey
                                                     File UserFile = new File ( "USERS\\" +  EMAILFIELD.getText ( ) ) ;
                                                     UserFile.mkdir ( ) ; 
                                                     PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator ( ) ;
                                                     gen.init ( pass1.getBytes ( ), EMAILFIELD.getText ( ).getBytes ( ), 2000 ) ;
                                                     byte [ ] sKey = ( ( KeyParameter ) gen.generateDerivedParameters ( 128 ) ).getKey ( ) ;   // n bits. 16 bytes -> 
                                                     //Produce AuthHash
                                                     PKCS5S2ParametersGenerator forauth = new PKCS5S2ParametersGenerator ( ) ;
                                                     gen.init ( sKey, pass1.getBytes ( ), 1000 ) ;
                                                     byte [ ] authHash = ( ( KeyParameter ) gen.generateDerivedParameters ( 128 ) ).getKey ( ) ;   // n bits. 16 bytes -> 
                                                     //E
                                                     
                                                     //F
                                                     //Construct the UserNameAuthHash object and save it to Users directory
                                                     UserNameAuthHash  UnAh = new UserNameAuthHash ( EMAILFIELD.getText ( ), authHash ) ;
                                                     FileOutputStream fout = null ;
                                                     ObjectOutputStream oos = null ;
                                                     fout = new FileOutputStream ( "USERS\\" + EMAILFIELD.getText ( ) + "\\" + EMAILFIELD.getText ( ) + ".auth" ) ;
                                                     oos = new ObjectOutputStream ( fout ) ;
                                                     oos.writeObject ( UnAh ) ;
                                                     oos.close ( ) ;
                                                     //F
                                                     
                                                     //G
                                                     //Export Cert and keys
                                                     String fileName = null ;
                                                     File selectedFile = null ;
                                                     JFileChooser fileChooser = new JFileChooser ( ) ;
                                                     int status = fileChooser.showSaveDialog ( null ) ;
                                                     if ( status == JFileChooser.APPROVE_OPTION ) 
                                                     {
                                                          selectedFile = fileChooser.getSelectedFile ( ) ;
                                                          fileName = selectedFile.getCanonicalPath ( ) ;
                                                          System.out.println ( " einai -> " + fileName ) ;
                                                     }
                                                     File f = new File ( fileName ) ;
                                                     //==
                                                     ZipOutputStream out = new ZipOutputStream ( new FileOutputStream ( f ) ) ;
                                                     byte [ ] Array  = cert.getEncoded ( ) ;
                                                     byte [ ] Array2  = PUBLIC_KEY.getEncoded ( ) ;
                                                     byte [ ] Array3 = PRIVATE_KEY.getEncoded ( ) ;
                                                     ArrayList < byte [ ] > Hr = new ArrayList < byte [ ] > ( ) ;
                                                     Hr.add ( Array ) ;
                                                     Hr.add ( Array2 ) ;
                                                     Hr.add ( Array3) ;
                                                     ArrayList < String > Na = new ArrayList < String> ( ) ;
                                                     Na.add ( new String ( "PMCert.crt") ) ;
                                                     Na.add ( new String ( "PMpubKey.key")  ) ;
                                                     Na.add ( new String ( "PMpriKey.key") ) ;
                                                     for ( int i = 0  ;  i < Hr.size ( ) ;  i ++ )
                                                     {
                                                         ZipEntry e = new ZipEntry ( Na.get ( i ) ) ;
                                                         out.putNextEntry ( e ) ;
                                                         byte [ ] data = Hr.get ( i ) ;
                                                         out.write ( data, 0, data.length ) ;
                                                         out.closeEntry ( ) ;
                                                     }
                                                     out.close ( ) ;
                                                     //G
                                                     
                                                     //Now OK
                                                     JOptionPane.showMessageDialog ( rootPane,"Sign up done ","Sucessfull",JOptionPane.INFORMATION_MESSAGE ) ;
                                                     this.dispose ( ) ;
                                                     new MainMenu ( ) ;
                                                 }
                                                 catch ( CertificateException CE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - CertificateException","Exception ",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( UnrecoverableKeyException UKE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - UnrecoverableKeyException","Exception ",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( KeyStoreException KSE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - KeyStoreException","Exception ",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( SignatureException SE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - SignatureException","Exception",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( InvalidKeyException IKE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - InvalidKeyException","Exception",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( NoSuchProviderException NSAE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - NoSuchProviderException","Exception ",JOptionPane.ERROR_MESSAGE ) ;
                                                     NSAE.printStackTrace();
                                                 }
                                                 catch ( NoSuchAlgorithmException NSAE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - NoSuchAlgorithmException","Exception",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                                 catch ( IOException IOE )
                                                 {
                                                     JOptionPane.showMessageDialog ( rootPane,"SignUpMenu - IOException","Try again ",JOptionPane.ERROR_MESSAGE ) ;
                                                 }
                                             }
                                             else if ( localFlag == true )
                                             { //try aggain
                                                 JOptionPane.showMessageDialog ( rootPane,"Try again with different Username","Try again ",JOptionPane.ERROR_MESSAGE ) ;
                                             }
                                         }
                                         else
                                         {
                                             JOptionPane.showMessageDialog ( rootPane,"Enter same password  !","Password ",JOptionPane.WARNING_MESSAGE ) ;
                                         }
                                     }
                                     else
                                     {
                                         JOptionPane.showMessageDialog ( rootPane,"Enter a valid email !","Validility Email",JOptionPane.WARNING_MESSAGE ) ;
                                         EMAILFIELD.setText ( "" ) ;
                                     }
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog ( rootPane,"Enter password !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog ( rootPane,"Enter password !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog ( rootPane,"Enter a UserName !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog ( rootPane,"Enter an email !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog ( rootPane,"Enter a surname !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
                }
            }
            else
            {
                JOptionPane.showMessageDialog ( rootPane,"Enter a name !","Blank Field",JOptionPane.WARNING_MESSAGE ) ;
            }
        }//End of If Sign_Up_Window
    } //End Action Methodou
} //End CLASS

    
