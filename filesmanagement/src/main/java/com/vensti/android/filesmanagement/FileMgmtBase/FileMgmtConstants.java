package com.vensti.android.filesmanagement.FileMgmtBase;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class FileMgmtConstants
{
    private FileMgmtConstants()
    {

    }

    public static final File DOCUMENTS_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    public static final File MUSIC_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    public static final File DCIM_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    public static final File DOWNLOADS_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    public static final int NUMBER_OF_COLUMNS = 4;

    public static final ArrayList<File> createInitialDirectoryList()
    {
        ArrayList<File> initialDirectoryList = new ArrayList<>();
        initialDirectoryList.add(DOCUMENTS_DIRECTORY);
        initialDirectoryList.add(DCIM_DIRECTORY);
        initialDirectoryList.add(MUSIC_DIRECTORY);
        initialDirectoryList.add(DOWNLOADS_DIRECTORY);
        return initialDirectoryList;
    }

    public static final String ARG_ACCOUNT_TYPE = "accountType";
    public static final String ARG_AUTH_TYPE = "authType";
    public static final String ARG_PASSWORD = "password";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "isAddingNewAccount";

    public static final String ACCOUNT_TYPE = "Vensti";
    public static final String AUTH_TYPE = "Basic";

    public static final String USER_POOL_ID = "us-east-2_9CW7NYbi4";
    public static final String CLIENT_ID = "h0dacnluaih7ca6etqvd7neu6";
    public static final String CLIENT_SECRET = "bgs9os6vmre1c50a1iahnbenqkqrmgnrc3fcoe8u3016ppc1cn3";
    //public static final String COGNITO_REGION = "arn:aws:cognito-idp:us-east-2:861389071105:userpool/us-east-2_9CW7NYbi4";

}
