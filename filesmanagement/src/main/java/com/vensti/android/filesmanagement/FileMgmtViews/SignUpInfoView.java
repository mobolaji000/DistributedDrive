package com.vensti.android.filesmanagement.FileMgmtViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtPresenters.SignUpInfoFragment;
import com.vensti.android.filesmanagement.R;

public class SignUpInfoView implements FileMgmtContract.View {

    private SignUpInfoFragment presenter;
    private View inflatedView;
    private Button submitButton;

    public View inflateView(LayoutInflater layoutInflater, ViewGroup container, FileMgmtContract.PresenterFragment presenterFragment)
    {
        setViewPresenter(presenterFragment);
        inflatedView = layoutInflater.inflate(R.layout.signup_info,container,false);
        setViewHandles(inflatedView);
        setListeners();
        return inflatedView;
    }

    public void setViewHandles(View rootView) {
        submitButton = rootView.findViewById(R.id.submitButton);
    }

    public void setViewPresenter(FileMgmtContract.PresenterFragment presenterFragment)
    {
        presenter = (SignUpInfoFragment) presenterFragment;
    }

    private void setListeners()
    {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignUpButtonPressed();
            }
        });
    }


}
