package com.badmitry.bkshackaton.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badmitry.bkshackaton.MainActivity
import com.badmitry.bkshackaton.R
import com.badmitry.bkshackaton.SaverParams
import com.badmitry.bkshackaton.databinding.FragmentOptionsBinding
import com.badmitry.bkshackaton.navigation.Screens
import com.badmitry.bkshackaton.rvadapters.FragmentOptionsCurrencyRVAdapter
import com.badmitry.bkshackaton.rvadapters.FragmentOptionsTimeRVAdapter
import com.badmitry.bkshackaton.view.SetCurrencyView
import com.badmitry.bkshackaton.view.SetTimeView
import com.badmitry.bkshackaton.viewmodels.OptionsFragmentViewModel
import com.badmitry.domain.entities.InvestParams
import com.badmitry.domain.entities.ParamsOfPortfolio
import com.badmitry.domain.entities.ValidationErrors
import javax.inject.Inject


class FragmentOptions : BaseFragment(), SetCurrencyView, SetTimeView {
    private lateinit var binding: FragmentOptionsBinding
    private lateinit var viewModel: OptionsFragmentViewModel
    private lateinit var adapterCurrency: FragmentOptionsCurrencyRVAdapter
    private lateinit var adapterTime: FragmentOptionsTimeRVAdapter

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, vmFactory)[OptionsFragmentViewModel::class.java]
        viewModel.observe(this, ::onProgress, ::onError)
        viewModel.liveData.observe(this, ::onDataChanged)
        viewModel.errorValidationLiveData.observe(this, ::onValidationError)
        viewModel.paramsOfPortfolioLiveData.observe(this, ::onBondsBuy)
    }

    private fun onBondsBuy(params: ParamsOfPortfolio) {
        binding.layoutProfitability.params = params
        binding.layoutProfitability.cvProfitability.visibility = View.VISIBLE
        SaverParams.instance.paramsOfPortfolio = params
    }

    private fun onValidationError(validationErrors: List<ValidationErrors>) {
        validationErrors.forEach {
            when (it) {
                ValidationErrors.USE_IIS -> binding.investTimeTitle.setTextColor(
                    resources.getColor(
                        R.color.bks_red
                    )
                )
                ValidationErrors.INVEST_SUM -> binding.investSumTitle.setTextColor(
                    resources.getColor(
                        R.color.bks_red
                    )
                )
                ValidationErrors.INVEST_TIME -> binding.investTimeTitle.setTextColor(
                    resources.getColor(
                        R.color.bks_red
                    )
                )
                ValidationErrors.INVEST_CURRENCY -> binding.currencyTitle.setTextColor(
                    resources.getColor(
                        R.color.bks_red
                    )
                )
                ValidationErrors.SUM_REPLENISHMENT -> binding.replenishmentTitle.setTextColor(
                    resources.getColor(R.color.bks_red)
                )
            }
        }
    }

    override fun setToolbar() {
        (requireActivity() as MainActivity).initToolbar(R.string.options, false)
    }

    private fun showKeyboard(isShow: Boolean, view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isShow) {
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun setCurrency(currency: String, value: String) {
        viewModel.setInvestParams(investCurrency = currency, investCurrencyValue = value)
        binding.rvInvestCurrency.visibility = View.GONE
        binding.tvInvestCurrency.let {
            it.text = currency
            it.visibility = View.VISIBLE
        }
        binding.tvCurrencyValue.let {
            it.visibility = View.VISIBLE
            it.text = value
        }
        showKeyboard(false, binding.rvInvestCurrency)
    }

    @SuppressLint("SetTextI18n")
    override fun setTime(time: String, year: String) {
        viewModel.setInvestParams(investTime = time, investYear = year)
        binding.rvInvestTime.visibility = View.GONE
        binding.tvInvestTime.text = "$time $year"
        showKeyboard(false, binding.rvInvestTime)
    }

    private fun initComponent() {
        binding.cvInvestSum.setOnClickListener {
            binding.etInvestSum.let {
                it.visibility = View.VISIBLE
                it.requestFocus()
                showKeyboard(true, it)
            }
            binding.investSumTitle.setTextColor(resources.getColor(R.color.bks_black))
        }
        binding.etInvestSum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setInvestParams(investingSum = binding.etInvestSum.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.cvInvestCurrency.setOnClickListener {
            showKeyboard(false, it)
            binding.rvInvestCurrency.visibility = View.VISIBLE
            binding.currencyTitle.setTextColor(resources.getColor(R.color.bks_black))
        }

        binding.cvInvestTime.setOnClickListener {
            showKeyboard(false, it)
            binding.rvInvestTime.visibility = View.VISIBLE
            binding.investTimeTitle.setTextColor(resources.getColor(R.color.bks_black))
        }

        binding.cbReinvest.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setInvestParams(reinvest = binding.cbReinvest.isChecked)
            showKeyboard(false, binding.cbReinvest)
            binding.replenishmentTitle.setTextColor(resources.getColor(R.color.bks_black))
        }
        binding.cbRegularReplenishment.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setInvestParams(regularReplenishment = binding.cbRegularReplenishment.isChecked)
            showKeyboard(false, binding.cbRegularReplenishment)
            binding.replenishmentTitle.setTextColor(resources.getColor(R.color.bks_black))
        }
        binding.cbUseIis.setOnCheckedChangeListener { compoundButton, b ->
            viewModel.setInvestParams(useIis = binding.cbUseIis.isChecked)
            showKeyboard(false, binding.cbUseIis)
            binding.investTimeTitle.setTextColor(resources.getColor(R.color.bks_black))
        }

        binding.cvReplenishmentSum.setOnClickListener {
            binding.etReplenishmentSum.let {
                it.visibility = View.VISIBLE
                it.requestFocus()
                showKeyboard(true, it)
                binding.replenishmentTitle.setTextColor(resources.getColor(R.color.bks_black))
            }
        }
        binding.etReplenishmentSum.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setInvestParams(sumReplenishment = binding.etReplenishmentSum.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.btnInvest.setOnClickListener {
            showKeyboard(false, it)
            viewModel.getBonds()
        }

        adapterCurrency = FragmentOptionsCurrencyRVAdapter(this, requireContext())
        binding.rvInvestCurrency.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInvestCurrency.adapter = adapterCurrency
        adapterTime = FragmentOptionsTimeRVAdapter(this, requireContext())
        binding.rvInvestTime.layoutManager = LinearLayoutManager(requireContext())
        binding.rvInvestTime.adapter = adapterTime
        binding.btnGraphs.setOnClickListener {
            SaverParams.instance.paramsOfPortfolio?.let {
                viewModel.navigate(Screens.PORTFOLIO_GRAPH)
            }
        }
        binding.btnPortfolio.setOnClickListener {
            SaverParams.instance.paramsOfPortfolio?.let {
                viewModel.navigate(Screens.PORTFOLIO)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun onDataChanged(investParams: InvestParams) {
        Log.e("!!!", "onDataChanged: $investParams")
        investParams.investSum?.let {
            binding.etInvestSum.visibility = View.VISIBLE
        }
        investParams.investCurrency?.let { currency ->
            binding.tvInvestCurrency.let {
                it.visibility = View.VISIBLE
                it.text = currency
            }
        }
        investParams.investCurrencyValue?.let { currencyValue ->
            binding.tvCurrencyValue.let {
                it.visibility = View.VISIBLE
                it.text = currencyValue
            }
        }
        investParams.investTime?.let { time ->
            investParams.investYear?.let { year ->
                binding.tvInvestTime.let {
                    it.visibility = View.VISIBLE
                    it.text = "$time $year"
                }
            }
        }
        investParams.sumReplenishment?.let { replenishment ->
            binding.etReplenishmentSum.visibility = View.VISIBLE
        }
        if (investParams.regularReplenishment) {
            binding.cvReplenishmentSum.visibility = View.VISIBLE
        } else {
            binding.cvReplenishmentSum.visibility = View.GONE
        }
    }
}