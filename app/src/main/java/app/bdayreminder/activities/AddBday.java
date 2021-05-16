package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import androidx.appcompat.widget.Toolbar;

public class AddBday extends AppCompatActivity {

    //declaring variables
    Button saveBdayBtn, addReminder;
    EditText name, surname, dob;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                            personModel = new PersonModel(-1, name.getText().toString(), surname.getText().toString(), dobFinal.toString());
                            Toast.makeText(AddBday.this, "Birthday added", Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e) {
                            Toast.makeText(AddBday.this, "Error adding Birthday", Toast.LENGTH_SHORT).show();
                            personModel = new PersonModel(-1,"error", "error", "error");
                        }

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddBday.this);
                        boolean success = dataBaseHelper.addOne(personModel);
                        Toast.makeText(AddBday.this, "Success = "+ success, Toast.LENGTH_SHORT).show();

                    } else {
                    }
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
                Intent intent=new Intent(AddBday.this, Settings.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}