package TwitterAnalyzer_GUI;

import java.util.ArrayList;

/**
 * Created by david on 26.02.2017.
 */
public class Attribute_Container {

    static Attribute_Container instance;

    private ArrayList<Attribute_Field> attributes;

    private Attribute_Container(){

        attributes = new ArrayList<Attribute_Field>();
    }

    public static Attribute_Container getInstance(){

        if(instance == null){
            instance = new Attribute_Container();
        }
        return instance;
    }

    public void addAttributeField(Attribute_Field attribute){

        attributes.add(attribute);
        //printAttributeList();
    }

    public void removeAttributeField(Attribute_Field attribute){

        attributes.remove(attribute);
        //printAttributeList();
    }

    private void printAttributeList(){

        for (int i=0; i<attributes.size(); i++){

            System.out.print(attributes.get(i).toString());
        }
    }

    public void updateAfterRemove(){

        //all the Attribute Fields left need to be rerendered

        //First delete all their GUI Elements
        for(int i = 0; i < attributes.size(); i++){

            Attribute_Field tempAttr = attributes.get(i);
            tempAttr.removeMyGuiElements();
        }

        //Then rerender them to the new positions
        for(int i = 0; i < attributes.size(); i++){

            Attribute_Field tempAttr = attributes.get(i);
            tempAttr.generateAttributeField();
        }
        updateQuery();
    }

    public void resetAttributes(){

        for(int i = 0; i < attributes.size(); i++){

            Attribute_Field tempAttr = attributes.get(i);
            tempAttr.removeMyGuiElements();
        }
        attributes.removeAll(attributes);
        updateQuery();
    }

    public void updateQuery(){
        Query theQuery = Query.getInstance();
        String myQuery = "";
        int i=0;
        for( ; i < attributes.size(); i++){

            myQuery += ", " +attributes.get(i).myAttribute;
        }
//        if(i>0){
//            myQuery += attributes.get(i).myAttribute;
//        }
        theQuery.updateRemainingAttr(myQuery);
    }
}
