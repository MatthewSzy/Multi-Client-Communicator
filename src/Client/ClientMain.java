package Client;

import Client.Controller.ClientInterfaceController;
import Resources.UserInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientMain extends Application
{
    private static List<String> online_users = Collections.synchronizedList(new ArrayList<String>());
    private static List<String> message_List = Collections.synchronizedList(new ArrayList<String>());
    private static UserInfo client_Data;

    public static void main(String[] args)
    {
        try
        {
            System.out.println("Connecting to Server...");
            Socket client = new Socket("localhost", 5100);
            System.out.println("Connected to Server");
            DataOutputStream data_Send = new DataOutputStream(client.getOutputStream());
            data_Send.writeUTF(args[0]);

            client_Data = new UserInfo(args[0], client);

            Thread client_Receive = new ClientReceive(client_Data, online_users, message_List);
            client_Receive.start();

            launch(args);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/Interface/ClientInterface.fxml"));
        AnchorPane anchorPane = loader.load();
        primaryStage.setTitle(client_Data.getUserName() + "'s Echo");

        ClientInterfaceController sceneControl = loader.getController();
        sceneControl.getOnlineUsersList(online_users);
        sceneControl.getMessageList(message_List);
        sceneControl.getUserInfo(client_Data);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
