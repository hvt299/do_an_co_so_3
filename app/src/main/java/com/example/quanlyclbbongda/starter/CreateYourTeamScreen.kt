package com.example.quanlyclbbongda.starter

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.data.models.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateYourTeamScreen(userEmail: String, openHomeScreen: (userEmail: String, teamID: Int) -> Unit, backLoginScreen: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Tạo đội bóng của bạn", fontSize = 18.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backLoginScreen()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
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
        ) {
            Image(
                painterResource(
                    id = R.drawable.background_app
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
                modifier = Modifier.matchParentSize()
            )
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            var (txtTieuDe, tfTenDoiBong, exDropDownMenuBox, tfDiaChi, btnTiepTuc) = createRefs()
            var tendoibong by remember {
                mutableStateOf("")
            }
//            var loaidoihinhsan by remember {
//                mutableStateOf("")
//            }
            var diachi by remember {
                mutableStateOf("")
            }

            var cacloaisan = arrayOf("Sân 11", "Sân 7", "Sân 5")
            var expanded by remember {
                mutableStateOf(false)
            }
            var selectedText by remember {
                mutableStateOf("")
            }

            var keyboardController = LocalSoftwareKeyboardController.current
            var focusManager = LocalFocusManager.current
            var context = LocalContext.current
            var appDB: AppDatabase = AppDatabase.getDatabase(context)

            var horizontalGuideLine = createGuidelineFromTop(0.25f)

            var maxTeamNameLength = 50
            var maxPitchSize = 2
            var maxAddressLength = 100

            Text(
                text = "TẠO ĐỘI BÓNG CỦA BẠN",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF97E7F5)
                ),
                modifier = Modifier.constrainAs(txtTieuDe) {
                    top.linkTo(horizontalGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            TextField(
                value = tendoibong,
                onValueChange = {
                    if (it.length <= maxTeamNameLength) tendoibong = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Tên đội bóng")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color(0xFF97E7F5),
                    unfocusedTextColor = Color(0xFF97E7F5),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfTenDoiBong) {
                        top.linkTo(txtTieuDe.bottom, margin = 25.dp)
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
                ),
                maxLines = 2
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier.constrainAs(exDropDownMenuBox) {
                    top.linkTo(tfTenDoiBong.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {
//                        if (it.length <= maxPitchSize) loaidoihinhsan = it
                    },
                    readOnly = true,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                    ),
                    placeholder = {
                        Text(text = "Loại đội hình sân")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color(0xFF97E7F5),
                        unfocusedTextColor = Color(0xFF97E7F5),
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        focusedPlaceholderColor = Color.White,
                        unfocusedPlaceholderColor = Color.White,
                        focusedTrailingIconColor = Color.White,
                        unfocusedTrailingIconColor = Color.White,
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .width(350.dp)
                        .menuAnchor(),
//                        .constrainAs(tfLoaiDoiHinhSan) {
//                            top.linkTo(tfTenDoiBong.bottom, margin = 25.dp)
//                            start.linkTo(parent.start)
//                            end.linkTo(parent.end)
//                        },
//                    keyboardOptions = KeyboardOptions(
//                        imeAction = ImeAction.Next,
//                        keyboardType = KeyboardType.Number
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onNext = {
//                            focusManager.moveFocus(FocusDirection.Down)
//                        }
//                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cacloaisan.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = diachi,
                onValueChange = {
                    if (it.length <= maxAddressLength) diachi = it
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(text = "Địa chỉ")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color(0xFF97E7F5),
                    unfocusedTextColor = Color(0xFF97E7F5),
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedPlaceholderColor = Color.White,
                    unfocusedPlaceholderColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                modifier = Modifier
                    .width(350.dp)
                    .constrainAs(tfDiaChi) {
                        top.linkTo(exDropDownMenuBox.bottom, margin = 25.dp)
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
                ),
                maxLines = 2
            )

            Button(
                onClick = {
                    if (!tendoibong.isNullOrEmpty() && !selectedText.isNullOrEmpty() && !diachi.isNullOrEmpty()) {
                        var team = Team(0, userEmail, tendoibong, selectedText, diachi)
                        GlobalScope.launch(Dispatchers.IO) {
                            appDB.teamDAO().insertTeam(team)
                            withContext(Dispatchers.Main){
                                var teamID = appDB.teamDAO().findByEmail(userEmail).teamID
                                Toast.makeText(context, "Tạo đội bóng thành công", Toast.LENGTH_SHORT).show()
                                openHomeScreen(userEmail, teamID)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .width(160.dp)
                    .constrainAs(btnTiepTuc) {
                        top.linkTo(tfDiaChi.bottom, margin = 25.dp)
                        end.linkTo(tfDiaChi.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xff37371f)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(Icons.Default.ArrowForward, contentDescription = null)
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(text = "Tiếp tục", fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreateYourTeamScreenPreview() {
    CreateYourTeamScreen(userEmail = "", openHomeScreen = {userEmail, teamID ->  }, backLoginScreen = {})
}