package com.example.quanlyclbbongda.team

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
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
fun UpdateMemberScreen(teamID: Int, memberID: Int, backTeamManagementScreen: () -> Unit) {
    var context = LocalContext.current
    var appDB: AppDatabase = AppDatabase.getDatabase(context)
    var updateMember = appDB.memberDAO().findByTeamIDMemberID(teamID, memberID)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (memberID == 0) "Thêm thành viên" else "Chỉnh sửa thành viên", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backTeamManagementScreen()
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
            var (tfTenThanhVien, exDropDownMenuBox, tfSoAo, tfSoDienThoai, ckbDoiTruong, btnThayDoi, btnBoQua) = createRefs()
            var tenthanhvien by remember {
                mutableStateOf(if (memberID == 0) "" else updateMember.memberName)
            }
            var loaivitri = arrayOf(
                "Thủ môn",
                "Hậu vệ",
                "Trung vệ",
                "Tiền vệ",
                "Tiền đạo",
                "Huấn luyện viên",
                "Ban huấn luyện"
            )
            var colordropdownmenu_indicator by remember {
                mutableStateOf(Color(0xFF08A045))
            }
            var expanded by remember {
                mutableStateOf(false)
            }
            var selectedText by remember {
                mutableStateOf(if (memberID == 0) "" else updateMember.memberPosition)
            }
            var soao by remember {
                mutableStateOf(if (memberID == 0) "" else updateMember.memberJerseyNumber.toString())
            }
            var sodienthoai by remember {
                mutableStateOf(if (memberID == 0) "" else updateMember.memberPhone)
            }
            var doitruong by remember {
                mutableStateOf(if (memberID == 0) false else updateMember.isCaptain)
            }

            var keyboardController = LocalSoftwareKeyboardController.current
            var focusManager = LocalFocusManager.current

            var horizontalGuideLine = createGuidelineFromTop(0.18f)

            var maxMemberNameLength = 50
            var maxJerseyNumber = 2
            var maxPhoneLength = 10

            TextField(
                value = tenthanhvien,
                onValueChange = {
                    if (it.length <= maxMemberNameLength) tenthanhvien = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Tên thành viên")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
//                    focusedIndicatorColor = Color.White,
//                    unfocusedIndicatorColor = Color.White,
//                    focusedPlaceholderColor = Color.White,
//                    unfocusedPlaceholderColor = Color.White,
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfTenThanhVien) {
                        top.linkTo(parent.top)
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

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(exDropDownMenuBox) {
                        top.linkTo(tfTenThanhVien.bottom, margin = 25.dp)
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
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                    ),
                    placeholder = {
                        Text(text = "Vị trí")
                    },
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
                    loaivitri.forEach { item ->
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
                value = soao,
                onValueChange = {
                    if (it.length <= maxJerseyNumber) soao = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Số áo (tùy chọn)")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
//                    focusedIndicatorColor = Color.White,
//                    unfocusedIndicatorColor = Color.White,
//                    focusedPlaceholderColor = Color.White,
//                    unfocusedPlaceholderColor = Color.White,
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfSoAo) {
                        top.linkTo(exDropDownMenuBox.bottom, margin = 25.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                )
            )

            TextField(
                value = sodienthoai,
                onValueChange = {
                    if (it.length <= maxPhoneLength) sodienthoai = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Số điện thoại")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
//                    focusedIndicatorColor = Color.White,
//                    unfocusedIndicatorColor = Color.White,
//                    focusedPlaceholderColor = Color.White,
//                    unfocusedPlaceholderColor = Color.White,
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfSoDienThoai) {
                        top.linkTo(tfSoAo.bottom, margin = 25.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
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
                    .selectable(
                        selected = doitruong,
                        onClick = {
                            doitruong = !doitruong
                        },
                        role = Role.Checkbox
                    )
                    .constrainAs(ckbDoiTruong) {
                        top.linkTo(tfSoDienThoai.bottom, margin = 35.dp)
                        start.linkTo(tfSoDienThoai.start)
                    }
            ) {
                var icon =
                    if (doitruong) R.drawable.baseline_check_box_24 else R.drawable.baseline_check_box_outline_blank_24
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Black
                )
                Text(
                    text = "Đội trưởng",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.Black
                )
            }

            Button(
                onClick = {
                    if (!tenthanhvien.isNullOrEmpty() && !selectedText.isNullOrEmpty() && !soao.isNullOrEmpty() && !sodienthoai.isNullOrEmpty()){
                        if (memberID == 0) {
                            var member = Member(0, teamID, tenthanhvien, selectedText, doitruong, soao.toInt(), sodienthoai)
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.memberDAO().insertMember(member)
                                withContext(Dispatchers.Main){
                                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                                    backTeamManagementScreen()
                                }
                            }
                        } else {
                            GlobalScope.launch(Dispatchers.IO) {
                                appDB.memberDAO().updateMember(tenthanhvien, selectedText, doitruong, soao.toInt(), sodienthoai, memberID)
                                withContext(Dispatchers.Main){
                                    Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show()
                                    backTeamManagementScreen()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(80.dp)
                    .constrainAs(btnThayDoi) {
                        top.linkTo(tfSoDienThoai.bottom, margin = 25.dp)
                        end.linkTo(tfSoDienThoai.end)
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
                    Image(if (memberID == 0) Icons.Default.Add else Icons.Default.Edit, contentDescription = null)
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpdateMemberScreenPreview() {
    UpdateMemberScreen(teamID = 0, memberID = 0, backTeamManagementScreen = {})
}