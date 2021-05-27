package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class PersonDetail extends AppCompatActivity {

    Button btnDelete, btnUpdate;
    EditText etName, etSurname, etDob;
    ImageView personPhoto;

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

        Bundle bundle = getIntent().getExtras();

//        Bitmap bitmap = bundle.getParcelable("personImage");

        String name = bundle.getString("personName");
        etName.setText(name);

        String surname = bundle.getString("personSurname");
        etSurname.setText(surname);

        String dob = bundle.getString("dob");
        etDob.setText(dob);



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Displaying confirmation alert dialog
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(PersonDetail.this);
                myAlertBuilder.setTitle("Are you sure?");
                myAlertBuilder.setMessage("Do you wish to delete this birthday?");
                myAlertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(PersonDetail.this);
                        Intent intent = getIntent();

                        dataBaseHelper.deleteOne(intent.getParcelableExtra("PERSON_SELECTED"));

                        etName.setText("");
                        etSurname.setText("");
                        etDob.setText("");

                        Toast.makeText(PersonDetail.this, "Birthday Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PersonDetail.this, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    //Displaying confirmation alert dialog
                    AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(PersonDetail.this);
                    myAlertBuilder.setTitle("Are you sure?");
                    myAlertBuilder.setMessage("Do you wish to update this birthday?");
                    myAlertBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(PersonDetail.this);
                            Intent intent = getIntent();

                            dataBaseHelper.updateOne(intent.getParcelableExtra("PERSON_SELECTED"), String.valueOf(etName.getText()),
                                    String.valueOf(etSurname.getText()), String.valueOf(etDob.getText()) );
                            Toast.makeText(PersonDetail.this, "Birthday updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                    myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(PersonDetail.this, "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    myAlertBuilder.show();
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
            case R.id.info:
                Intent intent2 = new Intent(PersonDetail.this, AppInfo.class);
                startActivity(intent2);
                break;
        }
        return false;
    }
}