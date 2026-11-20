package dev.reveles.view;

import dev.reveles.controller.CardController;
import dev.reveles.model.Card;
import dev.reveles.model.CircularList;
import dev.reveles.model.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainWindow
{
    private CardController controller;
    private CircularList<Card> currentList;
    private Node<Card> currentNode;
    private int currentIndex;

    private BorderPane root;
    private ImageView currentImageView;
    private ImageView prevImageView;
    private ImageView nextImageView;
    private Label nameLabel;
    private Label statLabel;
    private Label indexLabel;

    // ------------------------------
    public MainWindow(CardController controller)
    {
        this.controller = controller;
        this.root = new BorderPane();
        this.root.setPadding(new Insets(20));

        buildLeftMenu();
        buildRightViewer();

        loadList(controller.getAllCards());
    }
    // ------------------------------

    public BorderPane getRoot()
    {
        return root;
    }

    private void buildLeftMenu()
    {
        VBox menu = new VBox(20);
        menu.setAlignment(Pos.CENTER_LEFT);

        Button btnAll = new Button("Todas las cartas");
        btnAll.setOnAction(e -> loadList(controller.getAllCards()));

        HBox levelBox = new HBox(10);
        Button btnLevel = new Button("Por nivel");
        ComboBox<Integer> comboLevel = new ComboBox<>();
        for(int i = 1; i <= 12; i ++)
        {
            comboLevel.getItems().add(i);
        }
        comboLevel.getSelectionModel().selectFirst();
        btnLevel.setOnAction(e -> loadList(controller.getCardsByLevel(comboLevel.getValue())));
        levelBox.getChildren().addAll(btnLevel, comboLevel);

        HBox attrBox = new HBox(10);
        Button btnAttr = new Button("Por atributo");
        ComboBox<String> comboAttr = new ComboBox<>();
        comboAttr.getItems().addAll("DARK", "LIGHT", "EARTH", "WATER", "FIRE", "WIND", "DIVINE");
        comboAttr.getSelectionModel().selectFirst();
        btnAttr.setOnAction(e -> loadList(controller.getCardsByAttribute(comboAttr.getValue())));
        attrBox.getChildren().addAll(btnAttr, comboAttr);

        HBox atkBox = new HBox(10);
        Button btnAtk = new Button("Por atque");
        CheckBox checkAtk = new CheckBox("Bajo a Alto");
        btnAtk.setOnAction(e -> loadList(controller.getCardsSortedByAtk(checkAtk.isSelected())));
        atkBox.getChildren().addAll(btnAtk, checkAtk);

        HBox defBox = new HBox(10);
        Button btnDef = new Button("Por defensa");
        CheckBox checkDef = new CheckBox("Bajo a Alto");
        btnDef.setOnAction(e -> loadList(controller.getCardsSortedByDef(checkDef.isSelected())));
        defBox.getChildren().addAll(btnDef, checkDef);

        Button btnSound = new Button("Musica");

        menu.getChildren().addAll(btnAll, levelBox, attrBox, atkBox, defBox, btnSound);
        root.setLeft(menu);
    }

    private void buildRightViewer()
    {
        VBox viewer = new VBox(15);
        viewer.setAlignment(Pos.CENTER);
        viewer.setPadding(new Insets(0, 0, 0, 50));

        indexLabel = new Label("0 / 0");
        indexLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        nameLabel = new Label("Card Name");
        nameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        statLabel = new Label("Stats");

        HBox imageBox = new HBox(20);
        imageBox.setAlignment(Pos.CENTER);

        prevImageView = new ImageView();
        prevImageView.setFitWidth(100);
        prevImageView.setPreserveRatio(true);

        currentImageView = new ImageView();
        currentImageView.setFitWidth(280);
        currentImageView.setPreserveRatio(true);

        nextImageView = new ImageView();
        nextImageView.setFitWidth(100);
        nextImageView.setPreserveRatio(true);

        imageBox.getChildren().addAll(prevImageView, currentImageView, nextImageView);

        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        Button btnPrev = new Button("<");
        Button btnNext = new Button(">");

        btnPrev.setOnAction(e -> goPrevious());
        btnNext.setOnAction(e -> goNext());

        controls.getChildren().addAll(btnPrev, btnNext);

        viewer.getChildren().addAll(indexLabel, imageBox, nameLabel, statLabel, controls);
        root.setCenter(viewer);
    }

    private void loadList(CircularList<Card> list)
    {
        currentList = list;
        if(!currentList.isEmpty())
        {
            currentNode = currentList.getHead();
            currentIndex = 1;
            updateView();
        }
        else
        {
            currentImageView.setImage(null);
            prevImageView.setImage(null);
            nextImageView.setImage(null);
            nameLabel.setText("No se encontraron cartas");
            statLabel.setText("");
            indexLabel.setText("0 / 0");
        }
    }

    private void goNext()
    {
        if(currentNode != null)
        {
            currentNode = currentNode.getNext();
            currentIndex ++;
            if(currentIndex > currentList.getSize())
            {
                currentIndex = 1;
            }
            updateView();
        }
    }

    private void goPrevious()
    {
        if(currentNode != null)
        {
            currentNode = currentNode.getPrev();
            currentIndex --;
            if(currentIndex < 1)
            {
                currentIndex = currentList.getSize();
            }
            updateView();
        }
    }

    private void updateView()
    {
        if(currentNode == null)
        {
            return;
        }

        Card currentCard = currentNode.getInfo();
        Card prevCard = currentNode.getPrev().getInfo();
        Card nextCard = currentNode.getNext().getInfo();

        try
        {
            currentImageView.setImage(new Image(currentCard.getImageUrl(), true));
            prevImageView.setImage(new Image(prevCard.getImageUrl(), true));
            nextImageView.setImage(new Image(nextCard.getImageUrl(), true));
        }
        catch(Exception e)
        {
            System.out.println("Error loading images");
        }

        indexLabel.setText(currentIndex + " / " + currentList.getSize());
        nameLabel.setText(currentCard.getName());
        statLabel.setText(currentCard.getAttribute() + " | Level: " + currentCard.getLevel() + " | ATK: " + currentCard.getAtk() + " | DEF: " + currentCard.getDef());
    }
}