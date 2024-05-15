package com.example.quanlyclbbongda.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userEmail: String,
    teamID: Int,
    Logout: () -> Unit,
    openScheduleScreen: (userEmail: String, teamID: Int) -> Unit,
    openContactInfo: (userEmail: String, teamID: Int) -> Unit,
    openTeamInfoScreen: (userEmail: String, teamID: Int) -> Unit,
    openAboutUsScreen: () -> Unit,
    openTeamManagementScreen: (teamID: Int) -> Unit,
    openFundManagementScreen: (teamID: Int) -> Unit
) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var teamName: String = appDB.teamDAO().findByTeamID(teamID).teamName

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
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
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
                                openContactInfo(userEmail, teamID)
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
                                openTeamInfoScreen(userEmail, teamID)
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
                        Text(text = "Trang chủ", fontSize = 18.sp)
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
                        selected = true,
                        onClick = {},
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
                        selected = false,
                        onClick = {
                            openContactInfo(userEmail, teamID)
                        },
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
                            openTeamInfoScreen(userEmail, teamID)
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
            ) {
                Image(
                    painterResource(
                        id = R.drawable.background_home_app
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
                    modifier = Modifier.matchParentSize()
                )
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                var (btnQuanLyQuy, btnQuanLyTeam, btnDoiHinhRaSan) = createRefs()
                var horizontalGuideLine = createGuidelineFromTop(0.1f)
                var horizontalChain =
                    createHorizontalChain(
                        btnQuanLyQuy,
                        btnQuanLyTeam,
                        chainStyle = ChainStyle.Spread
                    )

                Button(
                    onClick = {
                              openFundManagementScreen(teamID)
                    },
                    modifier = Modifier.constrainAs(btnQuanLyQuy) {
                        top.linkTo(horizontalGuideLine)
                        horizontalChain
                        height = Dimension.value(125.dp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xff37371f)
                    ),
                    border = BorderStroke(1.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_money_management),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Quản lý quỹ", fontSize = 18.sp)
                    }
                }

                Button(
                    onClick = {
                        openTeamManagementScreen(teamID)
                    },
                    modifier = Modifier.constrainAs(btnQuanLyTeam) {
                        top.linkTo(horizontalGuideLine)
                        horizontalChain
                        height = Dimension.value(125.dp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xff37371f)
                    ),
                    border = BorderStroke(1.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_team),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Quản lý team", fontSize = 18.sp)
                    }
                }

                Button(
                    onClick = {},
                    modifier = Modifier.constrainAs(btnDoiHinhRaSan) {
                        top.linkTo(btnQuanLyQuy.bottom, margin = 30.dp)
                        start.linkTo(btnQuanLyQuy.start)
                        end.linkTo(btnQuanLyTeam.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(125.dp)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xff37371f)
                    ),
                    border = BorderStroke(1.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(10.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_lineup),
                            contentDescription = null,
                            modifier = Modifier.height(50.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Đội hình ra sân", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        userEmail = "",
        teamID = 0,
        Logout = {},
        openScheduleScreen = {userEmail, teamID ->  },
        openContactInfo = {userEmail, teamID ->  },
        openTeamInfoScreen = {userEmail, teamID ->  },
        openAboutUsScreen = {},
        openTeamManagementScreen = {},
        openFundManagementScreen = {}
    )
}