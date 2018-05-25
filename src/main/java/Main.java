import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);

        grid.setHgap(10);
//        grid.setPadding(new Insets(20,20,20,20));


    //  Name
        Text nameTitle = new Text ("Applicant Name:");
        grid.add(nameTitle,0,0);
        final TextField name = new TextField();
        grid.add(name,0,1);
    //  Address
        Text addresssTitle = new Text("Applicant Address:");
        grid.add(addresssTitle,0,2);
        TextField address = new TextField();
        grid.add(address,0,3);
    //  Parcel
        Text parcelNumTitle = new Text ("County Parcel Number:");
        grid.add(parcelNumTitle,0,4);
        final TextField parcelNum = new TextField();
        grid.add(parcelNum,0,5);
    //  Permit #
        Text permitNumTitle = new Text ("City Permit Number:");
        grid.add(permitNumTitle,0,6);
        final TextField permitNum = new TextField();
        grid.add(permitNum,0,7);




        primaryStage.setTitle("City Permit Builder");

//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
                CreatePDF.main(
                        "Houston Bang",
                        "820-55",
                        "2018-289",
                        "82 Lincoln Blvd");
//            }
//        });
        Button button = new Button("Create!");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println(name.getCharacters());
            }
        });
        grid.add(button,1,9);


        for (int i=1 ; i<20 ; i++) {
            if (i % 2 == 0) {
                grid.getRowConstraints().add(new RowConstraints(0));
            } else {
                grid.getRowConstraints().add(new RowConstraints(50));
            }
        }
        grid.getColumnConstraints().add(new ColumnConstraints(280));

        primaryStage.setScene(new Scene(grid, 400, 400));

        primaryStage.setMinHeight(80);
        primaryStage.setMinWidth(200);
        primaryStage.setMaxWidth(550);
        primaryStage.setMaxHeight(550);
        primaryStage.show();
//        Platform.exit();// Closes application ^.^
    }
}