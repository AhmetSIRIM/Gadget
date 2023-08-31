package com.asirim.gadget.ui.camera

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.asirim.gadget.databinding.FragmentCameraBinding
import com.asirim.gadget.isPermissionsGranted

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding

    override fun onResume() {
        super.onResume()
        if (!(requireContext().isPermissionsGranted(arrayOf(Manifest.permission.CAMERA)))) {
            navigateToPermissionFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun navigateToPermissionFragment() {
        findNavController().navigate(
            CameraFragmentDirections.actionCameraFragmentToPermissionFragment()
        )
    }

}