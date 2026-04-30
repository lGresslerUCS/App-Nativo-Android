package com.triple.app2.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

public class FileUtils {

    public static String getPath(Context context, Uri uri) {

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            String name = cursor.getString(index);

            cursor.close();

            return "/storage/emulated/0/Download/" + name;
        }

        return null;
    }
}