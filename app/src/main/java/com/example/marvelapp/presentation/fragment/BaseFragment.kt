package com.example.marvelapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.marvelapp.presentation.activity.ActivityCallback

abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!

    private lateinit var activityCallback: ActivityCallback

    protected abstract fun getViewBinding(): viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        activityCallback = requireActivity() as ActivityCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showMenuNavigation(show: Boolean) = activityCallback.showMenuNavigation(show)
    fun showToolbar(show: Boolean) = activityCallback.showToolbar(show)
}