package com.gmail.eamosse.imdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.gmail.eamosse.imdb.databinding.FragmentHomeBinding
import com.gmail.eamosse.imdb.ui.home.adapter.CategoryAdapter
import com.gmail.eamosse.imdb.ui.home.adapter.CategoryAdapterHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), CategoryAdapterHandler {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(homeViewModel) {
            token.observe(viewLifecycleOwner, Observer {
                //récupérer les catégories
                getCategories()
            })

            categories.observe(viewLifecycleOwner, Observer {
                binding.categoryList.adapter = CategoryAdapter(it, this@HomeFragment)
            })

            error.observe(viewLifecycleOwner, Observer {
                //afficher l'erreur
            })
        }
    }

    override fun onShowMoviesByCategory(id: Int, name: String) {

        val action = HomeFragmentDirections
            .actionHomeFragmentToHomeSecondFragment("From HomeFragment", id.toString(), name)
        NavHostFragment.findNavController(this@HomeFragment)
            .navigate(action)
    }
}
