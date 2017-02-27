package TwitterAnalyzer_GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

/**
 * Created by david on 27.02.2017.
 */
public class Filter_Search_Field {

    public static double xPos;
    public static double yPos;
    public static Pane mainPane;

    protected static double choice_attribute_length = 100;
    protected static double text_length = 120;
    protected static int padding_attribute = 10;

    private Filter_Search_Field mySearchField;

    public Filter_Search_Field(){

    }

    public Filter_Search_Field(Filter_Field.FilterMode mode, String type, FilterSubQuery subQuery){

        if(mode == Filter_Field.FilterMode.PREFERING){
            mySearchField = new Search_Preferring(type, subQuery);
        }else if(mode == Filter_Field.FilterMode.WHERE){
            mySearchField = new Search_Where(type, subQuery);
        }
    }

    public static void setMainPane(Pane pane){

        mainPane = pane;
    }

    public static void setNextPos(double x, double y){

        xPos = x;
        yPos = y;
    }

    public void removeMe(){
        if(mySearchField instanceof  Search_Preferring){
            ((Search_Preferring)mySearchField).removeMe();
        }else if(mySearchField instanceof  Search_Where){
            ((Search_Where)mySearchField).removeMe();
        }
    }
}
