package com.kuldeep.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.kuldeep.notesapp.databinding.FragmentRegisterBinding
import com.kuldeep.notesapp.model.UserRequest
import com.kuldeep.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding:FragmentRegisterBinding?=null
    private val binding get()=_binding!!

    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        binding.btnSignUp.setOnClickListener{
            authViewModel.registerUser(UserRequest("abcC@gmail.com","abcd1234","Kuldeep"))
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnLogin.setOnClickListener{
            authViewModel.loginUser(UserRequest("abcC@gmail.com","abcd1234","Kuldeep"))

        }
       /* binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    //token
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error ->{
                    binding.txtError.text=it.message
                }
                is NetworkResult.Loading ->{
                    binding.progressBar.isVisible=true
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null

    }

}