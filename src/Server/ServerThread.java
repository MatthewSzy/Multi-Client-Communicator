package Server;

import Resources.UserInfo;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerThread extends Thread
{
    final List<UserInfo> online_Users;
    final List<String> message_list;
    final ServerSocket server;

    public ServerThread(ServerSocket server, List<UserInfo> list_Users, List<String> list_Message)
    {
        this.server = server;
        this.online_Users = list_Users;
        this.message_list = list_Message;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("Waiting for the client...");
                Socket client = server.accept();
                System.out.println("Connection from " + client.getLocalPort() + client.getLocalAddress());

                DataInputStream data_Receive = new DataInputStream(client.getInputStream());

                String user_Name = data_Receive.readUTF();
                UserInfo new_User = new UserInfo(user_Name, client);
                this.online_Users.add(new_User);

                Thread server_ListSender = new ServerListSender(online_Users);
                server_ListSender.start();

                Thread server_Receive = new ServerReceive(online_Users, new_User, message_list);
                server_Receive.start();
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
}
