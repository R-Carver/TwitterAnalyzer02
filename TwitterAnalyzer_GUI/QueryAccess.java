package TwitterAnalyzer_GUI;

/**
 * Created by david on 28.02.2017.
 */
public class QueryAccess {

    private Query searchQuery;
    Controller mainController;

    public QueryAccess(Query query, Controller controller){

        searchQuery = query;
        mainController = controller;
        runTwitterAnalysis();

    }

    private void runTwitterAnalysis(){

        /*

            Hallo Lena,
            hier ist die Verbidung zu deiner App.
            Falls du statische Methoden hast koenntest du sie direkt hier aufrufen,
            ansonsten muessten wir uns noch ueberlegen was wir hier noch uebergeben muessen.

            Den Query als String bekommst du mit serachQuery.getQuery()

         */

        //Shows the result string in the GUI
        mainController.updateResult(searchQuery.getQuery());
    }

    public String getQueryString(){

        return searchQuery.getQuery();
    }
}
