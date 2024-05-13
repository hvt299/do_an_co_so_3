package com.example.quanlyclbbongda.login_signup

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.quanlyclbbongda.data.AppDatabase
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.data.models.Team
import com.example.quanlyclbbongda.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(openSignUpScreen: () -> Unit, openCreateYourTeamScreen: (email: String) -> Unit, openHomeScreen: (userEmail: String, teamID: Int) -> Unit) {
    val brightness = -50f
    val colorMatrix = floatArrayOf(
        1f, 0f, 0f, 0f, brightness,
        0f, 1f, 0f, 0f, brightness,
        0f, 0f, 1f, 0f, brightness,
        0f, 0f, 0f, 1f, 0f
    )
    Box(modifier = Modifier.fillMaxSize()) {
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
            .verticalScroll(rememberScrollState())
    ) {
        var (txtTieuDe, tfTaiKhoan, tfMatKhau, btnDangNhap, txtGhiChu, txtDangKy) = createRefs()
        var taikhoan by rememberSaveable {
            mutableStateOf("")
        }
        var matkhau by rememberSaveable {
            mutableStateOf("")
        }
        var anhienmatkhau by rememberSaveable {
            mutableStateOf(false)
        }

        var keyboardController = LocalSoftwareKeyboardController.current
        var focusManager = LocalFocusManager.current
        var context = LocalContext.current
        var appDB: AppDatabase = AppDatabase.getDatabase(context)

        var horizontalGuideLine = createGuidelineFromTop(0.3f)
        var horizontalChain =
            createHorizontalChain(txtGhiChu, txtDangKy, chainStyle = ChainStyle.Packed)

        var maxEmailLength = 35
        var maxPasswordLength = 35

        Text(
            text = "ĐĂNG NHẬP TÀI KHOẢN",
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
            value = taikhoan,
            onValueChange = {
                if (it.length <= maxEmailLength) taikhoan = it
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            label = {
                Text(text = "Email của bạn")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color(0xFF97E7F5),
                unfocusedTextColor = Color(0xFF97E7F5),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            modifier = Modifier
                .width(350.dp)
                .constrainAs(tfTaiKhoan) {
                    top.linkTo(txtTieuDe.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
//            supportingText = {
//                Text(
//                    text = "${taikhoan.length} / $maxEmailLength",
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.End
//                )
//            },
            maxLines = 1
        )

        TextField(value = matkhau,
            onValueChange = {
                if (it.length <= maxPasswordLength) matkhau = it
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
            ),
            label = {
                Text(text = "Mật khẩu")
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color(0xFF97E7F5),
                unfocusedTextColor = Color(0xFF97E7F5),
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White,
                focusedLeadingIconColor = Color.White,
                unfocusedLeadingIconColor = Color.White
            ),
            modifier = Modifier
                .width(350.dp)
                .constrainAs(tfMatKhau) {
                    top.linkTo(tfTaiKhoan.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = {
                    anhienmatkhau = !anhienmatkhau
                }) {
                    var id =
                        if (anhienmatkhau) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = null
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
//            supportingText = {
//                Text(
//                    text = "${matkhau.length} / $maxPasswordLength",
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.End
//                )
//            },
            visualTransformation = if (anhienmatkhau) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = {
                if (!taikhoan.isNullOrEmpty() && !matkhau.isNullOrEmpty()) {
                    var user: User? = null
                    var team: Team? = null
                    GlobalScope.launch() {
                        user = appDB.userDAO().findByEmailPassword(taikhoan, matkhau)
                        withContext(Dispatchers.Main){
                            if (user != null) {
                                team = appDB.teamDAO().findByEmail(taikhoan)
                                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                matkhau = ""
                                if (team != null) {
                                    openHomeScreen(taikhoan, team!!.teamID)
                                } else {
                                    openCreateYourTeamScreen(taikhoan)
                                }
                            } else {
                                Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.constrainAs(btnDangNhap) {
                top.linkTo(tfMatKhau.bottom, margin = 30.dp)
                start.linkTo(tfTaiKhoan.start)
                end.linkTo(tfTaiKhoan.end)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffea9010),
                contentColor = Color(0xff37371f)
            ),
            border = BorderStroke(1.dp, color = Color.Transparent),
            shape = RoundedCornerShape(8.dp),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text(
                text = "Đăng nhập",
                fontSize = 18.sp
            )
        }

        Text(text = "Chưa có tài khoản? ",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.constrainAs(txtGhiChu) {
                top.linkTo(btnDangNhap.bottom, margin = 25.dp)
                horizontalChain
            })
        Text(text = "Đăng kí ngay!",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .constrainAs(txtDangKy) {
                    top.linkTo(btnDangNhap.bottom, margin = 25.dp)
                    horizontalChain
                }
                .clickable {
                    matkhau = ""
                    openSignUpScreen()
                })
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(openSignUpScreen = {}, openCreateYourTeamScreen = {}, openHomeScreen = {userEmail: String, teamID: Int ->  })
}