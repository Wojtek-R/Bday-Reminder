package app.bdayreminder.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import androidx.appcompat.widget.Toolbar;

public class AddBday extends AppCompatActivity {

    //declaring variables
    Button saveBdayBtn, addReminder;
    EditText name, surname, dob;
    ImageView imageView;

    public static final String PREFS_NAME = "prefs";
    public static final String PREF_DARK_THEME = "dark_theme";

    public static final int PICK_IMAGE_REQUEST = 100;
    public Uri imageFilePath;
    public Bitmap imageToStore;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //enabling theme change
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bday);

        //setting toolbar as the app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //choosing validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //referencing
        saveBdayBtn = findViewById(R.id.addBday_saveBdayBtn);
        addReminder = findViewById(R.id.btn_reminder);

        name = findViewById(R.id.addBday_name);
        surname = findViewById(R.id.addBday_surname);
        dob = findViewById(R.id.addBday_et_Dob);

        //regex expressions for fields validation
        awesomeValidation.addValidation(this, R.id.addBday_name, "^[a-zA-Z]+$", R.string.nameError);
        awesomeValidation.addValidation(this, R.id.addBday_surname, "^[a-zA-Z]+$", R.string.surnameError);
        awesomeValidation.addValidation(this, R.id.addBday_et_Dob, "[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}", R.string.dateError);

        try {
            imageView = findViewById(R.id.iv_addBday);
        }
        catch (Exception e){

        }

        saveBdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (awesomeValidation.validate()) {
                        PersonModel personModel;

                        try {
                            //parsing to string format form date
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dobVar = sdf.parse(String.valueOf(dob.getText()));

                            //formatting string to desired format
                            String dobFinal = sdf.format(dobVar);

                            personModel = new PersonModel(-1, imageToStore, name.getText().toString(), surname.getText().toString(), dobFinal.toString());
                            Toast.makeText(AddBday.this, "Birthday added", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(AddBday.this, "Error adding Birthday", Toast.LENGTH_SHORT).show();
                            personModel = new PersonModel(-1,null,"error", "error", "error");
                        }

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddBday.this);
                        boolean success = dataBaseHelper.addOne(personModel);
//                        Toast.makeText(AddBday.this, "Success = "+ success, Toast.LENGTH_SHORT).show();

                        name.setText("");
                        surname.setText("");
                        dob.setText("");

                    } else {
                    }
                }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(imageView);
            }
        });

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            //method for adding events to calendar with inserted values
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, name.getText().toString() + "'s Birthday");
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    startActivity(intent);

                } else {
                }
            }
        });
    }

    public void chooseImage (View objectView){
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);

//            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
        catch (Exception e){

        }
    }

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);

                imageView.setImageBitmap(imageToStore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //inflating the setting item option on click
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
    //method for creating actions on menu item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(AddBday.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent intent1 = new Intent(AddBday.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.info:
                Intent intent2 = new Intent(AddBday.this, AppInfo.class);
                startActivity(intent2);
                break;
        }
        return false;
    }
}