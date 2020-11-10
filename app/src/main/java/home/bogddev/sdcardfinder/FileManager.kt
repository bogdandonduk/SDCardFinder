package home.bogddev.sdcardfinder

import android.content.Context
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File

class FileManager {

    companion object {
        var internalRootPath = Environment.getExternalStorageDirectory().absolutePath

        var externalRootPath = ""

        var isFindingExternalRoot = false

        fun findExternalRoot(context: Context) {
            val cursor: Cursor = context.contentResolver.query(MediaStore.Files.getContentUri("external"), arrayOf(
                    MediaStore.Files.FileColumns.DATA
            ), null, null, null, null)!!

            if(cursor.moveToFirst()) {

                externalRootPath = ""

                isFindingExternalRoot = true

                var shortestPathLength = 10000

                while(!cursor.isAfterLast) {
                    val data = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA))

                    if(!data.toLowerCase().contains(internalRootPath.toLowerCase())) {
                        if(!internalRootPath.toLowerCase().contains(data)) {
                            val splitData = data.split(File.separator)

                            if(splitData.size < shortestPathLength) {
                                shortestPathLength = splitData.size
                                externalRootPath = data
                            }
                        }
                    }

                    cursor.moveToNext()
                }

                cursor.close()

                isFindingExternalRoot = false
            }

        }
    }

}