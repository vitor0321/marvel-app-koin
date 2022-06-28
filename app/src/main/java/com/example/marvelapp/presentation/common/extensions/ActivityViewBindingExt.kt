package com.example.marvelapp.presentation.common.extensions

import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingExt<VB : ViewBinding>(
    private val activity: AppCompatActivity,
    private val initializer: (LayoutInflater) -> VB
) : ReadOnlyProperty<AppCompatActivity, VB>, LifecycleObserver {

    private var binding: VB? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onCreate(owner: LifecycleOwner) {
                if (binding == null) {
                    binding = initializer(activity.layoutInflater)
                }
                activity.setContentView(binding?.root!!)
                activity.lifecycle.removeObserver(this)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                activity.lifecycle.removeObserver(this)
                binding = null
            }
        })
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VB {

        binding?.let { return it }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw IllegalThreadStateException(
                "This cannot be called from other threads. It should be on the main thread only."
            )
        }
        return initializer(thisRef.layoutInflater).also { this.binding = it }
    }
}

fun <T : ViewBinding> AppCompatActivity.viewBinding(viewBindingFactory: (LayoutInflater) -> T) =
    ActivityViewBindingExt(this, viewBindingFactory)
