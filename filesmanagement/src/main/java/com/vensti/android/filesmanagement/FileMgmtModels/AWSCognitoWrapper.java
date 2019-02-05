package com.vensti.android.filesmanagement.FileMgmtModels;


import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;
import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;

public class AWSCognitoWrapper {

    private Context context;

    public AWSCognitoWrapper(Context context)
    {
        this.context = context;
    }

    public CognitoUserPool createUserPoool()
    {
        CognitoUserPool userPool = new CognitoUserPool(context,FileMgmtConstants.USER_POOL_ID,FileMgmtConstants.CLIENT_ID,FileMgmtConstants.CLIENT_SECRET,Regions.US_EAST_2);
        int i = 0;
        return userPool;
    }

}
