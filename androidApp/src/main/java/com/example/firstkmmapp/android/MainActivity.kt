package com.example.firstkmmapp.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstkmmapp.Greeting
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.firstkmmapp.api.TestHttpApi
import com.example.firstkmmapp.api.TestHttpApiImpl
import com.example.firstkmmapp.data.*
import com.example.firstkmmapp.repository.UserRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun greet(): String {
    return Greeting().greeting()
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            UsersPage()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class,
   ExperimentalFoundationApi::class
)
@Composable
fun UsersPage(vm: MainViewModel = hiltViewModel()){

    val context = LocalContext.current
    Surface() {
        LazyColumn(){
            items(vm.users){ user ->
                UserCard(user = user, onClick = {
                    Toast.makeText(context, "onClick user = ${user.name}", Toast.LENGTH_SHORT).show()
                }) {
                    vm.deleteUser(user.id)
                    Toast.makeText(context, "onLongClick user = ${user.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun UserCard(user: User, onClick: () -> Unit, onLongClick: () -> Unit ){
    var showList by remember{ mutableStateOf(false) }
    var currentState by remember { mutableStateOf(false) }
    val value by animateFloatAsState(
        targetValue = if(currentState) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        )
    )
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier
            .testTag("UserCard")
            .combinedClickable(onLongClick = {
                onLongClick()
            },
                onClick = { onClick() })) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = user.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .testTag("name"),
                    fontSize = 20.sp
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .clickable {
                            currentState = !currentState
                            showList = !showList
                        }
                        .rotate(value))
            }

            AnimatedVisibility(visible = showList,
                enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(expandFrom = Alignment.Top
                ) + fadeIn(), exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Column() {
                    Text(text = user.email, modifier = Modifier.testTag("email"))
                    Text(text = user.phone, modifier = Modifier.testTag("phone"))
                    Text(text = user.website, modifier = Modifier.testTag("website"))
                    Text(text = user.address.toString(), modifier = Modifier.testTag("address"))
                }
            }
        }
    }
}