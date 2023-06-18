package com.example.glidewebpanimationdrawablereusebug

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glidewebpanimationdrawablereusebug.databinding.ActivityMainBinding

class MainActivity : Activity() {

    companion object {
        private const val TAG = "MainActivity"

        val WEBP_URL = R.drawable.webp_anim
        val defaultImage = R.mipmap.ic_launcher_round
    }

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        TestAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.clickView.setOnClickListener {
            adapter.setList(getImageList())
        }
    }

    private fun getImageList(): List<Any> {
        return listOf(
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
            WEBP_URL,
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
            WEBP_URL,
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
            defaultImage,
        )
    }
}