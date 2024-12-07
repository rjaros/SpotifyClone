package components

import Assets
import Colors
import Constants
import ContentOpacity
import UserSelect
import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import dev.kilua.core.IComponent
import dev.kilua.html.AlignItems
import dev.kilua.html.Background
import dev.kilua.html.BoxShadow
import dev.kilua.html.Cursor
import dev.kilua.html.Display
import dev.kilua.html.FontWeight
import dev.kilua.html.Overflow
import dev.kilua.html.div
import dev.kilua.html.img
import dev.kilua.html.px
import dev.kilua.html.span
import dev.kilua.html.style.PClass
import dev.kilua.svg.circle
import dev.kilua.svg.path
import dev.kilua.svg.svg
import dev.kilua.utils.rem
import hideBackground
import rememberIsHoveredAsState
import scale
import toKiluaColor
import userSelect

@Composable
fun IComponent.ListItem() = div {
    val isHovered by rememberIsHoveredAsState()
    val animatedBGColor by animateColorAsState(
        if (isHovered) Color.White.copy(alpha = ContentOpacity.HOVERED)
        else Color.White.copy(alpha = ContentOpacity.NORMAL)
    )
    alignItems(AlignItems.Center)
    background(Background(color = animatedBGColor.toKiluaColor()))
    borderRadius(4.px)
    cursor(Cursor.Pointer)
    display(Display.Flex)
    flexGrow(1)
    height(48.px)
    overflow(Overflow.Hidden)
    userSelect(UserSelect.None)

    img(src = "https://placehold.co/48x48?text=Spotify") {
        boxShadow(
            BoxShadow(
                hOffset = 0.px,
                vOffset = 0.px,
                blurRadius = 16.px,
                spreadRadius = 4.px,
                color = Color.Black.copy(alpha = 0.25f).toKiluaColor(),
            )
        )
        height(48.px)
        width(48.px)
    }

    span {
        fontSize(14.px)
        fontWeight(FontWeight.Bold)
        hideBackground()
        marginLeft(8.px)
        +"The PropheC Radio"
    }

    // Spacer
    div { flexGrow(1) }

    // Play Button
    if (isHovered) {
        val hoveredStyle = dev.kilua.html.style.style(pClass = PClass.Hover) {
            scale(Constants.SCALE_HOVERED)
        }
        val pressedStyle = dev.kilua.html.style.style(pClass = PClass.Active) { scale(1f) }
        svg(className = hoveredStyle % pressedStyle, viewBox = "0 0 24 24") {
            val isButtonHovered by rememberIsHoveredAsState()
            borderRadius(16.px)
            boxShadow(
                BoxShadow(
                    blurRadius = 4.px,
                    color = Color.Black.copy(alpha = 0.25f).toKiluaColor(),
                    hOffset = 0.px,
                    spreadRadius = 2.px,
                    vOffset = 4.px,
                )
            )
            height(32.px)
            hideBackground()
            marginRight(8.px)
            width(32.px)
            circle(cx = 12.px, cy = 12.px, r = 12.px) {
                fill(
                    if (isButtonHovered) Colors.greenButtonBGHighlighted.value
                    else Colors.greenButtonBG.value
                )
            }
            // Play Button Path
            path(Assets.IC_PLAY_PATH)
        }
    }
}
