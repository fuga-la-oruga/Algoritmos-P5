package dev.reveles.view;

import dev.reveles.controller.CardController;
import dev.reveles.model.Card;
import dev.reveles.model.CircularList;
import dev.reveles.model.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;

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
    private Label descLabel;
    private Label indexLabel;

    private MediaPlayer mediaPlayer;
    private HashMap<String, Image> imageCache;

    // ------------------------------
    public MainWindow(CardController controller)
    {
        this.controller = controller;
        this.imageCache = new java.util.HashMap<>();
        this.root = new BorderPane();
        this.root.setStyle("-fx-background-color: #4A4A4A; -fx-font-family: 'Segoe UI', Arial, sans-serif;");

        setupAudio("src/main/resources/dev/reveles/Yu-Gi-Oh Dark God Revival Theme Japanese version.mp3");

        buildLeftMenu();
        buildRightViewer();

        loadList(controller.getAllCards());
    }
    // ------------------------------

    public BorderPane getRoot()
    {
        return root;
    }

    private void setupAudio(String filePath)
    {
        try
        {
            Media media = new Media(new File(filePath).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
        catch (Exception e)
        {
            System.out.println("Error al cargar la musica");
        }
    }

    private void buildLeftMenu()
    {
        VBox menu = new VBox(25);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: #383838; -fx-border-color: #5A5A5A; -fx-border-width: 0 3 0 0;");
        menu.setPrefWidth(220);

        Button btnAll = new Button("Todas las Cartas");
        btnAll.setMaxWidth(Double.MAX_VALUE);
        btnAll.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px;");
        btnAll.setOnAction(e -> loadList(controller.getAllCards()));

        VBox levelBox = new VBox(10);
        Button btnLevel = new Button("Por Nivel");
        btnLevel.setMaxWidth(Double.MAX_VALUE);
        btnLevel.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px;");
        ComboBox<Integer> comboLevel = new ComboBox<>();
        for (int i = 1; i <= 12; i++) {
            comboLevel.getItems().add(i);
        }
        comboLevel.getSelectionModel().selectFirst();
        comboLevel.setMaxWidth(Double.MAX_VALUE);
        comboLevel.setStyle("-fx-background-color: #2B2B2B; -fx-control-inner-background: #2B2B2B; -fx-text-base-color: white; -fx-font-weight: bold;");
        btnLevel.setOnAction(e -> loadList(controller.getCardsByLevel(comboLevel.getValue())));
        levelBox.getChildren().addAll(btnLevel, comboLevel);

        VBox attrBox = new VBox(10);
        Button btnAttr = new Button("Por Atributo");
        btnAttr.setMaxWidth(Double.MAX_VALUE);
        btnAttr.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px;");
        ComboBox<String> comboAttr = new ComboBox<>();
        comboAttr.getItems().addAll("DARK", "LIGHT", "EARTH", "WATER", "FIRE", "WIND", "DIVINE");
        comboAttr.getSelectionModel().selectFirst();
        comboAttr.setMaxWidth(Double.MAX_VALUE);
        comboAttr.setStyle("-fx-background-color: #2B2B2B; -fx-control-inner-background: #2B2B2B; -fx-text-base-color: white; -fx-font-weight: bold;");
        btnAttr.setOnAction(e -> loadList(controller.getCardsByAttribute(comboAttr.getValue())));
        attrBox.getChildren().addAll(btnAttr, comboAttr);

        VBox atkBox = new VBox(10);
        Button btnAtk = new Button("Por Ataque");
        btnAtk.setMaxWidth(Double.MAX_VALUE);
        btnAtk.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px;");
        CheckBox checkAtk = new CheckBox("Bajo a Alto");
        checkAtk.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        btnAtk.setOnAction(e -> loadList(controller.getCardsSortedByAtk(checkAtk.isSelected())));
        atkBox.getChildren().addAll(btnAtk, checkAtk);

        VBox defBox = new VBox(10);
        Button btnDef = new Button("Por Defensa");
        btnDef.setMaxWidth(Double.MAX_VALUE);
        btnDef.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px;");
        CheckBox checkDef = new CheckBox("Bajo a Alto");
        checkDef.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        btnDef.setOnAction(e -> loadList(controller.getCardsSortedByDef(checkDef.isSelected())));
        defBox.getChildren().addAll(btnDef, checkDef);

        HBox soundBox = new HBox(15);
        soundBox.setAlignment(Pos.CENTER_LEFT);
        Label lblSound = new Label("Musica");
        lblSound.setStyle("-fx-text-fill: #E0E0E0; -fx-font-size: 14px; -fx-font-weight: bold;");
        ToggleButton btnSound = new ToggleButton("ON");
        btnSound.setSelected(true);
        btnSound.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSound.setOnAction(e ->
        {
            if (btnSound.isSelected())
            {
                btnSound.setText("ON");
                if (mediaPlayer != null)
                {
                    mediaPlayer.play();
                }
            }
            else
            {
                btnSound.setText("OFF");
                if (mediaPlayer != null)
                {
                    mediaPlayer.pause();
                }
            }
        });
        soundBox.getChildren().addAll(lblSound, btnSound);

        menu.getChildren().addAll(btnAll, levelBox, attrBox, atkBox, defBox, soundBox);
        root.setLeft(menu);
    }

    private void buildRightViewer()
    {
        VBox viewer = new VBox(15);
        viewer.setAlignment(Pos.CENTER);
        viewer.setPadding(new Insets(20, 40, 20, 40));

        HBox imageBox = new HBox(20);
        imageBox.setAlignment(Pos.BOTTOM_CENTER);
        imageBox.setPadding(new Insets(0, 0, 10, 0));

        prevImageView = new ImageView();
        prevImageView.setFitWidth(100);
        prevImageView.setPreserveRatio(true);

        currentImageView = new ImageView();
        currentImageView.setFitHeight(330);
        currentImageView.setPreserveRatio(true);

        nextImageView = new ImageView();
        nextImageView.setFitWidth(100);
        nextImageView.setPreserveRatio(true);

        imageBox.getChildren().addAll(prevImageView, currentImageView, nextImageView);

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        Button btnPrev = new Button("<");
        btnPrev.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        indexLabel = new Label("0 / 0");
        indexLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        Button btnNext = new Button(">");
        btnNext.setStyle("-fx-background-color: #2B2B2B; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        btnPrev.setOnAction(e -> goPrevious());
        btnNext.setOnAction(e -> goNext());
        controls.getChildren().addAll(btnPrev, indexLabel, btnNext);

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setMaxWidth(700);

        nameLabel = new Label("Card Name");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #777777;");

        statLabel = new Label("Stats");
        statLabel.setStyle("-fx-text-fill: #DDDDDD; -fx-font-size: 16px; -fx-font-weight: bold;");

        descLabel = new Label("Description");
        descLabel.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 12px;");
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.JUSTIFY);
        descLabel.setMinHeight(85);
        descLabel.setMaxHeight(85);
        descLabel.setAlignment(Pos.TOP_LEFT);

        infoBox.getChildren().addAll(nameLabel, separator, statLabel, descLabel);

        viewer.getChildren().addAll(imageBox, controls, infoBox);
        root.setCenter(viewer);
    }

    private void loadList(CircularList<Card> list) {
        currentList = list;
        if (!currentList.isEmpty())
        {
            currentNode = currentList.getStart();
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
            descLabel.setText("");
            indexLabel.setText("0 / 0");
        }
    }

    private void goNext()
    {
        if (currentNode != null)
        {
            currentNode = currentNode.getNext();
            currentIndex ++;
            if (currentIndex > currentList.getSize())
            {
                currentIndex = 1;
            }
            updateView();
        }
    }

    private void goPrevious()
    {
        if (currentNode != null)
        {
            currentNode = currentNode.getPrev();
            currentIndex--;
            if (currentIndex < 1)
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
            currentImageView.setImage(getCardImage(currentCard));
            prevImageView.setImage(getCardImage(prevCard));
            nextImageView.setImage(getCardImage(nextCard));
        }
        catch(Exception e)
        {
            System.out.println("Error al cargar las imagenes");
        }

        indexLabel.setText(currentIndex + " / " + currentList.getSize());
        nameLabel.setText(currentCard.getName());
        statLabel.setText(currentCard.getAttribute() + " | Level " + currentCard.getLevel() + " | " +
                currentCard.getType() + " | ATK " + currentCard.getAtk() + " | DEF " + currentCard.getDef());
        descLabel.setText(currentCard.getDescription());

        preloadNearby(currentNode);
    }

    private void preloadNearby(Node<Card> centerNode)
    {
        if(centerNode == null)
        {
            return;
        }

        Node<Card> forward = centerNode.getNext();
        Node<Card> backward = centerNode.getPrev();

        for(int i = 0; i < 5; i ++)
        {
            if(forward != null)
            {
                String fUrl = forward.getInfo().getImageUrl();
                if(!imageCache.containsKey(fUrl))
                {
                    imageCache.put(fUrl, new Image(fUrl, true));
                }
                forward = forward.getNext();
            }

            if(backward != null)
            {
                String bUrl = backward.getInfo().getImageUrl();
                if(!imageCache.containsKey(bUrl))
                {
                    imageCache.put(bUrl, new Image(bUrl, true));
                }
                backward = backward.getPrev();
            }
        }
    }

    private Image getCardImage(Card card)
    {
        String url = card.getImageUrl();
        if(!imageCache.containsKey(url))
        {
            imageCache.put(url, new Image(url, true));
        }
        return imageCache.get(url);
    }
}