package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apex.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onResume() {
        super.onResume()
        setListeners()
    }

    private fun setListeners(){
        binding.fragmentMainInvoiceBtn.setOnClickListener{
            this.findNavController().navigate(R.id.action_mainFragment_to_apexListHeaderFragment)
        }
        binding.fragmentMainChequeBtn.setOnClickListener{
            this.findNavController().navigate(R.id.action_mainFragment_to_apexListHeaderFragment)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}