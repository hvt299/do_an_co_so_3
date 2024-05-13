package com.example.quanlyclbbongda.schedule

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.convertTo
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Member
import com.example.quanlyclbbongda.data.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScheduleScreen(teamID: Int, scheduleID: Int, backScheduleScreen: () -> Unit) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var updateSchedule = appDB.scheduleDAO().findByTeamIDScheduleID(teamID, scheduleID)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (scheduleID == 0) "Thêm lịch thi đấu" else "Chỉnh sửa lịch thi đấu",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backScheduleScreen()
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
            var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            var (tfThoiGian, tfNgay, tfTenDoiBong1, tfTenDoiBong2, tfTiSo, rowTeamResultMatch, btnThayDoi) = createRefs()
            var thoigian by remember {
                mutableStateOf(if (scheduleID == 0) "" else updateSchedule.scheduleTime.toString())
            }
            var ngay by remember {
                mutableStateOf(
                    if (scheduleID == 0) "" else updateSchedule.scheduleDate.format(
                        dateFormatter
                    ).toString()
                )
            }
            var tendoibong1 by remember {
                mutableStateOf(if (scheduleID == 0) "" else updateSchedule.firstTeamName)
            }
            var tendoibong2 by remember {
                mutableStateOf(if (scheduleID == 0) "" else updateSchedule.secondTeamName)
            }
            var tiso by remember {
                mutableStateOf(if (scheduleID == 0) "" else updateSchedule.scoreMatch)
            }
            var isSelectedWin by remember {
                mutableStateOf(if (scheduleID != 0) if (updateSchedule.teamResultMatch == 1) true else false else false)
            }
            var isSelectedDraw by remember {
                mutableStateOf(if (scheduleID != 0) if (updateSchedule.teamResultMatch == 2) true else false else false)
            }
            var isSelectedLose by remember {
                mutableStateOf(if (scheduleID != 0) if (updateSchedule.teamResultMatch == 3) true else false else false)
            }

            var keyboardController = LocalSoftwareKeyboardController.current
            var focusManager = LocalFocusManager.current
            var maxTeamNameLength = 50

            TextField(
                value = thoigian,
                onValueChange = {
                    if (it.length <= 5) thoigian = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Thời gian (ví dụ: 22:30)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfThoiGian) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text

                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            TextField(
                value = ngay,
                onValueChange = {
                    if (it.length <= 10) ngay = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Ngày (ví dụ: 12-05-2024)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfNgay) {
                        top.linkTo(tfThoiGian.bottom, margin = 25.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text

                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )
            TextField(
                value = tendoibong1,
                onValueChange = {
                    if (it.length <= maxTeamNameLength) tendoibong1 = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Tên đội bóng 1 (sân nhà)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfTenDoiBong1) {
                        top.linkTo(tfNgay.bottom, margin = 25.dp)
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
                )
            )
            TextField(
                value = tendoibong2,
                onValueChange = {
                    if (it.length <= maxTeamNameLength) tendoibong2 = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Tên đội bóng 2 (sân khách)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfTenDoiBong2) {
                        top.linkTo(tfTenDoiBong1.bottom, margin = 25.dp)
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
                )
            )
            TextField(
                value = tiso,
                onValueChange = {
                    if (it.length <= 5) tiso = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Tỉ số (ví dụ 1-0, 1-1, 0-0,...)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfTiSo) {
                        top.linkTo(tfTenDoiBong2.bottom, margin = 25.dp)
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
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .constrainAs(rowTeamResultMatch) {
                        top.linkTo(tfTiSo.bottom, margin = 25.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {
                Row(
                    modifier = Modifier.selectable(
                        selected = isSelectedWin,
                        onClick = {
                            isSelectedWin = !isSelectedWin
                            isSelectedDraw = false
                            isSelectedLose = false
                        },
                        role = Role.RadioButton

                    )
                ) {
                    RadioButton(
                        selected = isSelectedWin,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Thắng", modifier = Modifier.padding(horizontal = 10.dp))
                }

                Row(
                    modifier = Modifier.selectable(
                        selected = isSelectedDraw,
                        onClick = {
                            isSelectedWin = false
                            isSelectedDraw = !isSelectedDraw
                            isSelectedLose = false
                        },
                        role = Role.RadioButton

                    )
                ) {
                    RadioButton(
                        selected = isSelectedDraw,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Hòa", modifier = Modifier.padding(horizontal = 10.dp))
                }

                Row(
                    modifier = Modifier.selectable(
                        selected = isSelectedLose,
                        onClick = {
                            isSelectedWin = false
                            isSelectedDraw = false
                            isSelectedLose = !isSelectedLose
                        },
                        role = Role.RadioButton

                    )
                ) {
                    RadioButton(
                        selected = isSelectedLose,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Thua", modifier = Modifier.padding(horizontal = 10.dp))
                }
            }
            Button(
                onClick = {
                    if (!thoigian.isNullOrEmpty() && !ngay.isNullOrEmpty() && !tendoibong1.isNullOrEmpty() && !tendoibong2.isNullOrEmpty()) {
                        if (tiso.isNullOrEmpty()) tiso = "vs"
                        var ketqua =
                            if (isSelectedWin == true) 1 else if (isSelectedDraw == true) 2 else if (isSelectedLose == true) 3 else 0
                        if (scheduleID == 0) {
                            var schedule = Schedule(
                                0,
                                teamID,
                                LocalTime.parse(thoigian),
                                LocalDate.parse(ngay, dateFormatter),
                                tendoibong1,
                                tendoibong2,
                                tiso,
                                ketqua
                            )
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.scheduleDAO().insertSchedule(schedule)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT)
                                        .show()
                                    backScheduleScreen()
                                }
                            }
                        } else {
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.scheduleDAO().updateSchedule(
                                    LocalTime.parse(thoigian),
                                    LocalDate.parse(ngay, dateFormatter),
                                    tendoibong1,
                                    tendoibong2,
                                    tiso,
                                    ketqua,
                                    scheduleID
                                )
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Chỉnh sửa thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    backScheduleScreen()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Vui lòng nhập đầy đủ thông tin",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .width(80.dp)
                    .constrainAs(btnThayDoi) {
                        top.linkTo(rowTeamResultMatch.bottom, margin = 25.dp)
                        end.linkTo(tfTenDoiBong1.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffea9010),
                    contentColor = Color(0xff37371f)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        if (scheduleID == 0) Icons.Default.Add else Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpdateScheduleScreenPreview() {
    UpdateScheduleScreen(teamID = 0, scheduleID = 0, backScheduleScreen = {})
}