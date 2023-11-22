package com.dhanaprakash.sqlciphersample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.dhanaprakash.sqlciphersample.ui.theme.SqlCipherSampleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jobDao = SampleApplication.getInstance().getDatabase()?.getJobsDao()

        val mockJobs: List<Job> = listOf(
            Job(
                id = "job1",
                attributes = JobAttributes(
                    documents = listOf(
                        DownloadDocument(
                            fileName = "Document1.pdf",
                            url = "https://example.com/document1"
                        ),
                        DownloadDocument(
                            fileName = "Document2.pdf",
                            url = "https://example.com/document2"
                        )
                    ),
                    clientName = "Client A"
                ),
                status = "In Progress"
            ),
            Job(
                id = "job2",
                attributes = JobAttributes(
                    documents = listOf(
                        DownloadDocument(
                            fileName = "Document3.pdf",
                            url = "https://example.com/document3"
                        )
                    ),
                    clientName = "Client B"
                ),
                status = "Completed"
            ),
            Job(
                id = "job3",
                attributes = JobAttributes(
                    documents = listOf(
                        DownloadDocument(
                            fileName = "Document4.pdf",
                            url = "https://example.com/document3"
                        )
                    ),
                    clientName = "Client C"
                ),
                status = "Pending"
            ),
        )

        lifecycleScope.launch {
            jobDao?.insertJobs(mockJobs)
        }

        val jobsToDisplay = mutableStateListOf<Job>()

        lifecycleScope.launch {
            jobDao?.observeJobs()?.collect {
                jobsToDisplay.clear()
                jobsToDisplay.addAll(it)
            }
        }

        setContent {
            SqlCipherSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(jobsToDisplay) {
                            JobCard(job = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(job: Job, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "JobId : ${job.id}", style = MaterialTheme.typography.titleLarge)
                Text(text = job.status ?: "", style = MaterialTheme.typography.labelMedium)
            }
            Text(
                text = "Client Name : ${job.attributes?.clientName ?: ""}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobPreview() {
    SqlCipherSampleTheme {
        JobCard(
            Job(
                id = "1",
                status = "In Progress",
                attributes = JobAttributes(clientName = "Dhana")
            )
        )
    }
}