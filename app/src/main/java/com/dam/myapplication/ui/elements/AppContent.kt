package com.dam.myapplication.ui.elements

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dam.myapplication.R

private const val MIN_PASSWORD_LENGTH = 8
private const val USER_TEST = "pepe@pepe.com"
private const val PASSWORD_TEST = "12345678"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {

    var emailText by rememberSaveable { mutableStateOf("") }
    var passwordText by rememberSaveable { mutableStateOf("") }

    var validEmail by rememberSaveable { mutableStateOf(false) }
    var validPassword by rememberSaveable { mutableStateOf(false) }

    var logginError by rememberSaveable { mutableStateOf(false) }

    var loggin by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = if (loggin) "Pepe" else "Login")
                        if (loggin)
                            Button(onClick = {
                                loggin = false
                            }) {
                                Text(text = stringResource(id = R.string.logout))
                            }
                    }
                },
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

            if (loggin) Text(text = stringResource(id = R.string.access_ok))
            else {
                if (logginError)
                    Text(
                        modifier = Modifier.padding(horizontal = 40.dp),
                        text = stringResource(id = R.string.login_error_message),
                        color = MaterialTheme.colorScheme.error
                    )
                OutlinedTextField(
                    value = emailText,
                    onValueChange = {
                        emailText = it.trim() // Así no mete espacios o tabulaciones al final
                        validEmail = it.isValidEmail()
                    },
                    label = { Text(text = stringResource(id = R.string.email)) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = stringResource(id = R.string.email),
                            tint = when (validEmail) {
                                false -> MaterialTheme.colorScheme.error
                                else -> Color.Green // TODO: Verde Material3?
                            }
                        )
                    },
                    supportingText = {
                        // TODO: Se debería mostrar el mensaje solamente en ciertas condiciones
                        //       en las que se incumple el formato de email
                        if (!emailText.isValidEmail() && emailText.length >= 5)
                            Text(
                                text = "No tiene un formato correcto.",
                                color = MaterialTheme.colorScheme.error,
                            )
                    },
                    isError = logginError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = passwordText,
                    onValueChange = {
                        passwordText = it
                        validPassword = it.isValidPassword()
                    },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else {
                                    Icons.Filled.VisibilityOff
                                },
                                contentDescription = stringResource(id = R.string.password),
                                tint = when (validPassword) {
                                    false -> MaterialTheme.colorScheme.error
                                    else -> Color.Green // TODO: Verde Material3?
                                }
                            )
                        }

                    },
                    supportingText = {
                        if (!passwordText.isValidEmail() && passwordText.length < MIN_PASSWORD_LENGTH && passwordText.isNotEmpty())
                            Text(
//                                text = "Debe tener al menos 8 caracteres.",
                                text = "Faltan ${MIN_PASSWORD_LENGTH - passwordText.length} caracteres.",
                                color = MaterialTheme.colorScheme.error,
                            )
                    },
                    isError = logginError,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else {
                        PasswordVisualTransformation()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    singleLine = true,
                )
                Button(
                    onClick = { /*TODO*/
                        if (emailText == USER_TEST && passwordText == PASSWORD_TEST) {
                            loggin = true
                            logginError = false
                        } else
                            logginError = true
                    },
                    enabled = emailText.isValidEmail() && passwordText.isValidPassword()
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }

        }
    }
}

private fun String.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
private fun String.isValidPassword() = this.length >= MIN_PASSWORD_LENGTH
