package com.sunnyweather.permissionx

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.permissionx.wanghongbodev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeCallBtn.setOnClickListener {
            PermissionX.request(this, android.Manifest.permission.CALL_PHONE){ allGranted, deniedList ->
                if (allGranted) {
                    call()
                } else {
                    Toast.makeText(this, "你拒绝了$deniedList", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun call(){
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:15547827658")
            startActivity(intent)
        } catch (e: SecurityException){
            e.printStackTrace()
        }
    }
}