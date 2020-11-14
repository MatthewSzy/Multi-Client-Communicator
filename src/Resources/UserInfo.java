package Resources;

import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class UserInfo implements Serializable
{
    private String user_Name;
    private Socket user_Socket;

    public UserInfo(String name, Socket so)
    {
        this.user_Name = name;
        this.user_Socket = so;
    }

    public String getUserName()
    {
        return this.user_Name;
    }

    public Socket getUserSocket()
    {
        return this.user_Socket;
    }
}
