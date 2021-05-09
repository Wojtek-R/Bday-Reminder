package app.bdayreminder.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PERSON_TABLE = "PERSON_TABLE";
    public static final String COLUMN_PERSON_NAME = "PERSON_NAME";
    public static final String COLUMN_PERSON_SURNAME = "PERSON_SURNAME";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "person.db", null, 1);
    }

    //called when database accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PERSON_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PERSON_NAME + " TEXT, " + COLUMN_PERSON_SURNAME + " TEXT )";

        db.execSQL(createTableStatement);
    }

    //called when the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //method for adding one item to the database
    public boolean addOne(PersonModel personModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PERSON_NAME, personModel.getName());
        cv.put(COLUMN_PERSON_SURNAME, personModel.getSurname());

        long insert = db.insert(PERSON_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public List<PersonModel> getEveryone(){
        List<PersonModel> returnList = new ArrayList<>();

        //getting data form the database
        String queryString = " SELECT * FROM " + PERSON_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int personID = cursor.getInt(0);
                String personName = cursor.getString(1);
                String personSurname = cursor.getString(2);

                PersonModel newPerson = new PersonModel(personID, personName, personSurname);
                returnList.add(newPerson);

            }while (cursor.moveToNext());
        }
        else{
            //fail, do not add anything to the list.
        }

        //close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }
}
