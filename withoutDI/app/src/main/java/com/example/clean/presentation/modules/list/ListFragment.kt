package com.example.clean.presentation.modules.list
import com.example.clean.data.repository.CharacterRepositoryImpl
import com.example.clean.domain.usecase.CharacterUseCaseImpl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.clean.R
import com.example.clean.data.source.CharacterDataSourceImpl
import com.example.clean.databinding.FragmentFirstBinding
import com.example.clean.presentation.app.MainActivity

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private var repository = CharacterRepositoryImpl(dataSource = CharacterDataSourceImpl())
    private val usecase = CharacterUseCaseImpl(repository = repository)
    private var viewModel = ListViewModel(usecase = usecase)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lv = view.findViewById(R.id.charactersList) as ListView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, viewModel.getCharacters())
        lv.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}