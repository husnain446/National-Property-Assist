package byteshaft.com.nationalpropertyassist.drainassist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import byteshaft.com.nationalpropertyassist.AppGlobals;
import byteshaft.com.nationalpropertyassist.R;
import byteshaft.com.nationalpropertyassist.activities.SelectPropertyActivity;
import byteshaft.com.nationalpropertyassist.utils.ServicesTask;

public class EmergencyUnblockActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private Button button_submit;
    private RadioGroup radioGroup;
    private EditText details;
    private RadioButton radioButton;
    private String mRadioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_unblock_activity);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        button_submit = (Button) findViewById(R.id.submit);
        details = (EditText) findViewById(R.id.emergency_unblock_et);
        radioGroup.setOnCheckedChangeListener(this);

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppGlobals.serverIdForProperty == 2112) {
                    Intent intent = new Intent(getApplicationContext(), SelectPropertyActivity.class);
                    startActivity(intent);
                } else {
                    String description = details.getText().toString();
                    new ServicesTask(EmergencyUnblockActivity.this, description, mRadioText).execute();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        radioButton = (RadioButton) findViewById(checkedId);
        mRadioText = radioButton.getText().toString();
        System.out.println(mRadioText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppGlobals.serverIdForProperty != 2112) {
            button_submit.setText("Submit");
        } else {
            button_submit.setText("Select Property");
        }
    }
}
