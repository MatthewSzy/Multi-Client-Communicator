package Server;

import Resources.UserInfo;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerSender extends Thread
{
    final String message;
    final UserInfo client_Info;

    public ServerSender (UserInfo client, String Message)
    {
        this.client_Info = client;
        this.message = Message;
    }

    @Override
    public void run()
    {
        try
        {
            DataOutputStream data_Send = new DataOutputStream(client_Info.getUserSocket().getOutputStream());

            data_Send.writeUTF("send_Message");
            data_Send.flush();

            data_Send.writeUTF(message);
            data_Send.flush();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
