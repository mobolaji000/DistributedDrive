package com.vensti.android.filesmanagement.FileMgmtModels;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;


public class AuthenticationWrapper {

    private Activity activity;

    public void startAuthentication(Activity activity)
    {
        this.activity = activity;
        AccountManager accountManager = AccountManager.get(activity);
        Account[] accountsByType  = accountManager.getAccountsByType(FileMgmtConstants.ACCOUNT_TYPE);
        Bundle options = new Bundle();


        if (accountsByType.length == 0)
        {
            accountManager.addAccount(FileMgmtConstants.ACCOUNT_TYPE,FileMgmtConstants.AUTH_TYPE,null,null,null,new OnAddAccountIntentCreated(),new Handler(new OnError()) );
        }
        else if (accountsByType.length > 1)
        {
            //implement multiple accounts present. not important
        }
        else
        {
            accountManager.getAuthToken(accountsByType[0],FileMgmtConstants.AUTH_TYPE,options,activity,new OnTokenAcquired(), new Handler(new OnError()));
        }


    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle>
    {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try
            {
                Bundle bundle = result.getResult();
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            }
            catch (Exception e)
            {

            }
        }
    }


    private class OnAddAccountIntentCreated implements AccountManagerCallback<Bundle>
    {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try
            {
                Intent launch = (Intent) result.getResult().get(AccountManager.KEY_INTENT);
                if(launch!=null){
                    activity.startActivity(launch);
                    return;
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    private class OnError implements Handler.Callback
    {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    }
}
