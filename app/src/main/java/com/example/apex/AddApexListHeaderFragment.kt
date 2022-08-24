package com.example.apex

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apex.common.ModePage
import com.example.apex.common.NamePage
import com.example.apex.data.model.ApexListHeader
import com.example.apex.databinding.FragmentAddApexListHeaderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddApexListHeaderFragment() :
    Fragment(), DayDialogFragment.EventListener {
    private lateinit var binding: FragmentAddApexListHeaderBinding
    private val viewModel: ApexViewModel by viewModel()
    private var apexDay: Int? = null
    private val args: AddApexListHeaderFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        this.view?.isFocusableInTouchMode = true
        this.view?.requestFocus()
        setListeners()
        setInformation()
    }

    private fun setInformation() {
        binding.fragmentApexAddListHeaderAppBarText.text = "گروه " + args.namePage.getValue()
        binding.fragmentApexAddListHeaderAccountApexListNameTI.hint =
            "نام گروه " + args.namePage.getValue()
        binding.fragmentApexAddListHeaderSumApexListHeaderTv.hint =
            "جمع " + args.namePage.getValue() + " ها"
        binding.fragmentApexAddListHeaderDateApexListHeaderTv.text =
            "تاریخ راس " + args.namePage.getValue()
        if (args.modePage == ModePage.EDIT) {
            apexDay = args.apexListHeader?.apexDay
            binding.fragmentApexAddListHeaderPriceAndNumberCL.visibility = View.VISIBLE
            binding.fragmentApexAddListHeaderApexListNameTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.name)
            binding.fragmentApexAddListHeaderAccountApexListNameTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.accountName)
            binding.fragmentApexAddListHeaderApexListPriceTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.price.toString())
            binding.fragmentApexAddListHeaderPercentTv.text =
                "%" + args.apexListHeader?.percent.toString()
            binding.fragmentApexAddListHeaderNumberTv.text =
                args.apexListHeader?.numberItem.toString() + " مورد"
            binding.fragmentApexAddListHeaderPriceTv.text =
                args.apexListHeader?.totalPrice.toString() + " ریال"
            binding.fragmentApexAddListHeaderDayTv.text =
                args.apexListHeader?.apexDay.toString() + " روز"
            binding.fragmentApexAddListHeaderDateTv.text = args.apexListHeader?.date
            binding.fragmentApexAddListHeaderDescriptionTI.editText?.text =
                Editable.Factory.getInstance().newEditable(args.apexListHeader?.description)
        } else
            binding.fragmentApexAddListHeaderPriceAndNumberCL.visibility = View.GONE

       }

    private fun setListeners() {
        binding.fragmentApexAddListHeaderAddBtn.setOnClickListener {
            val apexListName =
                binding.fragmentApexAddListHeaderApexListNameTI.editText?.text.toString()
            if (apexListName != "") {
                val accountApexListName =
                    binding.fragmentApexAddListHeaderAccountApexListNameTI.editText?.text.toString()
                if (accountApexListName != "") {
                    val apexListPrice =
                        binding.fragmentApexAddListHeaderApexListPriceTI.editText?.text.toString()
                    if (apexListPrice != "") {
                        if (apexDay != null) {
                            val percent =
                                binding.fragmentApexAddListHeaderPercentTv.text.toString()
                                    .replace("%", "")
                                    .toInt()
                            val description =
                                binding.fragmentApexAddListHeaderDescriptionTI.editText?.text.toString()
                            if (args.apexListHeader == null) {

                                val apexListHeader = ApexListHeader(
                                    0,
                                    "1401/02/05",
                                    apexListName,
                                    accountApexListName,
                                    percent,
                                    apexDay!!,
                                    apexListPrice.toInt(),
                                    0,
                                    args.namePage==NamePage.INVOICE,
                                    0,
                                    description
                                )
                                if (viewModel.searchApexListHeader(apexListHeader)){
                                    viewModel.addApexListHeader(
                                        apexListHeader
                                    )
                                    Toast.makeText(
                                        requireContext(),
                                        "گروه ${args.namePage.getValue()} اضافه شد",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    this.findNavController()
                                        .navigate(
                                            AddApexListHeaderFragmentDirections.actionAddApexListHeaderFragmentToApexListItemFragment(apexListHeader,args.namePage)
                                        )

                                }else{
                                    Toast.makeText(
                                        requireContext(),
                                        "گروه ${args.namePage.getValue()} با این اسم وجود دارد",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }


                            } else {
                                val apexListHeader = ApexListHeader(
                                    args.apexListHeader!!.id,
                                    "1401/02/05",
                                    apexListName,
                                    accountApexListName,
                                    percent,
                                    apexDay!!,
                                    apexListPrice.toInt(),
                                    0,
                                    args.namePage==NamePage.INVOICE,
                                    0,
                                    description
                                )

                                viewModel.updateApexListHeader(
                                    apexListHeader
                                )
                                Toast.makeText(
                                    requireContext(),
                                    "گروه ${args.namePage.getValue()} آپدیت شد",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                this.findNavController()
                                    .navigate(
                                        AddApexListHeaderFragmentDirections.actionAddApexListHeaderFragmentToApexListItemFragment(apexListHeader,args.namePage)
                                    )
                            }


                        } else {
                            Toast.makeText(
                                requireContext(),
                                "راس روز را وارد کنید",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "مبلغ کل را وارد کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "نام طرف حساب را وارد کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "نام گروه ${args.namePage.getValue()} را وارد کنید", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.fragmentApexAddListHeaderDayFl.setOnClickListener {
            val dayDialogFragment = DayDialogFragment(this)
            dayDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexAddListHeaderDateFl.setOnClickListener {
//            var dateDialogFragment = DateDialogFragment()
//            dateDialogFragment.show(requireActivity().supportFragmentManager, null)
        }
        binding.fragmentApexAddListHeaderAddPercentIv.setOnClickListener {
            addPercent()
        }
        binding.fragmentApexAddListHeaderRemovePercentIv.setOnClickListener {
            removePercent()
        }
        binding.fragmentApexAddListHeaderBackBtn.setOnClickListener {
            this.requireActivity().onBackPressed()
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
                    requireActivity().onBackPressed()
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
        this.binding = FragmentAddApexListHeaderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun changeDay(day: Int) {
        this.apexDay = day
        binding.fragmentApexAddListHeaderDayTv.text = "$day روزه"
    }

    private fun addPercent() {
        if (binding.fragmentApexAddListHeaderPercentTv.text.toString().replace("%", "")
                .toInt() != 100
        )
            binding.fragmentApexAddListHeaderPercentTv.text =
                "%" + (binding.fragmentApexAddListHeaderPercentTv.text.toString().replace("%", "")
                    .toInt() + 1).toString()

    }

    @SuppressLint("SetTextI18n")
    private fun removePercent() {
        if (binding.fragmentApexAddListHeaderPercentTv.text.toString().replace("%", "")
                .toInt() != 0
        )
            binding.fragmentApexAddListHeaderPercentTv.text =
                "%" + (binding.fragmentApexAddListHeaderPercentTv.text.toString().replace("%", "")
                    .toInt() - 1).toString()

    }
}