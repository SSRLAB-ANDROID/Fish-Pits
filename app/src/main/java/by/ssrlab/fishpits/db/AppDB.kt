package by.ssrlab.fishpits.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.objects.district.DistrictDescripted
import by.ssrlab.fishpits.objects.district.DistrictForDB
import by.ssrlab.fishpits.objects.point.PointDescripted
import by.ssrlab.fishpits.objects.point.PointForDB


@Database(entities = [
    DistrictForDB::class,
    DistrictDescripted::class,
    PointForDB::class,
    PointDescripted::class,
    Region::class,
    WaterObject::class
                     ], version = 1, exportSchema = false)
abstract class AppDB: RoomDatabase() {
    abstract val appDao: AppDao

    companion object {

        @Volatile
        private var INSTANCE: AppDB? = null

        fun getInstance(context: Context): AppDB {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDB::class.java, "fish_pits_db_v1").build()
                INSTANCE as AppDB
            } else INSTANCE as AppDB
        }
    }
}