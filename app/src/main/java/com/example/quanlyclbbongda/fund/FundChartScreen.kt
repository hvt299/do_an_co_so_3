package com.example.quanlyclbbongda.fund

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.PieChartData
import com.example.quanlyclbbongda.team.PieChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FundChartScreen(teamID: Int, backFundManagementScreen: () -> Unit){
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var fundList = appDB.fundDAO().getFundByTeamID(teamID)
    var revenueTotal = 0
    var expenseTotal = 0
    for (i in fundList.indices) {
        if (fundList[i].typeOfFund == 0) {
            revenueTotal += Integer.parseInt(fundList[i].amountOfMoney.toString())
        } else {
            expenseTotal += Integer.parseInt(fundList[i].amountOfMoney.toString())
        }
    }
    var total = revenueTotal + expenseTotal
    var getPieChartData = listOf(
        PieChartData("Doanh thu [$revenueTotal]", revenueTotal * 100 / total.toFloat()),
        PieChartData("Chi phí [$expenseTotal]", expenseTotal * 100 / total.toFloat()),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Thống kê quỹ", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backFundManagementScreen()
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
                    onClick = {},
                    icon = {
                        Icon(painter = painterResource(id = R.drawable.baseline_attach_money_24), contentDescription = null)
                    },
                    label = {
                        Text("Thống kê quỹ")
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
        }
    }
}

@Preview
@Composable
private fun FundChartScreenPreview() {
    FundChartScreen(teamID = 0, backFundManagementScreen = {})
}