package com.discutions.app.interfaces

interface OnDataFetchListener {
    fun onLoading(loading: Boolean);
    fun onDataFetchSuccess(dataList:List<String>);
    fun onDataFetchFailure(errorMessage: String);
}