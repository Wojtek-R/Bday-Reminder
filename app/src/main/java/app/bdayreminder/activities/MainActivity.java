package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    //references
    Button addBdayBtn;
    ListView  listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}