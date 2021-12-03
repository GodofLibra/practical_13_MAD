package com.example.practical_13_19012021095



import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SMS_list : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {}
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), 1)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms_list)
        var msgData = ""

        val bottomnav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomnav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sms1 -> {
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
        val sms = ArrayList<Any>()

        val uriSms: Uri = Uri.parse("content://sms/inbox")
        val cursor: Cursor? = contentResolver.query(
            uriSms,
            arrayOf("_id", "address", "date", "body"),
            null,
            null,
            null
        )

        if(cursor != null) {
            cursor.moveToFirst()
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val address: String = cursor.getString(1)
                val body: String = cursor.getString(3)
                sms.add("Address = " + address + "\nSMS = " + body+"\n")
            }
        }

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, sms as List<Any?>)

        val listView:ListView = findViewById(R.id.all_sms_list)
        listView.setAdapter(adapter)

    }


}
