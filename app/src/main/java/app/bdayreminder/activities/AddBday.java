package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddBday extends AppCompatActivity {

    //references
    Button saveBdayBtn;
    EditText name, surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bday);

        saveBdayBtn=(Button)findViewById(R.id.addBday_saveBdayBtn);
        name = findViewById(R.id.addBday_name);
        surname = findViewById(R.id.addBday_surname);


        saveBdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    try {
                        PersonModel personModel = new PersonModel(-1, name.getText().toString(), surname.getText().toString());
                        Toast.makeText(AddBday.this, "Birthday added", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(AddBday.this, "Error adding Birthday", Toast.LENGTH_SHORT).show();
                    }



                }
        });

        name.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
        });

        surname.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
}