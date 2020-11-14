package Server;

import Resources.UserInfo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ServerListSender extends Thread
{
    final List<UserInfo> online_Users;

    public ServerListSender(List<UserInfo> list)
    {
        this.online_Users = list;
    }

    @Override
    public void run()
    {
        try
        {
            for(int i = 0; i < online_Users.size(); i++)
            {
                DataOutputStream data_Send = new DataOutputStream(online_Users.get(i).getUserSocket().getOutputStream());
                data_Send.writeUTF("send_List");
                data_Send.flush();
                data_Send.writeInt(online_Users.size());
                data_Send.flush();

                for(int j = 0; j < online_Users.size(); j++)
                {
                    data_Send.writeUTF(online_Users.get(j).getUserName());
                    data_Send.flush();
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}

