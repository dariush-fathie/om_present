package pro.ahoora.zhin.om.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Patient(
        @SerializedName("name")
        @Expose
        val name: String,

        @SerializedName("id")
        @Expose
        val id: Int,

        @SerializedName("x")
        @Expose
        val x: String,

        @SerializedName("y")
        @Expose
        val y: String,

        @SerializedName("z")
        @Expose
        val z: String)