package com.example.androidbase.common.dataLayer.remote.error

class ConnectionError : Throwable() {

    override fun toString(): String {
        return "Connection Error"
    }
}
