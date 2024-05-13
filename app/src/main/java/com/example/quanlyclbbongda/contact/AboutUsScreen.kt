package com.example.quanlyclbbongda.contact

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.quanlyclbbongda.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(backHomeScreen: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Về chúng tôi", fontSize = 18.sp)
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
            var (txtTieuDe, txtNoiDung1, txtNoiDung2, txtNoiDung3, imgLogo) = createRefs()

            Text(text = "Chào mừng đến với Football Club Management!",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .constrainAs(txtTieuDe) {
                        top.linkTo(parent.top, margin = 20.dp)
                        start.linkTo(parent.start)
                    },
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                text = "Nền tảng quản lý câu lạc bộ bóng đá hàng đầu giúp bạn nắm bắt mọi khía cạnh của đội bóng yêu quý. " +
                        "Từ lịch trình thi đấu, quản lý cầu thủ, đến phân tích chiến thuật, " +
                        "Football Club Management là trợ lý không thể thiếu cho mọi huấn luyện viên.",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(txtNoiDung1) {
                        top.linkTo(txtTieuDe.bottom)
                        start.linkTo(txtTieuDe.start)
                    },
                textAlign = TextAlign.Justify,
                color = Color.Black
            )

            Text(
                text = "Đơn giản hóa quản lý câu lạc bộ bóng đá của bạn với Football Club Management. " +
                        "Tất cả thông tin bạn cần, từ thống kê trận đấu đến tài chính câu lạc bộ, " +
                        "đều được tổ chức gọn gàng và dễ truy cập ngay trên điện thoại của bạn.",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(txtNoiDung2) {
                        top.linkTo(txtNoiDung1.bottom)
                        start.linkTo(txtTieuDe.start)
                    },
                textAlign = TextAlign.Justify,
                color = Color.Black
            )

            Text(
                text = "Biến mỗi quyết định thành chiến thắng với Football Club Management. " +
                        "Phát huy tối đa tiềm năng của đội bóng với công cụ phân tích dữ liệu tiên tiến " +
                        "và tính năng tùy chỉnh linh hoạt, giúp câu lạc bộ của bạn luôn dẫn đầu.",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .constrainAs(txtNoiDung3) {
                        top.linkTo(txtNoiDung2.bottom)
                        start.linkTo(txtTieuDe.start)
                    },
                textAlign = TextAlign.Justify,
                color = Color.Black
            )

            Image(painter = painterResource(id = R.drawable.icon_app_removebg), contentDescription = null,
                modifier = Modifier.height(125.dp).constrainAs(imgLogo){
                    top.linkTo(txtNoiDung3.bottom, margin = 25.dp)
                })
        }
    }
}

@Preview
@Composable
private fun AboutUsScreenPreview() {
    AboutUsScreen(backHomeScreen = {})
}