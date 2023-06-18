package com.example.glidewebpanimationdrawablereusebug

import android.app.Activity
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.glidewebpanimationdrawablereusebug.databinding.TestItemBinding

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    companion object {
        private const val TAG = "TestAdapter"
    }

    private val list = mutableListOf<Any>()

    fun setList(newList: List<Any>) {
        list.clear()
        list.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val binding =
            TestItemBinding.inflate((parent.context as Activity).layoutInflater, parent, false)
        return TestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TestViewHolder(private val binding: TestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val animCallback by lazy {
            object : Animatable2.AnimationCallback() {
                override fun onAnimationStart(drawable: Drawable?) {
                    super.onAnimationStart(drawable)

                    Log.d("test", "onAnimationStart drawable=$drawable")
                }

                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)

                    Log.d("test", "onAnimationEnd drawable=$drawable")
                }
            }
        }

        fun bind(url: Any, pos: Int) {
            if (url == MainActivity.WEBP_URL) {
                Log.d("test", "start bind $pos")
            }
            Glide.with(binding.imageView.context)
                .load(url)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.d("test", "load $url failed, ${e?.stackTraceToString()}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (url == MainActivity.WEBP_URL) {
                            Log.d("test", "run $resource, dataSource=$dataSource")
                            if (resource is Animatable2) {
                                resource.unregisterAnimationCallback(animCallback)
                                resource.registerAnimationCallback(animCallback)
                            }
                        }

                        return false
                    }
                })
                .into(binding.imageView)
        }
    }
}