package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apex.databinding.FragmentInvoiceBinding
import com.example.apex.databinding.FragmentMainBinding

class InvoiceFragment : Fragment() {
    private lateinit var binding: FragmentInvoiceBinding

    override fun onResume() {
        super.onResume()
        setListeners()
    }

    private fun setListeners(){
        binding.fragmentInvoiceBackBtn.setOnClickListener{
            this.requireActivity().onBackPressed()
        }
        binding.fragmentInvoiceAddInvoiceBtn.setOnClickListener {
            this.findNavController().navigate(R.id.action_invoiceFragment_to_addApexListInvoiceFragment)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInvoiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}