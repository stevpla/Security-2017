
import java.io.Serializable;


public class UserNameAuthHash implements Serializable
{
    private String Username ;
    private byte [ ] HashedValue ;
    
    public UserNameAuthHash ( String Username, byte [ ] HashedValue )
    {
        this.Username = Username ;
        this.HashedValue = HashedValue ;
    }
    
    public String GetUserName ( )
    {
        return this.Username ;
    }
    
    public byte [ ] GetHashedValue ( )
    {
        return this.HashedValue ;
    }
    
    
    @Override
    public String toString ( )
    {
        return "UserName-AuthHash : " + this.Username + ", " + this.HashedValue + "\n " ;
    }
}
