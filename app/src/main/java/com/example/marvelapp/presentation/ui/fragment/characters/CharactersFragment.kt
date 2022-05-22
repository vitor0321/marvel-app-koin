package com.example.marvelapp.presentation.ui.fragment.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.core.domain.model.Character
import com.example.marvelapp.R
import com.example.marvelapp.databinding.FragmentCharactersBinding
import com.example.marvelapp.util.viewBinding

class CharactersFragment : Fragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)

    private val charactersAdapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharactersAdapter()

        charactersAdapter.submitList(
            listOf(
                Character("3d- Man", "https://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"),
            )
        )
    }

    private fun initCharactersAdapter() {
        binding.recyclerCharacters.run {
            setHasFixedSize(true)
            adapter = charactersAdapter
        }
    }
}