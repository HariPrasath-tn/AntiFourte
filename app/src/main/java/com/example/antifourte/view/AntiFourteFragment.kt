package com.example.antifourte.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.antifourte.R
import com.example.antifourte.databinding.FragmentAntiFourteBinding
import com.example.antifourte.view_model.FragmentHolderActivityViewModel

class AntiFourteFragment : Fragment() {
    // creating container for dat binding
    private lateinit var binding:FragmentAntiFourteBinding
    private lateinit var viewModel:FragmentHolderActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // creating data binding between xml and activity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anti_fourte, container, false)
        viewModel = ViewModelProvider(this)[FragmentHolderActivityViewModel::class.java]

        // linking xml view model with view model class
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        /*
         * onclick listener for done button
         * if any text field is empty:
         *      shows "Data missing warning" for 3secs
         * else:
         *      begin bruteForce (to solve problem)
         */
        binding.done.setOnClickListener {
            if(!viewModel.startBruteForce()) {
                binding.warning.visibility = View.VISIBLE

                // running thread to show data missing warning for 3seconds
                Thread(Runnable {
                    Thread.sleep(3000)
                    binding.warning.visibility = View.INVISIBLE
                }).start()
            }
        }

        binding.clear.setOnClickListener {
            viewModel.setEntryEmpty()
        }

        return binding.root
    }

}