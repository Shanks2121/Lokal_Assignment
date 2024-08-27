package com.example.shanks.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shanks.R
import com.example.shanks.data.model.Job
import com.example.shanks.ui.theme.LokalPrimaryColor

@Composable
fun JobDetailsScreen(job: Job) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFe3f2fd), Color(0xFFbbdefb))
                )
            )
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFBDBDBD))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = job.title ?: "No title available",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold, fontSize = 24.sp, letterSpacing = 0.5.sp
                    ),
                    color = LokalPrimaryColor
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFBDBDBD))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                JobDetailRow(
                    label = "Company:",
                    value = job.company_name ?: "No company name available",
                    icon = painterResource(id = R.drawable.ic_company)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Posted on:",
                    value = (job.created_on ?: "No posting date available").take(10),
                    icon = painterResource(id = R.drawable.ic_calendar)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Location:",
                    value = job.primary_details?.Place ?: "No location available",
                    icon = painterResource(id = R.drawable.ic_location)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Salary:",
                    value = job.primary_details?.Salary ?: "No salary information available",
                    icon = painterResource(id = R.drawable.ic_salary)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Job Type:",
                    value = job.primary_details?.Job_Type ?: "No job type specified",
                    icon = painterResource(id = R.drawable.ic_job_type)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Experience:",
                    value = job.primary_details?.Experience ?: "No experience required",
                    icon = painterResource(id = R.drawable.ic_experience)
                )
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp
                )

                JobDetailRow(
                    label = "Qualification:",
                    value = job.primary_details?.Qualification ?: "No qualification required",
                    icon = painterResource(id = R.drawable.ic_qualification)
                )
            }
        }

        CallButton(actionLink = job.custom_link ?: "")

        Spacer(modifier = Modifier.height(20.dp))
        if (!job.whatsapp_no.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(LokalPrimaryColor, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable {
                        job.custom_link?.let { link ->
                            if (link.isNotEmpty()) {
                                // Open the number in WhatsApp
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("https://wa.me/${job.whatsapp_no}")
                                }
                                context.startActivity(intent)
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Chat on What's App",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Chat on What's App",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun JobDetailRow(label: String, value: String, icon: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = LokalPrimaryColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = label, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 0.2.sp
                ), color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value, style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp, letterSpacing = 0.2.sp
                ), color = Color.DarkGray
            )
        }
    }
}
