package iit.com.appointmentmanager;

import java.util.ArrayList;

class Logic {

    public static Logic logic = null;

    public ArrayList<String> searchFor(String s, String date, DBHelper mydb){
        ArrayList<String> allAppointments = mydb.getAllAppointments(date);
        ArrayList<String> foundAppointments = new ArrayList<String>();
        String[] tempArr;

        for(int x = 0; x < allAppointments.size(); x++){
            tempArr = allAppointments.get(x).split(",");

            if(containsString(s, tempArr[0])){
                foundAppointments.add(allAppointments.get(x));
                continue;
            }else if(containsString(s, tempArr[3])){
                foundAppointments.add(allAppointments.get(x));
                continue;
            }
        }
        return foundAppointments;
    }

    private Boolean containsString(String key, String searchString){
        int keyLength = key.length();
        Boolean contains = false;

        for(int x = keyLength; x <= searchString.length(); x++){
            if(searchString.substring(x - keyLength, x).equalsIgnoreCase(key)){
                contains = true;
                break;
            }
        }
        return contains;
    }

    public static Logic getInstance(){
        if(logic == null){
            logic = new Logic();
            return logic;
        }else{
            return logic;
        }
    }
}
