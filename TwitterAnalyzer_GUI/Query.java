package TwitterAnalyzer_GUI;

/**
 * Created by david on 27.02.2017.
 */
public class Query {

    private static Query instance;

    private String query_select;
    private String query_attr1 = "";
    private String query_remainingAttr;
    private String query_from;
    private String query_mode = "PREFERRING ";
    private String query_filter1 = "";
    private String query_connector = "";
    private String query_filter2 = "";

    private String theQuery;

    private Controller mainGui;

    private Query(Controller gui){
        mainGui = gui;
        query_select = "SELECT STREAM ";
        query_from = "\nFROM TwitterStream ";
        updateTheQuery();
    }

    public String getQuery(){

        return theQuery;
    }

    public void updateTheQuery(){
        if(query_remainingAttr != null){
            theQuery = query_select + query_attr1 + query_remainingAttr + query_from + query_mode + query_filter1 + query_connector + query_filter2;
        }else{
            theQuery = query_select + query_attr1 + query_from + query_mode + query_filter1 + query_connector + query_filter2;
        }
        updateQueryField();
    }

    public static Query getInstance(Controller gui){

        if(instance == null){
            instance = new Query(gui);
        }
        return instance;
    }

    public static Query getInstance(){

        return instance;
    }

    private void updateQueryField(){

        mainGui.updateQueryTextArea(theQuery);
    }

    public void updateQueryAttr1(String text){
        query_attr1 = text ;
        updateTheQuery();
    }

    public void updateRemainingAttr(String text){
        query_remainingAttr = text;
        updateTheQuery();
    }

    public void updateMode(String text){
        query_mode = text+ " ";
        updateTheQuery();
    }

    public void updateFilter1(String text){
        query_filter1 = text;
        updateTheQuery();
    }

    public void updateFilter2(String text){
        query_filter2 = text;
        updateTheQuery();
    }

    public void updateConnector(String text){
        query_connector = text;
        updateTheQuery();
    }

    public void resetQuery(){

        instance = new Query(mainGui);
    }


}
