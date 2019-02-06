package com.vensti.android.filesmanagement.FileMgmtPresenters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtViews.SignUpInfoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpInfoFragment extends Fragment implements FileMgmtContract.PresenterFragment {

    private SignUpInfoView signUpInfoView;
    private OnSignUpButtonPressedListener mCallBack;

    public SignUpInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpInfoView = (SignUpInfoView) getPresenterView();
        View inflatedView = signUpInfoView.inflateView(inflater,container,this);
        return inflatedView;
    }

    @Override
    public FileMgmtContract.Model getPresenterModel() {
        return null;
    }

    @Override
    public FileMgmtContract.View getPresenterView() {
        return new SignUpInfoView();
    }

    public interface OnSignUpButtonPressedListener
    {
        void onSignUpButtonPressed();
    }

    public void onSignUpButtonPressed()
    {
        mCallBack.onSignUpButtonPressed();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallBack = (OnSignUpButtonPressedListener) context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement OnSignUpButtonPressedListener.");
        }
    }

}
