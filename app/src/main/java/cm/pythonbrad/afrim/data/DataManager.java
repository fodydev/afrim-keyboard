package cm.pythonbrad.afrim.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.*;

public class DataManager {
    final static String TAG = DataManager.class.getSimpleName();
    final static String sharedDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
    Context context;
    final static String dataFolder = "afrim-data";
    final static String sharedDataDir = addTrailingSlash(sharedDir) + dataFolder;
    DataManager(Context context) {
        this.context = context;
    }

    public static void deployAssets(Context context) {
        final DataManager dataManager = (new DataManager(context));

        try {
            dataManager.copyDirorfileFromAssetManager(dataFolder, dataFolder);
        } catch (IOException ex) {
            Log.e(TAG, "I/O Exception", ex);
        }
    }

    public void copyDirorfileFromAssetManager(String arg_assetDir, String arg_destinationDir) throws IOException
    {
        String dest_dir_path = sharedDir + addLeadingSlash(arg_destinationDir);
        File dest_dir = new File(dest_dir_path);
        // To prevent user data overwrite.
        if (dest_dir.exists()) {
            Log.i(TAG, "Assets deployment skipped!");
            return;
        };
        Log.d(TAG, "Starts assets deployment...");
        createDir(dest_dir);
        AssetManager asset_manager = context.getAssets();
        String[] files = asset_manager.list(arg_assetDir);

        for (String file : files) {
            String abs_asset_file_path = addTrailingSlash(arg_assetDir) + file;
            String[] sub_files = asset_manager.list(abs_asset_file_path);

            if (sub_files.length == 0) {
                // It is a file
                String dest_file_path = addTrailingSlash(dest_dir_path) + file;
                copyAssetFile(abs_asset_file_path, dest_file_path);
            } else {
                // It is a sub directory
                copyDirorfileFromAssetManager(abs_asset_file_path, addTrailingSlash(arg_destinationDir) + file);
            }
        }

        Log.i(TAG, "Assets deployed!");
    }
    public void copyAssetFile(String assetFilePath, String destinationFilePath) throws IOException
    {
        Log.d(TAG, "copyAssetFile from " + assetFilePath + " to " + destinationFilePath);
        InputStream in = context.getAssets().open(assetFilePath);
        File destination = new File(destinationFilePath);
        destination.createNewFile();
        OutputStream out = new FileOutputStream(destinationFilePath);
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
    }
    private static String addTrailingSlash(String path)
    {
        if (path.charAt(path.length() - 1) != '/')
        {
            path += "/";
        }
        return path;
    }
    static String addLeadingSlash(String path)
    {
        if (path.charAt(0) != '/')
        {
            path = "/" + path;
        }
        return path;
    }
    static void createDir(File dir) throws IOException
    {
        Log.d(TAG, "createDir " + dir.getPath());
        if (dir.exists())
        {
            if (!dir.isDirectory())
            {
                throw new IOException("Can't create directory, a file is in the way");
            }
        } else
        {
            dir.mkdirs();
            if (!dir.isDirectory())
            {
                throw new IOException("Unable to create directory: ");
            }
        }
    }

    public static String buildPath(String[] values) {
        String path = sharedDataDir;

        for(String e: values) {
            path = addTrailingSlash(path) + e;
        }

        return path;
    }

}
