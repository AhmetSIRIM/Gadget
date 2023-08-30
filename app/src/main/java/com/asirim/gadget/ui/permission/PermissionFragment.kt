package com.asirim.gadget.ui.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch

class PermissionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (PackageManager.PERMISSION_GRANTED) {

            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) -> navigateToCameraFragment()

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }

        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            when (isGranted) {

                true -> {
                    Toast.makeText(
                        requireContext(),
                        "Permission request granted",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToCameraFragment()
                }

                false -> {
                    Toast.makeText(
                        requireContext(),
                        "Permission request denied",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
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