package com.ikran.flickrapiapp.data

object FlickrUtils {

    const val FORMAT = "json"
    const val SEARCH_URL = "https://www.flickr.com/services/rest/?method=flickr.photos.search" +
            "&api_key=079dc677216b8117c83b350848578316" +
            "&text=dogs" +
            "&privacy_filter=1&safe_search=1&content_type=1" +
            "&format=json&nojsoncallback=1"

    const val BASE_URL = "https://www.flickr.com/services/rest/"
    const val DEFAULT_PAGE_SIZE = 100
    const val DEFAULT_QUERY = "nature"
    const val API_KEY = "079dc677216b8117c83b350848578316"
    const val DEFAULT_PER_PAGE_SIZE = 10

    const val METHOD_SEARCH_RECENT = "flickr.photos.getRecent"
    const val METHOD_SEARCH = "flickr.photos.search"

    const val SMALL_SQUARE = "s"
    const val LARGE_SQUARE = "q"
    const val THUMBNAIL = "t"
    const val SMALL_240 = "m"
    const val SMALL_360 = "n"
    const val MEDIUM_500 = "-"
    const val MEDIUM_640 = "z"
    const val MEDIUM_800 = "c"
    const val MEDIUM_1024 = "b"
    const val LARGE_1600 = "h"
    const val LARGE_2048 = "k"

    fun getFlickrImageLink(id: String, secret: String, serverId: String, farmId: Int, size: String = "m.jpg"): String {

        return "https://live.staticflickr.com/${serverId}/${id}_${secret}_$size"
        //return "https://farm$farmId.staticflickr.com/$serverId/${id}_${secret}_$size.jpg"
    }

}