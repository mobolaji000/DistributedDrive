package com.vensti.android.filesmanagement.FileMgmtModels;

import android.os.Environment;
import android.util.Log;

import com.vensti.android.filesmanagement.FileMgmtBase.FileMgmtContract;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DeviceStorageExplorer implements FileMgmtContract.Model {

    private static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
        {
            return true;
        }
        return false;
    }

    public static final ArrayList<File> walkDirectory(File root)
    {

        ArrayList<File> sortedContent = new ArrayList<File>();

        try {
            if (!isExternalStorageReadable())
            {
                throw new Exception("Storage not mounted");
            }

            File[] rootContent = root.listFiles();
            for (int j = 0; j < rootContent.length; j++) {
                sortedContent.add(rootContent[j]);
            }

        }
        catch (NullPointerException npe)
        {
            Log.e("DEBUG","Null pointer exception");
        }
        catch (Exception e)
        {
            Log.e("DEBUG ",e.toString());
        }
        finally {
            Collections.sort(sortedContent,new FileComparator());
            return sortedContent;
        }
    }

    private static final class FileComparator implements Comparator<File>
    {
        @Override
        public int compare(File o1, File o2) {
            if(o1.isDirectory())
            {
                if(o2.isDirectory())
                {
                    return o1.compareTo(o2);
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                if(o2.isDirectory())
                {
                    return 1;
                }
                else
                {
                    return o1.compareTo(o2);
                }
            }
        }
    }





    @Override
    public FileMgmtContract.PresenterActivity getPresenter() {
        return null;
    }
}

