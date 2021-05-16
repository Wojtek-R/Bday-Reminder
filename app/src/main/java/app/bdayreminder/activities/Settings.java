package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    Button deleteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        deleteDb = findViewById(R.id.btn_deleteDb);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseHelper db = new DataBaseHelper(getBaseContext());

        deleteDb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                db.deleteAll();

                Toast.makeText(Settings.this, "Succes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}