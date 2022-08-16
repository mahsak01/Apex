package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apex.databinding.FragmentChequeBinding

class ChequeFragment : Fragment() {
    private lateinit var binding: FragmentChequeBinding

    override fun onResume() {
        super.onResume()
        setListeners()
    }

    private fun setListeners(){
        binding.fragmentChequeBackBtn.setOnClickListener{
            this.requireActivity().onBackPressed()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChequeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}