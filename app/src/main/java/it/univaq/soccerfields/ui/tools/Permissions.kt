package it.univaq.soccerfields.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

sealed class Permission(
    val title: String,
    val message: String,
    val permissions: List<String>
)

class LocationPermission: Permission(
    title = "Location Permission",
    message = "Your app need access to your location",
    permissions = listOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
)

class LifeCycleEvent(
    val event: Lifecycle.Event,
    val action: () -> Unit
)

//Controlla se i permessi sono stati autorizzati
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionChecker(
    permission: Permission,
    events: List<LifeCycleEvent> = emptyList(),
    content: @Composable () -> Unit = {}
){
    val permissionState = rememberMultiplePermissionsState(
        permissions = permission.permissions
    )

    if(permissionState.allPermissionsGranted){
        content()
        //Qualsiasi evento gli venga passato viene eseguita la sua action (start/stop)
        events.forEach {
            LifecycleEventEffect(it.event) {
                it.action()
            }
        }
    } else {
        if (permissionState.shouldShowRationale){
            PermissionDialog(
                permission = permission,
                onDismiss = {},
                onRequest = { permissionState.launchMultiplePermissionRequest() },
            )
        } else {
            SideEffect {
                permissionState.launchMultiplePermissionRequest()
            }
        }
    }
}

//Dialog per accettare i permessi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun PermissionDialog(
    permission: Permission = LocationPermission(),
    onDismiss: () -> Unit = {},
    onRequest: () -> Unit = {},
){
    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Column (
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = permission.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = permission.message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 15.dp),
            )

            Row {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = onRequest,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Request")
                }
            }
        }
    }
}