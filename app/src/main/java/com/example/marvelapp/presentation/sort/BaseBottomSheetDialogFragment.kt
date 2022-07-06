package com.example.marvelapp.presentation.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.viewbinding.ViewBinding
import com.example.marvelapp.presentation.activity.ActivityCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.Boolean.FALSE

@Suppress("UnsafeCallOnNullableType")
abstract class BaseBottomSheetDialogFragment<viewBinding : ViewBinding> :
    BottomSheetDialogFragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(showActionBarOptionMenu())
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