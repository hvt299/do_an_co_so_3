package com.example.quanlyclbbongda.team

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamInfoScreen(
    userEmail: String,
    teamID: Int,
    Logout: () -> Unit,
    backHomeScreen: () -> Unit,
    openContactInfo: (userEmail: String, teamID: Int) -> Unit,
    openScheduleScreen: (userEmail: String, teamID: Int) -> Unit,
    openAboutUsScreen: () -> Unit
) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var team: Team = appDB.teamDAO().findByTeamID(teamID)

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
                        Text(text = "Thông tin đội", fontSize = 18.sp)
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
                            text = team.teamName,
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
                        selected = true,
                        onClick = {},
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
                var (imgAnhBiaTeam, tfTenDoiBong, exDropDownMenuBox, tfDiaChi, btnCapNhat) = createRefs()
                var tendoibong by remember {
                    mutableStateOf(team.teamName)
                }
                var diachi by remember {
                    mutableStateOf(team.teamAddress)
                }

                var cacloaisan = arrayOf("Sân 11", "Sân 7", "Sân 5")
                var colordropdownmenu_indicator by remember {
                    mutableStateOf(Color(0xFF08A045))
                }
                var expanded by remember {
                    mutableStateOf(false)
                }
                var selectedText by remember {
                    mutableStateOf(team.teamPitchSize)
                }

                var keyboardController = LocalSoftwareKeyboardController.current
                var focusManager = LocalFocusManager.current

                Image(
                    painter = painterResource(id = R.drawable.background_team),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .constrainAs(imgAnhBiaTeam) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                TextField(
                    value = tendoibong,
                    onValueChange = {
                        tendoibong = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF08A045),
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Transparent))
                        .constrainAs(tfTenDoiBong) {
                            top.linkTo(imgAnhBiaTeam.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                    modifier = Modifier.constrainAs(exDropDownMenuBox) {
                        top.linkTo(tfTenDoiBong.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                ) {
                    TextField(
                        value = selectedText,
                        onValueChange = {
                            selectedText = it
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = colordropdownmenu_indicator,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = Color.Black,
                            unfocusedTrailingIconColor = Color.Black,
                        ),
                        shape = RoundedCornerShape(20.dp),
                        readOnly = true,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.Transparent))
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                            colordropdownmenu_indicator = Color.Transparent
                        }
                    ) {
                        cacloaisan.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                    colordropdownmenu_indicator = Color.Transparent
                                }
                            )
                        }
                    }
                }

                TextField(
                    value = diachi,
                    onValueChange = {
                        diachi = it
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF08A045),
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Transparent))
                        .constrainAs(tfDiaChi) {
                            top.linkTo(exDropDownMenuBox.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words

                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    ),
                )

                Button(
                    onClick = {
                        if (!tendoibong.isNullOrEmpty() && !selectedText.isNullOrEmpty() && !diachi.isNullOrEmpty()) {
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.teamDAO().updateTeam(tendoibong, selectedText, diachi, teamID, userEmail)
                                withContext(Dispatchers.Main){
                                    Toast.makeText(context, "Cập nhật đội bóng thành công", Toast.LENGTH_SHORT).show()
                                    backHomeScreen()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffea9010),
                        contentColor = Color(0xff37371f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnCapNhat) {
                            top.linkTo(tfDiaChi.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {
                    Text(text = "Cập nhật", fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun TeamInfoScreenPreview() {
    TeamInfoScreen(
        userEmail = "",
        teamID = 0,
        Logout = {},
        backHomeScreen = {},
        openContactInfo = {userEmail, teamID ->  },
        openScheduleScreen = {userEmail, teamID ->  },
        openAboutUsScreen = {})
}