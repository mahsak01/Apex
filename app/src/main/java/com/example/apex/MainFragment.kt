package com.example.apex

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_apex_items.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: ApexViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        setListeners()
        viewModel.getDate()

    }

    private fun setListeners(){
        binding.fragmentMainInvoiceBtn.setOnClickListener{
            this.findNavController()
                .navigate(
                    MainFragmentDirections.actionMainFragmentToApexListHeaderFragment(
                         NamePage.INVOICE
                    )
                )
        }
        binding.fragmentMainChequeBtn.setOnClickListener{
            this.findNavController()
                .navigate(
                    MainFragmentDirections.actionMainFragmentToApexListHeaderFragment(
                        NamePage.CHEQUE
                    )
                )
        }
        this.view?.setOnKeyListener(object :
            View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if( p1 == KeyEvent.KEYCODE_BACK )
                {

                    return true
                }
                return false
            }
        })
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