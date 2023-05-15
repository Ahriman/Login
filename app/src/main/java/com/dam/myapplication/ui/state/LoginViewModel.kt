package com.dam.myapplication.ui.state

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dam.myapplication.data.model.UsersList
import com.dam.myapplication.data.model.User

const val MIN_PASSWORD_LENGTH = 8 // TODO: Dejar público aquí para poder acceder desde LoginBlock ?

class LoginViewModel : ViewModel() {

    /**
     * Propiedades
     */
    private var _emailText by mutableStateOf("")
    val emailText get() = _emailText

    private var _passwordText by mutableStateOf("")
    val passwordText get() = _passwordText

    private var _validEmail by mutableStateOf(false)
    val validEmail get() = _validEmail

    private var _validPassword by mutableStateOf(false)
    val validPassword get() = _validPassword

    private var _logginError by mutableStateOf(false)
    val logginError get() = _logginError

    private var _loggedUser: User? by mutableStateOf(null)
    val loggedUser get() = _loggedUser

    private var _passwordVisible by mutableStateOf(false)
    val passwordVisible get() = _passwordVisible

//    private var _enableLogin by mutableStateOf(false)
//    val enableLogin get() = _enableLogin

    /**
     * Funciones
     */
    fun onChangeEmail(email: String) {
        _emailText = email.trim() // Así no mete espacios o tabulaciones al final
        _validEmail = email.isValidEmail()
    }

    fun onChangePassword(password: String) {
        _passwordText = password
        _validPassword = password.isValidPassword()
    }

    fun logIn() {

        val userFound = UsersList().list.find {
            it.email.equals(_emailText, ignoreCase = true) && it.password == _passwordText
        }

        // TODO: Aquí no me funciona el operador elvis por eso uso el if a continuación
        if (userFound != null) {
//            _enableLogin = true
            _logginError = false
            _loggedUser = userFound
        } else {
            _logginError = true
        }

    }

    fun logOut() {
        _loggedUser = null
    }

    fun isValidEmail() = _emailText.isValidEmail()
    fun isValidPassword() = _passwordText.isValidPassword()
    fun isValidEmailAndPassword() = isValidEmail() && isValidPassword()

    fun changePasswordVisible() {
        _passwordVisible = !_passwordVisible
    }

}

// TODO: Dentro o fuera de la clase ?
private fun String.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
private fun String.isValidPassword() = this.length >= MIN_PASSWORD_LENGTH