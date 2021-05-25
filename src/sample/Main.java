package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.plaf.synth.ColorType;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private Button[][] buttons = new Button[3][3];
    private boolean firstPlayerTurn;
    private AudioInputStream audioInputStream;
    public Stage popUpStage =  new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //POP UP

        GridPane gridPane1 = new GridPane();
        gridPane1.setPrefSize(350,350);
        gridPane1.setVgap(10);
        gridPane1.setHgap(10);
        gridPane1.setPadding(new Insets(10,10,10,10));
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.setBackground(new Background(new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Text firstPlayer = new Text("First Player:");
        firstPlayer.setFont(Font.font("Ravie", 15));
        TextField firstPlayerName = new TextField("firstPlayer");
        Text secondPlayer =new Text("Second Player:");
        secondPlayer.setFont(Font.font("Ravie", 15));
        TextField secondPlayerName = new TextField("secondPlayer");

        gridPane1.add(firstPlayer, 0, 0);
        gridPane1.add(firstPlayerName, 1, 0);
        gridPane1.add(secondPlayer, 0, 1);
        gridPane1.add(secondPlayerName, 1, 1);

        Button button1 = new Button("Start game");
        button1.setPrefSize(100, 50);
        BackgroundFill background_fill1 = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background1 = new Background(background_fill1);
        button1.setBackground(background1);
        button1.setTextFill(Color.WHITE);
        gridPane1.add(button1, 1, 2);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.show();
            }
        });

        Scene scene1 = new Scene(gridPane1);

        popUpStage.setTitle("Tic-Tac-Toe");
        popUpStage.setScene(scene1);
        popUpStage.show();

        //GAME

        GridPane gridPane=new GridPane();
        gridPane.setPrefSize(350,350);
        //a gombok kozott oldalt mekkora a res
        gridPane.setVgap(10);
        //a gombok kozott alul es felul mekkora a res
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        firstPlayerTurn = true;

        BackgroundFill background_fill = new BackgroundFill(Color.SKYBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setPrefSize(150, 150);

                int finalI = i;
                int finalJ = j;
                gridPane.add(buttons[i][j], j, i);

                buttons[finalI][finalJ].setFont(Font.font("Ravie", 40));
                buttons[finalI][finalJ].setBackground(background);
                buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(buttons[finalI][finalJ].getText().isEmpty()){
                            if(firstPlayerTurn){
                                primaryStage.setTitle(secondPlayerName.getText() + "'s turn.");
                                buttons[finalI][finalJ].setTextFill(Color.BLACK);
                                buttons[finalI][finalJ].setText("X");
                            } else{
                                primaryStage.setTitle(firstPlayerName.getText() + "'s turn.");
                                buttons[finalI][finalJ].setTextFill(Color.BLUE);
                                buttons[finalI][finalJ].setText("O");
                            }
                            firstPlayerTurn = !firstPlayerTurn;
                        }
//                        if(buttons[finalI][finalJ].getText().equals("X")){
//                            try {
//                                playSound();
//                            } catch (LineUnavailableException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (UnsupportedAudioFileException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                audioInputStream = AudioSystem.getAudioInputStream(new File("TicTacToeGame\\sound1.wav").getAbsoluteFile());
//                            } catch (UnsupportedAudioFileException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            String path = "TicTacToeGame\\sound1.wav";
//                            Media media = new Media(new File(path).toURI().toString());
//                            MediaPlayer mediaPlayer = new MediaPlayer(media);
//                            mediaPlayer.setAutoPlay(true);
//                        } else{
//                            try {
//                                audioInputStream = AudioSystem.getAudioInputStream(new File("TicTacToeGame\\sound2.wav").getAbsoluteFile());
//                            } catch (UnsupportedAudioFileException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
                        if(isWinner(finalI,finalJ)){
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("WINNER");
                            //a.setContentText("Winner: " + buttons[finalI][finalJ].getText());
                            if(buttons[finalI][finalJ].getText().equals("X")){
                                a.setContentText("Winner: " + firstPlayerName.getText());
                            } else{
                                a.setContentText("Winner: " + secondPlayerName.getText());
                            }
                            a.show();
                            gridPane.setDisable(true);
                        }else if(noWinner()){
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("NO WINNER");
                            a.setContentText("Try again, looser!");
                            a.show();
                            gridPane.setDisable(true);
                        }
                    }
                });
            }
        }

        Scene scene = new Scene(gridPane);

        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public boolean isWinner(int i, int j){
        int counter = 1;
        int l = j-1;
        int k = j+1;
        while(l>=0){
            if(buttons[i][l].getText().equals(buttons[i][j].getText())){
                ++counter;
            }else{
                break;
            }
            --l;
        }
        while(k<3){
            if(buttons[i][k].getText().equals(buttons[i][j].getText())){
                ++counter;
            }else{
                break;
            }
            ++k;
        }
        l = i-1;
        k = i+1;

        while(l>=0){
            if(buttons[l][j].getText().equals(buttons[i][j].getText())){
                ++counter;
            }else{
                break;
            }
            --l;
        }
        while(k<3){
            if(buttons[k][j].getText().equals(buttons[i][j].getText())){
                ++counter;
            }else{
                break;
            }
            ++k;
        }
        if(buttons[1][1].getText().equals(buttons[i][j].getText())){
            if(buttons[0][0].getText().equals(buttons[1][1].getText()) && (buttons[0][0].getText().equals(buttons[2][2].getText()))){
                return true;
            }
            if(buttons[0][2].getText().equals(buttons[1][1].getText()) && (buttons[0][2].getText().equals(buttons[2][0].getText()))){
                return true;
            }
        }

        if(counter==3){
            return true;
        }
        return false;
    }

    public boolean noWinner(){
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                if(!buttons[k][l].getText().isEmpty()){
                } else{
                    return false;
                }
            }
        }
        return true;
    }

//    private void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
//        AudioInputStream audio = null;
//        String path = "C:\\2. felev\\Java programozas\\TicTacToeGame\\sound1.wav";
//        audio = AudioSystem.getAudioInputStream(new File(path));
//        Clip clip = AudioSystem.getClip();
//        clip.open(audio);
//        clip.start();
//        audio.close();
//    }
}
