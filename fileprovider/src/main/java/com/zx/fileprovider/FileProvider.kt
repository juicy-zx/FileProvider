package com.zx.fileprovider

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import java.io.File

/**
 * <p>创建者       zhuxi</p>
 * <p>创建时间     2018/5/22 14:27</p>
 * <p>包名         com.zx.fileprovider</p>
 * <p>描述         Android 7.0 FileProvider </p>
 */
fun getUriForFile(context: Context, file: File): Uri {
    return if (Build.VERSION.SDK_INT >= 24) getUriForFile24(context, file)
    else Uri.fromFile(file)
}

private fun getUriForFile24(context: Context, file: File): Uri = android.support.v4.content.FileProvider.getUriForFile(context,
        context.packageName + ".fileprovider", file)

fun Intent.setData(context: Context, file: File, writeAble: Boolean) {
    if (Build.VERSION.SDK_INT >= 24) {
        data = getUriForFile(context, file)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeAble) {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    } else {
        data = Uri.fromFile(file)
    }
}

fun Intent.setDataAndType(context: Context, file: File, type: String, writeAble: Boolean) {
    if (Build.VERSION.SDK_INT >= 24) {
        setDataAndType(getUriForFile(context, file), type)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeAble) {
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    } else {
        setDataAndType(Uri.fromFile(file), type)
    }
}
