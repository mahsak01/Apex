package com.example.apex

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.apex.databinding.FragmentSortApexItemDialogBinding

class SortApexItemDialogFragment(private val sort:Int=2131231375, private val eventListeners: EventListeners) : DialogFragment() {
    private lateinit var binding: FragmentSortApexItemDialogBinding


    override fun onResume() {
        super.onResume()
        binding.fragmentApexSortDialogRadioGroup.check(sort)
        this.view?.isFocusableInTouchMode = true;
        this.view?.requestFocus();
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setListeners()
    }


    private fun setListeners() {
        binding.fragmentApexSortDialogRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            eventListeners.changeSort(checkedId)
        }
        this.view?.setOnKeyListener(object : DialogInterface.OnKeyListener,
            View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    requireActivity().onBackPressed()
                    return true
                }
                return false
            }

            override fun onKey(p0: DialogInterface?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == KeyEvent.KEYCODE_BACK) {
                    requireActivity().onBackPressed()
                    return true

                }
                return false
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.fragment_sort_apex_item_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

    interface EventListeners {
        fun changeSort(id: Int)
    }

}