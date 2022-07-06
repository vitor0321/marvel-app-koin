package com.example.marvelapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.marvelapp.R
import com.example.marvelapp.presentation.activity.ActivityCallback
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE

@Suppress("UnsafeCallOnNullableType")
abstract class BaseFragment<viewBinding : ViewBinding> : Fragment() {

    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!

    private lateinit var activityCallback: ActivityCallback

    protected abstract fun getViewBinding(): viewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setHasOptionsMenu(showActionBarOptionMenu())
        activityCallback = requireActivity() as ActivityCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return _binding?.root
    }


    protected open fun showActionBarOptionMenu(): Boolean = FALSE
    protected open fun showMenuNavigation(show: Boolean) = activityCallback.showMenuNavigation(show)
    protected open fun showToolbar(show: Boolean) = activityCallback.showToolbar(show)
    protected open fun setColorStatusBarAndNavigation(@ColorRes color: Int) =
        activityCallback.setColorStatusBarAndNavigation(color)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}