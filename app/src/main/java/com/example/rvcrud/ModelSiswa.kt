package com.example.rvcrud

import java.util.*

class ModelSiswa (var id: Int = ambilIdOtomatis(), var name: String = "", var email: String = "") {

    companion object{
        fun ambilIdOtomatis(): Int{
            val acak = Random()
            return acak.nextInt(100)
        }
    }

}