package com.hs.dgsw.android.qvick.home

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.biometric.BiometricPrompt
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.hs.dgsw.android.qvick.databinding.FragmentBottomSheetBinding
import com.hs.dgsw.android.qvick.service.remote.RetrofitBuilder
import com.hs.dgsw.android.qvick.service.remote.request.AttendanceRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val REQUIRED_PERMISSIONS = android.Manifest.permission.CAMERA
    }

    private var camera: Camera? = null
    private var cameraController: CameraControl? = null
    private lateinit var mBinding: FragmentBottomSheetBinding

    private val barcodeScanner: BarcodeScanner by lazy {
        val option = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        BarcodeScanning.getClient(option)
    }

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


    @OptIn(ExperimentalGetImage::class) private fun processBarcode(image: ImageProxy) {
        // ImageProxy에서 이미지 데이터 및 메타데이터 가져오기
        val mediaImage = image.image
        val rotationDegrees = image.imageInfo.rotationDegrees

        // 이미지를 InputImage로 변환
        val inputImage = InputImage.fromMediaImage(mediaImage!!, rotationDegrees)

        // 바코드 스캔
            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val qrCodeValue = barcode.displayValue ?: ""
                        Log.d(TAG, "QR 코드 스캔 결과: $qrCodeValue")

                        // 여기서 qrCodeValue를 필요한 곳에 전달하거나 저장할 수 있습니다.
                        if (bioCheck()){
                            lifecycleScope.launch(Dispatchers.IO){
                                kotlin.runCatching {
                                    RetrofitBuilder.getAttendanceRequestService().postAttendance(
                                        body = AttendanceRequest(
                                            code = qrCodeValue
                                        )
                                    )
                                }.onSuccess {
                                    Log.d(TAG, "성공 ㅇㅇㅇㅇㅇㅇ: $it")


                                }.onFailure {
                                    Log.d(TAG, "실패: $it")
                                }
                            }
                        }
                        else{
                            Toast.makeText(requireContext(), "지문이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "QR 코드 스캔 실패: ${e.message}", e)
                }
                .addOnCompleteListener {
                    // 이미지 처리가 완료된 후에는 ImageProxy를 닫아줘야 함
                    image.close()
                }
    }


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

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext())) {image ->
                    processBarcode(image)
                    image.close()
                }

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
            } catch (exc: Exception) {
                println("에러 $exc")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bioCheck() : Boolean{
        var isSuccess = false
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this@BottomSheetFragment, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show()
                    isSuccess = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("지문 인증") // 맨 위에 나오는 텍스트
            .setSubtitle("기기에 등록된 지문을 이용하여 지문을 인증해주세요.") // 서브 설명 텍스트
            .setNegativeButtonText("취소")
            .build()
        biometricPrompt.authenticate(promptInfo)
        return isSuccess
    }
}