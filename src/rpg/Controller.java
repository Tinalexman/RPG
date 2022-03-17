package rpg;

import animatefx.animation.Pulse;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private AnchorPane newGamePane, menuPane, settingsPane, gamePane, shopPane, inventoryPane, root;

    @FXML
    private Button newGameBtn, settingBtn, settingsBackBtn, newGameCancelBtn, newGameProceedBtn;

    @FXML
    private Button buyWeaponBtn, startGameBtn, optionOneBtn, optionTwoBtn, optionThreeBtn;

    public static Button optionOne, optionTwo, optionThree;

    @FXML
    private TextField playerNameField;

    @FXML
    private Label shopNameInfo;

    @FXML
    private Label currentWeaponName, weaponPriceLabel, weaponDamageLabel, weaponSpeedLabel, weaponTypeLabel, shopPlayerGoldLabel;

    @FXML
    private Label inventoryHealth, inventoryMuscle, inventoryBrains, inventoryCharm, inventoryGold, inventoryName, inventoryResistance;

    @FXML
    private TextArea messageArea;

    public static TextArea messageBox;

    @FXML
    private ImageView leftArrow, rightArrow;

    public Controller()
    {
        Shop.init();
    }

    @FXML
    public void quitGame(MouseEvent event)
    {
        Platform.exit();
    }

    @FXML
    private void animateButton(MouseEvent event)
    {
        Node node = (Node) event.getSource();
        new Pulse(node).setSpeed(0.5).play();
    }

    public static void loadPath()
    {
        Path path = Resource.paths[Game.CURRENT_PATH_INDEX];
        Platform.runLater(path.getRunnable());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        optionOne = this.optionOneBtn;
        optionTwo = this.optionTwoBtn;
        optionThree = this.optionThreeBtn;

        messageBox = this.messageArea;


        this.messageArea.skinProperty().addListener(new ChangeListener<>()
        {
            @Override
            public void changed(ObservableValue<? extends Skin<?>> observableValue, Skin<?> skin, Skin<?> t1)
            {
                if (t1 != null && t1.getNode() instanceof Region r)
                {
                    r.setBackground(Background.EMPTY);

                    r.getChildrenUnmodifiable().stream()
                            .filter(node -> node instanceof Region)
                            .map(node -> (Region) node)
                            .forEach(node -> node.setBackground(Background.EMPTY));

                    r.getChildrenUnmodifiable().stream()
                            .filter(node -> node instanceof Control)
                            .map(node -> (Control) node)
                            .forEach(node -> node.skinProperty().addListener(this));
                }
            }
        });

        this.newGameBtn.setOnMouseClicked(event -> this.newGamePane.toFront());
        this.settingBtn.setOnMouseClicked(event -> this.settingsPane.toFront());


        this.settingsBackBtn.setOnMouseClicked(event ->
        {
            if(!Game.isRunning())
                this.menuPane.toFront();
        });

        this.newGameCancelBtn.setOnMouseClicked(event ->
        {
            clearPlayerDetails();
            this.newGameProceedBtn.setDisable(true);
            this.playerNameField.setEditable(true);
            this.menuPane.toFront();
            Game.setHero(null);
        });


        this.newGameProceedBtn.setOnMouseClicked(event ->
        {
            if(createPlayer())
            {
                clearPlayerDetails();
                Resource.createPaths();
                this.shopNameInfo.setText(this.shopNameInfo.getText() + Game.getHero().getName() + "?");
                loadWeaponDetails(Shop.WEAPON_INDEX);
                this.shopPane.toFront();
            }
        });

        this.leftArrow.setOnMouseClicked(event ->
        {
            Shop.WEAPON_INDEX = (--Shop.WEAPON_INDEX < 0) ? Shop.getNumberOfWeapons() - 1 : Shop.WEAPON_INDEX;
            loadWeaponDetails(Shop.WEAPON_INDEX);
        });

        this.rightArrow.setOnMouseClicked(event ->
        {
            Shop.WEAPON_INDEX = (++Shop.WEAPON_INDEX >= Shop.getNumberOfWeapons()) ? 0 : Shop.WEAPON_INDEX;
            loadWeaponDetails(Shop.WEAPON_INDEX);
        });

        this.buyWeaponBtn.setOnMouseClicked(event ->
        {
            Player player = Game.getHero();
            if(player.getAttackWeapon() != null && player.getDefenseWeapon() != null)
                return;

            int playerGold = player.getGold();
            Weapon weapon = Shop.getWeapon(Shop.WEAPON_INDEX);
            int weaponCost = weapon.getWeaponCost();
            if(weaponCost <= playerGold)
            {
                if(Shop.getArmorTypes().contains(weapon))
                    player.setDefenseWeapon(weapon);
                else
                    player.setAttackWeapon(weapon);
                player.setGold(playerGold - weaponCost);
                weapon.isPurchased(true);
                this.shopPlayerGoldLabel.setText("" + (playerGold - weaponCost));
                this.buyWeaponBtn.setText("Purchased");
                this.buyWeaponBtn.setDisable(true);
            }
        });

        this.startGameBtn.setOnMouseClicked(event ->
        {
            this.gamePane.toFront();
            this.gamePane.requestFocus();
            this.startGameBtn.setVisible(false);

            Player player = Game.getHero();

            if(player.getDefenseWeapon() == null)
                player.setDefenseWeapon(new Weapon("Fists", 0, 20, 50));
            if(player.getAttackWeapon() == null)
                player.setDefenseWeapon(new Weapon("Tough Skin", 0, 0, 50));

            loadPath();
        });
    }

    private boolean createPlayer()
    {
        String playerName = this.playerNameField.getText().trim();
        if(!playerName.isEmpty())
        {
            Player player = new Player(playerName);
            Game.setHero(player);
            this.newGameProceedBtn.setDisable(false);
            this.playerNameField.setEditable(false);
            this.newGamePane.requestFocus();
            this.newGameProceedBtn.setDisable(false);
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wars: A simple RPG");
        alert.setHeaderText("Create Player");
        alert.setContentText("Please enter the player name");
        alert.show();
        return false;
    }

    private void clearPlayerDetails()
    {
        this.playerNameField.clear();
        this.newGamePane.requestFocus();
    }

    private void loadWeaponDetails(int index)
    {
        Weapon weapon = Shop.getWeapon(index);
        int playerGold = Game.getHero().getGold();
        this.shopPlayerGoldLabel.setText("" + playerGold);
        this.weaponTypeLabel.setText((Shop.getArmorTypes().contains(weapon) ? "Defense" : "Attack"));
        this.currentWeaponName.setText(weapon.getWeaponName());
        this.weaponDamageLabel.setText("" + weapon.getWeaponDamage());
        this.weaponSpeedLabel.setText("" + weapon.getWeaponSpeed());
        int weaponCost = weapon.getWeaponCost();
        this.weaponPriceLabel.setText("" + weaponCost);

        boolean playerHasWeapon = weapon.isPurchased();
        this.buyWeaponBtn.setDisable(playerHasWeapon || (playerGold < weaponCost));
        this.buyWeaponBtn.setText(playerHasWeapon ? "Purchased" : "Buy Weapon");
    }

    @FXML
    private void interact(KeyEvent event)
    {
        if(event.getCode().equals(KeyCode.P))
        {
            if(isInFront(this.inventoryPane, this.root))
                this.inventoryPane.toBack();
            else
            {
                this.inventoryPane.toFront();
                Player player = Game.getHero();
                this.inventoryHealth.setText("" + player.getHealth());
                this.inventoryBrains.setText("" + player.getBrains());
                this.inventoryCharm.setText("" + player.getCharm());
                this.inventoryMuscle.setText("" + player.getMuscle());
                this.inventoryGold.setText("" + player.getGold());
                this.inventoryName.setText(player.getName());
                this.inventoryResistance.setText("" + player.getResistance());
            }
        }
        else if(event.getCode().equals(KeyCode.W))
        {

        }
        else if(event.getCode().equals(KeyCode.S))
        {
            if(isInFront(this.shopPane, this.root))
                this.shopPane.toBack();
            else
                this.shopPane.toFront();
        }
        else if(event.getCode().equals(KeyCode.R))
            rollDice();
        else if(event.getCode().equals(KeyCode.Q))
        {
            if(isInFront(this.gamePane, this.root))
            {
                Game.setHero(null);
                this.menuPane.toFront();
            }
        }

    }

    private boolean isInFront(Node child, Pane parent)
    {
        List<Node> nodes = parent.getChildren();
        return nodes.get(nodes.size() - 1).equals(child);
    }

    @FXML
    private void rollDice()
    {
        int rollValue = Game.rollDice();
        int firstDieValue = rollValue / 10;
        int secondDieValue = rollValue % 10;

//        Player currentPlayer = Game.getPlayer(Game.CURRENT_PLAYER_INDEX);
//        Player otherPlayer = Game.getPlayer((Game.CURRENT_PLAYER_INDEX == 1) ? 0 : 1);
//
//        String rollInformation = currentPlayer.getName() + " rolled a ";
//        if(firstDieValue == secondDieValue)
//            rollInformation += "pair of " + firstDieValue + "'s.";
//        else
//            rollInformation += "" + firstDieValue + " and a " + secondDieValue + ".";
//        this.rollInfo.setText(rollInformation);
//
//        String report = Game.calculateDamage(currentPlayer, otherPlayer);
//        this.gamePlayerHealth.setText("Health: " + currentPlayer.getHealth());
//        this.gameBotHealth.setText("Health: " + otherPlayer.getHealth());
//
//        this.commentLabel.setText(report);
    }



}
