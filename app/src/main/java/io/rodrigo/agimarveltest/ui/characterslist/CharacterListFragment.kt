package io.rodrigo.agimarveltest.ui.characterslist


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.rodrigo.agimarveltest.MarvelApplication
import io.rodrigo.agimarveltest.R
import io.rodrigo.agimarveltest.databinding.FragmentCharacterListBinding
import io.rodrigo.agimarveltest.ui.ViewModelFactory
import io.rodrigo.agimarveltest.ui.characterdetails.CharacterDetailsFragment
import javax.inject.Inject

class CharacterListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity?.application as? MarvelApplication)
                ?.component?.inject(this)

        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CharactersListViewModel::class.java)

        val adapter = CharacterListAdapter(
                viewModel.characters,
                this,
                viewModel.itemCallback
        )

        adapter.onItemClicked = {

            val bundle = Bundle()
            bundle.putParcelable(CharacterDetailsFragment.ARG_CHARACTER, it)
            Navigation.findNavController(binding.root)
                    .navigate(R.id.action_characterListFragment_to_characterDetailsFragment,
                            bundle)
        }

        binding.characterList.adapter = adapter

        return binding.root
    }


}
