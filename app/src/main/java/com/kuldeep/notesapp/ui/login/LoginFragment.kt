package com.kuldeep.notesapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuldeep.notesapp.R
import com.kuldeep.notesapp.databinding.FragmentLoginBinding
import com.kuldeep.notesapp.model.UserRequest
import com.kuldeep.notesapp.ui.login.AuthViewModel
import com.kuldeep.notesapp.utils.NetworkResult
import com.kuldeep.notesapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding?=null
    private val binding get()=_binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener{
            val validationResult = validateUserInput()
            if(validationResult.first){
                authViewModel.loginUser(getUserRequest())
            }else{
                binding.txtError.text = validationResult.second
            }
        }
        binding.btnSignUp.setOnClickListener{
            findNavController().popBackStack()
        }
        bindOberves()
    }
    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString().trim()
        val password = binding.txtPassword.text.toString().trim()
        return UserRequest(emailAddress, password, "")
    }
    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(userRequest.username,userRequest.email,userRequest.password,true)
    }

    private fun bindOberves(){
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner,{
            binding.progressBar.isVisible = false
            when(it){
                is NetworkResult.Success ->{
                    //token
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
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