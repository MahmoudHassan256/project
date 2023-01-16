/**
 * Sample Skeleton for 'reserveadmin.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLot;
import il.cshaifasweng.OCSFMediatorExample.entities.Reservation;
import il.cshaifasweng.OCSFMediatorExample.entities.Worker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReserveAdminController {
    private static List<ParkingLot> parkingLots;
    private static Worker worker;

    public static Worker getWorker() {
        return worker;
    }

    public static void setWorker(Worker worker) {
        ReserveAdminController.worker = worker;
    }

    public static List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public static void setParkingLots(List<ParkingLot> parkingLots) {
        ReserveAdminController.parkingLots = parkingLots;
    }

    List<String> hoursList = Arrays.asList("00","01","02","03","04","05","06","07","08",
            "09","10","11","12","13","14","15","16","17","18","19","20","21","22","23");
    List <String> minutesList = Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13",
            "14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33",
            "34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52",
            "53","54","55","56","57","58","59");
    List<String> monthLst = Arrays.asList("01","02","03","04,","05","06","07","08,","09","10","11","12");
    List<String> yearLst = Arrays.asList("23","24","25","26","27","28");


    @FXML // fx:id="aririvalDate"
    private DatePicker aririvalDate; // Value injected by FXMLLoader

    @FXML // fx:id="arrivalHour"
    private ComboBox<String> arrivalHour; // Value injected by FXMLLoader

    @FXML // fx:id="arrivalMinute"
    private ComboBox<String> arrivalMinute; // Value injected by FXMLLoader

    @FXML // fx:id="btnBack"
    private Button btnBack; // Value injected by FXMLLoader

    @FXML // fx:id="btnReserve"
    private Button btnReserve; // Value injected by FXMLLoader

    @FXML // fx:id="cbOneTimer"
    private CheckBox cbOneTimer; // Value injected by FXMLLoader

    @FXML // fx:id="cbSubscriber"
    private CheckBox cbSubscriber; // Value injected by FXMLLoader

    @FXML // fx:id="departureDate"
    private DatePicker departureDate; // Value injected by FXMLLoader

    @FXML // fx:id="departureHour"
    private ComboBox<String> departureHour; // Value injected by FXMLLoader

    @FXML // fx:id="departureMinute"
    private ComboBox<String> departureMinute; // Value injected by FXMLLoader

    @FXML // fx:id="expirationMonth"
    private ComboBox<String> expirationMonth; // Value injected by FXMLLoader

    @FXML // fx:id="expirationYear"
    private ComboBox<String> expirationYear; // Value injected by FXMLLoader

    @FXML // fx:id="left"
    private VBox left; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLotComboBox"
    private ComboBox<Integer> parkingLotComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="right"
    private VBox right; // Value injected by FXMLLoader

    @FXML // fx:id="tfCVV"
    private TextField tfCVV; // Value injected by FXMLLoader

    @FXML // fx:id="tfCardNumber"
    private TextField tfCardNumber; // Value injected by FXMLLoader

    @FXML // fx:id="tfCardOwnerID"
    private TextField tfCardOwnerID; // Value injected by FXMLLoader

    @FXML // fx:id="tfEmail"
    private TextField tfEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tfID"
    private TextField tfID; // Value injected by FXMLLoader

    @FXML // fx:id="tfLicense"
    private TextField tfLicense; // Value injected by FXMLLoader

    @FXML // fx:id="tfSubscribtionID"
    private TextField tfSubscribtionID; // Value injected by FXMLLoader

    @FXML
    void cbOneTimerSelected(ActionEvent event) {
        if(cbOneTimer.isSelected())
        {
            cbSubscriber.setSelected(false);
            tfSubscribtionID.setDisable(true);
            tfCardNumber.setDisable(false);
            expirationMonth.setDisable(false);
            expirationYear.setDisable(false);
            tfCVV.setDisable(false);
            tfCardOwnerID.setDisable(false);
        }
        else
        {
            expirationMonth.setDisable(true);
            expirationYear.setDisable(true);
            tfCVV.setDisable(true);
            tfCardOwnerID.setDisable(true);
        }

    }

    @FXML
    void cbSubscriberSelected(ActionEvent event) {
        if(cbSubscriber.isSelected())
        {
            tfSubscribtionID.setDisable(false);
            cbOneTimer.setSelected(false);
            tfCardNumber.setDisable(true);
            expirationMonth.setDisable(true);
            expirationYear.setDisable(true);
            tfCVV.setDisable(true);
            tfCardOwnerID.setDisable(true);
        }
        else
            tfSubscribtionID.setDisable(true);

    }

    @FXML
    void gotoprimary(ActionEvent event) throws IOException {
         SimpleClient.getClient().sendToServer(new Message("#ShowAdminPageRequset",worker));
    }
    @FXML
    void reserveParkingLot(ActionEvent event) {
//check if all data is inserted + if subscribed
        LocalDateTime arrivalDate = LocalDateTime.of(aririvalDate.getValue().getYear(), aririvalDate.getValue().getMonth(),
                aririvalDate.getValue().getDayOfMonth(), Integer.parseInt(arrivalHour.getValue()),Integer.parseInt(arrivalMinute.getValue()));
        LocalDateTime departureDate1 = LocalDateTime.of(departureDate.getValue().getYear(), departureDate.getValue().getMonth(),
                departureDate.getValue().getDayOfMonth(), Integer.parseInt(departureHour.getValue()),Integer.parseInt(departureMinute.getValue()));
        if(!cbSubscriber.isSelected() && !cbOneTimer.isSelected())
        {
            //add error Message
            //show id of reservation if ok
        }
        else if(cbSubscriber.isSelected())
        {
            Reservation reservation = new Reservation(tfID.getText(), tfLicense.getText(),
                    parkingLotComboBox.getValue(),arrivalDate, departureDate1, tfEmail.getText(),"Subscriber", tfSubscribtionID.getText());
            try {
                SimpleClient.getClient().sendToServer(new Message("#AddReservationRequest", reservation));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        SimpleClient.getClient().sendToServer(new Message("#ShowAdminPageRequset",worker));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },1000);

        }
        else if (cbOneTimer.isSelected())
        {
            LocalDate expirationD = LocalDate.of(Integer.parseInt(expirationYear.getValue()), Integer.parseInt(expirationMonth.getValue()),1);
            Reservation reservation = new Reservation(tfID.getText(),tfLicense.getText(),
                    parkingLotComboBox.getValue(),arrivalDate, departureDate1, tfEmail.getText(),"one-Timer",
                    tfCardNumber.getText(),expirationD ,tfCVV.getText(),tfCardOwnerID.getText());
            try {
                SimpleClient.getClient().sendToServer(new Message("#AddReservationRequest", reservation));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        SimpleClient.getClient().sendToServer(new Message("#ShowAdminPageRequset",worker));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            },1000);



        }

    }
    public void initialize(){
        arrivalHour.getItems().clear();
        arrivalHour.setItems(FXCollections.observableArrayList(hoursList));
        departureHour.getItems().clear();
        departureHour.setItems(FXCollections.observableArrayList(hoursList));
        arrivalMinute.getItems().clear();
        arrivalMinute.setItems(FXCollections.observableArrayList(minutesList));
        departureMinute.getItems().clear();
        departureMinute.setItems(FXCollections.observableArrayList(minutesList));
        expirationMonth.getItems().clear();
        expirationMonth.setItems(FXCollections.observableArrayList(monthLst));
        expirationYear.getItems().clear();
        expirationYear.setItems(FXCollections.observableArrayList(yearLst));
        for(ParkingLot ParkingLot: parkingLots)
        {
            parkingLotComboBox.getItems().addAll(ParkingLot.getId());
        }
    }

}