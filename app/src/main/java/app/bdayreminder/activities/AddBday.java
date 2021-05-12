package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddBday extends AppCompatActivity {

    //references
    Button saveBdayBtn;
    EditText name, surname, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bday);

        saveBdayBtn=(Button)findViewById(R.id.addBday_saveBdayBtn);
        name = findViewById(R.id.addBday_name);
        surname = findViewById(R.id.addBday_surname);
        dob = findViewById(R.id.addBday_et_Dob);

        saveBdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PersonModel personModel;

                    try {
                        //parsing to string format form date
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dobVar = sdf.parse(String.valueOf(dob.getText()));

                        //formatting string to desired format
                        String dobFinal = sdf.format(dobVar);

                        personModel = new PersonModel(-1, name.getText().toString(), surname.getText().toString(), dobFinal.toString());
                        Toast.makeText(AddBday.this, "Birthday added", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e) {
                        Toast.makeText(AddBday.this, "Error adding Birthday", Toast.LENGTH_SHORT).show();
                        personModel = new PersonModel(-1,"error", "error", "error");
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(AddBday.this);

                    boolean success = dataBaseHelper.addOne(personModel);

                    Toast.makeText(AddBday.this, "Success = "+ success, Toast.LENGTH_SHORT).show();


                    //Toast.makeText(AddBday.this, everyone.toString(), Toast.LENGTH_SHORT).show();
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