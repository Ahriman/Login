package com.dam.myapplication.ui.elements

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dam.myapplication.R
import com.dam.myapplication.ui.state.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginBlock(
//    emailText: String,
//    onChangeEmail: (String) -> Unit,
//    passwordText: String,
//    onChangePassword: (String) -> Unit,
//    validEmail: Boolean,
//    validPassword: Boolean,
//    logginError: Boolean,
//    passwordVisible: Boolean,
//    enableLogin: Boolean,
) {
    val vm: LoginViewModel = viewModel() // TODO: Así o mejor pasar por parámetro los métodos
    val focusManager = LocalFocusManager.current

    if (vm.logginError)
        Text(
            modifier = Modifier.padding(horizontal = 40.dp),
            text = stringResource(id = R.string.login_error_message),
            color = MaterialTheme.colorScheme.error
        )
    OutlinedTextField(
        value = vm.emailText,
        onValueChange = { vm.onChangeEmail(it) },
        label = { Text(text = stringResource(id = R.string.email)) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(id = R.string.email),
                tint = when (vm.validEmail) {
                    false -> MaterialTheme.colorScheme.error
                    else -> Color.Green // TODO: Verde Material3?
                }
            )
        },
        supportingText = {
            // TODO: Se debería mostrar el mensaje solamente en ciertas condiciones
            //       en las que se incumple el formato de email
            if (!vm.isValidEmailAndPassword())
                Text(
                    text = "No tiene un formato correcto.",
                    color = MaterialTheme.colorScheme.error,
                )
        },
        isError = vm.logginError,
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
        value = vm.passwordText,
        onValueChange = { vm.onChangePassword(it) },
        label = { Text(text = stringResource(id = R.string.password)) },
        trailingIcon = {
            IconButton(
                onClick = { vm.changePasswordVisible() },
            ) {
                Icon(
                    imageVector = if (vm.passwordVisible) Icons.Filled.Visibility else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = stringResource(id = R.string.password),
                    tint = when (vm.validPassword) {
                        false -> MaterialTheme.colorScheme.error
                        else -> Color.Green // TODO: Verde Material3?
                    }
                )
            }

        },
//        supportingText = {
//            if (!passwordText.isValidEmail() && passwordText.length < MIN_PASSWORD_LENGTH && passwordText.isNotEmpty())
//                Text(
////                                text = "Debe tener al menos 8 caracteres.",
//                    text = "Faltan ${MIN_PASSWORD_LENGTH - passwordText.length} caracteres.",
//                    color = MaterialTheme.colorScheme.error,
//                )
//        },
        isError = vm.logginError,
        visualTransformation = if (vm.passwordVisible) VisualTransformation.None else {
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
        onClick = { vm.logIn() },
        enabled = vm.isValidEmailAndPassword()
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

