package com.dam.myapplication.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.myapplication.R
import com.dam.myapplication.ui.state.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {

    val vm: LoginViewModel = viewModel()

//    var loggin by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = vm.loggedUser?.name ?: stringResource(id = R.string.login))
                },
                modifier = Modifier.fillMaxWidth(),
//                navigationIcon = {
//                    if (loggin)
//                        IconButton(
//                            onClick = { loggin = false },
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
//                            )
//                        }
//                }
                actions = {
                    vm.loggedUser?.let {
                        Button(onClick = {
                            vm.logOut()
                        }) {
                            Text(text = stringResource(id = R.string.logout))
                        }
                    }
                }
            )
        },
//        bottomBar = {
//            if (loggin) {
//                BottomAppBar(actions = {
//
//                    Row(
//                        Modifier.fillMaxSize(),
//                        horizontalArrangement = Arrangement.spacedBy(
//                            5.dp,
//                            Alignment.CenterHorizontally
//                        ),
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Button(onClick = {
//                            loggin = false
//                        }) {
//                            Text(text = stringResource(id = R.string.logout))
//                        }
//                    }
//
//                })
//            }
//        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            vm.loggedUser?.let {
                Text(text = stringResource(id = R.string.access_ok))
            } ?: LoginBlock(
//                vm.emailText,
//                vm.passwordText,
//                vm.validEmail,
//                vm.validPassword,
//                vm.logginError,
//                vm.passwordVisible,
//                vm.enableLogin,
            )
        }
    }
}


