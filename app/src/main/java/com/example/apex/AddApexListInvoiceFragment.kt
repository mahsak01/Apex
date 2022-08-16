package com.example.apex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apex.databinding.FragmentInvoiceAddListBinding

class AddApexListInvoiceFragment:Fragment() {
    private lateinit var binding:FragmentInvoiceAddListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding=FragmentInvoiceAddListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}