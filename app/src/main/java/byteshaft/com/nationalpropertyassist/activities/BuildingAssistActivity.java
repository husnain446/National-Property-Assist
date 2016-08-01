package byteshaft.com.nationalpropertyassist.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import byteshaft.com.nationalpropertyassist.R;
import byteshaft.com.nationalpropertyassist.utils.ServicesTask;


public class BuildingAssistActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private EditText details;
    private Button submitButton;
    private RadioGroup radioGroup;
    private String mRadioText;
    private String mReceverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_building_assist);
        mReceverEmail = getString(R.string.email_string);

        details = (EditText) findViewById(R.id.building_assist_et);
        submitButton = (Button) findViewById(R.id.submit);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        submitButton.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.building_survey:
                mRadioText = "Building Survey";
                break;

            case R.id.repair_survey:
                mRadioText = "Repair Survey";
                break;

            case R.id.insurance_survey:
                mRadioText = "Insurance Survey";
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                String description = details.getText().toString();
                new ServicesTask(BuildingAssistActivity.this, description, mRadioText).execute();
                break;
        }
    }
}
