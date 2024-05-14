package com.example.quanlyclbbongda.contact

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Member
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactInfoScreen(
    userEmail: String,
    teamID: Int,
    Logout: () -> Unit,
    backHomeScreen: () -> Unit,
    openScheduleScreen: (userEmail: String, teamID: Int) -> Unit,
    openTeamInfo: (userEmail: String, teamID: Int) -> Unit,
    openAboutUsScreen: () -> Unit
) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var teamName: String = appDB.teamDAO().findByTeamID(teamID).teamName
    var memberList: List<Member> = appDB.memberDAO().findByTeamID(teamID)
    var deletedItem = remember {
        mutableStateListOf<Member>()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxSize()) {
                    ConstraintLayout(
                        modifier = Modifier
                            .background(Color(0xFFEA9010))
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        var (txtEmail, imgAvatar) = createRefs()
                        var horizontalGuideLine = createGuidelineFromTop(0.2f)

                        Image(
                            painter = painterResource(id = R.drawable.icon_app_removebg),
                            contentDescription = null,
                            modifier = Modifier
                                .height(70.dp)
                                .constrainAs(imgAvatar) {
                                    top.linkTo(horizontalGuideLine)
                                    start.linkTo(parent.start, margin = 16.dp)
                                }
                                .border(1.dp, Color.White, CircleShape)
                                .background(Color.White, CircleShape)
                        )

                        Text(
                            text = userEmail,
                            color = Color.White,
                            fontSize = 18.sp,
                            modifier = Modifier.constrainAs(txtEmail) {
                                top.linkTo(imgAvatar.bottom, margin = 10.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                            }
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Home,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Trang chủ", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                backHomeScreen()
                            }
                        )

                        Divider()

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Lịch thi đấu", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                openScheduleScreen(userEmail, teamID)
                            }
                        )

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Call,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Liên lạc", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        )

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Thông tin đội", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                openTeamInfo(userEmail, teamID)
                            }
                        )

                        Divider()

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Settings,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Cài đặt", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {}
                        )

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Về chúng tôi", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                openAboutUsScreen()
                            }
                        )

                        NavigationDrawerItem(
                            shape = RoundedCornerShape(0.dp),
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_logout_24),
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Đăng xuất", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                Logout()
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Liên hệ", fontSize = 18.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    actions = {
                        Text(
                            text = teamName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 10.dp),
                            color = Color(0xFF08A045)
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar() {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            backHomeScreen()
                        },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        },
                        label = {
                            Text("Trang chủ")
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            openScheduleScreen(userEmail, teamID)
                        },
                        icon = {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                        },
                        label = {
                            Text("Lịch thi đấu")
                        }
                    )

                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(Icons.Default.Call, contentDescription = null)
                        },
                        label = {
                            Text("Liên lạc")
                        }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            openTeamInfo(userEmail, teamID)
                        },
                        icon = {
                            Icon(Icons.Default.Info, contentDescription = null)
                        },
                        label = {
                            Text("Thông tin đội")
                        }
                    )
                }
            }
        ) {
            val brightness = -50f
            val colorMatrix = floatArrayOf(
                1f, 0f, 0f, 0f, brightness,
                0f, 1f, 0f, 0f, brightness,
                0f, 0f, 1f, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.LightGray)
            ) {
//                Image(
//                    painterResource(
//                        id = R.drawable.background_home_app
//                    ),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
//                    modifier = Modifier.matchParentSize()
//                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                LazyColumn(
                    modifier = Modifier
                        .height(600.dp)
                        .padding(vertical = 4.dp)
                ) {itemsIndexed(
                    items = memberList,
                    itemContent = { _, item ->
                        AnimatedVisibility(
                            visible = !deletedItem.contains(item),
                            enter = expandVertically(),
                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                        ) {
                            val call = SwipeAction(
                                onSwipe = {
                                    Log.d("aaa", "Call")
                                    val u = Uri.parse("tel:" + item.memberPhone)
                                    val i = Intent(Intent.ACTION_DIAL, u)
                                    try {
                                        context.startActivity(i)
                                    } catch (s: SecurityException) {
                                        Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Call,
                                        contentDescription = null,
                                        modifier = Modifier.padding(16.dp),
                                        tint = Color.White
                                    )
                                },
                                background = Color.Green
                            )

                            val message = SwipeAction(
                                onSwipe = {
                                    Log.d("aaa", "Message")
                                    val sms_uri = Uri.parse("smsto:${item.memberPhone}")
                                    val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
                                    sms_intent.putExtra("sms_body", "Hello ${item.memberName}! How are you today?")
                                    context.startActivity(sms_intent)
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Email,
                                        contentDescription = null,
                                        modifier = Modifier.padding(16.dp),
                                        tint = Color.White
                                    )
                                },
                                background = Color.Blue
                            )
                            SwipeableActionsBox(
                                swipeThreshold = 50.dp,
                                startActions = listOf(call),
                                endActions = listOf(message),
                                modifier = Modifier.padding(vertical = 20.dp)
                            ) {
                                ConstraintLayout(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .background(Color.White)
                                ) {
                                    var (imgAvatar, txtHoTen, txtSdt) = createRefs()
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_app_removebg),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(60.dp)
                                            .constrainAs(imgAvatar) {
                                                top.linkTo(parent.top, margin = 10.dp)
                                                start.linkTo(parent.start, margin = 10.dp)
                                            }
                                            .border(1.dp, Color.White, CircleShape)
                                            .background(Color.White, CircleShape)
                                    )
                                    Text(
                                        text = item.memberName,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.constrainAs(txtHoTen) {
                                            top.linkTo(parent.top, margin = 10.dp)
                                            start.linkTo(imgAvatar.end, margin = 10.dp)
                                        }
                                    )
                                    Text(
                                        text = item.memberPhone,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.constrainAs(txtSdt) {
                                            top.linkTo(txtHoTen.bottom, margin = 10.dp)
                                            start.linkTo(imgAvatar.end, margin = 10.dp)
                                        }
                                    )
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}

@Preview
@Composable
private fun ContactInfoScreenPreview() {
    ContactInfoScreen(userEmail = "", teamID = 0, Logout = {}, backHomeScreen = {}, openScheduleScreen = {userEmail, teamID ->  }, openTeamInfo = {userEmail, teamID ->  }, openAboutUsScreen = {})
}