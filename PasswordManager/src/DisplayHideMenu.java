
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
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






public class DisplayHideMenu extends JFrame implements ActionListener
{
    private JLabel DisplayorHidePasswordRecords = null ;
    private ArrayList < JButton > ButtonList = new ArrayList < JButton > ( ) ;
    private ArrayList < PasswordRecord > RecordList = new ArrayList < PasswordRecord > ( ) ;
    private Cipher CIPHER = null ;
    private ArrayList < SealedObject > EncryptedRecordsList = new ArrayList < SealedObject > ( ) ;
    private ArrayList < PasswordRecord > UnencryptedRecordsList = new ArrayList < PasswordRecord > ( ) ;
    private static boolean tempflag = false ;
    
    public DisplayHideMenu ( ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
    {
        super ( "Display/Hide" ) ;
        this.addWindowListener ( new java.awt.event.WindowAdapter ( ) 
        {
              @Override             //Otan patiseis to kokkino x tote aytomata ginetai LGOOUT
             public void windowClosing(java.awt.event.WindowEvent windowEvent) 
             {
                 MainMenu o = new MainMenu ( ) ;
             }
        } ) ;
        //Windows Closing
        setSize ( 750, 550 ) ;       // Size of Window
        setLocationRelativeTo ( null ) ;  // Display Window in center of Screen
        setVisible ( true ) ;
        
        // Manager Layout
        Container pane = getContentPane ( ) ;
        pane.setLayout ( null ) ;
        
        try
        {
            int c = 0 ;
            FileInputStream Fis = new FileInputStream ( "USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" ) ;
            ObjectInputStream In = null ;
            while ( true ) 
            {
                In = new ObjectInputStream ( Fis ) ;  
                Object OBJ = ( Object ) In.readObject ( ) ; 
                if ( OBJ instanceof SealedObject )
                {
                    SealedObject so = ( SealedObject ) OBJ ;
                    EncryptedRecordsList.add ( so ) ;//First decrypt it to display Domain
                    CIPHER = null ;
                    CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                    byte [ ] temp = LogInMenu.SymmetricKey ;
                    SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                    CIPHER.init ( Cipher.DECRYPT_MODE, key2  ) ;
                    PasswordRecord passwordRecordObj = ( PasswordRecord ) so.getObject ( CIPHER ) ;
                    RecordList.add ( passwordRecordObj ) ;
                }
                else if ( OBJ instanceof PasswordRecord )
                {
                   PasswordRecord pr = ( PasswordRecord ) OBJ ;
                   UnencryptedRecordsList.add ( pr ) ;
                   RecordList.add ( pr ) ;
                }
                c ++ ;
            }
        }
        catch ( ClassNotFoundException CNFE )
        {
        }
        catch ( IOException IOE )
        {
        }
        DisplayorHidePasswordRecords  = new JLabel ( "Encrypt or Decrypt passwords" ) ;
        DisplayorHidePasswordRecords.setBounds ( 35, 15, 550, 40 ) ;
        DisplayorHidePasswordRecords.setForeground ( Color.BLACK ) ;
        DisplayorHidePasswordRecords.setFont ( new Font ( "Courier New", Font.BOLD, 25 ) ) ;
        //Display records domains
        int x=0 ;
        if ( RecordList.isEmpty ( ) ) 
        {   JOptionPane.showMessageDialog(rootPane, "Empty Password Records");
            this.dispose ( ) ;
            new MainMenu ( ) ;
        }
        else
        {
            for ( int i = 0 ; i < RecordList.size ( ) ;  i ++ )
            {
                JButton o = new JButton ( RecordList.get ( i ).Getdomain ( ) ) ;
                o.setName ( RecordList.get ( i ).Getdomain ( ) ) ;
                o.setBounds ( 30, 80 + x, 300, 30  ) ;
                o.setForeground ( Color.BLACK ) ;
                o.setFont ( new Font ( "Courier New", Font.BOLD, 20 ) ) ;
                pane.add ( o ) ;
                o.addActionListener ( this ) ;
                ButtonList.add ( o ) ;
                x = x + 40 ;
            }
        }
        // Additions Components
        pane.add ( DisplayorHidePasswordRecords ) ;
        setContentPane ( pane ) ;
    }
    
    public void actionPerformed ( ActionEvent evt )
    {   
        Object source = evt.getSource ( ) ;
         tempflag = false ;
        for ( int i = 0 ; i < ButtonList.size ( ) ; i ++ )
        {     
            if ( ButtonList.get ( i ) == source )
            {  
                tempflag = false ;
                Object options [] = { "decrypt","encrypt" } ;
                int choice = JOptionPane.showOptionDialog(rootPane, 
                               "Choose encryption or decryption ?", "Decrypt or Encrypt?",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null,options, "e");
                 if (choice==0)
                 {  
                     if ( EncryptedRecordsList.isEmpty ( ) )
                     {
                         JOptionPane.showMessageDialog(rootPane, "Already saved as Decrypted record");
                     }
                     if ( UnencryptedRecordsList.isEmpty ( ) )
                     {
                         String srch = ButtonList.get ( i ).getName ( ) ;
                         PasswordRecord passwordRecordObj = null ;
                         for ( int j = 0 ; j < EncryptedRecordsList.size ( ) ;  j ++ )
                         {
                           try
                           {
                                CIPHER = null ;
                                CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                                byte [ ] temp = LogInMenu.SymmetricKey ;
                                SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                                CIPHER.init ( Cipher.DECRYPT_MODE, key2  ) ;
                                passwordRecordObj = ( PasswordRecord ) EncryptedRecordsList.get ( j ).getObject ( CIPHER ) ;
                           }
                           catch ( Exception E)
                           {
                           }
                           //
                           if ( srch.equals ( passwordRecordObj.Getdomain ( ) ) )
                           {
                               JOptionPane.showMessageDialog(rootPane, "Domain:  " + passwordRecordObj.Getdomain ( ) + " , "  + "Username:  " + passwordRecordObj.GetuserName ( ) + " , "
                               + "Password:  " + passwordRecordObj.Getpassword() + "  , Comment:  " + passwordRecordObj.Getcomment ( ) ) ;
                               //Delete it from List
                               EncryptedRecordsList.remove ( i ) ;
                               //Overwrite file and write again
                               try
                               {
                                FileOutputStream fout = null;
                                ObjectOutputStream oos = null;
                                fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                                 for ( int k = 0  ; k < EncryptedRecordsList.size ( ) ;  k ++ )
                                 {  
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject(EncryptedRecordsList.get ( k ) );
                                 }
                                 oos = new ObjectOutputStream(fout);
                                 oos.writeObject ( passwordRecordObj );
                                 oos.flush ( ) ;
                                 oos.close ( ) ;
                                 fout.close ( ) ;
                                //ok
                               }
                               catch ( IOException IOE )
                               {
                                   
                               }
                           }
                         }
                     }
                     if ( ! ( EncryptedRecordsList.isEmpty ( ) )  &&  ! ( UnencryptedRecordsList.isEmpty ( ) )  )
                     {   
                         PasswordRecord passwordRecordObj = null ;
                         String name = ButtonList.get ( i ).getName ( ) ;
                         for ( int h = 0 ; h < UnencryptedRecordsList.size ( ) ; h ++ )
                         {
                             if ( name.equals ( UnencryptedRecordsList.get ( h ).Getdomain ( ) ) )
                             {
                                 JOptionPane.showMessageDialog(rootPane, "Already saved as Decrypted record");
                             }
                         }
                         
                         for ( int h = 0 ; h < EncryptedRecordsList.size ( ) ; h ++ )
                         {
                           try
                           {
                                CIPHER = null ;
                                CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                                byte [ ] temp = LogInMenu.SymmetricKey ;
                                SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                                CIPHER.init ( Cipher.DECRYPT_MODE, key2  ) ;
                                passwordRecordObj = ( PasswordRecord ) EncryptedRecordsList.get ( h ).getObject ( CIPHER ) ;
                           }
                           catch ( Exception E)
                           {
                           }
                           //
                           if ( name.equals ( passwordRecordObj.Getdomain ( ) ) )
                           {
                               //
                               JOptionPane.showMessageDialog(rootPane, "Domain:  " + passwordRecordObj.Getdomain ( ) + " , "  + "Username:  " + passwordRecordObj.GetuserName ( ) + " , "
                         + "Password:  " + passwordRecordObj.Getpassword() + "  , Comment:  " + passwordRecordObj.Getcomment ( ) ) ;
                               //
                               EncryptedRecordsList.remove ( h ) ;
                               try
                               {
                                FileOutputStream fout = null;
                                ObjectOutputStream oos = null;
                                fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                                 for ( int k = 0  ; k < UnencryptedRecordsList.size ( ) ;  k ++ )
                                 {  
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject(UnencryptedRecordsList.get ( k ) );
                                 }
                                 for ( int k = 0  ; k < EncryptedRecordsList.size ( ) ;  k ++ )
                                 {  
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject(EncryptedRecordsList.get ( k ) );
                                 }
                                 oos = new ObjectOutputStream(fout);
                                 oos.writeObject ( passwordRecordObj );
                                 oos.flush ( ) ;
                                 oos.close ( ) ;
                                 fout.close ( ) ;
                                //ok
                               }
                               catch ( IOException IOE )
                               {
                                   
                               }
                           }
                         }//endFor
                     }//endif
                 }
                 else if ( choice==1 ) 
                 { 
                     if ( UnencryptedRecordsList.isEmpty ( ) )
                     {
                         JOptionPane.showMessageDialog(rootPane, "Already saved as Encrypted record");
                     }
                     if ( EncryptedRecordsList.isEmpty ( ) )
                     {
                         String srch = ButtonList.get ( i ).getName ( ) ;
                         for ( int j = 0 ; j < UnencryptedRecordsList.size ( ) ;  j ++ )
                         {
                             if ( ( UnencryptedRecordsList.get ( j ).Getdomain ( ) ).equals ( srch ) )
                             { PasswordRecord l = UnencryptedRecordsList.get ( j ) ;
                                 try
                                 {
                                    CIPHER = null ;
                                    CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                                    byte [ ] temp = LogInMenu.SymmetricKey ;
                                    SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                                    CIPHER.init ( Cipher.ENCRYPT_MODE, key2  ) ;
                                    SealedObject so = new SealedObject ( UnencryptedRecordsList.get ( j ), CIPHER ) ;
                                    UnencryptedRecordsList.remove ( j ) ;
                                     FileOutputStream fout = null;
                                    ObjectOutputStream oos = null;
                                    fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                                    for ( int c = 0 ; c < UnencryptedRecordsList.size ( ) ; c ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject ( UnencryptedRecordsList.get ( c ) );
                                    }
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject ( so ) ;
                                    oos.flush ( ) ;
                                    oos.close ( ) ;
                                    fout.close ( ) ;
                                 }
                                 catch ( Exception E )
                                 {
                                 }
                                 JOptionPane.showMessageDialog(rootPane, "Domain:  " + l.Getdomain ( ) + " , "  + "Username:  " + l.GetuserName ( ) + " , "
                         + "Password:  " + l.Getpassword() + "  , Comment:  " + l.Getcomment ( ) ) ;
                             }
                           
                         }
                     }
                     if ( ! ( EncryptedRecordsList.isEmpty ( ) )  &&  ! ( UnencryptedRecordsList.isEmpty ( ) )  )
                     {
                        
                         String name = ButtonList.get ( i ).getName ( ) ;
                         
                         for ( int h = 0 ; h < UnencryptedRecordsList.size ( ) ; h ++ )
                         {
                           if ( name.equals ( UnencryptedRecordsList.get ( h ).Getdomain ( ) ) )
                           {
                               tempflag = false ;
                               tempflag = true ;
                               JOptionPane.showMessageDialog(rootPane, "Domain:  " + UnencryptedRecordsList.get ( h ).Getdomain() + " , "  + "Username:  " + UnencryptedRecordsList.get ( h ).GetuserName ( ) + " , "
                         + "Password:  " + UnencryptedRecordsList.get ( h ).Getpassword() + "  , Comment:  " + UnencryptedRecordsList.get ( h ).Getcomment ( ) ) ;
                                 try
                                 {
                                    CIPHER = null ;
                                    CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                                    byte [ ] temp = LogInMenu.SymmetricKey ;
                                    SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                                    CIPHER.init ( Cipher.ENCRYPT_MODE, key2  ) ;
                                    SealedObject so = new SealedObject ( UnencryptedRecordsList.get ( h ), CIPHER ) ;
                                    UnencryptedRecordsList.remove ( h ) ;
                                    FileOutputStream fout = null;
                                    ObjectOutputStream oos = null;
                                    fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                                    for ( int f = 0 ; f < EncryptedRecordsList.size ( ) ; f ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject ( EncryptedRecordsList.get ( f ) );
                                    }
                                    for ( int f = 0 ; f < UnencryptedRecordsList.size ( ) ; f ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject ( UnencryptedRecordsList.get ( f ) );
                                    }
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject ( so );
                                    oos.flush ( ) ;
                                    oos.close ( ) ;
                                    fout.close ( ) ;
                                 }
                                 catch ( Exception E )
                                 {
                                 }
                           }
                         }//endFor
                         if ( tempflag == false )
                         {
                             tempflag = false ;
                             JOptionPane.showMessageDialog(rootPane, "Already saved as Encrypted record");
                         }
                     }//endif 
                 }
            }
        }
    }
}
