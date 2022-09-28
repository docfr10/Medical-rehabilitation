package com.example.medicalrehabilitation.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.medicalrehabilitation.viewmodel.BlankFragmentSendMailViewModel
import com.example.medicalrehabilitation.R
import com.example.medicalrehabilitation.databinding.FragmentBlankFragmentSendMailBinding

class BlankFragmentSendMail : Fragment() {
    private lateinit var viewModel: BlankFragmentSendMailViewModel
    private lateinit var binding: FragmentBlankFragmentSendMailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankFragmentSendMailBinding.inflate(inflater, container, false)

        val provider = ViewModelProvider(this)
        viewModel = provider[BlankFragmentSendMailViewModel::class.java]
        sendEmail(binding)

        binding.onMenuButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_blankFragmentSendMail_to_blankFragmentHome)
        }

        return binding.root
    }

    private fun chooseEmail() {
        viewModel.mutableLiveDataIntent.observe(viewLifecycleOwner) {
            startActivity(Intent.createChooser(it, getText(R.string.send_mail)))
        }

    }

    private fun sendEmail(binding: FragmentBlankFragmentSendMailBinding) {
        viewModel.sendEmail(binding, getText(R.string.app_name) as String)
        chooseEmail()
    }
}