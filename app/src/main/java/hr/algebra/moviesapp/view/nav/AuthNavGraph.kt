package hr.algebra.moviesapp.view.nav

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hr.algebra.moviesapp.R
import hr.algebra.moviesapp.view.AuthScreen
import hr.algebra.moviesapp.view.auth.AuthenticationScreen
import hr.algebra.moviesapp.viewmodel.AuthenticationViewModel


fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
    context: Context
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Login.route
    ) {



        composable(route = AuthScreen.Login.route) {
            AuthenticationScreen(
                icon = R.drawable.login,
                onLogin = {
                    authenticationViewModel.logIn(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, context.getString(R.string.unable_to_login), Toast.LENGTH_SHORT).show()
                        }
                    )

                },
                onRegister = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Register.route)
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) }
            )
        }
        composable(route = AuthScreen.Register.route) {
            AuthenticationScreen(
                icon = R.drawable.register,
                onLogin = {
                    navController.popBackStack()
                    navController.navigate(AuthScreen.Login.route)
                },
                onRegister = {
                    authenticationViewModel.register(
                        onSuccess = {
                            navController.popBackStack()
                            navController.navigate(Graph.MAIN)
                        },
                        onFail = {
                            Toast.makeText(context, context.getString(R.string.unable_to_register), Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                authenticationState = authenticationViewModel.authenticationState.value,
                onEmailChanged = { authenticationViewModel.onEmailChanged(it) },
                onPasswordChanged = { authenticationViewModel.onPasswordChanged(it) },
                isLogin = false
            )
        }
    }
}