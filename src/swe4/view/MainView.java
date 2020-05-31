package swe4.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import swe4.model.DataModel;

public class MainView {

  public static Scene create(Stage stage) {
    BorderPane mainLayout = new BorderPane();
    mainLayout.setTop(mainMenuButtons(stage));
    mainLayout.setCenter(mainMenuTabs());
    return new Scene(mainLayout, 800, 400);
  }

  private static HBox mainMenuButtons(Stage stage) {
    HBox mainMenuButtons = new HBox();
    Label lastSavedLabel = new Label("Zuletzt gespeichert um 11:34 Uhr");
    Button saveButton = new Button("Speichern");
    Button openButton = new Button("Öffnen");
    Button logoutButton = new Button("Ausloggen");
    logoutButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
          LoginView.create(stage);
        }
      }
    );
    mainMenuButtons.getChildren().addAll(lastSavedLabel, saveButton, openButton, logoutButton);
    return mainMenuButtons;
  }

  private static TabPane mainMenuTabs() {
    DataModel data = new DataModel();

    TabPane mainMenuTabs = new TabPane();

    Tab tabOrders = new Tab("Bestellungen");
    tabOrders.setContent(OrderTab.create(data.getOrders()));

    Tab tabMenu = new Tab("Speisekarte");
    tabMenu.setContent(MenuTab.create(data.getDishes()));

    Tab tabTimeSlots = new Tab("Zeitbereiche");
    tabTimeSlots.setContent(TimeSlotsTab.construct(data.getTimeSlots()));

    Tab tabUsers = new Tab("Benutzerverwaltung");
    tabUsers.setContent(UsersTab.create(data.getUsers()));

    mainMenuTabs.getTabs().addAll(tabOrders, tabMenu, tabTimeSlots, tabUsers);
    mainMenuTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    return mainMenuTabs;
  }
}