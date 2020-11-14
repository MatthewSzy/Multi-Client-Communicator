package Server.Controller;

import Resources.UserInfo;
import Server.ServerSender;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerInterfaceController implements Initializable
{
    private static List<UserInfo> online_users;
    private static List<String> message_list;
    private static UserInfo client_Info;

    @FXML
    private TextField InputTextID = new TextField();

    @FXML
    private Button SendButtonID = new Button();

    @FXML
    private ListView MessageListViewID = new ListView();

    @FXML
    private ListView UserListViewID = new ListView();

    public void getOnlineUsersList(List<UserInfo> list)
    {
        online_users = list;
    }

    public void getMessageList(List<String> list)
    {
        message_list = list;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Thread refresh_List_User = new Thread(this::refreshUsersList);
        refresh_List_User.setDaemon(true);
        refresh_List_User.start();

        Thread init_Action = new Thread(this::setMessageView);
        init_Action.setDaemon(true);
        init_Action.start();
    }

    public void refreshUsersList()
    {
        ArrayList<String> listOfUsers = new ArrayList<>();
        while (true)
        {
            Platform.runLater(() -> {
                listOfUsers.clear();
                if(online_users.size() > 0)
                {
                    for (UserInfo x : online_users)
                    {
                        listOfUsers.add(x.getUserName());
                    }
                    ObservableList<String> uList = FXCollections.observableArrayList(listOfUsers);
                    UserListViewID.setItems(uList);
                }
            });

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setMessageView()
    {
        ArrayList<String> listOfmessage = new ArrayList<>();
        while (true)
        {
            Platform.runLater(() -> {
                listOfmessage.clear();
                if(message_list.size() > 0)
                {
                    for (String x : message_list)
                    {
                        listOfmessage.add(x);
                    }
                    ObservableList<String> uList = FXCollections.observableArrayList(listOfmessage);
                    MessageListViewID.setItems(uList);
                }
            });

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendMessage(MouseEvent mouseEvent)
    {
        if(InputTextID.getText().trim().isEmpty() == false)
        {
            LocalTime time = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String message = "Server" + "(" + time.format(formatter) + "): " + InputTextID.getText();
            InputTextID.clear();
            message_list.add(message);

            for(int i=0; i<online_users.size(); i++)
            {
                Thread server_Sender = new ServerSender(online_users.get(i), message);
                server_Sender.start();
            }
        }
    }
}
