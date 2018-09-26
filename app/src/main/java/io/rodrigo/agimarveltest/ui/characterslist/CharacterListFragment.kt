package io.rodrigo.agimarveltest.ui.characterslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
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
        postponeEnterTransition()

        (activity?.application as? MarvelApplication)
                ?.component?.inject(this)

        val binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CharactersListViewModel::class.java)

        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }

        val adapter = CharacterListAdapter(
                viewModel.characters,
                this,
                viewModel.itemCallback
        )

        adapter.onItemClicked = { character, view ->

            val bundle = Bundle()
            bundle.putParcelable(CharacterDetailsFragment.ARG_CHARACTER, character)

            val extras = FragmentNavigator.Extras.Builder()
                    .addSharedElement(view, ViewCompat.getTransitionName(view) ?: "")
                    .build()

            Navigation.findNavController(binding.root)
                    .navigate(R.id.action_characterListFragment_to_characterDetailsFragment,
                            bundle,
                            null,
                            extras)
        }

        binding.characterList.adapter = adapter

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                startPostponedEnterTransition()
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })

        return binding.root
    }


}
