package com.example.compose_demo.data.room.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface TaskDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(task: Task)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun  update(task: Task)
    @Delete
    fun delete(task: Task)
    @Query("SELECT * FROM Tasks WHERE dayId = :thisdate")
    fun gettasksbydday(thisdate: Int): Flow<List<Task>>
    @Query("SELECT * FROM Tasks")
    fun getalltasks():Flow<List<Task>>


}
@Dao
interface DayDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(day: Day)
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun  update(day: Day)
    @Delete
    fun delete(day: Day)
    @Query("SELECT * FROM Days WHERE date=:thisdate")
    fun getdaybydate(thisdate:Date):Day?
    @Query("SELECT * FROM Days")
    fun getalldays():Flow<List<Day>>
}