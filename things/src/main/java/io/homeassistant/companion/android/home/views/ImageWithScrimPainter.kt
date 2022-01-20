package io.homeassistant.companion.android.home.views

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter

/**
 * A painter which takes wraps another [Painter] for drawing a background image and a [Brush] which
 * is used to create an effect over the image to ensure that text drawn over it will be legible.
 */
internal class ImageWithScrimPainter(
    val imagePainter: Painter,
    val brush: Brush,
    private var scrimAlpha: Float = 1.0f,
    private var alpha: Float = 1.0f
) : Painter() {

    private var colorFilter: ColorFilter? = null

    override fun DrawScope.onDraw() {
        val size = this.size
        with(imagePainter) { draw(size = size, alpha = alpha, colorFilter = colorFilter) }
        drawRect(brush = brush, alpha = scrimAlpha * alpha, colorFilter = colorFilter)
    }

    override fun applyAlpha(alpha: Float): Boolean {
        this.alpha = alpha
        return true
    }

    override fun applyColorFilter(colorFilter: ColorFilter?): Boolean {
        this.colorFilter = colorFilter
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (this::class != other::class) return false

        other as ImageWithScrimPainter

        if (imagePainter != other.imagePainter) return false
        if (brush != other.brush) return false
        if (scrimAlpha != other.scrimAlpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imagePainter.hashCode()
        result = 31 * result + brush.hashCode()
        result = 31 * result + scrimAlpha.hashCode()
        return result
    }

    override fun toString(): String {
        return "ImageWithScrimPainter(imagePainter=$imagePainter, brush=$brush, " +
            "scrimAlpha=$scrimAlpha)"
    }

    /**
     * Size of the combined painter, return Unspecified to allow us to fill the available space
     */
    override val intrinsicSize: Size = imagePainter.intrinsicSize
}
