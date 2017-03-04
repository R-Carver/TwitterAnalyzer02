package TwitterAnalyzer_GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.util.StringConverter;

/**
 * Created by david on 26.02.2017.
 */
public class Filter_Field {

    public enum FilterMode{
        PREFERING,
        WHERE
    }

    public static double nextFreeXPos;
    public static double yPos;
    public static Pane mainPane;
    public static ObservableList<Attribute> attributes = FXCollections.observableArrayList(
            new Attribute("created_at", "VARCHAR"),
            new Attribute("text", "VARCHAR"),
            new Attribute("source", "VARCHAR"),
            new Attribute("lang", "VARCHAR"),
            new Attribute("place", "VARCHAR"),
            new Attribute("user_name", "VARCHAR"),
            new Attribute("screen_name", "VARCHAR"),
            new Attribute("description", "VARCHAR"),
            new Attribute("location", "VARCHAR"),
            new Attribute("user_langs", "VARCHAR"),
            new Attribute("hashtags", "VARCHAR"),
            new Attribute("followers_count", "INTEGER"),
            new Attribute("friends_count", "INTEGER"),
            new Attribute("statuses_count", "INTEGER"),
            new Attribute("account_created_at","DATE")
    );

    private Filter_Search_Field search_field1;
    private Filter_Search_Field search_field2;

    private static Filter_Field instance;

    private ChoiceBox choice_attribute1;
    private ChoiceBox choice_attribute2;
    private FilterMode filterMode;

    //2nd Attribute Button
    public static ObservableList<String> combineOptions_preferred = FXCollections.observableArrayList(
           "PARETO", "PRIOR TO"
    );
    public static ObservableList<String> combineOptions_where = FXCollections.observableArrayList(
            "AND", "OR"
    );
    private Button button_secondAttribute;
    private Button button_removeSecond;
    private ChoiceBox choice_combineOption;

    private FilterSubQuery subQuery1;
    private FilterSubQuery subQuery2;

    private static double choice_attribute_length = 100;
    protected static int padding_attribute = 10;

    private Filter_Field(){
        filterMode = FilterMode.PREFERING;
        generateAttribute1();

        button_secondAttribute = new Button();
        renderButton();
    }

    public static Filter_Field getInstance(){

        if(instance == null){
            instance = new Filter_Field();
        }
        return instance;
    }

    public static void setMainPane(Pane pane){
        mainPane = pane;
    }

    public static void setNextFreePos(double x, double y){

        nextFreeXPos = x;
        yPos = y;
    }

    public void setFilterMode(int filterModeAsInt){
        if(filterModeAsInt == 0){
            filterMode = FilterMode.PREFERING;
        }else if(filterModeAsInt == 1){
            filterMode = FilterMode.WHERE;
        }
        //Update
        if(choice_attribute2 != null){
            deleteSecondAttribute();
        }
        deleteFirstAttribute();
        generateAttribute1();
    }

    private void generateAttribute1(){

        subQuery1 = new FilterSubQuery(1);

        choice_attribute1 = new ChoiceBox();
        choice_attribute1.setPrefWidth(choice_attribute_length);
        choice_attribute1.setConverter(attr_converter);
        choice_attribute1.setItems(attributes);

        choice_attribute1.setLayoutX(nextFreeXPos);
        choice_attribute1.setLayoutY(yPos);
        mainPane.getChildren().add(choice_attribute1);
        addListener1(choice_attribute1);
    }

    private void addListener1(ChoiceBox choice_attribute){

        choice_attribute.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double xPos = choice_attribute.getLayoutX() + choice_attribute.getPrefWidth() + padding_attribute;
                        double yPos = choice_attribute.getLayoutY();
                        Filter_Search_Field.setNextPos(xPos, yPos);
                        Filter_Search_Field.setMainPane(mainPane);

                        //remove old search field if one exists
                        if(search_field1 != null){
                            search_field1.removeMe();
                        }

                        Attribute currentAttr = attributes.get((int)newValue);
                        subQuery1.setAttribute(currentAttr.name);
                        search_field1 = new Filter_Search_Field(filterMode, currentAttr.type, subQuery1);
                    }
                }
        );
    }

    private void addListener2(ChoiceBox choice_attribute){

        choice_attribute.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double xPos = choice_attribute.getLayoutX() + choice_attribute.getPrefWidth() + padding_attribute;
                        double yPos = choice_attribute.getLayoutY();
                        Filter_Search_Field.setNextPos(xPos, yPos);
                        Filter_Search_Field.setMainPane(mainPane);

                        //remove old search field if one exists
                        if(search_field2 != null){
                            search_field2.removeMe();
                        }

                        Attribute currentAttr = attributes.get((int)newValue);
                        subQuery2.setAttribute(currentAttr.name);
                        search_field2 = new Filter_Search_Field(filterMode, currentAttr.type, subQuery2);
                    }
                }
        );
    }

    private void renderButton(){

        double xPos = nextFreeXPos + 470;
        double yPos = Filter_Field.yPos;
        button_secondAttribute.setLayoutX(xPos);
        button_secondAttribute.setLayoutY(yPos);
        button_secondAttribute.setPrefWidth(30);
        button_secondAttribute.setText("+");

        mainPane.getChildren().add(button_secondAttribute);

        button_secondAttribute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(choice_attribute2 == null){
                    generateAttribute2();
                }
            }
        });
    }

    private void generateAttribute2(){

        subQuery2 = new FilterSubQuery(2);

        double xPos = nextFreeXPos - 90;
        double yPos = Filter_Field.yPos + 50;
        choice_combineOption = new ChoiceBox();
        choice_combineOption.setLayoutX(xPos);
        choice_combineOption.setLayoutY(yPos);
        choice_combineOption.setPrefWidth(80);
        if(filterMode == FilterMode.PREFERING){
            choice_combineOption.setItems(combineOptions_preferred);
            choice_combineOption.setValue(combineOptions_preferred.get(0));
            subQuery2.setConnector(combineOptions_preferred.get(0));
            choice_combineOption.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    subQuery2.setConnector(combineOptions_preferred.get(newValue.intValue()));
                }
            });
        }else{
            choice_combineOption.setItems(combineOptions_where);
            choice_combineOption.setValue(combineOptions_where.get(0));
            subQuery2.setConnector(combineOptions_where.get(0));
            choice_combineOption.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    subQuery2.setConnector(combineOptions_where.get(newValue.intValue()));
                }
            });
        }

        choice_attribute2 = new ChoiceBox();
        choice_attribute2.setPrefWidth(choice_attribute_length);
        choice_attribute2.setConverter(attr_converter);
        choice_attribute2.setItems(attributes);

        choice_attribute2.setLayoutX(nextFreeXPos);
        choice_attribute2.setLayoutY(yPos);
        addListener2(choice_attribute2);

        mainPane.getChildren().add(choice_attribute2);
        mainPane.getChildren().add(choice_combineOption);

        generateDeleteButton();
    }

    private void generateDeleteButton(){

        double xPos = button_secondAttribute.getLayoutX();
        double yPos = choice_combineOption.getLayoutY();
        button_removeSecond = new Button();
        button_removeSecond.setLayoutX(xPos);
        button_removeSecond.setLayoutY(yPos);
        button_removeSecond.setPrefWidth(30);
        button_removeSecond.setText("-");

        mainPane.getChildren().add(button_removeSecond);

        button_removeSecond.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteSecondAttribute();
            }
        });
    }

    private void deleteSecondAttribute(){

        if(search_field2 != null){
            search_field2.removeMe();
        }
        mainPane.getChildren().remove(choice_combineOption);
        mainPane.getChildren().remove(choice_attribute2);
        choice_attribute2 = null;
        mainPane.getChildren().remove(button_removeSecond);

        subQuery2 = null;
    }

    private void deleteFirstAttribute(){

        if(search_field1 != null){
            search_field1.removeMe();
        }
        mainPane.getChildren().remove(choice_attribute1);
        choice_attribute1 = null;

        subQuery1 = null;
    }

    StringConverter<Attribute> attr_converter = new StringConverter<Attribute>() {
        @Override
        public String toString(Attribute object) {
            return object.name;
        }

        @Override
        public Attribute fromString(String string) {
            return null;
        }
    };

    public void reset(){
        deleteSecondAttribute();
        deleteFirstAttribute();
        generateAttribute1();
    }
}
