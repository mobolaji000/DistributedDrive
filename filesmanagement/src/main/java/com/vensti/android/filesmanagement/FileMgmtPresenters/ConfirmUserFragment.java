package com.vensti.android.filesmanagement.FileMgmtPresenters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtViews.ConfirmUserView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmUserFragment extends Fragment implements FileMgmtContract.PresenterFragment {

    private ConfirmUserView confirmUserView;
    private OnConfirmButtonPressedListener mCallBack;


    public ConfirmUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        confirmUserView = (ConfirmUserView) getPresenterView();
        View inflatedView = confirmUserView.inflateView(inflater,container,this);
        return inflatedView;
    }

    @Override
    public FileMgmtContract.Model getPresenterModel() {
        return null;
    }

    @Override
    public FileMgmtContract.View getPresenterView() {
        return new ConfirmUserView();
    }

    public interface OnConfirmButtonPressedListener
    {
        void onConfirmButtonPressed();
    }

    public void onConfirmButtonPressed()
    {
        mCallBack.onConfirmButtonPressed();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallBack = (OnConfirmButtonPressedListener) context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement OnConfirmButtonPressedListener.");
        }
    }

}
