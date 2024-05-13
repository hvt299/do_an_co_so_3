package com.example.quanlyclbbongda.schedule

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Member
import com.example.quanlyclbbongda.data.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    userEmail: String,
    teamID: Int,
    Logout: () -> Unit,
    backHomeScreen: () -> Unit,
    openContactInfoScreen: (userEmail: String, teamID: Int) -> Unit,
    openTeamInfoScreen: (userEmail: String, teamID: Int) -> Unit,
    openAboutUsScreen: () -> Unit,
    openUpdateScheduleScreen: (teamID: Int, scheduleID: Int) -> Unit
) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var scope = rememberCoroutineScope()
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var teamName: String = appDB.teamDAO().findByTeamID(teamID).teamName
    var scheduleList = appDB.scheduleDAO().findByTeamID(teamID)
    var deletedItem = remember {
        mutableStateListOf<Schedule>()
    }
    var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

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
                                        Icons.Default.Call,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Text(text = "Liên lạc", fontSize = 16.sp)
                                }
                            },
                            selected = false,
                            onClick = {
                                openContactInfoScreen(userEmail, teamID)
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
                        Text(text = "Lịch thi đấu", fontSize = 18.sp)
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
                        selected = true,
                        onClick = {},
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
                            openContactInfoScreen(userEmail, teamID)
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openUpdateScheduleScreen(teamID, 0)
                    },
                    containerColor = Color(0xFFEA9010),
                    contentColor = Color.Black
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
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
                var col = createRef()
                var showDialog by remember {
                    mutableStateOf(false)
                }

                Column(modifier = Modifier.constrainAs(col) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column {
                        Text(
                            text = "Đang diễn ra",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        itemsIndexed(
                            items = scheduleList,
                            itemContent = { _, item ->
                                AnimatedVisibility(
                                    visible = !deletedItem.contains(item),
                                    enter = expandVertically(),
                                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                                ) {
                                    var today = LocalDate.now()
                                    if (today.isEqual(item.scheduleDate)) {
                                        Column(modifier = Modifier.background(Color(0xFF08A045))) {
                                            Text(
                                                text = "${item.scheduleTime}, ${
                                                    item.scheduleDate.format(
                                                        dateFormatter
                                                    )
                                                }",
                                                color = Color.White,
                                                fontSize = 13.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .padding(
                                                        start = 15.dp,
                                                        top = 5.dp,
                                                        bottom = 5.dp,
                                                        end = 10.dp
                                                    )
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            )
                                            {
                                                Text(
                                                    text = "${item.firstTeamName}",
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = if (item.scoreMatch.isNullOrEmpty()) "vs" else "${item.scoreMatch}",
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = "${item.secondTeamName}",
                                                    color = Color.White,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Row() {
                                                    IconButton(onClick = {
                                                        openUpdateScheduleScreen(
                                                            teamID,
                                                            item.scheduleID
                                                        )
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Edit,
                                                            contentDescription = null,
                                                            tint = Color.White
                                                        )
                                                    }
                                                    IconButton(onClick = {
                                                        GlobalScope.launch(Dispatchers.IO) {
                                                            appDB.scheduleDAO().deleteSchedule(
                                                                Schedule(
                                                                    item.scheduleID,
                                                                    item.teamID,
                                                                    item.scheduleTime,
                                                                    item.scheduleDate,
                                                                    item.firstTeamName,
                                                                    item.secondTeamName,
                                                                    item.scoreMatch,
                                                                    item.teamResultMatch
                                                                )
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                deletedItem.add(item)
                                                                Toast.makeText(
                                                                    context,
                                                                    "Xoá thành công",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Delete,
                                                            contentDescription = null,
                                                            tint = Color.White
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column {
                        Text(
                            text = "Sắp diễn ra",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        itemsIndexed(
                            items = scheduleList,
                            itemContent = { _, item ->
                                AnimatedVisibility(
                                    visible = !deletedItem.contains(item),
                                    enter = expandVertically(),
                                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                                ) {
                                    var today = LocalDate.now()
                                    if (today.isBefore(item.scheduleDate)) {
                                        Column(modifier = Modifier.background(Color.White)) {
                                            Text(
                                                text = "${item.scheduleTime}, ${
                                                    item.scheduleDate.format(
                                                        dateFormatter
                                                    )
                                                }",
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .padding(
                                                        start = 15.dp,
                                                        top = 5.dp,
                                                        bottom = 5.dp,
                                                        end = 10.dp
                                                    )
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            )
                                            {
                                                Text(
                                                    text = "${item.firstTeamName}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = if (item.scoreMatch.isNullOrEmpty()) "vs" else "${item.scoreMatch}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = "${item.secondTeamName}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Row() {
                                                    IconButton(onClick = {
                                                        openUpdateScheduleScreen(
                                                            teamID,
                                                            item.scheduleID
                                                        )
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Edit,
                                                            contentDescription = null,
                                                            tint = Color.Black
                                                        )
                                                    }
                                                    IconButton(onClick = {
                                                        GlobalScope.launch(Dispatchers.IO) {
                                                            appDB.scheduleDAO().deleteSchedule(
                                                                Schedule(
                                                                    item.scheduleID,
                                                                    item.teamID,
                                                                    item.scheduleTime,
                                                                    item.scheduleDate,
                                                                    item.firstTeamName,
                                                                    item.secondTeamName,
                                                                    item.scoreMatch,
                                                                    item.teamResultMatch
                                                                )
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                deletedItem.add(item)
                                                                Toast.makeText(
                                                                    context,
                                                                    "Xoá thành công",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Delete,
                                                            contentDescription = null,
                                                            tint = Color.Black
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column {
                        Text(
                            text = "Đã kết thúc",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        itemsIndexed(
                            items = scheduleList,
                            itemContent = { _, item ->
                                AnimatedVisibility(
                                    visible = !deletedItem.contains(item),
                                    enter = expandVertically(),
                                    exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                                ) {
                                    var today = LocalDate.now()
                                    if (today.isAfter(item.scheduleDate)) {
                                        Column(modifier = Modifier.background(Color.Yellow)) {
                                            Text(
                                                text = "${item.scheduleTime}, ${
                                                    item.scheduleDate.format(
                                                        dateFormatter
                                                    )
                                                }",
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .padding(
                                                        start = 15.dp,
                                                        top = 5.dp,
                                                        bottom = 5.dp,
                                                        end = 10.dp
                                                    )
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            )
                                            {
                                                Text(
                                                    text = "${item.firstTeamName}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = if (item.scoreMatch.isNullOrEmpty()) "vs" else "${item.scoreMatch}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Text(
                                                    text = "${item.secondTeamName}",
                                                    color = Color.Black,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 13.sp,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(
                                                            start = 15.dp,
                                                            top = 5.dp,
                                                            bottom = 5.dp,
                                                            end = 10.dp
                                                        )
                                                )
                                                Row() {
                                                    IconButton(onClick = {
                                                        openUpdateScheduleScreen(
                                                            teamID,
                                                            item.scheduleID
                                                        )
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Edit,
                                                            contentDescription = null,
                                                            tint = Color.Black
                                                        )
                                                    }
                                                    IconButton(onClick = {
                                                        showDialog = true
                                                        GlobalScope.launch(Dispatchers.IO) {
                                                            appDB.scheduleDAO().deleteSchedule(
                                                                Schedule(
                                                                    item.scheduleID,
                                                                    item.teamID,
                                                                    item.scheduleTime,
                                                                    item.scheduleDate,
                                                                    item.firstTeamName,
                                                                    item.secondTeamName,
                                                                    item.scoreMatch,
                                                                    item.teamResultMatch
                                                                )
                                                            )
                                                            withContext(Dispatchers.Main) {
                                                                deletedItem.add(item)
                                                                Toast.makeText(
                                                                    context,
                                                                    "Xoá thành công",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }
                                                    }) {
                                                        Icon(
                                                            Icons.Default.Delete,
                                                            contentDescription = null,
                                                            tint = Color.Black
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                        },
                        icon = {
                            Icon(Icons.Default.Warning, contentDescription = null)
                        },
                        title = {
                            Text(text = "Bạn có muốn xóa lịch thi đấu trận bóng này không?")
                        },
                        text = {
                            Text(text = "Dữ liệu sau khi xóa không thể hoàn tác!")
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }
                            ) {
                                Text(text = "Hủy")
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog = false
                            }
                            ) {
                                Text(text = "Xác nhận")
                            }
                        },
                    )
                }

            }
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    ScheduleScreen(
        userEmail = "",
        teamID = 0,
        Logout = {},
        backHomeScreen = {},
        openContactInfoScreen = { userEmail, teamID -> },
        openTeamInfoScreen = { userEmail, teamID -> },
        openAboutUsScreen = {},
        openUpdateScheduleScreen = { teamID, scheduleID -> })
}