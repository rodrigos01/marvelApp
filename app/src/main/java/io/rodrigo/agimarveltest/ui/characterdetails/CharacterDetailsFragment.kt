package io.rodrigo.agimarveltest.ui.characterdetails


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.rodrigo.agimarveltest.MarvelApplication
import io.rodrigo.agimarveltest.databinding.FragmentCharacterDetailsBinding
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {

    companion object {
        const val ARG_CHARACTER = "arg_character"
    }

    @Inject
    lateinit var viewModelFactory: CharacterDetailsViewModel.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)

        activity?.let {
            (it.application as? MarvelApplication)
                    ?.component?.inject(this)

            (it as AppCompatActivity).setSupportActionBar(binding.toolbar)
        }

        val character = arguments?.getParcelable<MarvelCharacter>(ARG_CHARACTER)
        character?.let {
            viewModelFactory.character = it
            val viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(CharacterDetailsViewModel::class.java)

            binding.viewModel = viewModel

            binding.setLifecycleOwner(this)

            binding.retryButton.setOnClickListener { viewModel.retry() }

            val gridAdapter = ComicGridAdapter(viewModel.comics, this)
            binding.comicList.let {
                it.layoutManager = GridLayoutManager(context, 3)
                it.isNestedScrollingEnabled = false
                it.adapter = gridAdapter
            }
        }

        return binding.root
    }


}
