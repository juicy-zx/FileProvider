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
class FileProvider {
    companion object {
        fun getUriForFile(context: Context, file: File): Uri {
            return if (Build.VERSION.SDK_INT >= 24) getUriForFile24(context, file)
            else Uri.fromFile(file)
        }

        private fun getUriForFile24(context: Context, file: File): Uri = android.support.v4.content.FileProvider.getUriForFile(context,
                context.packageName + ".fileprovider", file)

        fun setIntentData(context: Context, intent: Intent, file: File, writeAble: Boolean) {
            if (Build.VERSION.SDK_INT >= 24) {
                intent.data = getUriForFile(context, file)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                if (writeAble) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            } else {
                intent.data = Uri.fromFile(file)
            }
        }

        fun setIntentDataAndType(context: Context, intent: Intent, file: File, type: String, writeAble: Boolean) {
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setDataAndType(getUriForFile(context, file), type)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                if (writeAble) {
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            } else {
                intent.setDataAndType(Uri.fromFile(file), type)
            }
        }
    }
}
