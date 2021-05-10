package com.ikran.flickrapiapp.search

import androidx.lifecycle.ViewModel
import com.ikran.flickrapiapp.data.Data
import com.ikran.flickrapiapp.data.Photo
import com.ikran.flickrapiapp.repo.SearchRepo
import javax.inject.Inject

class SearchVM @Inject constructor(val searchRepo: SearchRepo) : ViewModel() {

    var data: Data<Photo>? = null

    var oldQuery: String = ""

    fun search(query: String): Data<Photo>? {
        if (data == null || oldQuery != query)
            data = searchRepo.searchPhoto(query)

        oldQuery = query
        return data
    }


}