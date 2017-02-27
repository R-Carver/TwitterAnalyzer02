package TwitterAnalyzer_GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

/**
 * Created by david on 24.02.2017.
 */
public class Attribute_Field {

    public static double nextFreeXPos;
    public static double yPos;
    public static Pane mainPane;
    public static ObservableList<String> attributes = FXCollections.observableArrayList(
            "test",
            "source",
            "tweet_language"
    );

    private ChoiceBox choice_attribute;
    private Button button_newAttribute;
    private Button button_remove;
    private boolean newAttributeAvailable;
    private Attribute_Container container;

    private static double choice_attribute_length = 100;
    private static double button_newAttribute_length = 30;
    private static double button_remove_length = 30;
    private static int padding_attribute = 10;

    public String myAttribute;

    public Attribute_Field(){

        choice_attribute = new ChoiceBox();
        choice_attribute.setItems(attributes);
        choice_attribute.setPrefWidth(choice_attribute_length);

        button_newAttribute = new Button();
        button_newAttribute.setPrefWidth(button_newAttribute_length);
        button_newAttribute.setText("+");

        button_remove = new Button();
        button_remove.setPrefWidth(button_remove_length);
        button_remove.setText("-");
        generateAttributeField();
        setNewAttributeAvailable(true);

        container = Attribute_Container.getInstance();
        container.addAttributeField(this);
    }

    public static void setNextFreePos(double x, double y){

        nextFreeXPos = x;
        yPos = y;
    }

    public static void incrementNextFreePos(){
        nextFreeXPos += choice_attribute_length + button_newAttribute_length + button_remove_length + padding_attribute;
    }

    public static void decrementNextFreePos(){
        nextFreeXPos -= choice_attribute_length + button_newAttribute_length + button_remove_length + padding_attribute;
    }

    public static void setMainPane(Pane pane){
        mainPane = pane;
    }

    private void setNewAttributeAvailable(boolean bool){
        newAttributeAvailable = bool;
    }



    public void generateAttributeField(){

        choice_attribute.setLayoutX(nextFreeXPos);
        choice_attribute.setLayoutY(yPos);

        double xPos = choice_attribute.getLayoutX() + choice_attribute.getPrefWidth();
        button_newAttribute.setLayoutX(xPos);
        button_newAttribute.setLayoutY(yPos);
        addNewAttributeFunctionality();

        xPos = button_newAttribute.getLayoutX() + button_newAttribute.getPrefWidth();
        button_remove.setLayoutX(xPos);
        button_remove.setLayoutY(yPos);
        addRemoveFunctionality();

        mainPane.getChildren().add(choice_attribute);
        mainPane.getChildren().add(button_newAttribute);
        mainPane.getChildren().add(button_remove);
        addAttributeListener();

        incrementNextFreePos();
    }

    private void addAttributeListener(){

        choice_attribute.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myAttribute = attributes.get(newValue.intValue());
                container.updateQuery();
            }
        });
    }

    private void addNewAttributeFunctionality(){

        button_newAttribute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(newAttributeAvailable){
                    new Attribute_Field();
                    setNewAttributeAvailable(false);
                }
            }
        });
    }

    private void addRemoveFunctionality(){

        button_remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeMyGuiElements();

                //update bools from container
                removeFieldFromContainer();

                //rerender the elements left
                container.updateAfterRemove();
            }
        });
    }

    private void removeFieldFromContainer(){
        container.removeAttributeField(this);
    }

    public void removeMyGuiElements(){

        mainPane.getChildren().remove(choice_attribute);
        mainPane.getChildren().remove(button_newAttribute);
        mainPane.getChildren().remove(button_remove);
        decrementNextFreePos();
    }
}
