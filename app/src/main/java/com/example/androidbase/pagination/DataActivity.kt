package com.example.androidbase.pagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidbase.R
import dagger.android.DaggerActivity

class DataActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
    }
}
