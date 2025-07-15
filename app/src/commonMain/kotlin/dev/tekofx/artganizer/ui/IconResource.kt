package dev.tekofx.artganizer.ui

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

class IconResource private constructor(
    @DrawableRes private val resID: Int? = null,
    private val imageVector: ImageVector? = null,
    private val drawable: Drawable? = null
) {

    @Composable
    fun asPainterResource(): Painter {

        return when {
            resID != null -> painterResource(id = resID)
            imageVector != null -> rememberVectorPainter(image = imageVector)
            else -> {
                rememberVectorPainter(image = Icons.Filled.Home)
            }
        }

    }

    companion object {
        fun fromDrawableResource(@DrawableRes resID: Int): IconResource {
            return IconResource(resID = resID)
        }

        fun fromDrawableResource(drawable: Drawable): IconResource {
            return IconResource(drawable = drawable)
        }

        fun fromImageVector(imageVector: ImageVector?): IconResource {
            return IconResource(imageVector = imageVector)
        }
    }
}