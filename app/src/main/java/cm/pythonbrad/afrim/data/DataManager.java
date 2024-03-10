package cm.pythonbrad.afrim.data;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import cm.pythonbrad.afrim.R;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import java.io.*;
import java.util.List;

public class DataManager {
  static final String TAG = DataManager.class.getSimpleName();
  // In using public directory, even if we don't have external storage access
  // we will still able to deploy the data.
  static final String sharedDir =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath();
  static final String dataFolder = "afrim-data";
  static final String sharedDataDir = addTrailingSlash(sharedDir) + dataFolder;
  private static final DataManager sInstance = new DataManager();

  private DataManager() {
    // Intentional empty constructor for singleton.
  }

  public static void init(final Context context) {
    sInstance.initInternal(context);
  }

  private void initInternal(Context context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
      Toast.makeText(context, R.string.android_11_storage_permission_hint, Toast.LENGTH_LONG)
          .show();
    }

    XXPermissions.with((Activity) context)
        // Request multiple permission
        .permission(Permission.MANAGE_EXTERNAL_STORAGE)
        // Set permission request interceptor (local setting)
        // .interceptor(new PermissionInterceptor())
        // Setting does not trigger error detection mechanism (local setting)
        // .unchecked()
        .request(
            new OnPermissionCallback() {

              @Override
              public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                if (!allGranted) {
                  Toast.makeText(
                          context,
                          R.string.external_storage_permission_partially_granted,
                          Toast.LENGTH_SHORT)
                      .show();
                } else {
                  Toast.makeText(
                          context,
                          R.string.external_storage_permission_partially_granted,
                          Toast.LENGTH_SHORT)
                      .show();
                }
                sInstance.deployAssets(context);
              }

              @Override
              public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                if (doNotAskAgain) {
                  Toast.makeText(
                          context,
                          R.string.external_storage_permission_permanently_denied,
                          Toast.LENGTH_LONG)
                      .show();
                  // If it is permanently denied, jump to the application permission system settings
                  // page
                  XXPermissions.startPermissionActivity(context, permissions);
                } else {
                  Toast.makeText(
                          context, R.string.external_storage_permission_denied, Toast.LENGTH_LONG)
                      .show();
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
      Toast.makeText(context, R.string.dataset_deploy_progress, Toast.LENGTH_SHORT).show();
      copyDirorfileFromAssetManager(context, dataFolder, dataFolder);
      Toast.makeText(context, R.string.dataset_deploy_success, Toast.LENGTH_SHORT).show();
    } catch (IOException ex) {
      Log.e(TAG, "I/O Exception", ex);
      Toast.makeText(context, R.string.dataset_deploy_failed, Toast.LENGTH_LONG).show();
    }
  }

  private void copyDirorfileFromAssetManager(
      Context context, String arg_assetDir, String arg_destinationDir) throws IOException {
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
        copyDirorfileFromAssetManager(
            context, abs_asset_file_path, addTrailingSlash(arg_destinationDir) + file);
      }
    }

    Log.i(TAG, "Assets deployed!");
  }

  private void copyAssetFile(Context context, String assetFilePath, String destinationFilePath)
      throws IOException {
    Log.d(TAG, "copyAssetFile from " + assetFilePath + " to " + destinationFilePath);
    InputStream in = context.getAssets().open(assetFilePath);
    File destination = new File(destinationFilePath);
    destination.createNewFile();
    OutputStream out = new FileOutputStream(destinationFilePath);
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
    in.close();
    out.close();
  }

  private static String addTrailingSlash(String path) {
    if (path.charAt(path.length() - 1) != '/') {
      path += "/";
    }
    return path;
  }

  private String addLeadingSlash(String path) {
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    return path;
  }

  private void createDir(File dir) throws IOException {
    Log.d(TAG, "createDir " + dir.getPath());
    if (dir.exists()) {
      if (!dir.isDirectory()) {
        throw new IOException("Can't create directory, a file is in the way");
      }
    } else {
      dir.mkdirs();
      if (!dir.isDirectory()) {
        throw new IOException("Unable to create directory: ");
      }
    }
  }

  public static String buildPath(String[] values) {
    String path = sharedDataDir;

    for (String e : values) {
      path = addTrailingSlash(path) + e;
    }

    return path;
  }
}
