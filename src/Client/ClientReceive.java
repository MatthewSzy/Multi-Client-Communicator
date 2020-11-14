package Client;

import Resources.UserInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class ClientReceive extends Thread
{
    final List<String> online_users;
    final List<String> message_list;
    final UserInfo client_Info;

    public ClientReceive (UserInfo client, List<String> list1, List<String> list_Message)
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
                DataInputStream data_Receive = new DataInputStream(client_Info.getUserSocket().getInputStream());

                String type = data_Receive.readUTF();
                if(type.equals("send_List"))
                {
                    int number = data_Receive.readInt();
                    online_users.clear();
                    for(int i = 0; i < number; i++)
                    {
                        online_users.add(data_Receive.readUTF());
                    }
                }
                else if(type.equals("send_Message"))
                {
                    String message = data_Receive.readUTF();
                    message_list.add(message);
                }
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}

