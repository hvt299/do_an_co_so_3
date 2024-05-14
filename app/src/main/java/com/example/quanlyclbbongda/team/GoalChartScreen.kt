package com.example.quanlyclbbongda.team

import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.PieChartData
import com.example.quanlyclbbongda.ui.theme.blueColor
import com.example.quanlyclbbongda.ui.theme.greenColor
import com.example.quanlyclbbongda.ui.theme.lightgrayColor
import com.example.quanlyclbbongda.ui.theme.redColor
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalChartScreen(teamID: Int, backTeamManagementScreen: () -> Unit, backResultMatchChartScreen: () -> Unit){
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var scheduleList = appDB.scheduleDAO().findByTeamID(teamID)
    var goalTotal = 0
    var lostGoalTotal = 0
    for (i in scheduleList.indices) {
        if (scheduleList[i].teamResultMatch == 1) {
            if (Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString()) > Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())) {
                goalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
                lostGoalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())
            } else {
                goalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())
                lostGoalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
            }
        } else if (scheduleList[i].teamResultMatch == 3) {
            if (Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString()) < Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())){
                goalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
                lostGoalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())
            } else {
                goalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(2).toString())
                lostGoalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
            }
        } else if (scheduleList[i].teamResultMatch == 2) {
            goalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
            lostGoalTotal += Integer.parseInt(scheduleList[i].scoreMatch.get(0).toString())
        }
    }
    var total = goalTotal + lostGoalTotal
    var getPieChartData = listOf(
        PieChartData("Bàn thắng [$goalTotal]", goalTotal * 100 / total.toFloat()),
        PieChartData("Bàn thua [$lostGoalTotal]", lostGoalTotal * 100 / total.toFloat()),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Thống kê số bàn thắng thua", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backTeamManagementScreen()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar() {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                              backResultMatchChartScreen()
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sports_soccer_24),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Kết quả")
                    }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    label = {
                        Text("Ghi bàn")
                    }
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.White)
        ) {}
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            PieChart("", getPieChartData)
//            LazyColumn(
//                modifier = Modifier
//                    .height(600.dp)
//                    .padding(vertical = 4.dp)
//            ) {
//                itemsIndexed(
//                    items = memberList,
//                    itemContent = { _, item ->
//                        AnimatedVisibility(
//                            visible = !deletedItem.contains(item),
//                            enter = expandVertically(),
//                            exit = shrinkVertically(animationSpec = tween(durationMillis = 1000))
//                        ) {
//                            val call = SwipeAction(
//                                onSwipe = {
//                                    Log.d("aaa", "Call")
//                                    val u = Uri.parse("tel:" + item.memberPhone)
//                                    val i = Intent(Intent.ACTION_DIAL, u)
//                                    try {
//                                        context.startActivity(i)
//                                    } catch (s: SecurityException) {
//                                        Toast.makeText(
//                                            context,
//                                            "An error occurred",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                },
//                                icon = {
//                                    Icon(
//                                        Icons.Default.Call,
//                                        contentDescription = null,
//                                        modifier = Modifier.padding(16.dp),
//                                        tint = Color.White
//                                    )
//                                },
//                                background = Color.Green
//                            )
//
//                            val message = SwipeAction(
//                                onSwipe = {
//                                    Log.d("aaa", "Message")
//                                    val sms_uri = Uri.parse("smsto:${item.memberPhone}")
//                                    val sms_intent = Intent(Intent.ACTION_SENDTO, sms_uri)
//                                    sms_intent.putExtra(
//                                        "sms_body",
//                                        "Hello ${item.memberName}! How are you today?"
//                                    )
//                                    context.startActivity(sms_intent)
//                                },
//                                icon = {
//                                    Icon(
//                                        Icons.Default.Email,
//                                        contentDescription = null,
//                                        modifier = Modifier.padding(16.dp),
//                                        tint = Color.White
//                                    )
//                                },
//                                background = Color.Blue
//                            )
//                            SwipeableActionsBox(
//                                swipeThreshold = 50.dp,
//                                startActions = listOf(call),
//                                endActions = listOf(message),
//                                modifier = Modifier.padding(vertical = 20.dp)
//                            ) {
//                                ConstraintLayout(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(80.dp)
//                                        .background(Color.White)
//                                ) {
//                                    var (imgAvatar, txtHoTen, txtSdt) = createRefs()
//                                    Image(
//                                        painter = painterResource(id = R.drawable.icon_app_removebg),
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .height(60.dp)
//                                            .constrainAs(imgAvatar) {
//                                                top.linkTo(parent.top, margin = 10.dp)
//                                                start.linkTo(parent.start, margin = 10.dp)
//                                            }
//                                            .border(1.dp, Color.White, CircleShape)
//                                            .background(Color.White, CircleShape)
//                                    )
//                                    Text(
//                                        text = item.memberName,
//                                        fontSize = 16.sp,
//                                        color = Color.Black,
//                                        fontWeight = FontWeight.SemiBold,
//                                        modifier = Modifier.constrainAs(txtHoTen) {
//                                            top.linkTo(parent.top, margin = 10.dp)
//                                            start.linkTo(imgAvatar.end, margin = 10.dp)
//                                        }
//                                    )
//                                    Text(
//                                        text = item.memberPhone,
//                                        fontSize = 16.sp,
//                                        color = Color.Black,
//                                        modifier = Modifier.constrainAs(txtSdt) {
//                                            top.linkTo(txtHoTen.bottom, margin = 10.dp)
//                                            start.linkTo(imgAvatar.end, margin = 10.dp)
//                                        }
//                                    )
//                                }
//                            }
//                        }
//                    })
//            }
        }
    }
}

@Preview
@Composable
private fun GoalChartScreenPreview() {
    GoalChartScreen(teamID = 0, backTeamManagementScreen = {}, backResultMatchChartScreen = {})
}