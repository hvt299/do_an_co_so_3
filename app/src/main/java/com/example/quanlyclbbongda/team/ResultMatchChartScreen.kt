package com.example.quanlyclbbongda.team

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
import com.example.quanlyclbbongda.ui.theme.yellowColor
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultMatchChartScreen(teamID: Int, backTeamManagementScreen: () -> Unit) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var matchTotal = appDB.scheduleDAO().getTeamMatchTotalByTeamID(teamID)
    var winMatchTotal = appDB.scheduleDAO().getTeamResultMatchTotalByTeamID(teamID, 1)
    var drawMatchTotal = appDB.scheduleDAO().getTeamResultMatchTotalByTeamID(teamID, 2)
    var loseMatchTotal = appDB.scheduleDAO().getTeamResultMatchTotalByTeamID(teamID, 3)
    var unknownMatchTotal = matchTotal - winMatchTotal - drawMatchTotal - loseMatchTotal
    var getPieChartData = listOf(
        PieChartData("Thắng [$winMatchTotal]", winMatchTotal * 100 / matchTotal.toFloat()),
        PieChartData("Thua [$loseMatchTotal]", loseMatchTotal * 100 / matchTotal.toFloat()),
        PieChartData("Hòa [$drawMatchTotal]", drawMatchTotal * 100 / matchTotal.toFloat()),
        PieChartData("Chưa xác định [$unknownMatchTotal]", unknownMatchTotal * 100 / matchTotal.toFloat())
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Thống kê kết quả thi đấu", fontSize = 18.sp)
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
                    selected = true,
                    onClick = {
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
                    selected = false,
                    onClick = {
//                        openScheduleScreen(userEmail, teamID)
                    },
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

// on below line we are creating a
// pie chart function on below line.
@Composable
fun PieChart(title: String, getPieChartData: List<PieChartData>) {
    // on below line we are creating a column
    // and specifying a modifier as max size.
    Column(modifier = Modifier.fillMaxSize()) {
        // on below line we are again creating a column
        // with modifier and horizontal and vertical arrangement
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // on below line we are creating a simple text
            // and specifying a text as Web browser usage share
            Text(
                text = title,

                // on below line we are specifying style for our text
                style = TextStyle.Default,

                // on below line we are specifying font family.
//                fontFamily = FontFamily().Default,

                // on below line we are specifying font style
                fontStyle = FontStyle.Normal,

                // on below line we are specifying font size.
                fontSize = 20.sp
            )

            // on below line we are creating a column and
            // specifying the horizontal and vertical arrangement
            // and specifying padding from all sides.
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(320.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // on below line we are creating a cross fade and
                // specifying target state as pie chart data the
                // method we have created in Pie chart data class.
                Crossfade(targetState = getPieChartData) { pieChartData ->
                    // on below line we are creating an
                    // android view for pie chart.
                    AndroidView(factory = { context ->
                        // on below line we are creating a pie chart
                        // and specifying layout params.
                        PieChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                // on below line we are specifying layout
                                // params as MATCH PARENT for height and width.
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            // on below line we are setting description
                            // enables for our pie chart.
                            this.description.isEnabled = false

                            // on below line we are setting draw hole
                            // to false not to draw hole in pie chart
                            this.isDrawHoleEnabled = false

                            // on below line we are enabling legend.
                            this.legend.isEnabled = true

                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.textSize = 14F

                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER

                            // on below line we are specifying entry label color as white.
                            this.setEntryLabelColor(resources.getColor(R.color.white))
                        }
                    },
                        // on below line we are specifying modifier
                        // for it and specifying padding to it.
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {
                            // on below line we are calling update pie chart
                            // method and passing pie chart and list of data.
                            updatePieChartWithData(it, pieChartData)
                        })
                }
            }
        }
    }
}

// on below line we are creating a update pie
// chart function to update data in pie chart.
fun updatePieChartWithData(
    // on below line we are creating a variable
    // for pie chart and data for our list of data.
    chart: PieChart,
    data: List<PieChartData>
) {
    // on below line we are creating
    // array list for the entries.
    val entries = ArrayList<PieEntry>()

    // on below line we are running for loop for
    // passing data from list into entries list.
    for (i in data.indices) {
        val item = data[i]
        entries.add(PieEntry(item.value ?: 0.toFloat(), item.dataName ?: ""))
    }

    // on below line we are creating
    // a variable for pie data set.
    val ds = PieDataSet(entries, "")

    // on below line we are specifying color
    // int the array list from colors.
    ds.colors = arrayListOf(
        blueColor.toArgb(),
        redColor.toArgb(),
        greenColor.toArgb(),
        lightgrayColor.toArgb()
    )
    // on below line we are specifying position for value
    ds.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying position for value inside the slice.
    ds.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

    // on below line we are specifying
    // slice space between two slices.
    ds.sliceSpace = 2f

    // on below line we are specifying text color
    ds.valueTextColor = R.color.white

    // on below line we are specifying
    // text size for value.
    ds.valueTextSize = 18f

    // on below line we are specifying type face as bold.
    ds.valueTypeface = Typeface.DEFAULT_BOLD

    // on below line we are creating
    // a variable for pie data
    val d = PieData(ds)

    // on below line we are setting this
    // pie data in chart data.
    chart.data = d

    // on below line we are
    // calling invalidate in chart.
    chart.invalidate()
}

@Preview
@Composable
private fun ResultMatchChartScreenPreview() {
    ResultMatchChartScreen(teamID = 0, backTeamManagementScreen = {})
}