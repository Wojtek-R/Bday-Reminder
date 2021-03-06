package app.bdayreminder.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String PERSON_TABLE = "PERSON_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_PERSON_IMAGE = "PERSON_IMAGE";
    public static final String COLUMN_PERSON_NAME = "PERSON_NAME";
    public static final String COLUMN_PERSON_SURNAME = "PERSON_SURNAME";
    public static final String COLUMN_PERSON_DOB = "PERSON_DOB";

    public ByteArrayOutputStream objectByteArrayOutputStream;
    public byte[] imageInByte;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "person.db", null, 1);
    }

    //called when database accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PERSON_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PERSON_IMAGE + " BLOB, "+ COLUMN_PERSON_NAME + " TEXT, " + COLUMN_PERSON_SURNAME + " TEXT, " + COLUMN_PERSON_DOB + " TEXT )";

        db.execSQL(createTableStatement);
    }

    //called when the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //method for adding one item to the database
    public boolean addOne(PersonModel personModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = personModel.getImage();

        objectByteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
        imageInByte = objectByteArrayOutputStream.toByteArray();


        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PERSON_IMAGE, imageInByte);
        cv.put(COLUMN_PERSON_NAME, personModel.getName());
        cv.put(COLUMN_PERSON_SURNAME, personModel.getSurname());
        cv.put(COLUMN_PERSON_DOB, personModel.getDob());

        long insert = db.insert(PERSON_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateOne(PersonModel personModel, String name, String surname, String dob) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PERSON_NAME, name);
        cv.put(COLUMN_PERSON_SURNAME, surname);
        cv.put(COLUMN_PERSON_DOB, dob);

        int id = personModel.getId();

        long update = db.update(PERSON_TABLE, cv, "ID ="+ id, null );

        if (update == -1){
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
                byte[] byteArray = cursor.getBlob(1);
                Bitmap personImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String personName = cursor.getString(2);
                String personSurname = cursor.getString(3);
                String personDob = cursor.getString(4);

                PersonModel newPerson = new PersonModel(personID, personImage, personName, personSurname, personDob);
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

    public boolean deleteAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + PERSON_TABLE );
//        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME=" + PERSON_TABLE);
        return true;
    }

    public boolean deleteOne(PersonModel personModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + PERSON_TABLE + " WHERE " + COLUMN_ID + " = " + personModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return  true;
        }
        else {
            return false;
        }
//        return true;
    }
}