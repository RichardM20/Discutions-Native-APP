package com.discutions.app.interfaces

interface OnDataFetchListener {

    fun onDataFetchSuccess(dataList:List<String>);
    fun onDataFetchFailure(errorMessage: String);
}