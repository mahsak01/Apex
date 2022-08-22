package com.example.apex

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentBottomSheetEditApexBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditApexBottomSheetFragment(private val apexListHeader: ApexListHeader):BottomSheetDialogFragment() {

    private lateinit var binding:FragmentBottomSheetEditApexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onResume() {
        super.onResume()
        setInformation()
        addListener()
    }

    fun addListener(){
        binding.fragmentBottomSheetEditApexEditPageBtn.setOnClickListener {
            dismiss()
            this.findNavController()
                .navigate(
                    ApexListHeaderFragmentDirections.actionApexListHeaderFragmentToAddApexListHeaderFragment(
                       ModePage.EDIT ,NamePage.INVOICE,apexListHeader
                    )
                )
        }
    }

    private fun setInformation(){
        binding.fragmentBottomSheetEditApexApexListHeaderNameTv.text=apexListHeader.name
        binding.fragmentBottomSheetEditApexApexListHeaderAccountNameTv.text="طرف حساب: "+apexListHeader.accountName
        binding.fragmentBottomSheetEditApexApexListHeaderPriceTv.text=apexListHeader.totalPrice.toString()+" ریال"
        binding.fragmentBottomSheetEditApexApexListHeaderNumberTv.text=apexListHeader.numberItem.toString()+" مورد"
        binding.fragmentBottomSheetEditApexDayTv.text=apexListHeader.apexDay.toString()+"روزه"
        binding.fragmentBottomSheetEditApexDateFl.text=apexListHeader.date
        binding.fragmentBottomSheetEditApexDescriptionTI.text= Editable.Factory.getInstance().newEditable(apexListHeader.description)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bottom_sheet_edit_apex,
            container,
            false
        )
        return binding.root
    }


}