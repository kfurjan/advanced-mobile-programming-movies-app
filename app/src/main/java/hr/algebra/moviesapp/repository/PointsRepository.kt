package hr.algebra.moviesapp.repository

import hr.algebra.moviesapp.api.PointsApi
import hr.algebra.moviesapp.model.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PointsRepository @Inject constructor(
    private val pointsApi: PointsApi
) {
    suspend fun getPoints(): List<Point> {
        return withContext(Dispatchers.IO) {
            pointsApi.getPoints().values.toList()
        }
    }
}