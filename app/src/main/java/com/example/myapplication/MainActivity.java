package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.student.student;

public class MainActivity extends AppCompatActivity {
    // UI components declaration
    private EditText editText1;      // First Name Input
    private EditText editText2;      // Last Name Input
    private EditText editText3;      // Email Input
    private TextView textView1;      // Result Output View
    private Spinner spinner;         // Major Dropdown Selection
    private Button button;           // Submit Button
    private RadioGroup radioGroup;   // Class/Grade Radio Buttons Group
    private CheckBox checkBox;       // Terms & Conditions CheckBox
    private CheckBox checkBox2;      // Notifications CheckBox

    /**
     * Entry point of the Activity.
     * Sets the layout, initializes views, loads saved student data, and sets up click listener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind XML views to Java variables
        initViews();

        // Populate Spinner dropdown with major options
        initViews2();

        // Check and display saved student data from SharedPreferences if available
        loadSavedData();

        // Attach click listener to the submit button
        click();
    }

    /**
     * Binds XML layout elements to Java view objects using findViewById.
     */
    private void initViews() {
        editText2 = findViewById(R.id.edittext2);
        editText3 = findViewById(R.id.edittext3);
        editText1 = findViewById(R.id.edittext1);
        button = findViewById(R.id.button);
        radioGroup = findViewById(R.id.radioGroup);
        textView1 = findViewById(R.id.textView1);
        spinner = findViewById(R.id.spinner);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
    }

    /**
     * Creates an ArrayAdapter with major options from strings.xml (R.array.majors)
     * and sets it on the Spinner widget.
     */
    private void initViews2() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.majors,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(adapter);
    }

    /**
     * Sets click listener on the submission button to call showAtt().
     */
    private void click() {
        button.setOnClickListener(v -> showAtt());
    }

    /**
     * Validates all form inputs (First Name, Last Name, Email, Class, Checkbox, Spinner).
     * If valid, constructs a student object, saves it in SharedPreferences, and displays it.
     */
    private void showAtt() {
        // 1. Validate First Name (must not be empty)
        String firstName = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(firstName)) {
            editText1.setError(getString(R.string.error_enter_first_name));
            editText1.requestFocus();
            return;
        }

        // 2. Validate Last Name (must not be empty)
        String lastName = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(lastName)) {
            editText2.setError(getString(R.string.error_enter_last_name));
            editText2.requestFocus();
            return;
        }

        // 3. Validate Email format (must match valid email pattern)
        String email = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText3.setError(getString(R.string.error_invalid_email));
            editText3.requestFocus();
            return;
        }

        // 4. Validate Class selection (a radio button must be selected)
        if (radioisclicked() == null) {
            RadioButton lastButton = (RadioButton) radioGroup.getChildAt(radioGroup.getChildCount() - 1);
            if (lastButton != null) {
                lastButton.setError(getString(R.string.error_select_option));
                lastButton.setFocusableInTouchMode(true);
                lastButton.requestFocus();
            }
            return;
        }

        // 5. Validate Terms CheckBox (must be checked)
        if (checkBoxselected() == -1) {
            checkBox.setError(getString(R.string.error_check_box));
            checkBox.setFocusableInTouchMode(true);
            checkBox.requestFocus();
            return;
        }

        // 6. Validate Major Spinner (must not be default "enter major")
        String major = spinner.getSelectedItem().toString();
        if (major.equals(getString(R.string.enter_major_text))) {
            TextView spinnertext = (TextView) spinner.getSelectedView();
            if (spinnertext != null) {
                spinnertext.setError(getString(R.string.error_enter_major));
                spinnertext.setFocusableInTouchMode(true);
                spinnertext.setFocusable(true);
                spinnertext.requestFocus();
            }
            return;
        }

        // Create student object with all captured details
        student currentStudent = new student(
                firstName,
                lastName,
                email,
                radioisclicked(),
                checkBox.isChecked(),
                checkBox2.isChecked(),
                major
        );

        // Save or update student data in SharedPreferences
        saveStudent(currentStudent);

        // Make result TextView visible and display student string representation
        textView1.setVisibility(View.VISIBLE);
        textView1.setText(currentStudent.toString());
    }

    /**
     * Saves the student object fields into SharedPreferences persistent storage.
     * Overwrites any previously saved student data.
     *
     * @param s The student object to save.
     */
    private void saveStudent(student s) {
        SharedPreferences prefs = getSharedPreferences("StudentData", MODE_PRIVATE);
        prefs.edit()
                .putBoolean("has_data", true)
                .putString("name", s.getName())
                .putString("lname", s.getLname())
                .putString("email", s.getEmail())
                .putString("class1", s.getClass1())
                .putBoolean("terms", s.isTerms())
                .putBoolean("notifications", s.isNotifications())
                .putString("major", s.getMajor())
                .apply();
    }

    /**
     * Loads student data from SharedPreferences when the app launches.
     * If data exists, constructs a student object and displays it in the TextView.
     */
    private void loadSavedData() {
        SharedPreferences prefs = getSharedPreferences("StudentData", MODE_PRIVATE);
        // Check if data was previously saved
        if (prefs.getBoolean("has_data", false)) {
            String name = prefs.getString("name", "");
            String lname = prefs.getString("lname", "");
            String email = prefs.getString("email", "");
            String class1 = prefs.getString("class1", "");
            boolean terms = prefs.getBoolean("terms", false);
            boolean notifications = prefs.getBoolean("notifications", false);
            String major = prefs.getString("major", "");

            // Reconstruct student object and display
            student s = new student(name, lname, email, class1, terms, notifications, major);
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(s.toString());
        }
    }

    /**
     * Checks which radio button option is selected in the RadioGroup.
     *
     * @return Selected radio button text, or null if no option is selected.
     */
    private String radioisclicked() {
        int check1 = radioGroup.getCheckedRadioButtonId();
        if (check1 == -1) {
            return null;
        }
        RadioButton selected = findViewById(check1);
        return selected.getText().toString();
    }

    /**
     * Checks if the terms and conditions checkbox is checked.
     *
     * @return 1 if checked, -1 if unchecked.
     */
    private int checkBoxselected() {
        if (!checkBox.isChecked()) {
            return -1;
        }
        return 1;
    }

    /**
     * Checks the notifications checkbox state.
     *
     * @return "on" if checked, "off" if unchecked.
     */
    private String checkBoxselected2() {
        if (!checkBox2.isChecked()) {
            return getString(R.string.status_off);
        }
        return getString(R.string.status_on);
    }
}