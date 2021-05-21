package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toolbar
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //declarations
    Button addBdayBtn;
    ListView  listView;

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
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.main_lv);
        addBdayBtn = findViewById(R.id.main_addBtn);


        //setting toolbar as the app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<PersonModel> everyone = dataBaseHelper.getEveryone();

        //displaying db data in a list view
        ArrayAdapter personArrayAdapter = new ArrayAdapter<PersonModel>(MainActivity.this, R.layout.custom_list_item, everyone);
        listView.setAdapter(personArrayAdapter);

        addBdayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //opening new activity (add birthday page) on click
                Intent intent = new Intent(MainActivity.this, AddBday.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonModel clickedPerson = (PersonModel) parent.getItemAtPosition(position);

                String personName = clickedPerson.getName();

                Intent personInfo = new Intent(MainActivity.this, PersonDetail.class);
                personInfo.putExtra("PERSON_SELECTED", clickedPerson);
                personInfo.putExtra("personName", personName);

                startActivity(personInfo);
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
                Intent intent=new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}