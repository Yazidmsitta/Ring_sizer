package com.ringsize.app.util

object MeasurementCalculator {
    // Convert diameter (mm) to circumference (mm)
    fun diameterToCircumference(diameterMm: Double): Double {
        return Math.PI * diameterMm
    }
    
    // Convert circumference (mm) to diameter (mm)
    fun circumferenceToDiameter(circumferenceMm: Double): Double {
        return circumferenceMm / Math.PI
    }
    
    // Convert diameter (mm) to EU ring size
    fun diameterToEuSize(diameterMm: Double): Double {
        // EU size formula: (diameter_mm - 11.63) / 0.406
        return (diameterMm - 11.63) / 0.406
    }
    
    // Convert diameter (mm) to US ring size
    fun diameterToUsSize(diameterMm: Double): Double {
        // US size formula: (diameter_mm - 12.7) / 0.8128
        return (diameterMm - 12.7) / 0.8128
    }
    
    // Convert EU size to diameter (mm)
    fun euSizeToDiameter(euSize: Double): Double {
        return (euSize * 0.406) + 11.63
    }
    
    // Convert US size to diameter (mm)
    fun usSizeToDiameter(usSize: Double): Double {
        return (usSize * 0.8128) + 12.7
    }
    
    // Calculate all measurements from diameter
    fun calculateFromDiameter(diameterMm: Double): MeasurementResult {
        val circumference = diameterToCircumference(diameterMm)
        val euSize = diameterToEuSize(diameterMm)
        val usSize = diameterToUsSize(diameterMm)
        
        return MeasurementResult(
            diameterMm = diameterMm,
            circumferenceMm = circumference,
            sizeEu = euSize,
            sizeUs = usSize
        )
    }
    
    // Calculate all measurements from circumference
    fun calculateFromCircumference(circumferenceMm: Double): MeasurementResult {
        val diameter = circumferenceToDiameter(circumferenceMm)
        val euSize = diameterToEuSize(diameter)
        val usSize = diameterToUsSize(diameter)
        
        return MeasurementResult(
            diameterMm = diameter,
            circumferenceMm = circumferenceMm,
            sizeEu = euSize,
            sizeUs = usSize
        )
    }
    
    data class MeasurementResult(
        val diameterMm: Double,
        val circumferenceMm: Double,
        val sizeEu: Double,
        val sizeUs: Double
    )
}






