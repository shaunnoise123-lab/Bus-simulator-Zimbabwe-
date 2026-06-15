package com.bussimulatorzim.game.rendering

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color as AndroidColor
import com.bussimulatorzim.game.physics.Vector3
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vertex(
    val position: Vector3,
    val color: Int = AndroidColor.WHITE,
    val textureU: Float = 0f,
    val textureV: Float = 0f
)

data class Triangle(
    val v1: Vertex,
    val v2: Vertex,
    val v3: Vertex
) {
    fun projectToScreen(width: Int, height: Int): Triple<Triple<Float, Float, Float>, Triple<Float, Float, Float>, Triple<Float, Float, Float>> {
        val screenV1 = projectVertex(v1, width, height)
        val screenV2 = projectVertex(v2, width, height)
        val screenV3 = projectVertex(v3, width, height)
        return Triple(screenV1, screenV2, screenV3)
    }
    
    private fun projectVertex(v: Vertex, width: Int, height: Int): Triple<Float, Float, Float> {
        // Simple perspective projection
        val z = v.position.z + 100
        val scale = 500f / (z + 1)
        val x = (v.position.x * scale + width / 2)
        val y = (v.position.y * scale + height / 2)
        return Triple(x, y, z)
    }
}

class Model3D {
    val vertices = mutableListOf<Vertex>()
    val triangles = mutableListOf<Triangle>()
    
    fun createBus(): Model3D {
        // Create a simple bus shape
        val bus = Model3D()
        
        // Bus body vertices
        bus.vertices.addAll(listOf(
            // Front face
            Vertex(Vector3(-3f, 2f, 5f), AndroidColor.RED),
            Vertex(Vector3(3f, 2f, 5f), AndroidColor.RED),
            Vertex(Vector3(3f, -2f, 5f), AndroidColor.RED),
            Vertex(Vector3(-3f, -2f, 5f), AndroidColor.RED),
            // Back face
            Vertex(Vector3(-3f, 2f, -5f), AndroidColor.RED),
            Vertex(Vector3(3f, 2f, -5f), AndroidColor.RED),
            Vertex(Vector3(3f, -2f, -5f), AndroidColor.RED),
            Vertex(Vector3(-3f, -2f, -5f), AndroidColor.RED)
        ))
        
        // Create triangles
        bus.triangles.addAll(listOf(
            Triangle(bus.vertices[0], bus.vertices[1], bus.vertices[2]),
            Triangle(bus.vertices[0], bus.vertices[2], bus.vertices[3]),
            Triangle(bus.vertices[4], bus.vertices[6], bus.vertices[5]),
            Triangle(bus.vertices[4], bus.vertices[7], bus.vertices[6])
        ))
        
        return bus
    }
    
    fun createRoad(): Model3D {
        val road = Model3D()
        
        // Road surface
        road.vertices.addAll(listOf(
            Vertex(Vector3(-20f, -0.5f, -50f), AndroidColor.GRAY),
            Vertex(Vector3(20f, -0.5f, -50f), AndroidColor.GRAY),
            Vertex(Vector3(20f, -0.5f, 100f), AndroidColor.GRAY),
            Vertex(Vector3(-20f, -0.5f, 100f), AndroidColor.GRAY)
        ))
        
        road.triangles.addAll(listOf(
            Triangle(road.vertices[0], road.vertices[1], road.vertices[2]),
            Triangle(road.vertices[0], road.vertices[2], road.vertices[3])
        ))
        
        return road
    }
    
    fun createTree(): Model3D {
        val tree = Model3D()
        
        // Trunk
        tree.vertices.addAll(listOf(
            Vertex(Vector3(-0.5f, 0f, 0f), AndroidColor.parseColor("#8B4513")),
            Vertex(Vector3(0.5f, 0f, 0f), AndroidColor.parseColor("#8B4513")),
            Vertex(Vector3(0.5f, 3f, 0f), AndroidColor.parseColor("#8B4513")),
            Vertex(Vector3(-0.5f, 3f, 0f), AndroidColor.parseColor("#8B4513"))
        ))
        
        // Foliage (simplified sphere)
        tree.vertices.addAll(listOf(
            Vertex(Vector3(0f, 4f, 0f), AndroidColor.GREEN),
            Vertex(Vector3(1f, 3.5f, 0f), AndroidColor.GREEN),
            Vertex(Vector3(-1f, 3.5f, 0f), AndroidColor.GREEN)
        ))
        
        return tree
    }
}

class Camera3D {
    var position = Vector3(0f, 2f, -10f)
    var lookAt = Vector3(0f, 0f, 0f)
    var up = Vector3(0f, 1f, 0f)
    var fov = 60f
    var near = 0.1f
    var far = 1000f
    
    fun moveForward(distance: Float) {
        val direction = lookAt.copy()
        direction.x -= position.x
        direction.z -= position.z
        val len = sqrt(direction.x * direction.x + direction.z * direction.z)
        if (len > 0) {
            position.x += (direction.x / len) * distance
            position.z += (direction.z / len) * distance
        }
    }
    
    fun moveRight(distance: Float) {
        val direction = Vector3(sin((position.x - lookAt.x).toDouble()).toFloat(), 0f, cos((position.x - lookAt.x).toDouble()).toFloat())
        position.x += direction.x * distance
        position.z += direction.z * distance
    }
    
    fun rotate(yaw: Float, pitch: Float) {
        // Rotate camera around look point
        val distance = 10f
        lookAt.x = position.x + sin(yaw.toDouble()).toFloat() * distance
        lookAt.z = position.z + cos(yaw.toDouble()).toFloat() * distance
        lookAt.y = position.y + sin(pitch.toDouble()).toFloat() * distance
    }
}

class Graphics3DRenderer {
    private val camera = Camera3D()
    private val models = mutableListOf<Model3D>()
    private var screenWidth = 800
    private var screenHeight = 600
    
    fun initialize(width: Int, height: Int) {
        screenWidth = width
        screenHeight = height
    }
    
    fun addModel(model: Model3D) {
        models.add(model)
    }
    
    fun render(canvas: Canvas) {
        // Clear background
        canvas.drawColor(AndroidColor.CYAN)
        
        // Draw each model
        for (model in models) {
            for (triangle in model.triangles) {
                val (p1, p2, p3) = triangle.projectToScreen(screenWidth, screenHeight)
                
                val paint = Paint().apply {
                    color = triangle.v1.color
                    style = Paint.Style.FILL
                }
                
                canvas.drawPath(android.graphics.Path().apply {
                    moveTo(p1.first, p1.second)
                    lineTo(p2.first, p2.second)
                    lineTo(p3.first, p3.second)
                    close()
                }, paint)
                
                // Draw outline
                paint.style = Paint.Style.STROKE
                paint.color = AndroidColor.BLACK
                canvas.drawPath(android.graphics.Path().apply {
                    moveTo(p1.first, p1.second)
                    lineTo(p2.first, p2.second)
                    lineTo(p3.first, p3.second)
                    close()
                }, paint)
            }
        }
        
        // Draw HUD elements
        drawHUD(canvas)
    }
    
    private fun drawHUD(canvas: Canvas) {
        val paint = Paint().apply {
            color = AndroidColor.WHITE
            textSize = 20f
        }
        
        canvas.drawText("Camera: (${camera.position.x}, ${camera.position.y}, ${camera.position.z})", 10f, 30f, paint)
    }
    
    fun getCamera() = camera
}
