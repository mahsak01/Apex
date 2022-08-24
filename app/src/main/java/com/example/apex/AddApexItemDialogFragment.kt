package com.example.apex

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.apex.common.ModePage
import com.example.apex.common.differenceDates
import com.example.apex.common.priceInterests
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentAddApexItemDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddApexItemDialogFragment(
    private val apexListHeader: ApexListHeader,
    private val eventListener: EventListener,
    private val apexItem: ApexItem?
) : DialogFragment() {
    private lateinit var binding: FragmentAddApexItemDialogBinding

    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true;
        this.view?.requestFocus();
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        setListeners()
        setInformation()
    }

    private fun setInformation() {
        if (apexItem != null) {
            binding.fragmentAddApexItemDialogSerialTIL.editText?.text =
                Editable.Factory.getInstance().newEditable(apexItem.serial)
            binding.fragmentAddApexItemDialogPriceTIL.editText?.text =
                Editable.Factory.getInstance().newEditable(apexItem.price.toString())
            binding.fragmentAddApexItemDialogDateTIL.editText?.text =
                Editable.Factory.getInstance().newEditable(apexItem.date)
        }

    }

    private fun setListeners() {
        this.binding.fragmentAddApexItemDialogCancelBtn.setOnClickListener {
            dismiss()
        }
        this.binding.fragmentAddApexItemDialogAcceptBtn.setOnClickListener {
            val serial = binding.fragmentAddApexItemDialogSerialTIL.editText?.text.toString()
            if (serial != "") {
                val date = binding.fragmentAddApexItemDialogDateTIL.editText?.text.toString()
                if (date != "") {
                    val price = binding.fragmentAddApexItemDialogPriceTIL.editText?.text.toString()
                    if (price != "") {

                        if (apexItem == null) {
                            eventListener.addApexItem(
                                ApexItem(
                                    0,
                                    serial,
                                    date,
                                    price,
                                    apexListHeader.id
                                )
                            )
                            dismiss()
                            Toast.makeText(requireContext(), "اضافه شد", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            eventListener.updateApexItem(
                                ApexItem(
                                    apexItem.id,
                                    serial,
                                    date,
                                    price,
                                    apexListHeader.id
                                )
                            )
                            dismiss()
                            Toast.makeText(requireContext(), "آپدیت شد", Toast.LENGTH_SHORT)
                                .show()

                        }

                    } else {
                        Toast.makeText(requireContext(), "مبلغ را وارد کنید", Toast.LENGTH_SHORT)
                            .show()

                    }

                } else {
                    Toast.makeText(requireContext(), "تاریخ را وارد کنید", Toast.LENGTH_SHORT)
                        .show()

                }

            } else {
                Toast.makeText(requireContext(), "سریال را وارد کنید", Toast.LENGTH_SHORT).show()

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
            R.layout.fragment_add_apex_item_dialog,
            null,
            false
        )
        dialogBuilder.setView(binding.root)
        return dialogBuilder.create()
    }

    interface EventListener {
        fun addApexItem(apexItem: ApexItem)
        fun updateApexItem(apexItem: ApexItem)

    }

}