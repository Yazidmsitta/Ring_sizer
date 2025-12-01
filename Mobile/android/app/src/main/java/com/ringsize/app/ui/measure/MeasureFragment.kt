package com.ringsize.app.ui.measure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ringsize.app.R
import com.ringsize.app.databinding.FragmentMeasureBinding
import com.ringsize.app.data.model.MeasurementType
import com.ringsize.app.ui.viewmodel.MeasurementViewModel
import com.ringsize.app.ui.viewmodel.MeasurementViewModelFactory
import com.ringsize.app.util.SharedPreferencesManager
import com.ringsize.app.util.MeasurementCalculator
import com.ringsize.app.util.MeasurementCalculator.MeasurementResult
import com.ringsize.app.RingSizerApplication

class MeasureFragment : Fragment() {
    private var _binding: FragmentMeasureBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: MeasurementViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeasureBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefsManager = SharedPreferencesManager(requireContext())
        val application = requireActivity().application as RingSizerApplication
        viewModel = ViewModelProvider(this, MeasurementViewModelFactory(prefsManager, application))[MeasurementViewModel::class.java]
        
        binding.btnMeasureRing.setOnClickListener {
            showMeasurementOptionsDialog(MeasurementType.RING)
        }
        
        binding.btnMeasureFinger.setOnClickListener {
            showMeasurementOptionsDialog(MeasurementType.FINGER)
        }
        
        binding.btnMeasureBracelet.setOnClickListener {
            showMeasurementOptionsDialog(MeasurementType.BRACELET)
        }
        
        viewModel.currentMeasurement.observe(viewLifecycleOwner) { result: MeasurementResult? ->
            result?.let { measurementResult ->
                displayMeasurementResult(measurementResult)
            }
        }
    }
    
    private fun showMeasurementOptionsDialog(type: MeasurementType) {
        val options = arrayOf("Mesure manuelle", "Mesure par caméra")
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Choisir la méthode de mesure")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        // Mesure manuelle
                        when (type) {
                            MeasurementType.RING -> showRingMeasurementDialog()
                            MeasurementType.FINGER -> showFingerMeasurementDialog()
                            MeasurementType.BRACELET -> showBraceletMeasurementDialog()
                        }
                    }
                    1 -> {
                        // Mesure par caméra
                        val intent = android.content.Intent(requireContext(), CameraMeasureActivity::class.java)
                        intent.putExtra("MEASUREMENT_TYPE", type)
                        startActivity(intent)
                    }
                }
            }
            .setNegativeButton("Annuler", null)
            .show()
    }
    
    private fun showRingMeasurementDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_ring_measurement, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        val etCoinDiameter = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etCoinDiameter)
        val etRingDiameter = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etRingDiameter)
        val etMeasurementName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etMeasurementName)
        val btnCalculate = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCalculate)
        val btnCancel = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
        
        btnCancel.setOnClickListener { dialog.dismiss() }
        
        btnCalculate.setOnClickListener {
            val coinDiameter = etCoinDiameter.text.toString().toDoubleOrNull()
            val ringDiameter = etRingDiameter.text.toString().toDoubleOrNull()
            val name = etMeasurementName.text.toString()
            
            if (coinDiameter == null || ringDiameter == null) {
                Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (name.isBlank()) {
                Toast.makeText(context, "Veuillez entrer un nom pour la mesure", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Calculer à partir du diamètre
            viewModel.calculateFromDiameter(ringDiameter)
            dialog.dismiss()
            
            // Afficher le résultat et sauvegarder
            showMeasurementResultDialog(ringDiameter, null, name, MeasurementType.RING)
        }
        
        dialog.show()
    }
    
    private fun showFingerMeasurementDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_finger_measurement, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        val etCircumference = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etCircumference)
        val etMeasurementName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etMeasurementName)
        val btnCalculate = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCalculate)
        val btnCancel = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
        
        btnCancel.setOnClickListener { dialog.dismiss() }
        
        btnCalculate.setOnClickListener {
            val circumference = etCircumference.text.toString().toDoubleOrNull()
            val name = etMeasurementName.text.toString()
            
            if (circumference == null) {
                Toast.makeText(context, "Veuillez entrer la circonférence", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (name.isBlank()) {
                Toast.makeText(context, "Veuillez entrer un nom pour la mesure", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Calculer à partir de la circonférence
            viewModel.calculateFromCircumference(circumference)
            dialog.dismiss()
            
            // Afficher le résultat et sauvegarder
            showMeasurementResultDialog(null, circumference, name, MeasurementType.FINGER)
        }
        
        dialog.show()
    }
    
    private fun showBraceletMeasurementDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_finger_measurement, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        
        // Modifier le titre pour bracelet
        dialogView.findViewById<android.widget.TextView>(R.id.tvTitle).text = getString(R.string.measure_bracelet)
        dialogView.findViewById<android.widget.TextView>(R.id.tvInstructions).text = 
            "1. Enroulez une ficelle ou un ruban autour de votre poignet\n2. Marquez où la ficelle se croise\n3. Mesurez la longueur avec une règle\n4. Entrez la circonférence en mm"
        
        val etCircumference = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etCircumference)
        val etMeasurementName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etMeasurementName)
        val btnCalculate = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCalculate)
        val btnCancel = dialogView.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)
        
        btnCancel.setOnClickListener { dialog.dismiss() }
        
        btnCalculate.setOnClickListener {
            val circumference = etCircumference.text.toString().toDoubleOrNull()
            val name = etMeasurementName.text.toString()
            
            if (circumference == null) {
                Toast.makeText(context, "Veuillez entrer la circonférence", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (name.isBlank()) {
                Toast.makeText(context, "Veuillez entrer un nom pour la mesure", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // Calculer à partir de la circonférence
            viewModel.calculateFromCircumference(circumference)
            dialog.dismiss()
            
            // Afficher le résultat et sauvegarder
            showMeasurementResultDialog(null, circumference, name, MeasurementType.BRACELET)
        }
        
        dialog.show()
    }
    
    private fun showMeasurementResultDialog(
        diameter: Double?,
        circumference: Double?,
        name: String,
        type: MeasurementType
    ) {
        val result: MeasurementResult = viewModel.currentMeasurement.value ?: return
        
        val dialogView = layoutInflater.inflate(R.layout.dialog_measurement_result, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
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
            viewModel.saveMeasurement(name, type, result.diameterMm, result.circumferenceMm)
            Toast.makeText(context, "Mesure sauvegardée", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    private fun displayMeasurementResult(result: MeasurementResult) {
        binding.tvDiameter.text = "Diamètre: ${String.format("%.2f", result.diameterMm)} mm"
        binding.tvCircumference.text = "Circonférence: ${String.format("%.2f", result.circumferenceMm)} mm"
        binding.tvSizeEu.text = "Taille EU: ${String.format("%.1f", result.sizeEu)}"
        binding.tvSizeUs.text = "Taille US: ${String.format("%.1f", result.sizeUs)}"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


