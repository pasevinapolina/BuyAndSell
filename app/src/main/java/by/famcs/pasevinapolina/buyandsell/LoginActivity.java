package by.famcs.pasevinapolina.buyandsell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import by.famcs.pasevinapolina.buyandsell.adapters.HttpHandler;
import by.famcs.pasevinapolina.buyandsell.jsonparsers.GsonParser;
import by.famcs.pasevinapolina.buyandsell.models.User;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    ProgressDialog progressDialog;

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

        mLoginButton = (Button)findViewById(R.id.btn_login);
        mSignupLink = (TextView)findViewById(R.id.link_signup);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);

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

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        new AsyncLogin().execute(email,password);

        new android.os.Handler().postDelayed(
                ()-> {
                    if(user != null) {
                        onLoginSuccess();
                    } else {
                        onLoginFailed();
                    }
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
        //user = new User();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MainActivity.USER, user);
    }

    private class AsyncLogin extends AsyncTask<String, Boolean, Boolean>
    {
        //ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);

            String authMessage = getResources().getString(R.string.auth);

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(authMessage);
            progressDialog.show();

        }
        @Override
        protected Boolean doInBackground(String... params) {
            String url = "http://buyandsellweb.herokuapp.com/json/user_data.json";
            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makePOSTCall(url, params[0], params[1]);
            User[] users = GsonParser.parseJSONtoUser(response);

            boolean isAuth = false;
            for (User user1 : users) {
                if (user1.getEmail().equals(params[0]) &&
                        user1.getPassword().equals(params[1])) {
                    user = user1;
                    isAuth = true;
                }
            }

            return Boolean.valueOf(isAuth);
        }

        @Override
        protected void onPostExecute(Boolean isAuth) {

            //pdLoading.dismiss();
            if(progressDialog.isShowing()) {
                       progressDialog.dismiss();
                    }

            if(isAuth){
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                onLoginSuccess();

            }else {

                //onLoginSuccess();
                onLoginFailed();

            }
        }

    }
}
