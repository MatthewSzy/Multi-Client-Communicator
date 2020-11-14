package Server;

import Resources.UserInfo;
import Server.Controller.ServerInterfaceController;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServerMain extends Application
{
    private static List<UserInfo> online_Users = Collections.synchronizedList(new ArrayList<UserInfo>());
    private static List<String> message_List = Collections.synchronizedList(new ArrayList<String>());

    public static void main(String[] args)
    {
        try
        {
            ServerSocket Server = new ServerSocket(5100);
            System.out.println("Server is running");
            Thread server_Thread = new ServerThread(Server, online_Users, message_List);
            server_Thread.start();

            launch(args);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Server/Interface/ServerInterface.fxml"));
        AnchorPane anchorPane = loader.load();
        primaryStage.setTitle("Server Echo");

        ServerInterfaceController sceneControl = loader.getController();
        sceneControl.getOnlineUsersList(online_Users);
        sceneControl.getMessageList(message_List);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
