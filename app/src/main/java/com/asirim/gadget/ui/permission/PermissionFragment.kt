package com.asirim.gadget.ui.permission

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.asirim.gadget.databinding.FragmentPermissionBinding
import com.asirim.gadget.isPermissionsGranted
import kotlinx.coroutines.launch

class PermissionFragment : Fragment() {

    private lateinit var binding: FragmentPermissionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (requireContext().isPermissionsGranted(arrayOf(Manifest.permission.CAMERA))) {
            true -> navigateToCameraFragment()
            false -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(layoutInflater)
        return binding.root
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        when (isGranted) {

            true -> {
                Toast.makeText(
                    requireContext(), "Permission request granted", Toast.LENGTH_SHORT
                ).show()
                navigateToCameraFragment()
            }

            false -> {
                binding.buttonAllowCamera.apply {
                    visibility = VISIBLE
                    setOnClickListener { requestCameraPermission() }
                }
            }

        }
    }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun navigateToCameraFragment() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                findNavController().navigate(
                    PermissionFragmentDirections.actionPermissionFragmentToCameraFragment()
                )
            }
        }
    }

}