package TwitterAnalyzer_GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;


public class Controller {

    @FXML
    private Pane mainPane;
    @FXML
    private TextArea text_query;
    @FXML
    private TextArea text_result;
    @FXML
    private ChoiceBox choice_Attribute1;
    @FXML
    private Button button_newAttribute;
    @FXML
    private Button button_reset;
    @FXML
    private ChoiceBox choice_FilterType;
    @FXML
    private ChoiceBox choice_TwitterStream;

    //FMXL Variable Section Finished

    private int attributePadding = 10;
    private Filter_Field filter_field;
    private Attribute_Container att_container;

    private Query theQuery;

    public ObservableList<String> filterMode = FXCollections.observableArrayList("PREFERRING","WHERE");
    public ObservableList<String> twitterStream = FXCollections.observableArrayList("TwitterStream");
    public static ObservableList<String> attributes = FXCollections.observableArrayList(
            "created_at",
            "text",
            "source",
            "lang",
            "place",
            "user_name",
            "screen_name",
            "description",
            "location",
            "user_langs",
            "hashtags",
            "followers_count",
            "friends_count",
            "statuses_count",
            "account_created_at"
    );

    @FXML
    void initialize(){

        choice_TwitterStream.setItems(twitterStream);
        choice_TwitterStream.setValue(twitterStream.get(0));
        //Set the position for the next attribute
        double xPos = button_newAttribute.getLayoutX() + button_newAttribute.getPrefWidth() + attributePadding;
        double yPos = button_newAttribute.getLayoutY();
        Attribute_Field.setNextFreePos(xPos,yPos);
        Attribute_Field.setMainPane(mainPane);

        initializeFirstAttribute();
        initializeFilterType();
        initializeResetButton();

        theQuery = Query.getInstance(this);
    }

    private void initializeFirstAttribute(){

        choice_Attribute1.setItems(attributes);

        choice_Attribute1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String newAttribute = attributes.get(newValue.intValue());
                theQuery.updateQueryAttr1(newAttribute);
            }
        });
        //Functionality for + Button
        button_newAttribute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                new Attribute_Field();
                att_container = Attribute_Container.getInstance();
            }
        });
    }

    private void initializeFilterType(){

        choice_FilterType.setItems(filterMode);

        //Set to preferring and create Filter_Field
        choice_FilterType.setValue(filterMode.get(0));
        Filter_Field.setMainPane(mainPane);

        //Set the position for the next attribute
        double xPos = choice_FilterType.getLayoutX() + choice_FilterType.getPrefWidth() + attributePadding;
        double yPos = choice_FilterType.getLayoutY();
        Filter_Field.setNextFreePos(xPos, yPos);

        filter_field = Filter_Field.getInstance();

        choice_FilterType.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if((int)newValue == 0){
                            filter_field.setFilterMode(0);
                        }else if((int)newValue == 1){
                            filter_field.setFilterMode(1);
                        }
                        theQuery.updateMode(filterMode.get((int)newValue));
                    }
                }
        );
    }

    private void initializeResetButton(){

        button_reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filter_field.reset();
                if(att_container != null){
                    att_container.resetAttributes();
                }
                updateResult("");
            }
        });
    }

    public void updateQueryTextArea(String query){

        text_query.setText(query);
    }

    @FXML
    private void initiateSearch(){
        QueryAccess access = new QueryAccess(theQuery, this);
    }

    public void updateResult(String resultString){

        text_result.setText(resultString);
    }
}
