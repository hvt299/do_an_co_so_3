package com.example.quanlyclbbongda.lineup

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.quanlyclbbongda.R
import com.example.quanlyclbbongda.ui.theme.blueColor
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun LineUpScreen(){

    var initialPlayer = listOf(
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
        Player(R.drawable.odegaard, "Odegaard", 0, 0),
    )

    var players by remember {
        mutableStateOf(initialPlayer)
    }


    Box() {
        Image(painter = painterResource(id = R.drawable.lineupbackground), contentDescription = null, modifier = Modifier
            .fillMaxSize()
            .padding(15.dp))
        Player(R.drawable.odegaard, "Odegaard", 50, 200)
        Player(R.drawable.odegaard, "Odegaard", 50, 510)
        Player(R.drawable.odegaard, "Odegaard", -50, 510)
        Player(R.drawable.odegaard, "Odegaard", 130, 500)
        Player(R.drawable.odegaard, "Odegaard", -130, 500)
        Player(R.drawable.odegaard, "Odegaard", 0, 600)
        Player(R.drawable.odegaard, "Odegaard", -50, 400)
        Player(R.drawable.odegaard, "Odegaard", 50, 400)
        Player(R.drawable.odegaard, "Odegaard", -50, 200)
        Player(R.drawable.odegaard, "Odegaard", -130, 300)
        Player(R.drawable.odegaard, "Odegaard", 130, 300)
    }
}

@Composable
fun Player(image: Int, name: String, xPosition: Int, yPosition: Int){
    Box {
        Column(modifier = Modifier
            .fillMaxSize()
            .offset(xPosition.dp, yPosition.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier
                .size(35.dp, 35.dp)
                .clip(shape = CircleShape))
            Text(text = name, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LineUpScreenPreview(){
    LineUpScreen()
}