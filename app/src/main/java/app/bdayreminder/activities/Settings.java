package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    Button deleteDb;
    Switch sTheme;

    public static final String PREFS_NAME = "prefs";
    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        deleteDb = findViewById(R.id.btn_deleteDb);
        sTheme = findViewById(R.id.s_theme);
        sTheme.setChecked(useDarkTheme);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseHelper db = new DataBaseHelper(getBaseContext());

        deleteDb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Displaying confirmation alert dialog
                AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(Settings.this);
                myAlertBuilder.setTitle("Are you sure?");
                myAlertBuilder.setMessage("Do you wish to delete entire list of Birthdays?");
                myAlertBuilder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.deleteAll();
                        Toast.makeText(Settings.this, "All Birthdays List Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Settings.this, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                myAlertBuilder.show();
            }
        });



        sTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchTheme(isChecked);
            }
        });
    }
    //method for switching application theme
    public void switchTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(intent);
    }
    //inflating the setting item option on click
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items_nosettings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                Intent intent1 = new Intent(Settings.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.info:
                Intent intent2 = new Intent(Settings.this, AppInfo.class);
                startActivity(intent2);
                break;
        }
        return false;
    }
}