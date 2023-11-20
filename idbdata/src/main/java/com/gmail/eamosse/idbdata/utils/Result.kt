package com.gmail.eamosse.idbdata.utils

/**
 * Un classe utilitaire permettant de modéliser les réponses de l'API
 * On utilise une sealed classe qui permet de modéliser deux scénarios
 * 1) [Result.Succes] la requête a été effectuée avec succès et la data est retournée
 * 2) [Result.Error] la requête a échoué, une exception est levée ainsi que le code et le message
 * d'erreurs.
 */
sealed class Result<out T : Any> {
    data class Succes<out T : Any>(val data: T) : Result<T>()
    data class Error(
        val exception: Throwable,
        val code: Int,
        val message: String
    ) : Result<Nothing>()
}