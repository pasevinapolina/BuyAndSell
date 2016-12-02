package by.famcs.pasevinapolina.buyandsell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    //@BindView(R.id.input_name) EditText mName;
    @BindView(R.id.input_email) EditText mEmail;
    @BindView(R.id.input_password) EditText mPassword;
    @BindView(R.id.input_confirm) EditText mConfirm;
    @BindView(R.id.btn_signup) Button mSignupButton;
    @BindView(R.id.link_login) TextView mLoginLink;
    @BindView(R.id.input_login) EditText mLogin;
    //@BindView(R.id.input_last_name) EditText mLastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mSignupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        String dialogMsg = getResources().getString(R.string.creating_account);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(dialogMsg);
        progressDialog.show();

        //String name = mName.getText().toString();
        //String lastName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String reEnterPassword = mConfirm.getText().toString();

        // TODO:signup logic.

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mSignupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        String failedMsg = getResources().getString(R.string.signup_error);
        Toast.makeText(getBaseContext(), failedMsg, Toast.LENGTH_LONG).show();

        mSignupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        //String name = mName.getText().toString();
        //String lastName = mLastName.getText().toString();

        String login = mLogin.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirm.getText().toString();

        String errorMsg = "";

        if (login.isEmpty()) {
            setError(mLogin, R.string.not_empty_error);
            valid = false;
        } else {
            mLogin.setError(null);
        }


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMsg = getResources().getString(R.string.email_error);
            mEmail.setError(errorMsg);
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 5) {
            errorMsg = getResources().getString(R.string.password_error);
            mPassword.setError(errorMsg);
            valid = false;
        } else {
            mPassword.setError(null);
        }

        if (confirmPassword.isEmpty() || confirmPassword.length() < 5
                || !(confirmPassword.equals(password))) {
            errorMsg = getResources().getString(R.string.password_not_match);
            mConfirm.setError(errorMsg);
            valid = false;
        } else {
            mConfirm.setError(null);
        }

        return valid;
    }

    private void setError(EditText field, int errorMsgKey) {
        String errorMsg = getResources().getString(errorMsgKey);
        field.setError(errorMsg);
    }
}
