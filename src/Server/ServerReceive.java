package Server;

import Resources.UserInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class ServerReceive extends Thread
{
    final List<UserInfo> online_users;
    final List<String> message_list;
    final UserInfo client_Info;

    public ServerReceive (List<UserInfo> list1, UserInfo client, List<String> list_Message)
    {
        this.online_users = list1;
        this.client_Info = client;
        this.message_list = list_Message;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                DataInputStream data_receive = new DataInputStream(client_Info.getUserSocket().getInputStream());

                String message = data_receive.readUTF();
                message_list.add(message);

                for(int i=0; i<online_users.size(); i++)
                {
                    Thread server_Sender = new ServerSender(online_users.get(i), message);
                    server_Sender.start();
                }
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }

        }
    }
}
