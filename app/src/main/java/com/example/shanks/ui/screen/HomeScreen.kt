package com.example.shanks.ui.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shanks.R
import com.example.shanks.data.model.Job
import com.example.shanks.data.model.JobEntity
import com.example.shanks.vm.JobViewModel
import com.example.shanks.ui.theme.LokalPrimaryColor
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    viewModel: JobViewModel,
    jobs: List<Job>,
    navController: NavHostController,
    isOnline: State<Boolean>
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        if (!isOnline.value) {
            Text(text = "Please connect to the internet!", color = Gray)
        } else {
            val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                if (isOnline.value) {
                    viewModel.fetchJobs()
                } else {
                    Toast.makeText(
                        context, "Please connect to the internet!", Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                LazyColumn(
                    modifier = Modifier.background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(jobs) { job ->
                        JobItem(job = job, viewModel = viewModel) {
                            if (isOnline.value) {
                                navController.navigate("details_screen/${job.id}")
                            } else {
                                Toast.makeText(
                                    context, "Please connect to the internet!", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun JobItem(job: Job, viewModel: JobViewModel, onClick: () -> Unit) {
    val isBookmarked by viewModel.isBookmarked(job.id).observeAsState(false)
    Box(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            onClick()
        }) {

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = job.title ?: "No Title",
                    color = LokalPrimaryColor,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        R.string.company_name,
                        job.company_name ?: "Unknown Company"
                    ),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                    )
                )

                val place = job.primary_details?.Place ?: "Location Not Available"
                val salary = job.primary_details?.Salary ?: "Salary Not Mentioned"
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.location_name, place),
                    color = Black,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.salary_amt, salary),
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                CallButton(actionLink = job.custom_link ?: "")

            }
        }
        val currentJob = JobEntity(
            id = job.id,
            title = job.title ?: "No Title",
            companyName = job.company_name ?: "Unknown Company",
            place = job.primary_details?.Place ?: "Location Not Available",
            salary = job.primary_details?.Salary ?: "Salary Not Mentioned",
            phoneNumber = job.custom_link ?: ""
        )
        IconButton(
            onClick = {
                try {
                    if (isBookmarked) {
                        viewModel.deleteBookmark(currentJob)
                    } else {
                        viewModel.insertBookmark(currentJob)
                    }
                } catch (e: Exception) {
                    Log.d(
                        "HomeScreen",
                        "message == ${e.message}  stackTrace == ${e.stackTrace}  cause == ${e.cause}  "
                    )
                }

            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-24).dp, y = 8.dp)
                .background(White, CircleShape)
                .size(28.dp)
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Bookmark",
                tint = if (isBookmarked) Color.Red else Color.Gray
            )
        }
    }
}


@Composable
fun CallButton(actionLink: String) {
    val context = LocalContext.current
    if (!actionLink.isNullOrEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LokalPrimaryColor, shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse(actionLink)
                    }
                    context.startActivity(intent)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Call, contentDescription = "Call", tint = Black
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.call),
                color = Black,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            )
        }
    }
}