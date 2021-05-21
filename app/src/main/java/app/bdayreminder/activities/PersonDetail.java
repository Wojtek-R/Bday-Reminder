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
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetail extends AppCompatActivity {

    Button btDelete;
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
        setContentView(R.layout.activity_person_detail);

        btDelete = findViewById(R.id.btn_delete);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String message = "Birthdays " + intent.getParcelableExtra("PERSON_SELECTED").toString();

        TextView myText = findViewById(R.id.textView);

        myText.setText(message);

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(PersonDetail.this);


                dataBaseHelper.deleteOne(intent.getParcelableExtra("PERSON_SELECTED"));
                Toast.makeText(PersonDetail.this, "Birthday removed", Toast.LENGTH_SHORT).show();
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