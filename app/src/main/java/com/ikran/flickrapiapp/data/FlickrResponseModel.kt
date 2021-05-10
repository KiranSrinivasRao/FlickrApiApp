package com.ikran.flickrapiapp.data

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlickrApiResult(
    @SerializedName("photos") val photos: FlickrApiResponse,
    @SerializedName("stat") val stat: String
) : Serializable

data class FlickrApiResponse(

    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("perpage") val perpage: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("photo") val photo: List<Photo>
) : Serializable

data class Photo(

    @SerializedName("id") val id: String,
    @SerializedName("owner") val owner: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: Int,
    @SerializedName("farm") val farm: Int,
    @SerializedName("title") val title: String,
    @SerializedName("ispublic") val ispublic: Int,
    @SerializedName("isfriend") val isfriend: Int,
    @SerializedName("isfamily") val isfamily: Int
) : Serializable {

    fun getUrl(): String {
        val imageUrl = "https://live.staticflickr.com/${server}/${id}_${secret}_z.jpg"
        Log.e("Image", "$imageUrl")
        return imageUrl
        //return "https://farm$farm.staticflickr.com/$server/$id"+"_"+"$secret"+"_"+"m.jpg"
    }


}

/**
 * {o-format}

b
c
w
t
m
n
w
z

s,q,t - always available format


{
"id": "51159927841",
"owner": "50109591@N06",
"secret": "8488d52c9c",
"server": "65535",
"farm": 66,
"title": "Kisses",
"ispublic": 1,
"isfriend": 0,
"isfamily": 0
}

https://live.staticflickr.com/{server-id}/{id}_{o-secret}_o.{o-format}

https://live.staticflickr.com/65535/51159927841_8488d52c9c_z.jpg

 */