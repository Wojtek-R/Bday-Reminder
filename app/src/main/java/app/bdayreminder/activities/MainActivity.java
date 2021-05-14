package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
//import android.widget.Toolbar
import androidx.appcompat.widget.Toolbar;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    //references
    Button addBdayBtn;
    ListView  listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting toolbar as the app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.main_lv);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<PersonModel> everyone = dataBaseHelper.getEveryone();

        //displaying db data in a list view
        ArrayAdapter personArrayAdapter = new ArrayAdapter<PersonModel>(MainActivity.this, android.R.layout.simple_list_item_1, everyone);
        listView.setAdapter(personArrayAdapter);

        addBdayBtn=(Button)findViewById(R.id.main_addBtn);
        addBdayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //opening new activity (add birthday page) on click
                Intent intent=new Intent(MainActivity.this, AddBday.class);
                startActivity(intent);
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