package byteshaft.com.nationalpropertyassist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import byteshaft.com.nationalpropertyassist.AppGlobals;
import byteshaft.com.nationalpropertyassist.R;
import byteshaft.com.nationalpropertyassist.utils.ServicesTask;

public class PlumberActivity extends Activity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {


    private RadioGroup radioGroup;
    private EditText details;
    private Button submitButton;
    private String mRadioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_plumber_assist);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        details = (EditText) findViewById(R.id.plumber_assist_et);
        submitButton = (Button) findViewById(R.id.submit);
        radioGroup.setOnCheckedChangeListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.repair_plumbing:
                mRadioText = "Repair to leaking plumbing Installation";
                break;
            case R.id.renewal_of_plumbing_installation:
                mRadioText = "Renewal of plumbing Installation";
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppGlobals.serverIdForProperty != 2112) {
            submitButton.setText("Submit");
        } else {
            submitButton.setText("Select Property");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (AppGlobals.serverIdForProperty == 2112) {
                    Intent intent = new Intent(getApplicationContext(), SelectPropertyActivity.class);
                    startActivity(intent);
                } else {
                    String description = details.getText().toString();
                    new ServicesTask(PlumberActivity.this, description, mRadioText).execute();
                }
                break;
        }
    }
}
