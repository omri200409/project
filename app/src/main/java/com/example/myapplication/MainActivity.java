package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private TextView textView;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private TextView textView1;
    private RadioGroup radioGroup;
    private View checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components from XML layout
        initViews();
        click ();
    }
    private void initViews()
    {
        editText2 = findViewById(R.id.edittext2);
        editText3=findViewById(R.id.edittext3);
        editText1=findViewById(R.id.edittext1);
        textView = findViewById(R.id.textView);
        button=findViewById(R.id.button);
        radioGroup=findViewById(R.id.radioGroup);
        textView=findViewById(R.id.textView1);
    }

    /**
     * Sets click listener on the submission button.
     */
    private void click() {
        button.setOnClickListener(v -> showAtt());
    }

    /**
     * Displays user details in the result TextView when the submit button is clicked.
     */
    private void showAtt() {
        String email = editText3.getText().toString().trim();

        // Double check email format and highlight in red if invalid
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText3.setError("Please enter a valid email address");
            editText3.requestFocus();
            return;
        }
        if(!radioisclicked()){
            radioGroup
            radioGroup.requestFocus();
            return;
        }

        // Make result TextView visible and display user information
        textView.setVisibility(View.VISIBLE);
        textView.setText("Name: " + editText1.getText().toString() + ", last name : " + editText2.getText().toString() + ", email : " + email);
    }
private boolean radioisclicked(){
    int check1 = radioGroup.getCheckedRadioButtonId();
    if (check1 == -1){
        return false;
    }
    else
        return true;
    }

}