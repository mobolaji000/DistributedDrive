package com.vensti.android.filesmanagement.FileMgmtPresenters;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtModels.DeviceStorageExplorer;
import com.vensti.android.filesmanagement.FileMgmtViews.UploadButtonView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadButtonFragment extends Fragment implements FileMgmtContract.PresenterFragment {

    private OnUploadButtonPressedListener mCallBack;
    private UploadButtonView uploadButtonView;

    public UploadButtonFragment() {
        // Required empty public constructor
    }

    public interface OnUploadButtonPressedListener
    {
        void onUploadButtonPressed(boolean directoryExplorerInflated);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        uploadButtonView = (UploadButtonView) getPresenterView();
        View inflatedView = uploadButtonView.inflateView(inflater,container,this);
        return inflatedView;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallBack = (OnUploadButtonPressedListener) context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement OnUploadButtonPressedListener.");
        }
    }


    public void onUploadButtonPressed(boolean directoryExplorerInflated)
    {
        mCallBack.onUploadButtonPressed(directoryExplorerInflated);
    }

    public FileMgmtContract.View getPresenterView() {
        return new UploadButtonView();
    }

    public FileMgmtContract.Model getPresenterModel() {
        return new DeviceStorageExplorer();
    }

}
