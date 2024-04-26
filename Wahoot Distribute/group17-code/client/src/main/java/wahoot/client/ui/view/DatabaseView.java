package wahoot.client.ui.view;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import wahoot.client.ui.fxml.ResizeableFXMLView;
import wahoot.client.ui.view.navigator.TopView;
import wahoot.db.Manager;
import wahoot.db.models.Student;
import wahoot.db.models.User;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * view represents the database transactions from the admin's perspective
 */
public class DatabaseView extends ResizeableFXMLView<BorderPane> {

    @FXML
    private TextField inputField;

    private Manager manager;

    ObservableList<StudentBean> data;

    @FXML
    private Button addButton;


    @FXML
    private TableColumn<StudentBean, Long> IDColumn;

    @FXML
    private TableColumn<StudentBean, String> nameColumn;

    @FXML
    private TableColumn<StudentBean, String> typeColumn;

    @FXML
    private TableColumn<StudentBean, Long> scoreColumn;

    @FXML
    private TableColumn<StudentBean, String> emailColumn;

    @FXML
    private TableView<StudentBean> table;

    @FXML
    private BorderPane root;

    @FXML
    void add(MouseEvent event) {
        String command = inputField.getText();
       // String[] args = command.split(";");
            //Student s = new Student(Long.parseLong(args[0]), args[1], Integer.parseInt(args[2]));
            //manager.save(s);
            //table.getItems().add(new StudentBean(s.getID(), s.getName(), s.getAge()));


    }

    public static class StudentBean {
        private Student student;

        private final SimpleStringProperty name;
        private final SimpleStringProperty email;
        private final SimpleLongProperty id;
        private final SimpleLongProperty score;
        private final SimpleStringProperty accounttype;

        public StudentBean(long id, String name, long score, String email, String accounttype) {
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleLongProperty(id);
            this.score = new SimpleLongProperty(score);
            this.email = new SimpleStringProperty(email);
            this.accounttype = new SimpleStringProperty(accounttype);
        }

        public long getScore() {
            return score.get();
        }

        public String getEmail() {
            return email.get();
        }

        public String getAccounttype() {
            return accounttype.get();
        }

        public String getName() {
            return name.get();
        }

        public long getId() {
            return id.get();
        }

        public static List<StudentBean> translate(List<User> users) {
            List<StudentBean> list = new LinkedList<>();
            for(User user: users) {
                long score = 0;
                if(user instanceof Student s) {
                    score = s.getScore();
                }
                list.add(new StudentBean(user.getID(), user.getUsername(), score, user.getEmail(), user.getAccountType().getType()));
            }
            return list;
        }
    }



    public DatabaseView(Manager manager) {
        this.manager = manager;
        root.setTop(new TopView());

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("accounttype"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        data = FXCollections.observableList(StudentBean.translate(manager.getAllByClass(User.class)));

        new Thread(() -> {
            while(true) {
                List<User> list = manager.getAllByClass(User.class);
                Platform.runLater(() -> {
                    table.getItems().clear();
                    table.getItems().addAll(StudentBean.translate(list));
                    table.refresh();
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        table.getItems().addAll(data);
    }
}
