package TwitterAnalyzer_GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by david on 27.02.2017.
 */
public class Search_Where extends Filter_Search_Field {

    private ChoiceBox choice_searchtype;
    private TextField text_1;
    private TextField text_2;

    private FilterSubQuery subQuery;
    private String type;

    public static ObservableList<String> search_String = FXCollections.observableArrayList(
        "-","LIKE", "IN"
    );

    public static ObservableList<String> search_Num = FXCollections.observableArrayList(
        "-",">", "<", ">=", "<=", "=","BETWEEN"
    );

    public static ObservableList<String> search_Date = FXCollections.observableArrayList(
            "-",">", "<", ">=", "<=", "=","BETWEEN"
    );

    public Search_Where(String type, FilterSubQuery subQuery){

        this.type = type;
        this.subQuery = subQuery;
        if(type == "VARCHAR"){

            generateStringSearch();
        }else if(type == "INTEGER"){

            generateNumSearch();
        }else if(type == "DATE"){

            generateDateSearch();
        }
    }

    private void generateStringSearch(){

        choice_searchtype = new ChoiceBox();
        choice_searchtype.setPrefWidth(super.choice_attribute_length);
        choice_searchtype.setItems(search_String);

        choice_searchtype.setLayoutX(super.xPos);
        choice_searchtype.setLayoutY(super.yPos);
        mainPane.getChildren().add(choice_searchtype);

        choice_searchtype.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        //remove old fields so they dont override
                        removeTextFields();
                        if(newValue.intValue() >=1 && newValue.intValue()<=2){
                            generateSingleTextField();
                        }else if(newValue.intValue() == 0){
                            generateSingleTextFieldEmpty();
                        }
                        subQuery.setMode(search_String.get(newValue.intValue()));
                    }
                }
        );
    }

    private void generateNumSearch(){

        choice_searchtype = new ChoiceBox();
        choice_searchtype.setPrefWidth(super.choice_attribute_length);
        choice_searchtype.setItems(search_Num);

        choice_searchtype.setLayoutX(super.xPos);
        choice_searchtype.setLayoutY(super.yPos);
        mainPane.getChildren().add(choice_searchtype);

        choice_searchtype.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        //remove old fields so they dont override
                        removeTextFields();
                        if(newValue.intValue() >=1 && newValue.intValue()<=5){
                            generateSingleTextField();
                        }else if(newValue.intValue() == 6){
                            generateDoubleTextField();
                        }else if(newValue.intValue() == 0){
                            generateSingleTextFieldEmpty();
                        }
                        subQuery.setMode(search_Num.get(newValue.intValue()));
                    }
                }
        );
    }

    private void generateDateSearch(){
        choice_searchtype = new ChoiceBox();
        choice_searchtype.setPrefWidth(super.choice_attribute_length);
        choice_searchtype.setItems(search_Date);

        choice_searchtype.setLayoutX(super.xPos);
        choice_searchtype.setLayoutY(super.yPos);
        mainPane.getChildren().add(choice_searchtype);

        choice_searchtype.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        //remove old fields so they dont override
                        removeTextFields();
                        if(newValue.intValue() == 0){
                            generateSingleTextFieldEmpty();
                        }else{
                            generateSingleTextField();
                        }
                        subQuery.setMode(search_Date.get(newValue.intValue()));
                    }
                }
        );
    }

    private void generateSingleTextField(){

        double xPos = choice_searchtype.getLayoutX() + choice_searchtype.getPrefWidth() + super.padding_attribute;
        double yPos = choice_searchtype.getLayoutY();
        text_1 = new TextField();
        text_1.setPrefWidth(super.text_length);
        text_1.setLayoutX(xPos);
        text_1.setLayoutY(yPos);
        mainPane.getChildren().add(text_1);
        text_1.textProperty().addListener((observable, oldValue, newValue)->{
            String query = "";
            if(type == "VARCHAR"){
                query = "('" + newValue + "')";
            }else if(type == "INTEGER"){
                query = newValue;
            }else if(type == "DATE"){
                query = "'" + newValue + "'";
            }
            subQuery.setSearch(query);
        });
    }

    private void generateSingleTextFieldEmpty(){

        double xPos = choice_searchtype.getLayoutX() + choice_searchtype.getPrefWidth() + super.padding_attribute;
        double yPos = choice_searchtype.getLayoutY();
        text_1 = new TextField();
        text_1.setPrefWidth(super.text_length);
        text_1.setLayoutX(xPos);
        text_1.setLayoutY(yPos);
        mainPane.getChildren().add(text_1);
        text_1.textProperty().addListener((observable, oldValue, newValue)->{
            String query = "";
            query = newValue;
            subQuery.setSearch(query);
        });
    }

    private void generateDoubleTextField(){
        double xPos = choice_searchtype.getLayoutX() + choice_searchtype.getPrefWidth() + super.padding_attribute;
        double yPos = choice_searchtype.getLayoutY();
        text_1 = new TextField();
        text_1.setPrefWidth(super.text_length);
        text_1.setLayoutX(xPos);
        text_1.setLayoutY(yPos);
        text_1.textProperty().addListener((observable, oldValue, newValue)->{
            String query = "";
            if(type == "VARCHAR"){
                query = "('" + newValue + "')";
            }else if(type == "INTEGER"){
                query = newValue;
            }
            subQuery.setSearch(query);
        });

        xPos = text_1.getLayoutX() + text_1.getPrefWidth() + super.padding_attribute/2;
        yPos = text_1.getLayoutY();
        text_2 = new TextField();
        text_2.setPrefWidth(super.text_length);
        text_2.setLayoutX(xPos);
        text_2.setLayoutY(yPos);
        text_2.textProperty().addListener((observable, oldValue, newValue)->{
            String query = "";
            if(type == "VARCHAR"){
                query = "('" + newValue + "')";
            }else if(type == "INTEGER"){
                query = newValue;
            }
            subQuery.setSearch2(query);
        });

        mainPane.getChildren().add(text_1);
        mainPane.getChildren().add(text_2);
    }

    private void removeTextFields(){
        if(text_1 != null){
            mainPane.getChildren().remove(text_1);
            subQuery.setSearch("");
        }
        if(text_2 != null){
            mainPane.getChildren().remove(text_2);
            subQuery.setSearch2("");
        }
    }
    @Override
    public void removeMe(){
        mainPane.getChildren().remove(choice_searchtype);
        removeTextFields();
        subQuery.resetSubQuery();
    }
}
