package com.example.apex

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentInvoiceAddListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddApexListInvoiceFragment() :
    Fragment(), DayDialogFragment.EventListener {
    private lateinit var binding: FragmentInvoiceAddListBinding
    private val viewModel: ApexViewModel by viewModel()
    private var apexDay: Int? = null
    private val args: AddApexListInvoiceFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        setListeners()
        setInformation()
    }

    private fun setInformation() {
        binding.fragmentInvoiceAddListAppBarText.text = "گروه " + args.namePage.getValue()
        binding.fragmentInvoiceAddListAccountApexListNameTI.hint =
            "نام گروه " + args.namePage.getValue()
        binding.fragmentInvoiceAddListSumApexListHeaderTv.hint =
            "جمع " + args.namePage.getValue() + " ها"
        binding.fragmentInvoiceAddListDateApexListHeaderTv.text =
            "تاریخ راس " + args.namePage.getValue()
        if (args.modePage == ModePage.EDIT) {
            binding.fragmentInvoiceAddListPriceAndNumberCL.visibility = View.VISIBLE
            binding.fragmentInvoiceAddListInvoiceApexListNameTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.name)
            binding.fragmentInvoiceAddListAccountApexListNameTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.accountName)
            binding.fragmentInvoiceAddListPercentTv.text =
                "%" + args.apexListHeader?.percent.toString()
            binding.fragmentInvoiceAddListNumberTv.text =
                args.apexListHeader?.numberItem.toString() + " مورد"
            binding.fragmentInvoiceAddListPriceTv.text =
                args.apexListHeader?.totalPrice.toString() + " ریال"
            binding.fragmentInvoiceAddListDayTv.text = args.apexListHeader?.apexDay.toString()
            binding.fragmentInvoiceAddListDateTv.text = args.apexListHeader?.date
            binding.fragmentInvoiceAddListDescriptionTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.description)
        } else
            binding.fragmentInvoiceAddListPriceAndNumberCL.visibility = View.GONE
    }

    private fun setListeners() {
        binding.fragmentInvoiceAddListAddBtn.setOnClickListener {
            val apexListName =
                binding.fragmentInvoiceAddListInvoiceApexListNameTI.editText?.text.toString()
            if (apexListName != "") {
                val accountApexListName =
                    binding.fragmentInvoiceAddListAccountApexListNameTI.editText?.text.toString()
                if (accountApexListName != "") {
                    if (apexDay != null) {
                        val percent =
                            binding.fragmentInvoiceAddListPercentTv.text.toString().replace("%", "")
                                .toInt()
                        val description =
                            binding.fragmentInvoiceAddListDescriptionTI.editText?.text.toString()
                        viewModel.addApexListHeader(
                            ApexListHeader(
                                0,
                                "1401/02/05",
                                apexListName,
                                accountApexListName,
                                percent,
                                apexDay!!,
                                0,
                                true,
                                0,
                                description
                            )
                        )
                        Toast.makeText(requireContext(), "گروه فاکتور اضافه شد", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(), "راس روز را وارد کنید", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "نام طرف حساب را وارد کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "نام گروه فاکتور را وارد کنید", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.fragmentInvoiceAddListDayFl.setOnClickListener {
            val dayDialogFragment = DayDialogFragment(this)
            dayDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentInvoiceAddListDateFl.setOnClickListener {
//            var dateDialogFragment = DateDialogFragment()
//            dateDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentInvoiceAddListAddPercentIv.setOnClickListener {
            addPercent()
        }
        binding.fragmentInvoiceAddListRemovePercentIv.setOnClickListener {
            removePercent()
        }
        binding.fragmentInvoiceAddListBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentInvoiceAddListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun changeDay(day: Int) {
        this.apexDay = day
        binding.fragmentInvoiceAddListDayTv.text = "$day روزه"
    }

    fun addPercent() {
        if (binding.fragmentInvoiceAddListPercentTv.text.toString().replace("%", "").toInt() != 100)
            binding.fragmentInvoiceAddListPercentTv.text =
                "%" + (binding.fragmentInvoiceAddListPercentTv.text.toString().replace("%", "")
                    .toInt() + 1).toString()

    }

    fun removePercent() {
        if (binding.fragmentInvoiceAddListPercentTv.text.toString().replace("%", "").toInt() != 0)
            binding.fragmentInvoiceAddListPercentTv.text =
                "%" + (binding.fragmentInvoiceAddListPercentTv.text.toString().replace("%", "")
                    .toInt() - 1).toString()

    }
}