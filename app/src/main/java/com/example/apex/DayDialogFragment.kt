package com.example.apex

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.apex.databinding.FragmentGetDayDialogBinding

class DayDialogFragment(val eventListener: EventListener):DialogFragment() {
    private lateinit var binding:FragmentGetDayDialogBinding


    override fun onResume() {
        super.onResume()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        this.setListeners()
    }

    private fun setListeners() {
        this.binding.fragmentGetDayDialogCancelBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentGetDayDialogAcceptBtn.setOnClickListener {
            val day=binding.fragmentGetDayDialogDayTIL.editText?.text.toString()
            if (day!=null && day!=""){
                eventListener.changeDay(day.toInt())
                dismiss()

            }
            else{
                Toast.makeText(requireContext(), "راس روز را وارد کنید", Toast.LENGTH_SHORT).show()

            }
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        this.binding = DataBindingUtil.inflate(
            LayoutInflater.from(this.requireContext()),
            R.layout.fragment_get_day_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

      interface EventListener{
        fun changeDay(day:Int)
    }

}