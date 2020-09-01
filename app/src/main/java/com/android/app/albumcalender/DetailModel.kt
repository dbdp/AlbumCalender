package com.android.app.albumcalender

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class DetailModel(private val context: Context) {
    private val imageUrisMutableLiveData: MutableLiveData<List<DetailItem>> = MutableLiveData()
    val imageUrisLiveData: LiveData<List<DetailItem>> get() = imageUrisMutableLiveData

    init {
        imageUrisMutableLiveData.postValue(getImageUriList())
    }

    private fun getImageUriList(): List<DetailItem> {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
        val selectionArgs = arrayOf(
            dateToTimestamp(day = 1, month = 1, year = 1970).toString()
        )
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )
        val uriList: MutableList<DetailItem> = mutableListOf()
        cursor?.use {
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateTakenColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val dateTaken = Date(cursor.getLong(dateTakenColumn))
                val cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))

                cal.time = dateTaken
                Log.d(
                    "sechan",
                    "DAY  :" + cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(
                        Calendar.DAY_OF_MONTH
                    )
                )

                val displayName = cursor.getString(displayNameColumn)
                val contentUri = Uri.withAppendedPath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id.toString()
                )
                var path: String
                //https://m.blog.naver.com/PostView.nhn?blogId=zoomen1004&logNo=110188764442&proxyReferer=https:%2F%2Fwww.google.com%2F
                context.contentResolver.query(contentUri, null, null, null).apply {
                    moveToNext()
                    path = getString(getColumnIndex("_data"))
                    close()
                }

                val positionInfo = getLatitueAndLongitude(path)
                uriList.add(DetailItem(contentUri, positionInfo.first, positionInfo.second))
            }
        }
        return uriList
    }

    //https://stackoverflow.com/questions/15403797/how-to-get-the-latitude-and-longitude-of-an-image-in-sdcard-to-my-application
    private fun getLatitueAndLongitude(path: String): Pair<String?, String?> {
        val exif = ExifInterface(path)
        return Pair(
            convertToDegree(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)),
            convertToDegree(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE))
        )

    }

    private fun dateToTimestamp(day: Int, month: Int, year: Int): Long =
        SimpleDateFormat("dd.MM.yyyy").let { formatter ->
            formatter.parse("$day.$month.$year")?.time ?: 0
        }

    private fun convertToDegree(stringDMS: String?): String {
        if (stringDMS.isNullOrBlank()) {
            return ""
        }
        var result: Double? = null
        val DMS = stringDMS.split(",".toRegex(), 3).toTypedArray()
        val stringD = DMS[0].split("/".toRegex(), 2).toTypedArray()
        val D0: Double = stringD[0].toDouble()
        val D1: Double = stringD[1].toDouble()
        val FloatD = D0 / D1
        val stringM = DMS[1].split("/".toRegex(), 2).toTypedArray()
        val M0: Double = stringM[0].toDouble()
        val M1: Double = stringM[1].toDouble()
        val FloatM = M0 / M1
        val stringS = DMS[2].split("/".toRegex(), 2).toTypedArray()
        val S0: Double = stringS[0].toDouble()
        val S1: Double = stringS[1].toDouble()
        val FloatS = S0 / S1
        result = FloatD + FloatM / 60 + (FloatS / 3600).toFloat()
        return result.toString()
    }


}