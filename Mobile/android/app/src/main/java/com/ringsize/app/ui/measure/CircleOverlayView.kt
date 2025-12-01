package com.ringsize.app.ui.measure

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs
import kotlin.math.sqrt

class CircleOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    
    private var bitmap: Bitmap? = null
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    
    private val referencePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(30, 255, 0, 0)
        style = Paint.Style.FILL
    }
    
    var circleCenterX: Float = 0f
    var circleCenterY: Float = 0f
    var circleRadius: Float = 0f
    
    var referenceCenterX: Float = 0f
    var referenceCenterY: Float = 0f
    var referenceRadius: Float = 0f
    
    var isAdjustable: Boolean = false
        set(value) {
            field = value
            invalidate()
        }
    
    private var isDraggingCircle: Boolean = false
    private var isDraggingReference: Boolean = false
    private var isResizingCircle: Boolean = false
    private var isResizingReference: Boolean = false
    
    fun setImageBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        // Initialiser le cercle au centre
        circleCenterX = width / 2f
        circleCenterY = height / 2f
        circleRadius = minOf(width, height) / 6f
        
        // Initialiser la référence en bas à droite
        referenceCenterX = width * 0.8f
        referenceCenterY = height * 0.8f
        referenceRadius = minOf(width, height) / 12f
        
        invalidate()
    }
    
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (circleCenterX == 0f && circleCenterY == 0f) {
            circleCenterX = w / 2f
            circleCenterY = h / 2f
            circleRadius = minOf(w, h) / 6f
            
            referenceCenterX = w * 0.8f
            referenceCenterY = h * 0.8f
            referenceRadius = minOf(w, h) / 12f
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        bitmap?.let {
            val scaleX = width.toFloat() / it.width
            val scaleY = height.toFloat() / it.height
            val scale = minOf(scaleX, scaleY)
            
            val scaledWidth = it.width * scale
            val scaledHeight = it.height * scale
            val left = (width - scaledWidth) / 2f
            val top = (height - scaledHeight) / 2f
            
            canvas.drawBitmap(it, Rect(0, 0, it.width, it.height), 
                RectF(left, top, left + scaledWidth, top + scaledHeight), null)
        }
        
        if (isAdjustable) {
            // Dessiner le cercle principal (anneau)
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, fillPaint)
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, circlePaint)
            
            // Dessiner les poignées de redimensionnement
            canvas.drawCircle(circleCenterX + circleRadius, circleCenterY, 15f, circlePaint.apply { style = Paint.Style.FILL })
            
            // Dessiner le cercle de référence (pièce)
            canvas.drawCircle(referenceCenterX, referenceCenterY, referenceRadius, referencePaint)
            canvas.drawCircle(referenceCenterX + referenceRadius, referenceCenterY, 10f, referencePaint.apply { style = Paint.Style.FILL })
        } else {
            // Mode affichage seulement
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, fillPaint)
            canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, circlePaint)
            canvas.drawCircle(referenceCenterX, referenceCenterY, referenceRadius, referencePaint)
        }
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isAdjustable) return false
        
        val x = event.x
        val y = event.y
        
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Vérifier si on touche le cercle principal
                val distToCircle = sqrt((x - circleCenterX) * (x - circleCenterX) + (y - circleCenterY) * (y - circleCenterY))
                val distToCircleEdge = abs(distToCircle - circleRadius)
                
                if (distToCircleEdge < 30f) {
                    // Vérifier si on touche la poignée de redimensionnement
                    val distToHandle = sqrt((x - (circleCenterX + circleRadius)) * (x - (circleCenterX + circleRadius)) + (y - circleCenterY) * (y - circleCenterY))
                    if (distToHandle < 30f) {
                        isResizingCircle = true
                    } else {
                        isDraggingCircle = true
                    }
                    return true
                }
                
                // Vérifier si on touche la référence
                val distToReference = sqrt((x - referenceCenterX) * (x - referenceCenterX) + (y - referenceCenterY) * (y - referenceCenterY))
                val distToReferenceEdge = abs(distToReference - referenceRadius)
                
                if (distToReferenceEdge < 30f) {
                    val distToRefHandle = sqrt((x - (referenceCenterX + referenceRadius)) * (x - (referenceCenterX + referenceRadius)) + (y - referenceCenterY) * (y - referenceCenterY))
                    if (distToRefHandle < 30f) {
                        isResizingReference = true
                    } else {
                        isDraggingReference = true
                    }
                    return true
                }
            }
            
            MotionEvent.ACTION_MOVE -> {
                if (isDraggingCircle) {
                    circleCenterX = x
                    circleCenterY = y
                    invalidate()
                } else if (isResizingCircle) {
                    circleRadius = sqrt((x - circleCenterX) * (x - circleCenterX) + (y - circleCenterY) * (y - circleCenterY))
                    if (circleRadius < 20f) circleRadius = 20f
                    if (circleRadius > minOf(width, height) / 2f) circleRadius = minOf(width, height) / 2f
                    invalidate()
                } else if (isDraggingReference) {
                    referenceCenterX = x
                    referenceCenterY = y
                    invalidate()
                } else if (isResizingReference) {
                    referenceRadius = sqrt((x - referenceCenterX) * (x - referenceCenterX) + (y - referenceCenterY) * (y - referenceCenterY))
                    if (referenceRadius < 10f) referenceRadius = 10f
                    if (referenceRadius > minOf(width, height) / 4f) referenceRadius = minOf(width, height) / 4f
                    invalidate()
                }
            }
            
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDraggingCircle = false
                isResizingCircle = false
                isDraggingReference = false
                isResizingReference = false
            }
        }
        
        return true
    }
}

