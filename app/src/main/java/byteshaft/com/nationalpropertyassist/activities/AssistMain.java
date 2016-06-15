package byteshaft.com.nationalpropertyassist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import byteshaft.com.nationalpropertyassist.R;

public class AssistMain extends AppCompatActivity implements View.OnClickListener {

    private Button drainAssist;
    private Button buildingAssist;
    private Button homeBuyerAssist;
    private Button plumberAssist;
    private Button waterAssist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist_main);


        drainAssist = (Button) findViewById(R.id.drain_assist);
        buildingAssist = (Button) findViewById(R.id.building_assist);
        homeBuyerAssist = (Button) findViewById(R.id.home_buyer_assist);
        plumberAssist = (Button) findViewById(R.id.plumber_assist);
        waterAssist = (Button) findViewById(R.id.water_assist);
        drainAssist.setOnClickListener(this);
        buildingAssist.setOnClickListener(this);
        homeBuyerAssist.setOnClickListener(this);
        plumberAssist.setOnClickListener(this);
        waterAssist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drain_assist:
                startActivity(new Intent(getApplicationContext(), DrainAssistActivity.class));
                break;

            case R.id.water_assist:
                startActivity(new Intent(getApplicationContext(), WaterAssistActivity.class));
                break;

            case R.id.home_buyer_assist:
                startActivity(new Intent(getApplicationContext(), HomeAssistActivity.class));
                break;

            case R.id.plumber_assist:
                startActivity(new Intent(getApplicationContext(), PlumberActivity.class));
                break;

            case R.id.building_assist:
                startActivity(new Intent(getApplicationContext(), BuildingAssistActivity.class));
                break;
        }
    }
}
