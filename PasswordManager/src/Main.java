
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
import javax.swing.JOptionPane;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V1CertificateGenerator;



public class Main
{

    public static void main(String[] args) 
    {
        final String jksFileName = "PM.jks" ;
        final String FolderName = "USERS" ;
        final String jksPassword = "password" ;
        KeyStore ks = null ;
        
        File virtualFile = new File ( jksFileName ) ;
        if ( virtualFile.exists ( ) )
        {
            //Skip code and display Menu..
            new MainMenu ( ) ;
        }
        else
        {
            try
            {
                //1
                //Create Key pair. Create Public and Private Key
                KeyPairGenerator OBJECT_KEY = null;
                KeyPair KEY_PAIR = null;
                PrivateKey PRIVATE_KEY = null;
                PublicKey PUBLIC_KEY = null;
                OBJECT_KEY = KeyPairGenerator.getInstance("RSA");  //Επιστρέφουμε το αντικείμενο που παράγει τα κλειδιά με τον RSA
                OBJECT_KEY.initialize(2048); //Αρχικοποιούμε την γεννήτρια
                KEY_PAIR = OBJECT_KEY.genKeyPair(); //Παράγουμε το ζευγάρι κλειδιών και το αποθηκεύουμε στο KEY_PAIR
                PRIVATE_KEY = KEY_PAIR.getPrivate(); //Αποκτούμε την αναφορά στο ιδιωτικό κλειδί
                //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                PUBLIC_KEY = KEY_PAIR.getPublic(); //Αποκτούμε την αναφορά στο δημόσιο κλειδί
                //1
                
                //2
                //Create the X.509 Self Signed Certificate of the Application
                Date startDate = new Date ( ) ;             // time from which certificate is valid
                Calendar cal = Calendar.getInstance ( ) ;
                cal.set ( 2018, Calendar.JUNE, 1 ) ;         //Year, month and day of month
                Date expiryDate = cal.getTime ( ) ;          // time after which certificate is not valid
                BigInteger serialNumber = BigInteger.valueOf ( 322013999 ) ;     // serial number for certificate
                X509V1CertificateGenerator certGen = new X509V1CertificateGenerator ( ) ;
                X509Principal dnName = new X509Principal ( "CN=Test CA Certificate" ) ;
                certGen.setSerialNumber ( serialNumber ) ;
                certGen.setIssuerDN ( dnName ) ;
                certGen.setNotBefore ( startDate ) ;
                certGen.setNotAfter ( expiryDate ) ;
                certGen.setSubjectDN ( dnName ) ;       // note: same as issuer
                certGen.setPublicKey ( PUBLIC_KEY ) ;
                certGen.setSignatureAlgorithm ( "SHA1withRSA" ) ;
                SecureRandom sr = SecureRandom.getInstance ( "SHA1PRNG" ) ;
                X509Certificate cert = certGen.generate ( PRIVATE_KEY, sr ) ;
                //2
                
                //3
                //Create An empty KeyStore 
                ks = KeyStore.getInstance( "JKS" ) ;
                ks.load ( null, jksPassword.toCharArray ( ) ) ;  //create empty JKS
                ArrayList < Certificate > Ar  = new ArrayList < Certificate > ( )  ;
                Ar.add ( cert ) ;
                Certificate [ ] stockArr = new Certificate [ Ar.size ( ) ] ;
                stockArr = Ar.toArray ( stockArr ) ;  //Array of Certificate
                ks.setCertificateEntry ( "CertApp", cert ) ;   //Add to keystore the cert
                ks.setKeyEntry ( "Priv", PRIVATE_KEY, jksPassword.toCharArray ( ), stockArr ) ;  //add to keystore the Private Key
                //Now store the keystore to a file .jks
                FileOutputStream Fostm = new FileOutputStream ( "PM.jks" ) ;
                ks.store ( Fostm, jksPassword.toCharArray ( ) ) ;
                Fostm.close ( ) ;
                //3
                
                //4
                //Create the folder USERS. Is a directory that contains all Users folders
                File file = new File ( FolderName ) ;
                file.mkdir ( ) ;
                //4
            }  
            catch ( InvalidKeyException IKE )
            {
                JOptionPane.showMessageDialog ( null,"Main - InvalidKeyException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ; 
            }
            catch ( SignatureException SE )
            {
                JOptionPane.showMessageDialog ( null,"Main - SignatureException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                System.exit ( 1 ) ; 
            }
            catch ( KeyStoreException KSE )
            {
                JOptionPane.showMessageDialog ( null,"Main - KeyStoreException","Critical error",JOptionPane.ERROR_MESSAGE ) ;
                KSE.printStackTrace();
                //System.exit ( 1 ) ; 
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
           
            /*KeyStore YO = KeyStore.getInstance("JKS");
            // get user password and file input stream
            FileInputStream RE = null;
            try 
            {
                RE = new FileInputStream("PM.jks");
                YO.load(RE, jksPassword.toCharArray() );
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
            PrivateKey pkkp = (PrivateKey) YO.getKey("ala", jksPassword.toCharArray());
            System.out.println(fdr.toString());*/
            
            //call Menu
            new MainMenu ( ) ;
        }
    }
}
