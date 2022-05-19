package com.example.marvelapp.util

import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingPropertyDelegate<VB : ViewBinding>(
    private val activity: AppCompatActivity,
    private val initializer: (LayoutInflater) -> VB
) : ReadOnlyProperty<AppCompatActivity, VB>, LifecycleObserver {

    private var binding: VB? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            //remove o binding ao destruir o fragment
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        super.onDestroy(owner)
                        binding = null
                    }
                })
            }

            //observa o ciclo de vida ao cirar um fragment
            override fun onCreate(owner: LifecycleOwner) {
                if (binding == null) {
                    binding = initializer(activity.layoutInflater)
                }
                activity.setContentView(binding?.root!!)
                activity.lifecycle.removeObserver(this)
            }

            //remove observer de lifecycle quando fragment é destruido
            override fun onDestroy(owner: LifecycleOwner) {
                activity.lifecycle.removeObserver(this)
                binding = null
            }
        })
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): VB {

        //se existe retorna direto
        binding?.let { return it }

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw IllegalThreadStateException("This cannot be called from other threads. It should be on the main thread only.")
        }
        return initializer(thisRef.layoutInflater).also { this.binding = it }
    }
}

//Passe "NomeDoXMLBinding::bind" por referencia a este metodo
fun <T : ViewBinding> AppCompatActivity.viewBinding(viewBindingFactory: (LayoutInflater) -> T) =
    ViewBindingPropertyDelegate(this, viewBindingFactory)
