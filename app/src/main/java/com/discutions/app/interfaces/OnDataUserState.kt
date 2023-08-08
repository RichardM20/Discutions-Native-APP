package com.discutions.app.interfaces

import com.discutions.app.models.ProfileData

interface OnDataUserState {
    fun onSuccess(profileData: ProfileData);
    fun onFailed(errorMessage:String);
}
