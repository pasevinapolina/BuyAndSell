package by.famcs.pasevinapolina.buyandsell;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.btn_login)
    Button mLoginButton;

    @BindView(R.id.input_email)
    EditText mEmail;

    @BindView(R.id.input_password)
    EditText mPassword;

    @BindView(R.id.link_signup)
    TextView mSignupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener((v)-> {login();});
    }

    public void login() {

        Log.d(TAG, "Login");

        if(!validate()) {
            onLoginFailed();
            return;
        }

        mLoginButton.setEnabled(false);

        String authMessage = getResources().getString(R.string.auth);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(authMessage);
        progressDialog.show();

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        //TODO

        new android.os.Handler().postDelayed(
                ()-> {
                    onLoginSuccess();
                    progressDialog.dismiss();
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SIGNUP) {
            if(resultCode == RESULT_OK) {

                //TODO: successful signup
                this.finish();
            }
        }

    }

    private void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        finish();
    }

    private void onLoginFailed() {
        String failedMsg = getResources().getString(R.string.auth_error);
        Toast.makeText(getBaseContext(), failedMsg, Toast.LENGTH_LONG).show();
        mLoginButton.setEnabled(true);
    }

    public boolean validate() {

        boolean isValid = true;

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String errorMsg = "";

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMsg = getResources().getString(R.string.email_error);
            mEmail.setError(errorMsg);
            isValid = false;
        } else {
            mEmail.setError(null);
        }

        if(password.isEmpty() || password.length() < 5) {
            errorMsg = getResources().getString(R.string.password_error);
            mPassword.setError(errorMsg);
            isValid = false;
        } else {
            mPassword.setError(null);
        }
        return isValid;
    }
}