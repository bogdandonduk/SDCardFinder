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

                var shortestPathLength = 100000000

                val internalRootFile = File(internalRootPath)

                while (!cursor.isAfterLast && externalRootPath.isEmpty()) {
                    val data = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA))

                    if (!data.contains(internalRootFile.absolutePath, true)) {
                        if(!internalRootFile.absolutePath.contains(data, true)) {
                            val splitData = data.split(File.separator)

                            if (splitData.size < shortestPathLength) {
                                shortestPathLength = splitData.size

                                File(data).apply {
                                    if (isDirectory && totalSpace != internalRootFile.totalSpace && !data.contains(internalRootFile.parent!!, true)) {
                                        externalRootPath = data

                                    }
                                }

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