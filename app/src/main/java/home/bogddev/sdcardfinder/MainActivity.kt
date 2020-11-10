package home.bogddev.sdcardfinder

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var findBtn: Button
    lateinit var sdCardList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findBtn = findViewById(R.id.findBtn)
        sdCardList = findViewById(R.id.sdCardList)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }

        findBtn.setOnClickListener {
                sdCardList.layoutManager = LinearLayoutManager(this)

                try {
                    FileManager.findExternalRoot(this)

                    sdCardList.adapter = SdCardAdapter(this, File(FileManager.externalRootPath).listFiles())

                } catch (e: Exception) {
                    Toast.makeText(this, "No sd card or error", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

        } else {
            finish()
        }
    }
}