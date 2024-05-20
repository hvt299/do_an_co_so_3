package com.example.quanlyclbbongda.fund

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
import androidx.compose.foundation.pager.rememberPagerState
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
import com.example.quanlyclbbongda.data.models.Fund
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
fun UpdateFundScreen(teamID: Int, fundID: Int, backFundManagementScreen: () -> Unit) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var updateFund = appDB.fundDAO().findByTeamIDFundID(teamID, fundID)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (fundID == 0) "Thêm hoạt động" else "Chỉnh sửa lịch sử thu chi",
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backFundManagementScreen()
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
            var (tfTime, tfDate, tfContentFund, tfAmountOfMoney, rowTypeOfFund, btnThayDoi) = createRefs()
            var time by remember {
                mutableStateOf(if (fundID == 0) "" else updateFund.time.toString())
            }
            var date by remember {
                mutableStateOf(
                    if (fundID == 0) "" else updateFund.date.format(
                        dateFormatter
                    ).toString()
                )
            }
            var contentFund by remember {
                mutableStateOf(if (fundID == 0) "" else updateFund.contentFund)
            }
            var amountOfMoney by remember{
                mutableStateOf(if (fundID == 0) "" else updateFund.amountOfMoney.toString())
            }
            var isCollection by remember{
                mutableStateOf(if (fundID != 0) (if (updateFund.typeOfFund == 0) true else false) else false)
            }
            var isSpent by remember{
                mutableStateOf(if (fundID != 0) (if (updateFund.typeOfFund == 1) true else false) else false)
            }

            var keyboardController = LocalSoftwareKeyboardController.current
            var focusManager = LocalFocusManager.current
            var maxTeamNameLength = 50

            TextField(
                value = time,
                onValueChange = {
                    if (it.length <= 5) time = it
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
                    .constrainAs(tfTime) {
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
                value = date,
                onValueChange = {
                    if (it.length <= 10) date = it
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
                    .constrainAs(tfDate) {
                        top.linkTo(tfTime.bottom, margin = 25.dp)
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
                value = amountOfMoney,
                onValueChange = {
                    amountOfMoney = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Nhập số tiền: ")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfAmountOfMoney) {
                        top.linkTo(tfDate.bottom, margin = 25.dp)
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
                value = contentFund,
                onValueChange = {
                    contentFund = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Nhập nội dung")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfContentFund) {
                        top.linkTo(tfAmountOfMoney.bottom, margin = 25.dp)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .constrainAs(rowTypeOfFund) {
                        top.linkTo(tfContentFund.bottom, margin = 25.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            ) {
                Row(
                    modifier = Modifier.selectable(
                        selected = isCollection,
                        onClick = { // default is collection
                            isCollection = !isCollection
                            isSpent = false
                        },
                        role = Role.RadioButton

                    )
                ) {
                    RadioButton(
                        selected = isCollection,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Thu", modifier = Modifier.padding(horizontal = 10.dp))
                }

                Row(
                    modifier = Modifier.selectable(
                        selected = isSpent,
                        onClick = {
                            isCollection = false
                            isSpent = !isSpent
                        },
                        role = Role.RadioButton

                    )
                ) {
                    RadioButton(
                        selected = isSpent,
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Red,
                            unselectedColor = Color.Black,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Text(text = "Chi", modifier = Modifier.padding(horizontal = 10.dp))
                }

            }
            Button(
                onClick = { // sv Thai
                    if (!time.isNullOrEmpty() && !date.isNullOrEmpty() && !amountOfMoney.isNullOrEmpty() && !contentFund.isNullOrEmpty()) {
                        var typeOfFund = if (isCollection == true) 0 else 1
                        if (fundID == 0) {
                            var fund = Fund(
                                0,
                                teamID,
                                amountOfMoney.toInt(),
                                contentFund,
                                typeOfFund,
                                LocalTime.parse(time),
                                LocalDate.parse(date, dateFormatter)
                            )
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.fundDAO().insertFund(fund)
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT)
                                        .show()
                                    backFundManagementScreen()
                                }
                            }
                        } else {
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.fundDAO().updateFund(
                                    amountOfMoney.toInt(),
                                    contentFund,
                                    typeOfFund,
                                    LocalTime.parse(time),
                                    LocalDate.parse(date, dateFormatter),
                                    fundID,
                                    teamID
                                )
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "Chỉnh sửa thành công",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        backFundManagementScreen()
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
                        top.linkTo(rowTypeOfFund.bottom, margin = 25.dp)
                        end.linkTo(parent.end)
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
                        if (fundID == 0) Icons.Default.Add else Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpdateFundScreenPreview() {
    UpdateFundScreen(teamID = 0, fundID = 0, backFundManagementScreen = {})
}