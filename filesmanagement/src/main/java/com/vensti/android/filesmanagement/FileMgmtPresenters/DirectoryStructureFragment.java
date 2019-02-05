package com.vensti.android.filesmanagement.FileMgmtPresenters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtModels.DeviceStorageExplorer;
import com.vensti.android.filesmanagement.FileMgmtViews.DirectoryStructureView;
import com.vensti.android.filesmanagement.FileMgmtViews.FileFolderImageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectoryStructureFragment extends Fragment implements FileMgmtContract.PresenterFragment {

    private DirectoryStructureView directoryStructureView;
    private FileFolderImageAdapter fileFolderImageAdapter;
    private DeviceStorageExplorer deviceStorageExplorer;

    public DirectoryStructureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        directoryStructureView = (DirectoryStructureView) getPresenterView();
        View inflatedView = directoryStructureView.inflateView(inflater,container,this);
        deviceStorageExplorer = (DeviceStorageExplorer) getPresenterModel();
        fileFolderImageAdapter = (FileFolderImageAdapter)directoryStructureView.getAdapter();
        return inflatedView;
    }

    public void updateDirectoryStructureView()
    {
        fileFolderImageAdapter.trackFileAndFolderSelection();
    }

    public void getDataToDownload()
    {

        Set<String> data = fileFolderImageAdapter.getDataToDownload();
        int i = 0;
    }

    public ArrayList<File> getFilesandFoldersToShow(File fileObject)
    {
        return deviceStorageExplorer.walkDirectory(fileObject);
    }

    public FileMgmtContract.View getPresenterView() {
        return new DirectoryStructureView(getContext());
    }

    public FileMgmtContract.Model getPresenterModel() {
        return new DeviceStorageExplorer();
    }
}
