package app.bdayreminder.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AddBday extends AppCompatActivity {

    //references
    Button saveBdayBtn;
    EditText name, surname, dob;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bday);

        //choosing validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        saveBdayBtn=(Button)findViewById(R.id.addBday_saveBdayBtn);
        name = findViewById(R.id.addBday_name);
        surname = findViewById(R.id.addBday_surname);
        dob = findViewById(R.id.addBday_et_Dob);

        //regex expressions for fields validation
        awesomeValidation.addValidation(this, R.id.addBday_name, "^[a-zA-Z]+$", R.string.nameError);
        awesomeValidation.addValidation(this, R.id.addBday_surname, "^[a-zA-Z]+$", R.string.surnameError);
        awesomeValidation.addValidation(this, R.id.addBday_et_Dob, "[0-9]{1,2}(/|-)[0-9]{1,2}(/|-)[0-9]{4}", R.string.dateError);


        saveBdayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if (awesomeValidation.validate()) {
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
                    } else {
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