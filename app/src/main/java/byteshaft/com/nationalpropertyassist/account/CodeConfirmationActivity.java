package byteshaft.com.nationalpropertyassist.account;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import byteshaft.com.nationalpropertyassist.AppGlobals;
import byteshaft.com.nationalpropertyassist.Helpers;
import byteshaft.com.nationalpropertyassist.MainActivity;
import byteshaft.com.nationalpropertyassist.R;
import byteshaft.com.nationalpropertyassist.WebServiceHelper;
import byteshaft.com.nationalpropertyassist.fragments.Help;

/**
 * Created by husnain on 6/7/16.
 */
public class CodeConfirmationActivity extends Activity {

    private Button mSubmitButton;
    private EditText mEmail;
    private EditText mCode;

    private String mConfirmationEmail;
    private String mConformationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_code_activity);
        mEmail = (EditText) findViewById(R.id.et_confirmation_code_email);
        mCode = (EditText) findViewById(R.id.et_confirmation_code);
        mSubmitButton = (Button) findViewById(R.id.btn_confirmation_code_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmationEmail = mEmail.getText().toString();
                mConformationCode = mCode.getText().toString();
                System.out.println(mConfirmationEmail);
                System.out.println(mConformationCode);
                if (validateConfirmationCode()) {
                    new UserConfirmationTask().execute();
                }
            }
        });

        mEmail.setText(RegistrationActivity.mEmail);
        mConfirmationEmail = RegistrationActivity.mEmail;
    }

    public boolean validateConfirmationCode() {
        boolean valid = true;
        if (mConformationCode.isEmpty() || mConformationCode.length() < 4) {
            mCode.setError("Minimum 4 Characters");
            valid = false;
        } else {
            mCode.setError(null);
        }
        return valid;
    }

    private class UserConfirmationTask extends AsyncTask<String, Integer, String> {

        private boolean noInternet = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSubmitButton.setEnabled(false);
            WebServiceHelper.showProgressDialog(CodeConfirmationActivity.this, "Activating User");

        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject jsonObject;
            if (WebServiceHelper.isNetworkAvailable() && WebServiceHelper.isInternetWorking()) {

                try {
                    jsonObject = WebServiceHelper.ActivationCodeConfirmation(mConfirmationEmail
                            , mConformationCode);
                    System.out.println(jsonObject + "okay");
                    if (AppGlobals.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        String first_name = jsonObject.getString(AppGlobals.KEY_FIRSTNAME);
                        String last_name = jsonObject.getString(AppGlobals.KEY_LASTNAME);
                        String email = jsonObject.getString(AppGlobals.KEY_EMAIL);
                        String mobile_phone = jsonObject.getString(AppGlobals.KEY_MOBILEPHONE);
                        String home_phone = jsonObject.getString(AppGlobals.KEY_HOMEPHONE);
                        String token = jsonObject.getString(AppGlobals.KEY_USER_TOKEN);

                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_FIRSTNAME, first_name);
                        Log.i("First name", " " + Helpers.getStringFromSharedPreferences(AppGlobals.KEY_FIRSTNAME));
                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_LASTNAME, last_name);
                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_EMAIL, email);
                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_MOBILEPHONE, mobile_phone);
                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_HOMEPHONE, home_phone);
                        Helpers.saveDataToSharedPreferences(AppGlobals.KEY_USER_TOKEN, token);
                        Log.i("user token", " " + Helpers.getStringFromSharedPreferences(AppGlobals.KEY_USER_TOKEN));
                        Helpers.saveUserLogin(true);
                        finish();

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                noInternet = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);
            WebServiceHelper.dismissProgressDialog();
            if (noInternet) {
                Helpers.alertDialog(CodeConfirmationActivity.this, "Connection error",
                        "Check your internet connection");
            }
            if (AppGlobals.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Toast.makeText(AppGlobals.getContext(),
                        "Confirmation successful",
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(AppGlobals.getContext(),
                        "Confirmation failed, check internet and retry", Toast.LENGTH_LONG).show();
            }
        }
    }
}