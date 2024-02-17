package com.hs.dgsw.android.qvick

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hs.dgsw.android.qvick.databinding.FragmentBottomSheetBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val REQUIRED_PERMISSIONS = android.Manifest.permission.CAMERA
    }

    private var camera: Camera? = null
    private var cameraController: CameraControl? = null
    private lateinit var mBinding: FragmentBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // x버튼 구현
        mBinding.backBtn.setOnClickListener {
            dismiss()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: onCreate")
        // 카메라 권한 확인
        if (allPermissionsGranted()) {
            Log.d(TAG, "onCreate: 성공1")
            startCamera()// 카메라 실행
        } else {
            Log.d(TAG, "onCreate: 실패1")
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(REQUIRED_PERMISSIONS), REQUEST_CODE_PERMISSIONS
            )
        }
    }

    // 권한 요청 결과를 판단(requestPermissions에 의해 호출)
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults:
        IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "onCreate: 성공2")

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "접근 권한이 허용되지 않아 카메라를 실행할 수 없습니다. 설정에서 접근 권한을 허용해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        requireContext(), android.Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun startCamera(){
        Log.d(TAG, "startCamera: startCamera")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(mBinding.viewFinder.surfaceProvider)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

                cameraController = camera!!.cameraControl
                cameraController!!.setZoomRatio(1F) // 1x Zoom
            } catch (exc: Exception) {
                println("에러 $exc")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }
}