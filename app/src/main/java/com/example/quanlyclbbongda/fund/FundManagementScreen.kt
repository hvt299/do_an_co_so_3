package com.example.quanlyclbbongda.fund

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Fund
import com.example.quanlyclbbongda.data.models.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

//sử dụng format dd/mm/yy
@SuppressLint("NewApi")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundManagementScreen(
    teamID: Int,
    backHomeScreen: () -> Unit,
    viewStatistics: (teamID: Int) -> Unit,
) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var fundTotalDB: Int = appDB.fundDAO().getNumberFundByTeamID(teamID)

    var fundTotal by remember {
        mutableStateOf(fundTotalDB)
    }
    var fundList: List<Fund> = appDB.fundDAO().getFundByTeamID(teamID)
    var deletedItem = remember {
        mutableStateListOf<Fund>()
    }
    var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Quản lý quỹ", fontSize = 18.sp)
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
            var (col1, col2, col3) = createRefs()
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
                        text = "Lịch sử thu chi",
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
                        text = "Số hoạt động",
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
                            text = fundTotal.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    IconButton(onClick = {
//                        openUpdateMemberScreen(teamID, 0)
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
                        items = fundList,
                        itemContent = { _, item ->
                            AnimatedVisibility(
                                visible = !deletedItem.contains(item),
                                enter = expandVertically(),
                                exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 20.dp)
                                ) {
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
                                                    if (item.collectedFund != 0) {
                                                        Text(
                                                            text = "Thu: ${item.collectedFund}",
                                                            color = Color.Blue,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    else{
                                                        Text(
                                                            text = "Chi: ${item.spentFund}",
                                                            color = Color.Red,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    Text(
                                                        text = "${item.contentFund}",
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(text = "${item.time}, ${item.date.format(dateFormatter)}")
                                                }
                                            }
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Absolute.Right
                                            ) {
                                                IconButton(onClick = {
//                                                    openUpdateMemberScreen(teamID, item.memberID)
                                                }) {
                                                    Icon(
                                                        Icons.Default.Edit,
                                                        contentDescription = null,
                                                        tint = Color.Blue
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    GlobalScope.launch(Dispatchers.IO) {
                                                        appDB.fundDAO().deleteFund(
                                                            Fund(
                                                                item.fundID,
                                                                item.teamID,
                                                                item.contentFund,
                                                                item.collectedFund,
                                                                item.spentFund,
                                                                item.time,
                                                                item.date
                                                            )
                                                        )
                                                        withContext(Dispatchers.Main) {
                                                            deletedItem.add(item)
                                                            fundTotal--
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .constrainAs(col3) {
                        top.linkTo(col2.bottom)
                        start.linkTo(parent.start)
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(onClick = {
//                    openResultMatchChartScreen(teamID)
                }) {
                    Text(
                        text = "Thống kê quỹ",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
        }
    }
}