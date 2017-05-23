
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;





public class Main
{

    public static void main(String[] args) 
    {
        final String jksFile = "PM.jks" ;
        final String FolderName = "USERS" ;
        final String jksPassword = "password" ;
        
        File virtualFile = new File ( jksFile ) ;
        if ( virtualFile.exists ( ) )
        {
            //Skip code and display Menu..
            new MainMenu ( ) ;
        }
        else
        {
            //Create KeyStore and folder USERS
        
            //First create a universal folder for all users. In this folder there will be created all
            //the username folders
            File file = new File ( FolderName ) ;
            file.mkdir ( ) ;
            KeyStore ks = null ;
            try 
            {   //Load JKS before access item
                 ks = KeyStore.getInstance ( "JKS" ) ;
                // get user password 
                char [ ] password = jksPassword.toCharArray ( ) ;  
                ks.load ( null, password ) ; 
                // Store away the keystore. 
                FileOutputStream fos = new FileOutputStream ( jksFile ) ; 
                ks.store ( fos, password ) ;
                //fos.close ( ) ; afou grapso to dimosio idiotiko kai to self signed cert tote to kleino !!!!!!!!!!!!!!sososososososososososososssssssssssssssssoooooooooooo
                
                //First create Key Pair - Public and Private. RSA 2048 Bits
                KeyPairGenerator OBJECT_KEY = null;
                KeyPair KEY_PAIR = null;
                PrivateKey PRIVATE_KEY = null;
                PublicKey PUBLIC_KEY = null;
                OBJECT_KEY = KeyPairGenerator.getInstance( "RSA" ) ;  //Επιστρέφουμε το αντικείμενο που παράγει τα κλειδιά με τον RSA
                OBJECT_KEY.initialize(2048); //Αρχικοποιούμε την γεννήτρια
                KEY_PAIR = OBJECT_KEY.genKeyPair(); //Παράγουμε το ζευγάρι κλειδιών και το αποθηκεύουμε στο KEY_PAIR
                PRIVATE_KEY = KEY_PAIR.getPrivate(); //Αποκτούμε την αναφορά στο ιδιωτικό κλειδί
                //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                PUBLIC_KEY = KEY_PAIR.getPublic ( ) ; //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                
                //Write keys to KeyStore
                //KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection ( password ) ;
                ks.setKeyEntry ( "privKeyAlias", PRIVATE_KEY, password, null ) ;
                ks.setKeyEntry ( "publicKeyAlias", PUBLIC_KEY, password, null ) ;
                //Create X.509 Self Signed certificate
                
                //Entry the certificate
                //ks.setCertificateEntry ( jksFile, null ) ;

            }    
            catch ( KeyStoreException KSE )
            {
                JOptionPane.showMessageDialog ( null,"Main - KeyStoreException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ; 
            }
            catch ( CertificateException CE )
            {
                JOptionPane.showMessageDialog ( null,"Main - CertificateException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ;
            }
            catch ( NoSuchAlgorithmException NSAE )
            {
                JOptionPane.showMessageDialog ( null,"Main - NoSuchAlgorithmException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ;
            }
            catch ( FileNotFoundException FNFE )
            {
                JOptionPane.showMessageDialog ( null,"Main - FileNotFoundException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ;
            }
            catch ( IOException IOE )
            {
                JOptionPane.showMessageDialog ( null,"Main - IOException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ;
            }
            //call Menu
            new MainMenu ( ) ;
        }
    }
}
