package com.cranked.androidcorelibrary.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.cranked.androidcorelibrary.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileUtils {

    var createFilePath = ""

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun createPicturesFile(context: Context): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        val file = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        createFilePath = file.absolutePath
        return file
    }


    fun takePicture(context: Activity, authority: String, requestCode: Int): Boolean {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file: File
        try {
            file = createPicturesFile(context)
        } catch (ex: Exception) {
            return false
        }

        val uri: Uri = FileProvider.getUriForFile(
            context,
            authority,
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        context.startActivityForResult(intent, requestCode)
        return true
    }


    fun saveBitmapToFile(bitmap: Bitmap, filePath: String, subPath: String): Boolean {
        return try {
            var bitmapBool: Boolean
            val mainFile = File(filePath)

            if (!mainFile.exists())
                mainFile.mkdirs()

            FileOutputStream("$filePath/$subPath").use {
                bitmapBool = bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            bitmapBool
        } catch (ex: Exception) {
            Log.e("saveToFile", ex.toString())
            false
        }
    }

    fun getSizeOfFile(size: Long, round: Int = 2): String {
        try {
            if (size <= 0) return "0"
            val units = arrayOf("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
            val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
            return String.format("%.${round}f", size / Math.pow(1024.0, digitGroups.toDouble())) +
                    " " + units[digitGroups]

        } catch (e: Exception) {
            println(e.toString())
        }
        return ""
    }

    fun Double.round(decimals: Int = 2): Double =
        "%.${decimals}f".format(this).replace(",", ".").toDouble()

    fun getMimeType(url: String): String {
        MimeTypeMap.getFileExtensionFromUrl(url)?.let {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(it)?.let { type ->
                return type
            }
        }
        return "*/*"
    }


    fun getExtension(fileName: String): String {
        val arrayOfFilename = fileName.toCharArray()
        for (i in arrayOfFilename.size - 1 downTo 1) {
            if (arrayOfFilename[i] == '.') {
                return fileName.substring(i + 1, fileName.length)
            }
        }
        return ""
    }

    fun getExtensionMimeType(fileName: String): String {
        val extension = getExtension(fileName)
        if (MimeTypeMap.getSingleton().hasExtension(extension)) {
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)?.let {
                return it
            }
        }
        return "*/*"
    }

    fun getFileSize(file : File): Long {
        var size: Long = 0
        if (file.isDirectory) {
            for (child in file.listFiles()) {
                size += getFileSize(child)
            }
        } else {
            size = file.length()
        }
        return size
    }

    fun getFileFromPath(path: String) = File(path)

    fun getUri(uriPath: String): Uri? {
        try {
            if (uriPath.isNotEmpty()) {
                return Uri.parse(uriPath)
            }
        } catch (e: Exception) {

        }
        return null
    }

    fun getImageByType(type: String): Int {
        when (type) {
            "application/zip" -> {
                return R.drawable.icon_zip
            }
            "psd" -> {
                return R.drawable.icon_psd
            }
            "otf" -> {
                return R.drawable.icon_default
            }
            "image/jpeg" -> {
                return R.drawable.icon_jpg
            }
            "image/png" -> {
                return R.drawable.icon_png
            }
            "application/pdf" -> {
                return R.drawable.icon_pdf
            }
            "image/svg+xml" -> {
                return R.drawable.icon_svg
            }
            "text/html" -> {
                return R.drawable.icon_html
            }
            "text/css" -> {
                return R.drawable.icon_css
            }
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> {
                return R.drawable.icon_doc
            }
            else -> {
                return R.drawable.icon_default
            }
        }


    }

    fun convertFileToBase64(filePath: String, listener: CovertFileToBase64Listener) {
        val thread = Thread {
            BitmapFactory.decodeFile(filePath)?.let {
                val os = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.PNG, 100, os)
                val imageBytes = os.toByteArray()
                Base64.encodeToString(imageBytes, Base64.DEFAULT)?.let {
                    listener.onResult(it)
                }
            }

            listener.onResult(null)
        }
        thread.start()
    }


}