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
import by.famcs.pasevinapolina.buyandsell.models.User;

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

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginButton.setOnClickListener((v)-> {login();});

        mSignupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
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
        //disable going back to MainActivity
        moveTaskToBack(true);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SIGNUP) {
            if(resultCode == RESULT_OK) {

                this.finish();
            }
        }

    }

    private void onLoginSuccess() {
        mLoginButton.setEnabled(true);
        user = new User();
        user.setLogin(mEmail.getText().toString());
        user.setPassword(mPassword.getText().toString());
        getIntent().putExtra(MainActivity.USER, user);
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
