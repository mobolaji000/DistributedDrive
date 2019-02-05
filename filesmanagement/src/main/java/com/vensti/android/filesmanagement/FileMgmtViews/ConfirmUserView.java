package com.vensti.android.filesmanagement.FileMgmtViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtPresenters.ConfirmUserFragment;
import com.vensti.android.filesmanagement.R;

public class ConfirmUserView implements FileMgmtContract.View {

    private ConfirmUserFragment presenter;
    private View inflatedView;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, FileMgmtContract.PresenterFragment presenterFragment) {
        setViewPresenter(presenterFragment);
        inflatedView = inflater.inflate(R.layout.signup_info,container,false);
        setViewHandles(inflatedView);
        setListeners();
        return inflatedView;
    }

    @Override
    public void setViewHandles(View rootView) {

    }

    @Override
    public void setViewPresenter(FileMgmtContract.PresenterFragment presenterFragment) {
        presenter = (ConfirmUserFragment) presenterFragment;
    }

    private void setListeners()
    {

    }
}
