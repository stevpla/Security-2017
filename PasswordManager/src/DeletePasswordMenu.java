
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



public class DeletePasswordMenu extends JFrame implements ActionListener
{
    private JLabel DisplayorHidePasswordRecords = null ;
    private ArrayList < JButton > ButtonList = new ArrayList < JButton > ( ) ;
    private ArrayList < PasswordRecord > RecordList = new ArrayList < PasswordRecord > ( ) ;
    private Cipher CIPHER = null ;
    private ArrayList < SealedObject > EncryptedRecordsList = new ArrayList < SealedObject > ( ) ;
    private ArrayList < PasswordRecord > UnencryptedRecordsList = new ArrayList < PasswordRecord > ( ) ;
 
    
    public DeletePasswordMenu ( ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
    {
        super ( "DeletePasswordRecord" ) ;
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
        DisplayorHidePasswordRecords  = new JLabel ( "Delete passwords" ) ;
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
        
        for ( int i = 0 ; i < ButtonList.size ( ) ; i ++ )
        {     
            String srch = ButtonList.get ( i ).getName ( ) ;
            if ( ButtonList.get ( i ) == source )
            {  
                if ( EncryptedRecordsList.isEmpty ( ) )
                {
                    for ( int h = 0 ; h < UnencryptedRecordsList.size ( ) ;  h ++ )
                    {
                        if ( srch.equals ( UnencryptedRecordsList.get ( h ).Getdomain ( ) ) )
                        {
                           try
                           {
                            FileOutputStream fout = null;
                            ObjectOutputStream oos = null;
                            fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                            //delete this
                            UnencryptedRecordsList.remove ( h ) ;
                            for ( int g = 0 ; g < UnencryptedRecordsList.size ( ) ;  g ++ )
                            {
                               //write all list
                                oos = new ObjectOutputStream(fout);
                                oos.writeObject(UnencryptedRecordsList.get ( g ) );
                            }
                            oos.flush ( ) ;
                            oos.close ( ) ;
                            fout.close ( ) ;
                           //ok
                           }
                           catch ( IOException IOE )
                           {   
                           }
                           JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                        }
                    }
                }
                if ( UnencryptedRecordsList.isEmpty ( ) )
                {
                    PasswordRecord passwordRecordObj = null ;
                    for ( int g = 0 ; g < EncryptedRecordsList.size ( ) ; g ++ )
                    {
                        //decrypt it to check domain name
                        try
                        {
                            CIPHER = null ;
                            CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                            byte [ ] temp = LogInMenu.SymmetricKey ;
                            SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                            CIPHER.init ( Cipher.DECRYPT_MODE, key2  ) ;
                            passwordRecordObj = ( PasswordRecord ) EncryptedRecordsList.get ( g ).getObject ( CIPHER ) ;
                        }
                        catch ( Exception E)
                        {
                        }
                        //
                        if ( srch.equals ( passwordRecordObj.Getdomain ( ) ) )
                        {
                           try
                           {
                                FileOutputStream fout = null;
                                ObjectOutputStream oos = null;
                                fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME ( ) + "\\" + LogInMenu.FIND_PATHNAME ( ) + ".pass" );
                                //delete this
                                EncryptedRecordsList.remove ( g ) ;
                                for ( int p = 0 ; p < EncryptedRecordsList.size ( ) ;  p ++ )
                                {
                                    //write all list
                                    oos = new ObjectOutputStream(fout);
                                    oos.writeObject(EncryptedRecordsList.get ( p ) );
                                }
                                oos.flush ( ) ;
                                oos.close ( ) ;
                                fout.close ( ) ;
                                //ok
                           }
                           catch ( IOException IOE )
                           {  
                           }
                           JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                        }
                    }
                }
                if ( ( ! ( UnencryptedRecordsList.isEmpty ( ) ) )   &&  ( ! ( EncryptedRecordsList.isEmpty ( ) ) ) )
                {
                    
                    if ( UnencryptedRecordsList.size ( ) > 1 )
                    {
                        for ( int f = 0 ; f < UnencryptedRecordsList.size ( ) ; f ++ )
                        {
                            if ( UnencryptedRecordsList.get ( f ).Getdomain().equals ( srch ) )
                            {
                                UnencryptedRecordsList.remove ( f ) ;
                                try
                                {
                                    FileOutputStream fout = null;
                                    ObjectOutputStream oos = null;
                                    fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME() + "\\" + LogInMenu.FIND_PATHNAME() + ".pass");
                                    for ( int u = 0 ; u < UnencryptedRecordsList.size ( ) ; u ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject(UnencryptedRecordsList.get(u));
                                    }
                                    for ( int u = 0 ; u < EncryptedRecordsList.size ( ) ; u ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject(EncryptedRecordsList.get(u));
                                    }
                                    oos.flush();
                                    oos.close();
                                    fout.close();
                                }
                                catch ( IOException IOE )
                                {
                                }
                                JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                            }
                        }
                    }
                    else if ( UnencryptedRecordsList.size ( ) == 1 )
                    {
                      if ( UnencryptedRecordsList.get(0).Getdomain().equals(srch))
                      {
                        //write only list with encrypto records
                        try
                        {
                            FileOutputStream fout = null;
                            ObjectOutputStream oos = null;
                            fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME() + "\\" + LogInMenu.FIND_PATHNAME() + ".pass");
                            //delete this
                            UnencryptedRecordsList.remove ( 0 ) ;
                            for (int p = 0; p < EncryptedRecordsList.size(); p++) 
                            {
                                //write all list
                                oos = new ObjectOutputStream(fout);
                                oos.writeObject(EncryptedRecordsList.get(p));
                            }
                            oos.flush();
                            oos.close();
                            fout.close();
                            //ok
                            JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                        } 
                        catch (IOException IOE) 
                        {
                        }
                      }
                    }
                    
                    if ( EncryptedRecordsList.size ( ) > 1 )
                    {
                        PasswordRecord passwordRecordObj = null ;
                        for ( int f = 0 ; f < EncryptedRecordsList.size ( ) ; f ++ )
                        {
                            //decrypt
                            try
                            {
                                CIPHER = null ;
                                CIPHER = Cipher.getInstance ( "AES" ) ;  //symmetric decryption
                                byte [ ] temp = LogInMenu.SymmetricKey ;
                                SecretKey key2 = new SecretKeySpec ( temp, 0, temp.length, "AES" ) ;
                                CIPHER.init ( Cipher.DECRYPT_MODE, key2  ) ;
                                passwordRecordObj = ( PasswordRecord ) EncryptedRecordsList.get ( f ).getObject ( CIPHER ) ;
                            }
                            catch ( Exception E)
                            {
                            }
                            
                            //
                            if ( passwordRecordObj.Getdomain ( ).equals ( srch ) )
                            {
                                JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                                EncryptedRecordsList.remove ( f ) ;
                                try
                                {
                                    FileOutputStream fout = null;
                                    ObjectOutputStream oos = null;
                                    fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME() + "\\" + LogInMenu.FIND_PATHNAME() + ".pass");
                                    for ( int u = 0 ; u < EncryptedRecordsList.size ( ) ; u ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject(EncryptedRecordsList.get(u));
                                    }
                                    for ( int u = 0 ; u < UnencryptedRecordsList.size ( ) ; u ++ )
                                    {
                                        oos = new ObjectOutputStream(fout);
                                        oos.writeObject(UnencryptedRecordsList.get(u));
                                    }
                                    oos.flush();
                                    oos.close();
                                    fout.close();
                                }
                                catch ( IOException IOE )
                                {
                                }
                            }
                        }
                    }
                    else if ( EncryptedRecordsList.size ( ) == 1 )
                    {
                        PasswordRecord passwordRecordObj = null ;
                        //
                        try 
                        {
                            CIPHER = null;
                            CIPHER = Cipher.getInstance("AES");  //symmetric decryption
                            byte[] temp = LogInMenu.SymmetricKey;
                            SecretKey key2 = new SecretKeySpec(temp, 0, temp.length, "AES");
                            CIPHER.init(Cipher.DECRYPT_MODE, key2);
                            passwordRecordObj = (PasswordRecord) EncryptedRecordsList.get(0).getObject(CIPHER);
                        } 
                        catch (Exception E) 
                        {
                        }
                        //
                      if( srch.equals ( passwordRecordObj.Getdomain()) )
                      {
                        //write only list with encrypto records
                        try
                        {
                            FileOutputStream fout = null;
                            ObjectOutputStream oos = null;
                            fout = new FileOutputStream("USERS\\" + LogInMenu.FIND_PATHNAME() + "\\" + LogInMenu.FIND_PATHNAME() + ".pass");
                            //delete this
                            EncryptedRecordsList.remove ( 0 ) ;
                            for (int p = 0; p < UnencryptedRecordsList.size(); p++) 
                            {
                                //write all list
                                oos = new ObjectOutputStream(fout);
                                oos.writeObject(UnencryptedRecordsList.get(p));
                            }
                            oos.flush();
                            oos.close();
                            fout.close();
                            //ok
                            JOptionPane.showMessageDialog(rootPane, "Password deleted") ;
                        } 
                        catch (IOException IOE) 
                        {
                        }
                    }
                    }
                }
            }
        }
    }
 }


