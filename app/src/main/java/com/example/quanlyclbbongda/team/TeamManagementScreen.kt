package com.example.quanlyclbbongda.team

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamManagementScreen(
    teamID: Int,
    backHomeScreen: () -> Unit,
    openUpdateMemberScreen: (teamID: Int, memberID: Int) -> Unit
) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var memberTotalDB: Int = appDB.memberDAO().getMemberTotalByTeamID(teamID)
    var memberTotal by remember {
        mutableStateOf(memberTotalDB)
    }
    var memberList: List<Member> = appDB.memberDAO().findByTeamID(teamID)
    var deletedItem = remember {
        mutableStateListOf<Member>()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Quản lý team", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backHomeScreen()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
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
//                .background(Color.LightGray)
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
            var (col1, col2) = createRefs()

//            Column(modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//                .clip(RoundedCornerShape(5.dp))
//                .border(BorderStroke(1.dp, color = Color.Black))
//                .shadow(elevation = 1.5.dp)
//                .constrainAs(col1) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                }) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                        .background(Color.LightGray),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "Cập nhật kết quả thi đấu", fontSize = 18.sp)
//                }
//                Spacer(modifier = Modifier.height(15.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 15.dp)
//                ) {
//                    var isSelectedWin by remember {
//                        mutableStateOf(true)
//                    }
//                    var isSelectedDraw by remember {
//                        mutableStateOf(false)
//                    }
//                    var isSelectedLose by remember {
//                        mutableStateOf(false)
//                    }
//                    Row(
//                        modifier = Modifier.selectable(
//                            selected = isSelectedWin,
//                            onClick = {
//                                isSelectedWin = !isSelectedWin
//                                isSelectedDraw = false
//                                isSelectedLose = false
//                            },
//                            role = Role.RadioButton
//
//                        )
//                    ) {
//                        RadioButton(
//                            selected = isSelectedWin,
//                            onClick = null,
//                            colors = RadioButtonDefaults.colors(
//                                selectedColor = Color.Red,
//                                unselectedColor = Color.Black,
//                                disabledSelectedColor = Color.Gray,
//                                disabledUnselectedColor = Color.Gray
//                            )
//                        )
//                        Text(text = "Thắng", modifier = Modifier.padding(horizontal = 10.dp))
//                    }
//
//                    Row(
//                        modifier = Modifier.selectable(
//                            selected = isSelectedDraw,
//                            onClick = {
//                                isSelectedWin = false
//                                isSelectedDraw = !isSelectedDraw
//                                isSelectedLose = false
//                            },
//                            role = Role.RadioButton
//
//                        )
//                    ) {
//                        RadioButton(
//                            selected = isSelectedDraw,
//                            onClick = null,
//                            colors = RadioButtonDefaults.colors(
//                                selectedColor = Color.Red,
//                                unselectedColor = Color.Black,
//                                disabledSelectedColor = Color.Gray,
//                                disabledUnselectedColor = Color.Gray
//                            )
//                        )
//                        Text(text = "Hòa", modifier = Modifier.padding(horizontal = 10.dp))
//                    }
//
//                    Row(
//                        modifier = Modifier.selectable(
//                            selected = isSelectedLose,
//                            onClick = {
//                                isSelectedWin = false
//                                isSelectedDraw = false
//                                isSelectedLose = !isSelectedLose
//                            },
//                            role = Role.RadioButton
//
//                        )
//                    ) {
//                        RadioButton(
//                            selected = isSelectedLose,
//                            onClick = null,
//                            colors = RadioButtonDefaults.colors(
//                                selectedColor = Color.Red,
//                                unselectedColor = Color.Black,
//                                disabledSelectedColor = Color.Gray,
//                                disabledUnselectedColor = Color.Gray
//                            )
//                        )
//                        Text(text = "Thua", modifier = Modifier.padding(horizontal = 10.dp))
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier.padding(start = 15.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    var tiso1 by remember {
//                        mutableStateOf("")
//                    }
//                    var tiso2 by remember {
//                        mutableStateOf("")
//                    }
//
//                    Text(
//                        text = "Tỉ số:",
//                        fontSize = 16.sp,
//                        modifier = Modifier.padding(end = 15.dp)
//                    )
//                    TextField(
//                        value = tiso1, onValueChange = {
//                            tiso1 = it
//                        },
//                        modifier = Modifier
//                            .padding(end = 15.dp)
//                            .width(30.dp)
//                            .height(10.dp),
//                        colors = TextFieldDefaults.colors(
//                            unfocusedContainerColor = Color.Transparent,
//                            focusedContainerColor = Color.Transparent
//                        )
//                    )
//                    Text(
//                        text = "-",
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(end = 15.dp)
//                    )
//                    TextField(
//                        value = tiso2, onValueChange = {
//                            tiso2 = it
//                        },
//                        modifier = Modifier
//                            .padding(end = 15.dp)
//                            .width(30.dp)
//                            .height(10.dp),
//                        colors = TextFieldDefaults.colors(
//                            unfocusedContainerColor = Color.Transparent,
//                            focusedContainerColor = Color.Transparent
//                        )
//                    )
//                    TextButton(onClick = { /*TODO*/ }) {
//                        Text(
//                            text = "Hủy",
//                            fontSize = 16.sp,
//                            color = Color.Red,
//                            fontWeight = FontWeight.Bold,
//                            textDecoration = TextDecoration.Underline
//                        )
//                    }
//                    TextButton(onClick = { /*TODO*/ }) {
//                        Text(
//                            text = "Cập nhật",
//                            fontSize = 16.sp,
//                            color = Color.Blue,
//                            fontWeight = FontWeight.Bold,
//                            textDecoration = TextDecoration.Underline
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 15.dp)
//                ) {
//                    TextButton(onClick = { /*TODO*/ }) {
//                        Text(
//                            text = "Thống kê kết quả thi đấu",
//                            color = Color.Black,
//                            fontSize = 16.sp,
//                            textDecoration = TextDecoration.Underline
//                        )
//                    }
//                }
//            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .constrainAs(col1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Hồ sơ đội bóng",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(BorderStroke(1.dp, color = Color.Black))
                .shadow(elevation = 1.5.dp)
                .constrainAs(col2) {
                    top.linkTo(col1.bottom)
                    start.linkTo(parent.start)
                }
            ) {
                Row(
                    modifier = Modifier.padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Thành viên",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 15.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .size(40.dp, 25.dp)
                            .padding(end = 15.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Yellow)
                    ) {
                        Text(
                            text = memberTotal.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    IconButton(onClick = {
                        openUpdateMemberScreen(teamID, 0)
                    }) {
                        Icon(Icons.Default.AddCircle, contentDescription = null)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .height(500.dp)
                        .padding(vertical = 4.dp)
                ) {
                    itemsIndexed(
                        items = memberList,
                        itemContent = { _, item ->
                            AnimatedVisibility(
                                visible = !deletedItem.contains(item),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp)) {
                                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                        Row(
                                            modifier = Modifier
                                                .height(150.dp)
                                                .background(Color.LightGray)
                                                .padding(horizontal = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row() {
                                                Column {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.icon_app),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(50.dp, 50.dp)
                                                            .clip(
                                                                CircleShape
                                                            )
                                                    )
                                                    if (item.isCaptain == true) {
                                                        Text(text = "Captain", color = Color.Red, fontWeight = FontWeight.Bold)
                                                    }
                                                    Text(text = item.memberName, fontWeight = FontWeight.Bold)
                                                    Text(text = "Số áo: ${item.memberJerseyNumber}")
                                                    Text(text = item.memberPosition)
                                                }
                                            }
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Absolute.Right
                                            ) {
                                                IconButton(onClick = {
                                                    openUpdateMemberScreen(teamID, item.memberID)
                                                }) {
                                                    Icon(
                                                        Icons.Default.Edit,
                                                        contentDescription = null,
                                                        tint = Color.Blue
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    GlobalScope.launch(Dispatchers.IO) {
                                                        appDB.memberDAO().deleteMember(
                                                            Member(
                                                                item.memberID,
                                                                item.teamID,
                                                                item.memberName,
                                                                item.memberPosition,
                                                                item.isCaptain,
                                                                item.memberJerseyNumber,
                                                                item.memberPhone
                                                            )
                                                        )
                                                        withContext(Dispatchers.Main) {
                                                            deletedItem.add(item)
                                                            memberTotal--
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
                                                        tint = Color.Red
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TeamManagermentScreenPreview() {
    TeamManagementScreen(teamID = 0, backHomeScreen = {}, openUpdateMemberScreen = {teamID, memberID ->  })
}