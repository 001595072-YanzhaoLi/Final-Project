package com.example.finalporject.C;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.png.PngProcessingException;
import com.drew.imaging.riff.RiffProcessingException;
import com.drew.imaging.tiff.TiffProcessingException;
import com.example.finalporject.M.MBuilder;
import com.example.finalporject.M.ModelImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * image management tools controller
 *
 * @author Zhe Ding, Yanzhao Li
 * @date 2021/11/19
 */
public class Controller implements Initializable {
    final ObservableList<String> listItemsBefore = FXCollections.observableArrayList();
    final ObservableList<String> listItemsAfter = FXCollections.observableArrayList();
    private MBuilder model = new MBuilder.Builder().build();
    @FXML
    private ListView<String> listBefore;
    @FXML
    private ListView<String> listAfter;
    @FXML
    private Button downButton;
    @FXML
    private Button importButton;
    @FXML
    private TextField importURL;
    @FXML
    private TextField tarFor;
    @FXML
    private ImageView imageThumb;

    @FXML
    private TextArea textOutput;

    /**
     * click to import image to listItemsBefore
     *
     * @param action action
     */
    @FXML
    private void onClickImport(ActionEvent action) {
        String URL = importURL.getText();
        if (!listItemsBefore.contains(URL)) {
            int flag = model.setFile(URL);
            if (flag == 1) {
                listItemsBefore.add(URL);
                textOutput.setText("finish import");
            } else {
                textOutput.setText("invalid URL, file not exist");
            }
        } else {
            textOutput.setText("file already in the list");
        }
    }

    /**
     * click to delete image in listItemsBefore
     *
     * @param action action
     */
    @FXML
    private void onClickDel(ActionEvent action) {
        if (listBefore.getSelectionModel().getSelectedItem() == null) {
            textOutput.setText("please select a file in before processing list to delete");
            return;
        }
        int selectedItem = listBefore.getSelectionModel().getSelectedIndex();
        model.remove(listBefore.getSelectionModel().getSelectedItem());
        listItemsBefore.remove(selectedItem);
        textOutput.setText("finish delete");
        imageThumb.setImage(null);
    }

    /**
     * click to convert image format
     *
     * @param action action
     */
    @FXML
    private void onClickConvert(ActionEvent action) {
        if (listBefore.getSelectionModel().getSelectedItem() == null) {
            textOutput.setText("please select a file in before processing list to convert");
            return;
        }
        String U1 = listBefore.getSelectionModel().getSelectedItem();

        int dotI = U1.lastIndexOf('.');
        String U = U1.substring(0, dotI + 1) + tarFor.getText();
        if (!listItemsAfter.contains(U) && model.checkFromat(tarFor.getText())) {
            int flag = model.makeConversion(tarFor.getText(), U1);
            listItemsAfter.add(U);
            textOutput.setText("finish convert");
        } else if (!listItemsAfter.contains(U) && !model.checkFromat(tarFor.getText())) {
            textOutput.setText("invalid image format");
        } else {
            textOutput.setText("already have this image in after processing list");
        }
    }

    /**
     * click on list to show image and information
     *
     * @param arg0 arg0
     */
    @FXML
    private void ListClick(MouseEvent arg0) {
        String SelectedURL = listBefore.getSelectionModel().getSelectedItem();
        if (SelectedURL != null) {
            try {
                FileInputStream FIS = new FileInputStream(SelectedURL);
                Image image = new Image(FIS);
                imageThumb.setImage(image);
                textOutput.setText(model.getTag(SelectedURL));
            } catch (FileNotFoundException e) {
                textOutput.setText("file not find");
            } catch (JpegProcessingException e) {
                textOutput.setText("error in read details");
            } catch (IOException e) {
                textOutput.setText("error file");
            } catch (PngProcessingException e) {
                textOutput.setText("error in read details");
            } catch (TiffProcessingException e) {
                textOutput.setText("error in read details");
            } catch (RiffProcessingException e) {
                textOutput.setText("error in read details");
            }

        }

    }

    /**
     * click to download the image
     *
     * @param action 行动
     */
    @FXML
    private void onClickDown(ActionEvent action) {
        if (listAfter.getSelectionModel().getSelectedItem() == null) {
            textOutput.setText("please select a file in after processing list to download");
            return;
        }
        try {
            model.download(listAfter.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            textOutput.setText("download fail");
        }
        int selectedItem2 = listAfter.getSelectionModel().getSelectedIndex();
        System.out.println(listAfter.getSelectionModel().getSelectedItem());
        listItemsAfter.remove(selectedItem2);
        textOutput.setText("finish download");
    }

    /**
     * initialize the list
     *
     * @param url url
     * @param rb  rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listBefore.setItems(listItemsBefore);
        listAfter.setItems(listItemsAfter);


    }

}