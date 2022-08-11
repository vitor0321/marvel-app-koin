package com.example.marvelapp.presentation.fragment.intro

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentIntroBinding
import com.example.marvelapp.presentation.common.extensions.getDrawable
import com.example.marvelapp.presentation.common.extensions.navTo
import com.example.marvelapp.presentation.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Boolean
import java.lang.Boolean.FALSE
import kotlin.Int
import kotlin.apply
import kotlin.arrayOfNulls
import kotlin.getValue

class IntroFragment : BaseFragment<FragmentIntroBinding>() {

    override fun getViewBinding(): FragmentIntroBinding =
        FragmentIntroBinding.inflate(layoutInflater)

    private val viewModel: IntroViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewConfig()
        initButton()
        observerIntroState()
        setAdapter()
    }

    private fun setViewConfig() {
        showMenuNavigation(FALSE)
        showToolbar(FALSE)
        setColorStatusBarAndNavigation(R.color.status_bar_and_naviagtion)
    }

    private fun observerIntroState() {
        viewModel.stateIntro.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is IntroViewModel.UiStateIntro.ShowIntro -> {
                    binding.flipperIntro.displayedChild = FLIPPER_CHILD_INTRO
                    viewModel.saveIntroState(Boolean.TRUE)
                }
                is IntroViewModel.UiStateIntro.HideIntro -> {
                    binding.flipperIntro.displayedChild = FLIPPER_CHILD_LOADING
                    navTo(R.id.action_introFragment_to_charactersFragment)
                }
                else -> {}
            }
        }
    }

    private fun initButton(){
        binding.introGo.setOnClickListener {
            navTo(R.id.action_introFragment_to_charactersFragment)
        }
    }

    private fun setAdapter() {
        val items = listOf(
            IntroSlide(
                R.drawable.intro_order,
                R.string.intro_order_title,
                R.string.intro_order_description
            ),
            IntroSlide(
                R.drawable.intro_favorites,
                R.string.intro_favorites_title,
                R.string.intro_favorites_description
            ),
            IntroSlide(
                R.drawable.intro_detail,
                R.string.intro_details_title,
                R.string.intro_details_description
            ),
        )

        val indicators = binding.introSlideIndicator
        val slideViewPager = binding.introSlideViewpager
        val introSlideAdapter = IntroSlideAdapter(items)

        slideViewPager.adapter = introSlideAdapter

        setupIndicators(indicators, introSlideAdapter.itemCount)
        setCurrentIndicator(indicators, 0)

        slideViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(indicators, position)
                if (introSlideAdapter.itemCount - 1 == position) {
                    binding.introGo.text = "let's go"
                } else binding.introGo.text = "skip"
            }
        })
    }

    private fun setupIndicators(indicatorContainer: LinearLayout, indicatorCount: Int) {
        val indicators = arrayOfNulls<ImageView>(indicatorCount)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val indicatorSpace = resources.getDimensionPixelSize(R.dimen.slide_indicator_space)
        params.setMargins(indicatorSpace, 0, 0, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i]?.apply {
                this.setImageDrawable(getDrawable(R.drawable.indicator_unselected))
                this.layoutParams = params
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(indicatorContainer: LinearLayout, index: Int) {
        for (i in 0 until indicatorContainer.childCount) {
            val img = indicatorContainer[i] as? ImageView
            when (index == i) {
                true -> img?.setImageDrawable(getDrawable(R.drawable.indicator_selected))
                else -> img?.setImageDrawable(getDrawable(R.drawable.indicator_unselected))
            }
        }
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_INTRO = 1
    }
}