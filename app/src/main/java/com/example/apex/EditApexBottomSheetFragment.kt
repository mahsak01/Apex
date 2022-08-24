package com.example.apex

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.apex.common.*
import com.example.apex.data.model.ApexItem
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentBottomSheetEditApexBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditApexBottomSheetFragment(
    private val apexListHeader: ApexListHeader,
    val namePage: NamePage
) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetEditApexBinding
    private val viewModel: ApexViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true;
        this.view?.requestFocus();
        viewModel.getApexItems(apexListHeader)
        setInformation()
        addObserve()
        addListener()
    }

    fun addListener() {
        binding.fragmentBottomSheetEditApexEditPageBtn.setOnClickListener {
            dismiss()
            this.findNavController()
                .navigate(
                    ApexListHeaderFragmentDirections.actionApexListHeaderFragmentToAddApexListHeaderFragment(
                        ModePage.EDIT, NamePage.INVOICE, apexListHeader
                    )
                )
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

    fun addObserve() {
        viewModel.apexItemsLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.fragmentBottomSheetEditApexLastInformation.visibility = View.GONE
                binding.fragmentBottomSheetEditApexPriceAndDayTv.visibility = View.GONE
                binding.fragmentBottomSheetEditApexPriceInterestTv.visibility = View.GONE
            } else {
                var totalPrice = totalPrice(it)
                binding.fragmentBottomSheetEditApexLastInformation.visibility = View.VISIBLE
                binding.fragmentBottomSheetEditApexPriceAndDayTv.visibility = View.VISIBLE
                binding.fragmentBottomSheetEditApexPriceInterestTv.visibility = View.VISIBLE
                binding.fragmentBottomSheetEditApexPriceAndDayTv.text =
                    "مبلغ کل " + totalPrice + "ریال " + apexDay(it, apexListHeader) + " روزه"
                binding.fragmentBottomSheetEditApexPriceInterestTv.text =
                    "مبلغ کل بهره " + totalPriceInterest(it, apexListHeader) + " ریال"
                var lastPrice = getLastPrice(apexListHeader, totalPrice)
                binding.fragmentBottomSheetEditApexLastPriceTv.text = "$lastPrice ریال"
                var lastDate = getLastDate(apexListHeader, totalPrice, lastPrice)
                binding.fragmentBottomSheetEditApexLastDateFl.text = lastDate
                binding.fragmentBottomSheetEditApexLastPriceInterestTv.text = getLastInterest(
                    apexListHeader, lastPrice,
                    differenceDates(lastDate, apexListHeader.date)
                ).toString()
                binding.fragmentBottomSheetEditApexLastPriceInterestTv.isSelected = true
                binding.fragmentBottomSheetEditApexLastDateFl.isSelected = true
                binding.fragmentBottomSheetEditApexLastPriceTv.isSelected = true


            }
        }
    }

    private fun setInformation() {
        binding.fragmentBottomSheetEditApexApexListHeaderNameTv.text = apexListHeader.name
        binding.fragmentBottomSheetEditApexApexListHeaderAccountNameTv.text =
            "طرف حساب: " + apexListHeader.accountName
        binding.fragmentBottomSheetEditApexApexListHeaderPriceTv.text =
            apexListHeader.totalPrice.toString() + " ریال"
        binding.fragmentBottomSheetEditApexApexListHeaderNumberTv.text =
            apexListHeader.numberItem.toString() + " مورد"
        binding.fragmentBottomSheetEditApexDayTv.text = apexListHeader.apexDay.toString() + "روزه"
        binding.fragmentBottomSheetEditApexDateFl.text = apexListHeader.date
        binding.fragmentBottomSheetEditApexDescriptionTI.text =
            Editable.Factory.getInstance().newEditable(apexListHeader.description)


        binding.fragmentBottomSheetEditApexSumApexLabel.text = "جمع ${namePage.getValue()}ها"
        binding.fragmentBottomSheetEditApexDateApexLabel.text = "تاریخ راس ${namePage.getValue()}"
        binding.fragmentBottomSheetEditApexLastPriceApexLabel.text =
            "مبلغ آخرین ${namePage.getValue()}"
        binding.fragmentBottomSheetEditApexLastDateApexLabel.text =
            "تاریخ اخرین ${namePage.getValue()}"
        binding.fragmentBottomSheetEditApexLastPriceInterestApexLabel.text =
            "بهره آخرین ${namePage.getValue()}"

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