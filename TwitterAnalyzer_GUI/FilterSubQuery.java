package TwitterAnalyzer_GUI;

/**
 * Created by david on 27.02.2017.
 */
public class FilterSubQuery {

    String theSubQuery;

    String query_connector = "";
    String query_attribute = "";
    String query_mode = "";
    String query_search = "";
    String query_mode_seperated = "";
    String query_search2 ="";

    private int subQueryCount;

    public FilterSubQuery(int count){

        //updateSubQuery();
        subQueryCount = count;
    }

    public void updateSubQuery(){

        theSubQuery = query_connector + query_attribute + query_mode + query_search + query_mode_seperated +  query_search2;
        Query theQuery = Query.getInstance();
        if(subQueryCount == 1){
            theQuery.updateFilter1(theSubQuery);
        }else if(subQueryCount == 2){
            theQuery.updateFilter2(theSubQuery);
        }

    }

    public void setConnector(String text){
        query_connector = "\n\t\t\t\t"+text+ " ";
        updateSubQuery();
    }

    public void setAttribute(String text){
        query_attribute = text+ " ";
        updateSubQuery();
    }
    public void setMode(String text){

        if(text == "-"){
            query_mode = "";
        }else{
            query_mode = text+ " ";
        }
        updateSubQuery();
    }
    public void setModeSeperated(String text){
        if(text == "IN/NOT IN"){
            query_mode = "IN"+ " ";
            query_mode_seperated = "NOT IN" + " ";
        }else if(text == "IN/ELSE"){
            query_mode = "IN" + " ";
            query_mode_seperated = "ELSE" + " ";
        }else{
            query_mode_seperated = text;
        }
        updateSubQuery();
    }
    public void setSearch(String text){
        query_search = text + " ";
        updateSubQuery();
    }
    public void setSearch2(String text){
        query_search2 = text + " ";
        updateSubQuery();
    }

    public void resetSubQuery(){

        query_connector = "";
        query_attribute = "";
        query_mode = "";
        query_search = "";
        query_mode_seperated = "";
        query_search2 ="";
        updateSubQuery();
    }

    public String getTheSubQuery(){
        return theSubQuery;
    }
}
