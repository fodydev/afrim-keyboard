package cm.pythonbrad.afrim.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.Toaster;

import java.io.*;
import java.util.List;

import cm.pythonbrad.afrim.R;

public class DataManager {
    final static String TAG = DataManager.class.getSimpleName();
    // In using public directory, even if we don't have external storage access
    // we will still able to deploy the data.
    final static String sharedDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
    final static String dataFolder = "afrim-data";
    final static String sharedDataDir = addTrailingSlash(sharedDir) + dataFolder;
    private static final DataManager sInstance = new DataManager();

    private DataManager() {
        // Intentional empty constructor for singleton.
    }

    public static void init(final Context context) {
        sInstance.initInternal(context);
    }

    private void initInternal(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            Toaster.show(R.string.android_11_storage_permission_hint);
        }

        XXPermissions.with((Activity) context)
                // Request multiple permission
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                // Set permission request interceptor (local setting)
                //.interceptor(new PermissionInterceptor())
                // Setting does not trigger error detection mechanism (local setting)
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (!allGranted) {
                            Toaster.show(R.string.external_storage_permission_partially_granted);
                        } else {
                            Toaster.showShort(R.string.external_storage_permission_granted);
                        }
                        sInstance.deployAssets(context);
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            Toaster.show(R.string.external_storage_permission_permanently_denied);
                            // If it is permanently denied, jump to the application permission system settings page
                            XXPermissions.startPermissionActivity(context, permissions);
                        } else {
                            Toaster.showShort(R.string.external_storage_permission_denied);
                        }
                    }
                });
    }

    public static DataManager getInstance() {
        return sInstance;
    }

    public void deployAssets(Context context) {
        try {
            // We don't want overwrite if the data inside the afrim dataset folder.
            // Note that the user can customize it.
            if (new File(sharedDataDir).exists()) return;
            Toaster.showShort(R.string.dataset_deploy_progress);
            copyDirorfileFromAssetManager(context, dataFolder, dataFolder);
            Toaster.showShort(R.string.dataset_deploy_success);
        } catch (IOException ex) {
            Log.e(TAG, "I/O Exception", ex);
            Toaster.showShort(R.string.dataset_deploy_failed);
        }
    }

    private void copyDirorfileFromAssetManager(Context context, String arg_assetDir, String arg_destinationDir) throws IOException
    {
        String dest_dir_path = sharedDir + addLeadingSlash(arg_destinationDir);
        File dest_dir = new File(dest_dir_path);
        // To prevent user data overwrite.
        if (dest_dir.exists()) {
            Log.i(TAG, "Assets deployment skipped!");
            return;
        }
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
                copyAssetFile(context, abs_asset_file_path, dest_file_path);
            } else {
                // It is a sub directory
                copyDirorfileFromAssetManager(context, abs_asset_file_path, addTrailingSlash(arg_destinationDir) + file);
            }
        }

        Log.i(TAG, "Assets deployed!");
    }
    private void copyAssetFile(Context context, String assetFilePath, String destinationFilePath) throws IOException
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
    private String addLeadingSlash(String path)
    {
        if (path.charAt(0) != '/')
        {
            path = "/" + path;
        }
        return path;
    }
    private void createDir(File dir) throws IOException
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
