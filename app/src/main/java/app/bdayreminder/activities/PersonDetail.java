package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class PersonDetail extends AppCompatActivity {

    Button btnDelete, btnUpdate;
    EditText etName, etSurname, etDob;

    public static final String PREFS_NAME = "prefs";
    public static final String PREF_DARK_THEME = "dark_theme";

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        etDob = findViewById(R.id.et_dob);

        //regex expressions for fields validation
        awesomeValidation.addValidation(this, R.id.et_name, "^[a-zA-Z]+$", R.string.nameError);
        awesomeValidation.addValidation(this, R.id.et_surname, "^[a-zA-Z]+$", R.string.surnameError);
        awesomeValidation.addValidation(this, R.id.et_dob, "[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}", R.string.dateError);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

//        String message = "Birthdays " + intent.getParcelableExtra("PERSON_SELECTED").toString();
        String name = bundle.getString("personName");
        etName.setText(name);

        String surname = bundle.getString("personSurname");
        etSurname.setText(surname);

        String dob = bundle.getString("dob");
        etDob.setText(dob);

//        TextView myText = findViewById(R.id.textView);
//        myText.setText(message);



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(PersonDetail.this);
                Intent intent = getIntent();

                dataBaseHelper.deleteOne(intent.getParcelableExtra("PERSON_SELECTED"));
                Toast.makeText(PersonDetail.this, "Birthday removed", Toast.LENGTH_SHORT).show();

                etName.setText("");
                etSurname.setText("");
                etDob.setText("");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(PersonDetail.this);
                    Intent intent = getIntent();

                    dataBaseHelper.updateOne(intent.getParcelableExtra("PERSON_SELECTED"), String.valueOf(etName.getText()),
                            String.valueOf(etSurname.getText()), String.valueOf(etDob.getText()) );
                    Toast.makeText(PersonDetail.this, "Birthday updated", Toast.LENGTH_SHORT).show();

                    etName.setText("");
                    etSurname.setText("");
                    etDob.setText("");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.settings:
                Intent intent=new Intent(PersonDetail.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.home:
                Intent intent1 = new Intent(PersonDetail.this, MainActivity.class);
                startActivity(intent1);
                break;
        }
        return false;
    }
}