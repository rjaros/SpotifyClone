package components

import Assets
import Colors
import Constants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import animateColorOnInteraction
import dev.kilua.core.IComponent
import dev.kilua.html.AlignItems
import dev.kilua.html.Background
import dev.kilua.html.BoxShadow
import dev.kilua.html.Cursor
import dev.kilua.html.Display
import dev.kilua.html.FlexWrap
import dev.kilua.html.FontWeight
import dev.kilua.html.IDiv
import dev.kilua.html.JustifyContent
import dev.kilua.html.Overflow
import dev.kilua.html.Position
import dev.kilua.html.TextDecoration
import dev.kilua.html.TextDecorationLine
import dev.kilua.html.TextOverflow
import dev.kilua.html.div
import dev.kilua.html.img
import dev.kilua.html.perc
import dev.kilua.html.px
import dev.kilua.html.rem
import dev.kilua.html.spant
import dev.kilua.panel.flexPanel
import dev.kilua.panel.gridPanel
import dev.kilua.panel.hPanel
import dev.kilua.panel.vPanel
import dev.kilua.svg.path
import dev.kilua.svg.svg
import models.PlaylistBasicInfo
import models.PlaylistType
import rememberIsHoveredAsState
import rememberIsPressedAsState
import rememberScrollOffset
import rememberWidth
import toKiluaColor

@Composable
fun IComponent.MainBody() {
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    var hoveredListItemIndex by remember { mutableStateOf(0) }
    val hoveredColor by remember {
        derivedStateOf {
            Color(
                when (hoveredListItemIndex) {
                    0 -> 0XFF3B5C56
                    1 -> 0XFF3B4650
                    2 -> 0XFF461610
                    3 -> 0XFF301A17
                    4 -> 0XFF4F5E2B
                    5 -> 0XFF561B26
                    6 -> 0XFF202020
                    else -> 0XFF614C3D
                }
            )
        }
    }

    vPanel {
        val scrollOffset by rememberScrollOffset(ScrollDirection.Vertical)
        val headerBackgroundOpacity by remember {
            derivedStateOf {
                val percentage = (scrollOffset / 100).coerceIn(0.0, 1.0)
                when (percentage) {
                    0.0 -> 0f
                    in 0.0..0.25 -> 0.25f
                    in 0.25..0.75 -> 0.5f
                    else -> 1f
                }
            }
        }

        background(Background(color = Colors.containerElevated))
        borderRadius(Constants.CONTAINER_RADIUS.px)
        flexGrow(1)
        overflowY(Overflow.Auto)
        position(Position.Relative)

        // Filter Chips
        hPanel(gap = 8.px) {
            background(
                Background(
                    color = animateColorAsState(
                        targetValue = hoveredColor.copy(alpha = headerBackgroundOpacity),
                        animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing),
                    ).value.toKiluaColor()
                ),
            )
            padding(contentPadding)
            position(Position.Sticky)
            top(0.px)
            zIndex(1)
            mediaTypeFilters.forEachIndexed { index, filter ->
                SelectableChip(
                    text = filter,
                    isSelected = index == selectedFilterIndex,
                    onSelectionChange = { selectedFilterIndex = index },
                )
            }
        }

        // Main Content
        vPanel {
            val animatedBackgroundColorState = animateColorAsState(
                targetValue = hoveredColor,
                animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
            )
            width(100.perc)
            position(Position.Absolute)

            PlaylistGrid(animatedBackgroundColorState) { hoveredListItemIndex = it }

            repeat(10) { listIndex ->
                CategorisedPlaylists(
                    categoryTitle = when (listIndex) {
                        0 -> "Made For You"
                        1 -> "Your top mixes"
                        2 -> "Recently played"
                        3 -> "Recommended Stations"
                        4 -> "Jump back in"
                        5 -> "Popular radio"
                        6 -> "2024 India Wrapped"
                        7 -> "More like 00's Love Hits"
                        8 -> "More of what you like"
                        else -> "Today's biggest hits"
                    },
                    playlists = List(7) { index ->
                        PlaylistBasicInfo(
                            imageUrl = "https://placehold.co/48x48?text=Spotify",
                            artists = when (listIndex) {
                                0 -> when (index) {
                                    0 -> "Pritam, Shankar-Ehsaan-Loy, Vishal Shekhar"
                                    1 -> "Manavgeet Gill, Diljit Dosanjh, Akhil, and more"
                                    2 -> "Garciia, Luna Novina, Deep Vibrations and more"
                                    3 -> "Adnan Sami, Ghulam Ali, Shankar-Ehsaan-Loy and more"
                                    4 -> "Olivia Line, Ageena, Aksel Møller and more"
                                    5 -> "Your weekly mixtape of fresh music"
                                    else -> "Catch all the latest music from artists"
                                }

                                1 -> when (index) {
                                    0 -> "Pritam, Abhijeet and Yusuf Mohammed"
                                    1 -> "Darshan Raval, Vishal Mishra, Adnan Sami"
                                    2 -> "AKASA, David Guetta and more"
                                    3 -> "Sachin-Jigar, Salim-Sulaiman, Benny Dayal"
                                    4 -> "Sia, Ava Max, Shakira and more"
                                    5 -> "Faris Shafi, David Guetta, Wajid and more"
                                    else -> "Vishal-Shekhar, Pritam, Salim-Sulaiman"
                                }

                                2 -> when (index) {
                                    0 -> "Pritam, Shankar-Ehsaan-Loy, Vishal Shekhar"
                                    1 -> "AKASA, David Guetta and more"
                                    2 -> "With Tegi Pannu, Harnoor, AP Dhillon and more"
                                    3 -> "Groove with the tribe because 'Naina da kehna'"
                                    4 -> "Aditi Rikhari, Wajid and Vishal Mishra"
                                    5 -> "With Atif Aslam, Vishal-Shekhar, and more"
                                    else -> "This is Sanam. The essential tracks, all here."
                                }

                                3 -> when (index) {
                                    0 -> "With Karan Aujla, Shubh, Sidhu Moosewala"
                                    1 -> "With Uttam Singh, Lata Mangeshkar, and more"
                                    2 -> "With Pritam, Mohit Chauhan, Vishal-Shekhar"
                                    3 -> "With Mustafa Zahid, Atif Aslam, Vishal-Shekhar"
                                    4 -> "With Darshan Raval, Sachet Tandon, Atif Aslam"
                                    5 -> "With Pritam, Sachin-Jigar, Mohit Chauhan"
                                    else -> "With Darshan Raval, Ankit Tiwari, Vishal Mishra"
                                }

                                4 -> when (index) {
                                    0 -> "2003 to 2020 emraan hashmi hits"
                                    1 -> "Fall in love with 00's Bollywood like never before"
                                    2 -> "Adnan Sami"
                                    3 -> "Hottest tracks from Coke Studio"
                                    4 -> "Artist"
                                    5 -> "Pritam"
                                    else -> "Artist"
                                }

                                5 -> when (index) {
                                    0 -> "With A.R. Rahman, Vishal-Shekhar, Atif Aslam"
                                    1 -> "With Pritam, Mohit Chauhan, Armaan Malik"
                                    2 -> "With Harris Jayaraj, Anirudh..."
                                    3 -> "With Anuradha Paudwal, Asha Bhosale"
                                    4 -> "With A.R. Rahman, Swarnalatha,..."
                                    5 -> "With Shubh, Diljit Dosanjh, Sidhu Moosewala"
                                    else -> "With Badshah, Diljit Dosanjh, Guru Randhawa"
                                }

                                6 -> when (index) {
                                    0 -> "The most streamed songs of 2024 in India"
                                    1 -> "The most streamed songs of 2024 in Hindi"
                                    2 -> "I-Pop tracks we loved the most in 2024"
                                    3 -> "The most streamed tracks of 2024 in Punjabi"
                                    4 -> "The most streamed tracks of 2024 in Telugu"
                                    5 -> "The most streamed tracks of 2024 in Tamil"
                                    else -> "The most streamed tracks of 2024 in Malayalam"
                                }

                                7 -> when (index) {
                                    0 -> "Bollywood songs that ruled hearts in 00's"
                                    1 -> "Soulful tunes for the heart that has love"
                                    2 -> "Celebrate the Queen of melodies Shreya Ghoshal"
                                    3 -> "Essential tracks of Emraan Hashmi, all in one playlist"
                                    4 -> "Essential tracks of Shah Rukh Khan, all in one playlist"
                                    5 -> "Bollywood's mesmerising ode to sufi"
                                    else -> "Essential tracks of Alia Bhatt all in one playlist"
                                }

                                8 -> when (index) {
                                    0 -> "Turn down the lights, these are the..."
                                    1 -> "Catch all the celebrations and dancing"
                                    2 -> "A perfect travel mix for your journey."
                                    3 -> "Bollywood's mesmerising ode to sufi."
                                    4 -> "Let these songs help you walk that extra mile"
                                    5 -> "Essential tracks of Alia Bhatt all in one playlist"
                                    else -> "Essential tracks of Shahid Kapoor all in one playlist"
                                }

                                else -> when (index) {
                                    0 -> "Every track you're listening/should be listening to"
                                    1 -> "Hottest Hindi music that India is listening to"
                                    2 -> "Hottest tracks from your favourite I-Pop icons"
                                    3 -> "Catch the hottest Punjabi tracks. Cover Diljit Dosanjh."
                                    4 -> "Catch all the top Haryanvi hits. Cover"
                                    5 -> "Ultimate 101 Punjabi Hits with Arjan Velly"
                                    else -> "Our editor's picks for best Malayalam songs in 2024"
                                }
                            },
                            type = when {
                                listIndex == 4 && (index == 4 || index == 6) -> PlaylistType.Artist
                                else -> PlaylistType.Songs
                            },
                            title = when (listIndex) {
                                2 -> when (index) {
                                    0 -> "Daily Mix 1"
                                    1 -> "Dance/Electronic Mix"
                                    2 -> "The PropheC Radio"
                                    3 -> "New Music Hindi"
                                    4 -> "Shreya Ghoshal Mix"
                                    5 -> "Arijit Singh Radio"
                                    else -> "This Is Sanam"
                                }

                                4 -> when (index) {
                                    0 -> "Emraan Hashmi 2003 to 2020"
                                    1 -> null
                                    2 -> "Tera Chehra"
                                    3 -> null
                                    4 -> "Adnan Sami"
                                    5 -> "Laal Singh Chadha"
                                    else -> "Harsh Kargeti"
                                }

                                else -> null
                            }
                        )
                    }
                )
            }

            // Footer Links
            flexPanel(justifyContent = JustifyContent.SpaceBetween) {
                flexWrap(FlexWrap.Wrap)
                marginBottom(32.px)
                paddingLeft(32.px)
                paddingRight(32.px)
                style("gap", "48px")
                FooterLinks(title = "Company", links = listOf("About", "Jobs", "For the Record"))
                FooterLinks(
                    title = "Communities",
                    links = listOf(
                        "For Artists",
                        "Developers",
                        "Advertising",
                        "Investors",
                        "Vendors"
                    ),
                )
                FooterLinks(title = "Useful links", links = listOf("Support", "Free Mobile App"))
                FooterLinks(
                    title = "Spotify Plans",
                    links = listOf(
                        "Premium Individual",
                        "Premium Duo",
                        "Premium Family",
                        "Premium Student",
                        "Spotify Free",
                    )
                )

                // Social Media Buttons
                hPanel(gap = 16.px) {
                    SocialMediaButton(*Assets.IC_INSTAGRAM_LOGO_PATHS_16)
                    SocialMediaButton(Assets.IC_TWITTER_LOGO_PATH_16)
                    SocialMediaButton(Assets.IC_FACEBOOK_LOGO_PATH_16)
                }
            }

            // Divider
            div {
                background(Background(color = Colors.containerHighlighted))
                flexGrow(1)
                height(1.px)
                marginBottom(32.px)
                marginLeft(32.px)
                marginRight(32.px)
            }

            // Quick Links and Copyright
            hPanel(gap = 16.px, rowGap = 8.px) {
                flexWrap(FlexWrap.Wrap)
                marginBottom(80.px)
                marginLeft(32.px)
                marginRight(32.px)

                for (link in quickLinks) QuickLink(link)

                // Spacer
                div { flexGrow(1) }

                spant("© 2024 Spotify AB") {
                    color(Colors.onContainer)
                    cursor(Cursor.Text)
                    fontSize(14.px)
                    marginRight(16.px)
                    style("font-weight", "300")
                    style("user-select", "text")
                }
            }
        }
    }
}

@Composable
private fun IDiv.CategorisedPlaylists(categoryTitle: String, playlists: List<PlaylistBasicInfo>) {
    vPanel {
        hPanel(
            alignItems = AlignItems.Center,
            justifyContent = JustifyContent.SpaceBetween
        ) {
            marginBottom(8.px)
            paddingLeft(16.px)
            paddingRight(32.px)
            spant(categoryTitle) {
                val isHovered by rememberIsHoveredAsState()
                color(Colors.white)
                fontSize(24.px)
                fontWeight(FontWeight.Bold)
                role(Constants.Role.BUTTON)
                if (isHovered) textDecoration(TextDecoration(line = TextDecorationLine.Underline))
            }
            spant("Show all") {
                val isHovered by rememberIsHoveredAsState()
                color(Colors.onContainer)
                fontSize(14.px)
                fontWeight(FontWeight.Bold)
                role(Constants.Role.BUTTON)
                if (isHovered) textDecoration(TextDecoration(line = TextDecorationLine.Underline))
            }
        }

        gridPanel {
            gridAutoRows("0px")
            gridTemplateColumns("repeat(auto-fit, minmax(160px, 1fr))")
            gridTemplateRows("auto")
            marginBottom(48.px)
            marginLeft(4.px)
            marginRight(12.px)
            overflow(Overflow.Hidden)
            for (playlist in playlists) {
                vPanel {
                    animateColorOnInteraction(
                        normalColor = Colors.transparent,
                        hoverColor = Colors.container,
                        pressColor = Colors.black,
                        applyOnBackground = true
                    )
                    borderRadius(8.px)
                    padding(12.px)
                    role(Constants.Role.BUTTON)
                    flexPanel {
                        val isContainerHovered by rememberIsHoveredAsState()
                        position(Position.Relative)
                        img(playlist.imageUrl) {
                            borderRadius(
                                when (playlist.type) {
                                    PlaylistType.Songs -> 8.px
                                    PlaylistType.Artist -> 50.perc
                                }
                            )
                            boxShadow(
                                BoxShadow(
                                    hOffset = 0.px,
                                    vOffset = 4.px,
                                    blurRadius = 8.px,
                                    spreadRadius = 0.px,
                                    color = Color.Black.copy(alpha = 0.25f).toKiluaColor(),
                                )
                            )
                            height(100.perc)
                            style("aspect-ratio", "1/1")
                            width(100.perc)
                        }
                        GreenPlayButton(sizePx = 48, marginBottomPx = 8) {
                            val animatedOpacity by animateFloatAsState(
                                if (isContainerHovered) 1f else 0f
                            )
                            val animatedTranslationY by animateFloatAsState(
                                if (isContainerHovered) 0f else 4f
                            )
                            opacity(animatedOpacity.toDouble())
                            transform("translate(0 $animatedTranslationY)")
                        }
                    }
                    if (playlist.title != null) {
                        spant(playlist.title) {
                            color(Colors.white)
                            marginTop(12.px)
                        }
                    }
                    spant(playlist.artists) {
                        color(Colors.onContainer)
                        fontSize(0.8125.rem)
                        marginTop(if (playlist.title != null) 4.px else 12.px)
                        overflowY(Overflow.Hidden)
                        style("-webkit-box-orient", "vertical")
                        style("-webkit-line-clamp", "2")
                        style("display", "-webkit-box")
                        textOverflow(TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Composable
private fun IDiv.QuickLink(label: String) {
    spant(label) {
        val isHovered by rememberIsHoveredAsState()
        color(if (isHovered) Colors.white else Colors.onContainer)
        fontSize(14.px)
        role(Constants.Role.BUTTON)
        style("font-weight", "300")
        style("user-select", "text")
    }
}

@Composable
private fun IDiv.SocialMediaButton(vararg paths: String) {
    flexPanel(alignItems = AlignItems.Center, justifyContent = JustifyContent.Center) {
        val isPressed by rememberIsPressedAsState()
        // TODO: Extract these colors out
        animateColorOnInteraction(
            normalColor = dev.kilua.html.Color("#292929"),
            hoverColor = dev.kilua.html.Color("#727272"),
            pressColor = dev.kilua.html.Color("#555555"),
            applyOnBackground = true,
        )
        borderRadius(20.px)
        height(40.px)
        role(Constants.Role.BUTTON)
        width(40.px)
        svg(viewBox = Constants.VIEW_BOX_16) {
            fill(if (isPressed) "#b8b8b8" else Colors.white.value)
            height(16.px)
            width(16.px)
            for (currentPath in paths) path(currentPath)
        }
    }
}

@Composable
private fun IDiv.FooterLinks(
    title: String,
    links: List<String>,
) {
    vPanel(gap = 8.px) {
        spant(title) {
            color(Colors.white)
            fontWeight(FontWeight.Bold)
            style("user-select", "text")
        }
        for (link in links) {
            spant(link) {
                val isHovered by rememberIsHoveredAsState()
                color(if (isHovered) Colors.white else Colors.onContainer)
                if (isHovered) textDecoration(TextDecoration(line = TextDecorationLine.Underline))
                role(Constants.Role.BUTTON)
                style("font-weight", "300")
                style("user-select", "text")
            }
        }
    }
}

@Composable
private fun IDiv.PlaylistGrid(
    animatedBackgroundColorState: State<Color>,
    onHoveredIndexChanged: (Int) -> Unit,
) {
    gridPanel {
        val gridWidth by rememberWidth()
        val isLargeGrid by remember { derivedStateOf { (gridWidth ?: 0.0) >= 815 } }
        columnGap(8.px)
        display(Display.Grid)
        gridTemplateColumns("1fr 1fr${if (isLargeGrid) " 1fr 1fr" else ""}")
        paddingBottom(56.px)
        paddingLeft(16.px)
        paddingRight(16.px)
        paddingTop(64.px)
        rowGap(8.px)
        style(
            name = "background",
            value = "linear-gradient(" +
                    "${animatedBackgroundColorState.value.toKiluaColor().value}, " +
                    "${Colors.transparent.value} 70%)"
        )
        fakePlaylists.forEachIndexed { index, title ->
            ListItem(
                title = title,
                imageUrl = "https://placehold.co/48x48?text=Spotify",
                onHoverChanged = { isHovered ->
                    onHoveredIndexChanged(if (isHovered) index else 0)
                }
            )
        }
    }
}

private val contentPadding = 16.px
private val mediaTypeFilters = listOf("All", "Music", "Podcasts")

private val fakePlaylists = listOf(
    "The PropheC Radio",
    "New Music Hindi",
    "Arijit Singh Radio",
    "Daily Mix 5",
    "Emraan Hashmi 2003 to 2020",
    "This Is Sanam",
    "Happy Vibes",
    "Daily Mix 6"
)

private val quickLinks = listOf(
    "Legal",
    "Safety & Privacy Center",
    "Privacy Policy",
    "Cookies",
    "About Ads",
    "Accessibility",
)
