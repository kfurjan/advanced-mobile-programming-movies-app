package hr.algebra.moviesapp.api

import hr.algebra.moviesapp.model.Point
import retrofit2.http.GET

interface PointsApi {
    @GET("/points.json")
    suspend fun getPoints(): Map<String, Point>
}