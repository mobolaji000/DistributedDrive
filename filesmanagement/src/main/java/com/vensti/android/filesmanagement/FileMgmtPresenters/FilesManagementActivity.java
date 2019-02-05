package com.vensti.android.filesmanagement.FileMgmtPresenters;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtModels.AuthenticationWrapper;
import com.vensti.android.filesmanagement.R;


public class FilesManagementActivity extends AppCompatActivity implements FileMgmtContract.PresenterActivity, UploadButtonFragment.OnUploadButtonPressedListener {


    private ViewGroup mRootView;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private UploadButtonFragment uploadButtonFragment = new UploadButtonFragment();
    private DirectoryStructureFragment directoryStructureFragment = new DirectoryStructureFragment();


    public ViewGroup getRootView(){
        return (LinearLayout) this.findViewById(R.id.uploadFileRootView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationWrapper authenticationWrapper = new AuthenticationWrapper();
        authenticationWrapper.startAuthentication(this);
        mRootView = getRootView();
        inflateFragments(R.id.fragment1,uploadButtonFragment);
        setContentView(R.layout.main_activity);
    }

    @Override
    public void onBackPressed()
    {
        directoryStructureFragment.updateDirectoryStructureView();
    }

    public void onUploadButtonPressed(boolean directoryExplorerInflated)
    {
        if(directoryExplorerInflated)
        {
            directoryStructureFragment.getDataToDownload();
        }
        else
        {
            inflateFragments(R.id.fragment2,directoryStructureFragment);
        }
    }

    public void inflateFragments(int fragmentContainer,Fragment fragmentContent)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragmentContainer, fragmentContent);
        fragmentTransaction.commit();
    }


}
