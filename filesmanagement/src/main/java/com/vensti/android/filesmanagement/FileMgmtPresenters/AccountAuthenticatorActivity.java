package com.vensti.android.filesmanagement.FileMgmtPresenters;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;
import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtModels.AWSCognitoWrapper;
import com.vensti.android.filesmanagement.R;


public class AccountAuthenticatorActivity extends AppCompatActivity implements FileMgmtContract.PresenterActivity, SignUpInfoFragment.OnSignUpButtonPressedListener, ConfirmUserFragment.OnConfirmButtonPressedListener {


    private ViewGroup mRootView;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private SignUpInfoFragment signUpInfoFragment = new SignUpInfoFragment();
    private ConfirmUserFragment confirmUserFragment = new ConfirmUserFragment();
    private CognitoUser cognitoUser;
    private GenericHandler confirmationCallback;
    private SignUpHandler signUpCallback;
    private AuthenticationHandler authenticationCallback;
    private String authToken;
    private String email;
    private String password;
    private String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getRootView();
        setUpAWSCallBackHandlers();
        inflateFragments(R.id.fragment1,signUpInfoFragment);
        setContentView(R.layout.activity_account_authenticator);
    }


    @Override
    public ViewGroup getRootView() {
        return (LinearLayout) this.findViewById(R.id.signUpRootView);
    }

    @Override
    public void inflateFragments(int fragmentContainer, Fragment fragmentContent) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, fragmentContent);
        fragmentTransaction.commit();
    }

    public void replaceFragments(int fragmentContainer, Fragment newFragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onSignUpButtonPressed() {
        continueSignUp();
    }

    @Override
    public void onConfirmButtonPressed() {
        final String code = ((EditText)findViewById(R.id.confirmationCodeInput)).getText().toString();
        checkConfirmationInfo(code);
    }

    private void continueSignUp()
    {
        email = ((EditText)findViewById(R.id.emailInput)).getText().toString() ;
        password = ((EditText)findViewById(R.id.passwordInput)).getText().toString();
        phone = ((EditText)findViewById(R.id.phoneInput)).getText().toString();

        AWSCognitoWrapper awsCognitoWrapper = new AWSCognitoWrapper(getApplicationContext());
        CognitoUserPool userPool = awsCognitoWrapper.createUserPoool();
        CognitoUserAttributes userAttributes =new CognitoUserAttributes();
        userAttributes.addAttribute("phone_number","+01"+phone);
        userPool.signUpInBackground(email,password,userAttributes,null,signUpCallback);
    }

    private void handleConfirmation(CognitoUser user)
    {
        cognitoUser = user;
        replaceFragments(R.id.fragment1,confirmUserFragment);
    }

    private void checkConfirmationInfo(String code){
        cognitoUser.confirmSignUpInBackground(code,true,confirmationCallback);
    }

    private void finishLogin(Intent intent)
    {

        final AccountManager accountManager = AccountManager.get(this);
        AccountAuthenticatorResponse mAccountAuthenticatorResponse = getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(FileMgmtConstants.ARG_PASSWORD);
        final Account account = new Account(accountName,intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if(getIntent().getBooleanExtra(FileMgmtConstants.ARG_IS_ADDING_NEW_ACCOUNT,false))
        {
            String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authTokenType = intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
            accountManager.addAccountExplicitly(account,accountPassword,null);
            accountManager.setAuthToken(account,authTokenType,authToken);
        }
        else
        {
            accountManager.setPassword(account,accountPassword);
        }

        if (true) {
            mAccountAuthenticatorResponse.onResult(intent.getExtras());
        } else {
            mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                    "canceled");
        }

        setResult(RESULT_OK,intent);
        finish();
    }

    private void setUpAWSCallBackHandlers()
    {
        signUpCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                if(!signUpConfirmationState)
                {
                    handleConfirmation(user);
                }
                else
                {

                }
            }
            @Override
            public void onFailure(Exception exception)
            {
            }
        };

        confirmationCallback = new GenericHandler(){

            @Override
            public void onSuccess() {
                cognitoUser.getSessionInBackground(authenticationCallback);
            }
            @Override
            public void onFailure(Exception exception) {

            }
        };

        authenticationCallback = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                authToken = userSession.getAccessToken().getJWTToken();
                final Intent result = new Intent();
                result.putExtra(AccountManager.KEY_ACCOUNT_NAME,email);
                result.putExtra(AccountManager.KEY_ACCOUNT_TYPE,FileMgmtConstants.ACCOUNT_TYPE);
                result.putExtra(AccountManager.KEY_AUTHTOKEN,authToken);
                result.putExtra(FileMgmtConstants.ARG_PASSWORD,password);
                finishLogin(result);
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId) {
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(UserId,password,null);
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        };
    }
}