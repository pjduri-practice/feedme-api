package FeedMe.Auth.Authorization.models;

import java.util.ArrayList;

public class ChoiceColumnData {
    public static ArrayList<ChoiceColumn> findByColumnAndValue(String column, String value, Iterable<ChoiceColumn> allChoiceColumns) {

        ArrayList<ChoiceColumn> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")){
            return (ArrayList<ChoiceColumn>) allChoiceColumns;
        }

        if (column.equals("all")){
            results = findByValue(value, allChoiceColumns);
            return results;
        }
        for (ChoiceColumn choiceColumn : allChoiceColumns) {

            String aValue = getFieldValue(choiceColumn, column);

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(choiceColumn);
            }
        }

        return results;
    }

    public static String getFieldValue(ChoiceColumn choiceColumn, String fieldName){

        String theValue = "";
        if (fieldName.equals("name")){
            theValue = choiceColumn.getName();
        }

        return theValue;
    }

    public static ArrayList<ChoiceColumn> findByValue(String value, Iterable<ChoiceColumn> allChoiceColumns) {
        String lower_val = value.toLowerCase();

        ArrayList<ChoiceColumn> results = new ArrayList<>();

        for (ChoiceColumn choiceColumn : allChoiceColumns) {

            if (choiceColumn.getName().toLowerCase().contains(lower_val)) {
                results.add(choiceColumn);
            }

        }

        return results;
    }

}