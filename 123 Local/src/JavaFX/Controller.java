package JavaFX;

import Connectivity.ConnectionClass;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();
    Statement statement;

    {try {statement = connection.createStatement();} catch (SQLException throwables) {throwables.printStackTrace();}}
    public String sql;
    public ResultSet resultSet;

    int el = 0;

@FXML
    public TextField id = new TextField();
    public TextField count= new TextField();
    public TextArea description= new TextArea();
    public TextField short_name = new TextField();
    public TextField market_price= new TextField();
    public TextField pictureLink=new TextField();
    public ImageView picture = new ImageView();

    public Button closeButton;
    public Button minimaizeButton;
    public TextField login;
    public PasswordField pass;
    public Button log_button;
    public ImageView profile;

    public Button PrevTb;
    public Button NextTb;

    public Button confirm_ad;
    public Button confirm_ed;
    public ImageView clear_tab;
    public ImageView close_te;

    public FlowPane main_content;

    String[] tables = {"department1" , "department2", "department3"};

    /**
     * Class User consist all information about autorisation user;
     */
    static class User{
        public String username;
        private boolean adminPriv;
        public String table;

        public void setUsername(String username){
            this.username = username;
        }
        public void setTable(String table){
            if(table==null) {
                this.table = "department1";
            } else{
                this.table = table;
            }
        }

        public void setAdminPriv(boolean adminPriv) {
            this.adminPriv = adminPriv;
        }

        public String getUsername(){
            return username;
        }
        public String getTable(){
            return table;
        }
        public boolean isAdminPriv() {
            return adminPriv;
        }

        public void setNull(){
            username= null;
            adminPriv= false;
            table = null;
        }
    }
    User user = new User();

    /**
     * Class Content_class - one element of list, consist all information about one element;
     */
    class Content_class{
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement;

        {try {statement = connection.createStatement();} catch (SQLException throwables) {throwables.printStackTrace();}}
        public String sql1;
        public ResultSet resultSet1;

        public Button main_content_el;
        int idClass;
        String short_nameClass = "";
        int countClass = 0;
        double market_priceClass = 0;
        String descriptionClass = "";
        Image pictureClass = null;
        String pictureLinkClass = null;


        public Content_class(){

        }

        public Content_class(int i) throws SQLException {
            idClass = i;
            setShort_nameClass();
            setCountClass();
            setPriceClass();
            setPictureClass();
            setDescriptionClass();

        }

        public Content_class(String short_name1, int count1, double market_price1, String pictureLink1, String description1) throws SQLException {
            sql1 = "INSERT INTO "+user.getTable()+"(short_name, market_price, count, picture, description) VALUES ('"+short_name1+"',"+market_price1+", "+count1+",'"+pictureLink1+"','"+description1+"')";
            statement.executeUpdate(sql1);
            short_nameClass = short_name1;
            setIdClass();
            countClass = count1;
            market_priceClass = market_price1;
            pictureLinkClass = pictureLink1;
            descriptionClass = description1;
        }
        void setIdClass() throws SQLException {
            sql1 = "SELECT id FROM "+user.getTable() +" WHERE short_name = '" + short_nameClass + "'";
            resultSet1 = statement.executeQuery(sql1);    //get id from mysql
            resultSet1.next();
            idClass = resultSet1.getInt(1);
        }
        void setShort_nameClass() throws SQLException {
            sql1 = "SELECT short_name FROM "+user.getTable() +" WHERE id = " + idClass;
            resultSet1 = statement.executeQuery(sql1);
            resultSet1.next();
            short_nameClass = resultSet1.getString(1);
        }
        void setCountClass() throws SQLException {
            sql1 = "SELECT count FROM "+user.getTable() +" WHERE short_name = '" + short_nameClass + "'";
            System.out.println(sql1);
            resultSet1 = statement.executeQuery(sql1);    //get count from mysql
            resultSet1.next();
            System.out.println(resultSet1.getInt(1));
            countClass = resultSet1.getInt(1);
        }
        void setPriceClass() throws SQLException {
            sql1 = "SELECT market_price FROM "+user.getTable() +" WHERE short_name = '" + short_nameClass + "'";
            resultSet1 = statement.executeQuery(sql1);    //get price from mysql
            resultSet1.next();
            market_priceClass = resultSet1.getDouble(1);
        }
        void setPictureClass() throws SQLException {
            sql1 = "SELECT picture FROM "+user.getTable() +" WHERE short_name = '" + short_nameClass + "'";
            resultSet1 = statement.executeQuery(sql1);    //get pic from mysql
            resultSet1.next();
            pictureLinkClass = resultSet1.getString(1);
        }
        void setDescriptionClass() throws SQLException {
            sql1 = "SELECT description FROM "+user.getTable() +" WHERE short_name = '" + short_nameClass + "'";
            resultSet1 = statement.executeQuery(sql1);    //get desc from mysql
            resultSet1.next();
            descriptionClass = resultSet1.getString(1);
        }









        void setAll() throws SQLException {
            short_name.setText(short_nameClass);
            id.setText(String.valueOf(idClass));
            count.setText(String.valueOf(countClass));
            market_price.setText(String.valueOf(market_priceClass));
            if(pictureLinkClass!=null){
                try {
                    pictureClass = new Image(pictureLinkClass);
                    picture.setImage(pictureClass);
                    pictureLink.setText(pictureLinkClass);
                } catch (Exception e) {
//                    System.out.println("Не подходящий формат");
                }
            }
            description.setText(descriptionClass);
        }

        void buttCreate(){
            main_content_el = new Button(short_nameClass);
            main_content_el.setPrefSize(210, 35);
        }

        Button getButt(){
            return main_content_el;
        }
    }

    public List<Content_class> element_list = new ArrayList<Content_class>();
    //buttons

    public void clear_table(MouseEvent mouseEvent) {
        IconSetNULL();
    }
    public void close_text(MouseEvent mouseEvent) {
        IconSetNULL();
        ChangeON_OFF(false);
    }
    public void Confirm_add(ActionEvent actionEvent) throws SQLException {

        element_list.add(new Content_class(short_name.getText(), Integer.valueOf(count.getText()), Double.valueOf(market_price.getText()), pictureLink.getText(), description.getText()));
        int i = element_list.size()-1;
        element_list.get(i).buttCreate();  //create element with name

        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(0.25));
        ft.setNode(element_list.get(i).getButt());
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);

        element_list.get(i).getButt().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                try {
                    element_list.get(i).setAll();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        main_content.getChildren().addAll(element_list.get(i).getButt());   //add element

        ft.play();
        ChangeON_OFF(false);
    }

    public void Confirm_edit(ActionEvent actionEvent) throws SQLException {
        sql = "UPDATE `"+user.getTable()+"` SET short_name = '"+short_name.getText()+"', market_price = '"+Double.parseDouble(market_price.getText())+"', count = '"+ Integer.parseInt(count.getText())+"', picture = '"+pictureLink.getText()+"', description = '"+description.getText()+"' WHERE "+user.getTable()+".id = '"+id.getText()+"'";
        System.out.println(sql);
        statement.executeUpdate(sql);
        LoadContent(false);
        LoadContent(true);
        ChangeON_OFF(false);
    }

    public void add(ActionEvent actionEvent) throws SQLException {
        ChangeON_OFF(true);
        confirm_ed.setVisible(false);
    }
    public void del(ActionEvent actionEvent) throws SQLException {
        sql = "DELETE FROM "+user.getTable()+" WHERE short_name = '"+short_name.getText()+"' AND id="+id.getText();
        statement.executeUpdate(sql);
        LoadContent(false);
        LoadContent(true);
    }

    public void edit(ActionEvent actionEvent) {
        ChangeON_OFF(true);
        confirm_ad.setVisible(false);
    }
    public void minimaize_but(ActionEvent actionEvent) {
        Stage stage = (Stage) minimaizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
    //b
    public void close_but(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    //b
    public void prev_press(ActionEvent actionEvent) {
    }
    //b
    public void next_press(ActionEvent actionEvent) throws SQLException {
    }
    //b
    public void prev_table(ActionEvent actionEvent) throws SQLException {
        IconSetNULL();
        int i = tables.length;
        while(!user.getTable().equals(tables[i])){
            i--;

        }
        if(i==0-1){
            i=tables.length;
        } else {
            i--;
        }
        user.setTable(tables[i]);
        LoadContent(false);
        LoadContent(true);
    }
    public void plus(ActionEvent actionEvent) throws SQLException {
        int data = Integer.valueOf(count.getText())+1;
        sql = "UPDATE `"+user.getTable()+"` SET `count` = '"+data+"' WHERE "+user.getTable()+".id = '"+id.getText()+"'";
        int st = statement.executeUpdate(sql);
        count.setText(String.valueOf(data));
    }

    public void minus(ActionEvent actionEvent) throws SQLException {
        int data = Integer.valueOf(count.getText())-1;
        sql = "UPDATE `"+user.getTable()+"` SET `count` = '"+data+"' WHERE "+user.getTable()+".id = '"+id.getText()+"'";
        int st = statement.executeUpdate(sql);
        count.setText(String.valueOf(data));
    }
    //b
    int i = 0;
    public void next_table(ActionEvent actionEvent) throws SQLException {
        IconSetNULL();
        int i = 0;
        while(!user.getTable().equals(tables[i])){
            i++;

        }
        if(i==tables.length-1){
            i=0;
        } else {
            i++;
        }
        user.setTable(tables[i]);
        LoadContent(false);
        LoadContent(true);
    }
    //b
    public void menu_press(ActionEvent actionEvent) {

    }
    //b
    public void log_in_out(ActionEvent actionEvent) throws SQLException, InterruptedException {
        if(log_button.getText().equals("Вхід")){
            sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                if(resultSet.getString(2).equals(login.getText())&&resultSet.getString(3).equals(pass.getText())){
                    //check whether the login corresponds to at least one of the data in the database
                    AnimationProfile(1);
                    login.setDisable(true);
                    pass.setDisable(true);
                    user.setUsername(resultSet.getString(2));
                    user.setAdminPriv(resultSet.getBoolean(4));
                    user.setTable(resultSet.getString(5));

                    log_button.setText("Вихід");

                    LoadContent(true);

                    break;
                } else {
                    AnimationProfile(0);
                }
            }
        } else{

            AnimationProfile(3);

            login.setDisable(false);
            pass.setDisable(false);
            SetMySQLTable();
            user.setNull();

            IconSetNULL();

            log_button.setText("Вхід");

            LoadContent(false);

        }
    }
//end_buttons

    //functions
    public void LoadContent(boolean in) throws SQLException {
        if(in) {
            sql = "SELECT * FROM "+user.getTable()+" ORDER BY short_name ASC";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            element_list.clear();
            int i = 0;
            do {     //add database elements to main_content FlowPane

                int num = resultSet.getInt(1);


                element_list.add(new Content_class(num));  //create obj
                element_list.get(i).buttCreate();  //create element with name


                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(0.25));
                ft.setNode(element_list.get(i).getButt());
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setCycleCount(1);

                int finalI = i;

                element_list.get(i).getButt().setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e){
                        try {
                            IconSetNULL();
                            element_list.get(finalI).setAll();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                main_content.getChildren().addAll(element_list.get(i).getButt());   //add element

                ft.play();
                i++;
            } while (resultSet.next());
        } else {
            main_content.getChildren().clear();
            element_list.clear();

        }
    }

    public void ChangeON_OFF(boolean in){
            count.setEditable(in);
            short_name.setEditable(in);
            market_price.setEditable(in);
            pictureLink.setEditable(in);
            description.setEditable(in);
            confirm_ad.setVisible(in);
            confirm_ed.setVisible(in);
            close_te.setVisible(in);
    }
    public void IconSetNULL(){
        id.setText(null);
        count.setText(null);
        description.setText(null);
        short_name.setText(null);
        market_price.setText(null);
        pictureLink.setText(null);
        picture.setImage(null);
    }

    public void AnimationProfile(int in) throws InterruptedException {
        Image img=null;
        if(in==1){
            img = new Image("/JavaFX/Photo/User_Icon_Correct.png");
        } else if(in==0) {
            img = new Image("/JavaFX/Photo/User_Icon_Incorrect.png");
        } else {
            img = new Image("/JavaFX/Photo/User_Icon_Zero.png");
        }
        profile.setImage(img);
    }

    public void SetMySQLTable() throws SQLException {
        sql = "UPDATE `users` SET `last_table` = '"+user.getTable()+"' WHERE users.username = '"+user.getUsername()+"'";
        int st = statement.executeUpdate(sql);
    }
}

