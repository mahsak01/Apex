package com.example.apex

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.apex.databinding.FragmentGetDayDialogBinding

class DayDialogFragment(val eventListener: EventListener):DialogFragment() {
    private lateinit var binding:FragmentGetDayDialogBinding


    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true;
        this.view?.requestFocus();
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
        this.view?.setOnKeyListener(object : DialogInterface.OnKeyListener,
            View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if( p1 == KeyEvent.KEYCODE_BACK )
                {
                    requireActivity().onBackPressed()
                    return true
                }
                return false
            }

            override fun onKey(p0: DialogInterface?, p1: Int, p2: KeyEvent?): Boolean {
                if( p1 == KeyEvent.KEYCODE_BACK )
                {
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