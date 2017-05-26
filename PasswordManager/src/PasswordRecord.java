
import java.io.Serializable;


class PasswordRecord implements Serializable
{
    private String userName, comment, domain, password ;
    
    public PasswordRecord ( String userName, String comment, String domain, String password )
    {
        this.comment = comment ;
        this.domain = domain ;
        this.password = password ;
        this.userName = userName ;
    }
    
    public String GetuserName ( )
    {
        return userName ;
    }
    
    public String Getcomment ( )
    {
        return comment ;
    }
    
    public String Getdomain ( )
    {
        return domain ;
    }
    
    public String Getpassword ( )
    {
        return password ;
    }
    
    @Override
    public String toString ( )
    {
        return " Domain -> " + domain + ", Username -> " + userName + ", Password -> " + password + ", Comment -> " + comment ;
    }
}
