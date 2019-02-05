package com.vensti.android.filesmanagement.FileMgmtBase;

/**
 * Created by mobolajioo on 10/8/18.
 */

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vensti.android.venstidrive.CoreContract;

public interface FileMgmtContract extends CoreContract {

    interface View  {
        void setViewPresenter(FileMgmtContract.PresenterFragment presenterFragment);
        void setViewHandles(android.view.View rootView);
        android.view.View inflateView(LayoutInflater inflater, ViewGroup container, PresenterFragment presenterFragment);
    }

    interface PresenterActivity{
        ViewGroup getRootView ();
        void inflateFragments(int fragmentContainer,Fragment fragmentContent);
    }

    interface PresenterFragment{
        FileMgmtContract.View getPresenterView ();
        FileMgmtContract.Model getPresenterModel();
    }

    interface Model{
        PresenterActivity getPresenter ();
    }

}
