package io;
/*
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
// Class does not work
public class SaveGraph extends Application {

    final static int CANVAS_WIDTH = 400;
    final static int CANVAS_HEIGHT = 400;

    @Override
    public void start(final Stage primaryStage) {

        final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        Group root = new Group();

        Button buttonSave = new Button("SaveGraph");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();

                //Set extension filter
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);

                //Show save file dialog
                File file = fileChooser.showSaveDialog(primaryStage);

                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
                        canvas.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(SaveGraph.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(buttonSave);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, canvas);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 100, 100);
        primaryStage.setTitle("SaveGraph");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}*/