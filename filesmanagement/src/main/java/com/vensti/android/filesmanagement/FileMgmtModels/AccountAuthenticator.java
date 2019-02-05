package com.vensti.android.filesmanagement.FileMgmtModels;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;
import com.vensti.android.filesmanagement.FileMgmtPresenters.AccountAuthenticatorActivity;

public class AccountAuthenticator extends AbstractAccountAuthenticator {
    private final Context context;
    public AccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(context,AccountAuthenticatorActivity.class);
        intent.putExtra(FileMgmtConstants.ARG_ACCOUNT_TYPE,accountType);
        intent.putExtra(FileMgmtConstants.ARG_AUTH_TYPE,authTokenType);
        intent.putExtra(FileMgmtConstants.ARG_IS_ADDING_NEW_ACCOUNT,true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        return bundle;
        }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        final AccountManager accountManager = AccountManager.get(context);
        String authToken = accountManager.peekAuthToken(account,authTokenType);

        if(TextUtils.isEmpty(authToken))
        {
            final String password = accountManager.getPassword(account);
            if(password!=null)
            {
                //authenticate to server using account name, password, and token type
            }
        }

        if(!TextUtils.isEmpty(authToken))
        {
            final Bundle resultScenario1 = new Bundle();
            resultScenario1.putString(AccountManager.KEY_ACCOUNT_NAME,account.name);
            resultScenario1.putString(AccountManager.KEY_ACCOUNT_TYPE,account.type);
            resultScenario1.putString(AccountManager.KEY_AUTHTOKEN,authToken);
            return resultScenario1;
        }

        final Intent intent = new Intent(context, AccountAuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);
        intent.putExtra(FileMgmtConstants.ARG_ACCOUNT_TYPE,account.type);
        intent.putExtra(FileMgmtConstants.ARG_AUTH_TYPE,authTokenType);

        final Bundle resultScenario2 = new Bundle();
        resultScenario2.putParcelable(AccountManager.KEY_INTENT,intent);
        return resultScenario2;

    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }

}

