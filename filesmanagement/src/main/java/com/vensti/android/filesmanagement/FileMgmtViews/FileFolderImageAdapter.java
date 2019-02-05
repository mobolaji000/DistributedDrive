package com.vensti.android.filesmanagement.FileMgmtViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtConstants;
import com.vensti.android.filesmanagement.FileMgmtPresenters.DirectoryStructureFragment;
import com.vensti.android.filesmanagement.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileFolderImageAdapter extends RecyclerView.Adapter<FileFolderImageAdapter.FileFolderViewHolder> {

    private Context mContext;
    private DirectoryStructureFragment presenterFragment;
    private ArrayList<File> directoryData;
    private String currentViewGroupKey;
    private HashMap<String,Boolean> thisDirectorySelectedState = new HashMap<>();
    private HashMap<String,HashMap<String,Boolean>> allDirectoriesSelectedState = new HashMap<>();
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private final FileFolderClickListener mClickListener = new FileFolderClickListener();

    public FileFolderImageAdapter(Context c, ArrayList<File> data, ProgressBar bar, DirectoryStructureFragment presenter)
    {
        mContext = c;
        progressBar = bar;
        currentViewGroupKey = data.get(0).getParent();
        presenterFragment = presenter;
        setDirectoryData(data);
    }

    private void setDirectoryData(ArrayList<File> data)
    {
        directoryData = data;
    }

    @Override
    public int getItemCount() {
        return directoryData.size();
    }


    private void unselect(ImageView fileFolderCheckMark, View fileFolderView)
    {
        fileFolderCheckMark.setVisibility(View.INVISIBLE);
        fileFolderView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.backgroundColor));
    }

    private void select(ImageView fileFolderCheckMark, View fileFolderView)
    {
        fileFolderCheckMark.setVisibility(View.VISIBLE);
        fileFolderView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.transparentGrey));
    }

    private void setDataToDownload(String currentViewGroupKey)
    {
        HashMap<String,Boolean> getContent = new HashMap<>();
        getContent.putAll(thisDirectorySelectedState);
        allDirectoriesSelectedState.put(currentViewGroupKey,getContent);
    }

    public Set<String> getDataToDownload()
    {
        Set<String> filesAndFolders = new HashSet<>();
        for(HashMap<String,Boolean> fileFolderObject: allDirectoriesSelectedState.values())
        {
            for(Map.Entry<String,Boolean> entry:fileFolderObject.entrySet())
            {
                if(entry.getValue())
                {
                    filesAndFolders.add(entry.getKey());
                }
            }
        }
        return filesAndFolders;
    }

    private final class FileFolderClickListener implements View.OnClickListener, View.OnLongClickListener {

        public void onClick(View fileFolderView)
        {
            handleClickOrLongClick(fileFolderView,false);
        }

        public boolean onLongClick(View fileFolderView){
            handleClickOrLongClick(fileFolderView,true);
            return true;
        }

        public void handleClickOrLongClick(View fileFolderView, boolean isLongClick)
        {
            ImageView fileFolderCheckMark = fileFolderView.findViewById(R.id.check_mark_image);
            File fileObject = (File) fileFolderCheckMark.getTag(R.id.fileOrFolderObject);
            boolean state;


            if (fileObject.isFile() || isLongClick)
            {
                currentViewGroupKey = fileObject.getParent();

                state = (thisDirectorySelectedState.get(fileObject.getPath()) == null)  ?  true : !thisDirectorySelectedState.get(fileObject.getPath());
                thisDirectorySelectedState.put(fileObject.getPath(),state);
                if (state)
                {
                    select(fileFolderCheckMark,fileFolderView);
                }
                else
                {
                    unselect(fileFolderCheckMark,fileFolderView);
                }
            }
            else
            {
                currentViewGroupKey = fileObject.getPath();
                FileFolderImageAdapter.this.setDirectoryData(presenterFragment.getFilesandFoldersToShow(fileObject));
                progressBar.setVisibility(View.VISIBLE);
                new Thread(runSleep).start();
                FileFolderImageAdapter.this.notifyDataSetChanged();
            }
            setDataToDownload(currentViewGroupKey);
        }
    }

    public void trackFileAndFolderSelection()
    {
        String x = Environment.getExternalStorageDirectory().getPath();
        if (currentViewGroupKey.equals(x) || x.equals(currentViewGroupKey.substring(0,currentViewGroupKey.lastIndexOf("/"))))
        {
            this.setDirectoryData(FileMgmtConstants.createInitialDirectoryList());
        }
        else
        {
            currentViewGroupKey = currentViewGroupKey.substring(0,currentViewGroupKey.lastIndexOf("/") );
            this.setDirectoryData(presenterFragment.getFilesandFoldersToShow(new File(currentViewGroupKey)));
        }
        progressBar.setVisibility(View.VISIBLE);
        new Thread(runSleep).start();
        this.notifyDataSetChanged();
    }

    @Override
    public FileFolderViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View fileFolderView = layoutInflater.inflate(R.layout.file_folder,viewGroup,false);
        fileFolderView.setOnClickListener(mClickListener);
        fileFolderView.setOnLongClickListener(mClickListener);

        FileFolderViewHolder viewHolder = new FileFolderViewHolder(fileFolderView);
        return viewHolder;
    }

    public void onBindViewHolder(FileFolderViewHolder holder, int position)
    {
        holder.fileFolderName.setText(directoryData.get(position).getName());

        if(thisDirectorySelectedState.get(directoryData.get(position).getPath()) != null && thisDirectorySelectedState.get(directoryData.get(position).getPath()))
        {
            select(holder.checkMarkImage,holder.fileFolderView);
        }
        else
        {
            unselect(holder.checkMarkImage,holder.fileFolderView);
        }

        if (directoryData.get(position).isFile())
        {
            holder.fileFolderImage.setImageResource(R.drawable.file);
        }
        else
        {
            holder.fileFolderImage.setImageResource(R.drawable.folder);
        }
        holder.checkMarkImage.setTag(R.id.fileOrFolderObject,directoryData.get(position));
    }

    public static final class FileFolderViewHolder extends RecyclerView.ViewHolder{

        private ImageView fileFolderImage;
        private TextView fileFolderName;
        private ImageView checkMarkImage;
        private View fileFolderView;

        private FileFolderViewHolder(View fileFolderView)
        {
            super(fileFolderView);
            this.fileFolderView = fileFolderView;
            fileFolderImage = fileFolderView.findViewById(R.id.file_folder_image);
            fileFolderName = fileFolderView.findViewById(R.id.file_folder_name);
            checkMarkImage = fileFolderView.findViewById(R.id.check_mark_image);
        }

    }

    public static final class DecorateView extends RecyclerView.ItemDecoration{
        private int margin = 50;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            outRect.top = margin;
        }
    }

    private final Runnable runSleep = new Runnable() {
        @Override
        public void run() {
            try
            {
                for(int i=0;i<4;i++)
                {
                    Thread.sleep(100);
                    handler.post(runChangeProgress);
                }
                handler.post(runStopProgress);
            }
            catch (InterruptedException ie){
                Log.d("ERROR","InterruptedException Error");
            }
            }
    };

    private final Runnable runChangeProgress = new Runnable() {
        @Override
        public void run() {
            progressBar.incrementProgressBy(25);
        }
    };

    private final Runnable runStopProgress = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

}
