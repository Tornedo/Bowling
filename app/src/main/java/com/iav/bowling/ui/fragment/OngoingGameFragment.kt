package com.iav.bowling.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iav.bowling.ui.viewmodel.OngoingGameViewModel
import com.iav.bowling.R
import kotlinx.android.synthetic.main.fragment_ongoing_game.*

class OngoingGameFragment : Fragment() {

    private lateinit var viewModel: OngoingGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ongoing_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(OngoingGameViewModel::class.java)

        fragmentCurrentPinsNextButton.setOnClickListener {
            validateAndPassInput()
        }

        fragmentCurrentFramePinsToKnockDownEditText.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    fragmentCurrentFramePinsToKnockDownInputLayout.error = null
                }

            }
        )
        fragmentCurrentFramePinsToKnockDownEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateAndPassInput()
                true
            } else {
                false
            }
        }
        setDataListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setDataListeners() {
        viewModel.frameInPlay.observe(viewLifecycleOwner, Observer {
            fragmentCurrentPinsTitle.text = "${requireContext().getText(R.string.frame_current)} : $it"
        })
        viewModel.gameOver.observe(viewLifecycleOwner, Observer {
            if(it == true) fragmentCurrentPinsTitle.text = "Game Over"
        })
    }

    private fun validateAndPassInput() {
        fragmentCurrentFramePinsToKnockDownEditText.text.toString().apply {
            if(isValidNumber(this)) {
                viewModel.doKnockDownPins(this.toInt())
                fragmentCurrentFramePinsToKnockDownEditText.setText("")
            }
            else {
                fragmentCurrentFramePinsToKnockDownInputLayout.error = "Please input a valid number of Pins!"
            }
        }
    }

    private fun isValidNumber(input: String): Boolean {
        if (viewModel.gameOver.value!!) return false
        if (input.isEmpty()) return false
        if (input.toInt() <= viewModel.remainingPinsInFrame.value!!) return true
        return false
    }

    companion object {
        fun newInstance() = OngoingGameFragment()
    }

}
