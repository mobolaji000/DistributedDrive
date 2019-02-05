package com.vensti.android.filesmanagement.FileMgmtViews;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;
import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;
import com.vensti.android.filesmanagement.FileMgmtPresenters.DirectoryStructureFragment;
import com.vensti.android.filesmanagement.R;

public class DirectoryStructureView implements FileMgmtContract.View {

    private DirectoryStructureFragment presenter;
    private Context mContext;

    private FrameLayout mDirectoryStructureView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;




    public View inflateView(LayoutInflater layoutInflater, ViewGroup container, FileMgmtContract.PresenterFragment presenterFragment)
    {
        setViewPresenter(presenterFragment);
        mDirectoryStructureView = (FrameLayout) layoutInflater.inflate(R.layout.directory_structure,container,false);
        mProgressBar =  mDirectoryStructureView.findViewById(R.id.progress_circular);
        mRecyclerView = mDirectoryStructureView.findViewById(R.id.directory_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(mContext,FileMgmtConstants.NUMBER_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FileFolderImageAdapter(mContext,FileMgmtConstants.createInitialDirectoryList(), mProgressBar,presenter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new FileFolderImageAdapter.DecorateView());
        return mDirectoryStructureView;
    }

    public DirectoryStructureView(Context c)
    {
        mContext = c;
    }

    public void setViewHandles(View rootView) {

    }

    public void setViewPresenter(FileMgmtContract.PresenterFragment presenterFragment)
    {
        presenter = (DirectoryStructureFragment) presenterFragment;
    }

    public RecyclerView.Adapter getAdapter()
    {
        return mAdapter;
    }
}
