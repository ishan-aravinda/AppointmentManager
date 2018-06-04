package iit.com.appointmentmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AppointmentDB.db";
    public static final String TABLE_NAME = "appointments";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table appointments " +
                        "(title text, date text, time text, description text, primary key (title, date))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(db);
    }

    /**
     * Adds a new appointment to the database
     * @param title - Title of the appointment
     * @param date - Date of the appointment
     * @param time - Time of the appointment
     * @param description - Appointment details
     * @return - returns false if there is a duplicate title on the same date, else, returns true
     */
    public Boolean addAppointment (String title, String date, String time, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("description", description);

        try {
            db.insertOrThrow("appointments", null, contentValues);
            return true;
        } catch(SQLiteConstraintException e){
            return false;
        }
    }

    /**
     * Deletes a specific appointment
     * @param title - title of the appointment to be deleted
     * @param date - date of the appointment
     */
    public void deleteAppointment(String title, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"title=? and date=?",new String[]{title, date});
    }

    /**
     * Deletes all appointments on a specific date
     * @param date - date of the appointments that need to be deleted
     */
    public void deleteAllAppointments(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"date=?",new String[]{date});
    }

    /**
     * Update an appointment on a specific date with the new details
     * @param date - date of the appointment
     * @param prevTitle - Title of the appointment that existed before
     * @param title - new title
     * @param time - new time
     * @param description - new appointment details
     * @return - returns false if there is a duplicate title on the same date, else, returns true
     */
    public Boolean updateAppointment(String date, String prevTitle, String title, String time, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("time", time);
        contentValues.put("description", description);

        try {
            db.update(TABLE_NAME, contentValues, "title=? and date=?", new String[]{prevTitle, date});
            return true;
        } catch(SQLiteConstraintException e){
            return false;
        }
    }

    /**
     * Moves an appointment to a new date
     * @param prevDate - Previous date of the appointment
     * @param newDate - New date of the appointment
     * @param title - Title of the appointment
     */
    public void moveAppointment(String prevDate, String newDate, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", newDate);

        db.update(TABLE_NAME, contentValues, "title=? and date=?", new String[]{title, prevDate});
        System.out.println("Updated");
    }

    /**
     * Returns the list of appointments on a particular date
     * @param date - date of the appointments
     * @return - The list of appointments
     */
    public ArrayList<String> getAppointmentsOnDate(String date) {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from appointments where date = '" + date + "'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(COLUMN_TITLE)) + "," + res.getString(res.getColumnIndex(COLUMN_TIME))
                    + "," + res.getString(res.getColumnIndex(COLUMN_DESCRIPTION)));
            res.moveToNext();
        }
        return arrayList;
    }

    /**
     * Get all the appointments in the database after a given date
     * @param date - The given date
     * @return - List of all the appointments
     */
    public ArrayList<String> getAllAppointments(String date){
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from appointments where date >= date('" + date + "')", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(res.getString(res.getColumnIndex(COLUMN_TITLE)) + "," + res.getString(res.getColumnIndex(COLUMN_DATE))
                    + "," + res.getString(res.getColumnIndex(COLUMN_TIME)) + "," + res.getString(res.getColumnIndex(COLUMN_DESCRIPTION)));
            res.moveToNext();
        }
        return arrayList;
    }
}
