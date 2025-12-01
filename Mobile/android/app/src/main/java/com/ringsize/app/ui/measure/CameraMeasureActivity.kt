package com.ringsize.app.ui.measure

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ringsize.app.R
import com.ringsize.app.databinding.ActivityCameraMeasureBinding
import com.ringsize.app.data.model.MeasurementType
import com.ringsize.app.ui.viewmodel.MeasurementViewModel
import com.ringsize.app.ui.viewmodel.MeasurementViewModelFactory
import com.ringsize.app.util.MeasurementCalculator
import com.ringsize.app.util.SharedPreferencesManager
import com.ringsize.app.RingSizerApplication
import java.io.File

class CameraMeasureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraMeasureBinding
    private lateinit var viewModel: MeasurementViewModel
    private var capturedImageUri: Uri? = null
    private var capturedBitmap: Bitmap? = null
    private var measurementType: MeasurementType = MeasurementType.RING
    private var referenceDiameterMm: Double = 21.0 // Diamètre d'une pièce de 1€ en mm (par défaut)
    
    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera()
        } else {
            Toast.makeText(this, "Permission caméra requise", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && capturedImageUri != null) {
            loadCapturedImage()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraMeasureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        measurementType = intent.getSerializableExtra("MEASUREMENT_TYPE") as? MeasurementType ?: MeasurementType.RING
        
        val prefsManager = SharedPreferencesManager(this)
        val application = this.application as RingSizerApplication
        viewModel = androidx.lifecycle.ViewModelProvider(
            this,
            MeasurementViewModelFactory(prefsManager, application)
        )[MeasurementViewModel::class.java]
        
        setupUI()
    }
    
    private fun setupUI() {
        binding.btnCapture.setOnClickListener {
            checkCameraPermission()
        }
        
        binding.btnUsePhoto.setOnClickListener {
            if (capturedBitmap != null) {
                binding.circleOverlay.visibility = android.view.View.VISIBLE
                binding.btnAdjustCircle.visibility = android.view.View.VISIBLE
                binding.btnCalculate.visibility = android.view.View.VISIBLE
                binding.btnUsePhoto.visibility = android.view.View.GONE
            }
        }
        
        binding.btnAdjustCircle.setOnClickListener {
            binding.circleOverlay.isAdjustable = !binding.circleOverlay.isAdjustable
            binding.btnAdjustCircle.text = if (binding.circleOverlay.isAdjustable) {
                "Terminer l'ajustement"
            } else {
                "Ajuster le cercle"
            }
        }
        
        binding.btnCalculate.setOnClickListener {
            calculateMeasurement()
        }
        
        binding.btnSetReference.setOnClickListener {
            showReferenceDialog()
        }
        
        binding.btnCancel.setOnClickListener {
            finish()
        }
        
        // Instructions initiales
        binding.tvInstructions.text = when (measurementType) {
            MeasurementType.RING -> "1. Placez l'anneau sur une surface plane\n2. Placez une pièce de monnaie à côté pour référence\n3. Capturez la photo"
            MeasurementType.FINGER -> "1. Placez votre doigt sur une surface plane\n2. Placez une pièce de monnaie à côté pour référence\n3. Capturez la photo"
            MeasurementType.BRACELET -> "1. Placez le bracelet sur une surface plane\n2. Placez une pièce de monnaie à côté pour référence\n3. Capturez la photo"
        }
        
        binding.tvReference.visibility = android.view.View.VISIBLE
        binding.btnSetReference.visibility = android.view.View.VISIBLE
    }
    
    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
    
    private fun openCamera() {
        val photoFile = File(getExternalFilesDir(null), "ring_photo_${System.currentTimeMillis()}.jpg")
        capturedImageUri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )
        cameraLauncher.launch(capturedImageUri)
    }
    
    private fun loadCapturedImage() {
        capturedImageUri?.let { uri ->
            val inputStream = contentResolver.openInputStream(uri)
            capturedBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            
            capturedBitmap?.let { bitmap ->
                binding.imageView.setImageBitmap(bitmap)
                binding.circleOverlay.setImageBitmap(bitmap)
                binding.btnUsePhoto.visibility = android.view.View.VISIBLE
                binding.tvInstructions.text = "Ajustez le cercle pour qu'il corresponde à l'anneau, puis calculez"
            }
        }
    }
    
    private fun showReferenceDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_reference_coin, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Définir la référence")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val etReference = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etReferenceDiameter)
                val referenceText = etReference.text.toString()
                referenceDiameterMm = referenceText.toDoubleOrNull() ?: 21.0
                binding.tvReference.text = "Référence: ${String.format("%.1f", referenceDiameterMm)} mm"
                Toast.makeText(this, "Référence définie", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Annuler", null)
            .create()
        
        dialog.show()
    }
    
    private fun calculateMeasurement() {
        val circleRadius = binding.circleOverlay.circleRadius
        val referenceRadius = binding.circleOverlay.referenceRadius
        
        if (circleRadius <= 0f || referenceRadius <= 0f) {
            Toast.makeText(this, "Veuillez ajuster le cercle et définir la référence", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Calculer le diamètre réel en mm
        // ratio = (rayon_cercle / rayon_référence) * diamètre_référence
        val ratio = circleRadius / referenceRadius
        val actualDiameterMm = ratio * referenceDiameterMm
        
        // Calculer les mesures
        val result = MeasurementCalculator.calculateFromDiameter(actualDiameterMm)
        
        // Afficher le résultat
        showResultDialog(result, actualDiameterMm)
    }
    
    private fun showResultDialog(result: MeasurementCalculator.MeasurementResult, diameterMm: Double) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_measurement_result, null)
        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        
        dialogView.findViewById<android.widget.TextView>(R.id.tvDiameter).text = 
            "Diamètre: ${String.format("%.2f", result.diameterMm)} mm"
        dialogView.findViewById<android.widget.TextView>(R.id.tvCircumference).text = 
            "Circonférence: ${String.format("%.2f", result.circumferenceMm)} mm"
        dialogView.findViewById<android.widget.TextView>(R.id.tvSizeEu).text = 
            "Taille EU: ${String.format("%.1f", result.sizeEu)}"
        dialogView.findViewById<android.widget.TextView>(R.id.tvSizeUs).text = 
            "Taille US: ${String.format("%.1f", result.sizeUs)}"
        
        val btnSave = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSave)
        val btnCancel = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
        
        btnCancel.setOnClickListener { dialog.dismiss() }
        
        btnSave.setOnClickListener {
            val etName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etMeasurementName)
            val name = etName.text.toString().ifBlank { "Mesure caméra" }
            
            viewModel.saveMeasurement(name, measurementType, result.diameterMm, result.circumferenceMm)
            Toast.makeText(this, "Mesure sauvegardée", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            finish()
        }
        
        dialog.show()
    }
}

