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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);


    //  Name
        Text nameTitle = new Text ("Applicant Name:");
        grid.add(nameTitle,0,0);
        final TextField name = new TextField();
        grid.add(name,0,1,3,1);
    //  Address
        Text addresssTitle = new Text("Applicant Address:");
        grid.add(addresssTitle,0,2);
        final TextField address = new TextField();
        grid.add(address,0,3,3,1);
    //  Parcel
        Text parcelNumTitle = new Text ("County Parcel Number:");
        grid.add(parcelNumTitle,0,4);
        final TextField parcelNum = new TextField();
        grid.add(parcelNum,0,5,3,1);
    //  Permit #
        Text permitNumTitle = new Text ("City Permit Number:");
        grid.add(permitNumTitle,0,6);
        final TextField permitNum = new TextField();
        grid.add(permitNum,0,7,3,1);

        //  Construction Type
        Text buildType = new Text("Construction Type(s):");
        grid.add(buildType,0,8);

        final CheckBox[] cb = new CheckBox[7];
        cb[0] = new CheckBox("build");
        grid.add(cb[0],0,9);
        cb[1] = new CheckBox("erect");
        grid.add(cb[1],0,10);
        cb[2] = new CheckBox("install");
        grid.add(cb[2],0,11);
        cb[3] = new CheckBox("add to");
        grid.add(cb[3],0,12);

        cb[4] = new CheckBox("repair");
        grid.add(cb[4],1,9);
        cb[5] = new CheckBox("move");
        grid.add(cb[5],1,10);
        cb[6] = new CheckBox("wreck");
        grid.add(cb[6],1,11);




        primaryStage.setTitle("City Permit Builder");

//        CreatePDF.main(
//                "Houston Bang",
//                "820-55",
//                "2018-289",
//                "82 Lincoln Blvd");
        Button button = new Button("Create!");
        button.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                Stats permitApplication = new Stats(
                        name.getText(),
                        parcelNum.getText(),
                        permitNum.getText(),
                        address.getText(),
                        populateBuild(cb)
                );
                CreatePDF.main(permitApplication);
            }
        });
        grid.add(button,2,9);


        for (int i=1 ; i<20 ; i++) {
            if (i % 2 == 0) {
                grid.getRowConstraints().add(new RowConstraints(0));
            } else {
                grid.getRowConstraints().add(new RowConstraints(50));
            }
        }
        grid.getColumnConstraints().add(new ColumnConstraints(120));

        primaryStage.setScene(new Scene(grid, 360, 375));

        primaryStage.setMinHeight(80);
        primaryStage.setMinWidth(200);
        primaryStage.setMaxWidth(550);
        primaryStage.setMaxHeight(550);
        primaryStage.show();
//        Platform.exit();// Closes application ^.^
    }

    public static String[] populateBuild(CheckBox[] array) {
        LinkedList<String> truths = new LinkedList<String>();


        for (CheckBox tar: array) {
            if (tar.isSelected()) {
                truths.add(tar.getText());
            }
        }
        if (truths.isEmpty()) { return null; }

        String[] magi = new String[truths.size()];
        for (int i = 0 ; !truths.isEmpty() || i < magi.length ; i++) {
            magi[i] = truths.remove();
        }
        return magi;
    }
}