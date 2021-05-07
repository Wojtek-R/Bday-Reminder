package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button addBdayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBdayBtn=(Button)findViewById(R.id.main_addBtn);
        addBdayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this, AddBday.class);
                startActivity(intent);
            }
        });
    }



    public void goTo(View view){
        Button addBirthday = findViewById(R.id.main_addBtn);
    }
}