package com.vensti.android.filesmanagement.FileMgmtViews;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtPresenters.UploadButtonFragment;
import com.vensti.android.filesmanagement.R;

public class UploadButtonView implements FileMgmtContract.View {

    private Button uploadButton;
    private UploadButtonFragment presenter;
    private View inflatedView;
    private boolean directoryExplorerInflated;

    public View inflateView(LayoutInflater layoutInflater, ViewGroup container, FileMgmtContract.PresenterFragment presenterFragment)
    {
        setViewPresenter(presenterFragment);
        inflatedView = layoutInflater.inflate(R.layout.button,container,false);
        setViewHandles(inflatedView);
        setListeners();
        return inflatedView;
    }

    public void setViewHandles(View rootView) {
        uploadButton = rootView.findViewById(R.id.uploadButton);
    }

    public void setViewPresenter(FileMgmtContract.PresenterFragment presenterFragment)
    {
        presenter = (UploadButtonFragment)presenterFragment;
    }

    private void setListeners()
    {
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ASSERT","Button ClickListener set");
                presenter.onUploadButtonPressed(directoryExplorerInflated);
                if(!directoryExplorerInflated)
                {
                    uploadButton.setBackgroundColor(ContextCompat.getColor(presenter.getContext(),R.color.buttonToDownload));
                    directoryExplorerInflated = true;
                }

            }
        });
    }

}
