package com.rviannaoliveira.marvelapp

import android.support.test.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection


/**
 * Criado por rodrigo on 25/06/17.
 */

fun MockWebServer.initMockServer() {
    this.start()
    this.url("/v1/marvel/")
    this.setDispatcher(dispatcher)
    MarvelApplication.URL = this.url("/v1/marvel/")
}

val dispatcher: Dispatcher = object : Dispatcher() {
    @Throws(InterruptedException::class)
    override fun dispatch(request: RecordedRequest): MockResponse {
        var nameFile = request.path.substringAfterLast("public/")
                .substringBeforeLast("?")

        if (request.path.contains("With=D")) {
            nameFile = "d_$nameFile"
        }

        if (request.path.contains("1009144") || request.path.contains("323")) {
            nameFile = "detail_$nameFile"
        }

        return getMockResponseOK(nameFile)
    }

    private fun getMockResponseOK(nameFile: String): MockResponse {
        return MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(readFileFromAssets(nameFile))
    }
}

fun readFileFromAssets(fileName: String): String {
    val builder = StringBuilder()
    val completeFileName = fileName + ".json"

    try {
        val stream = InstrumentationRegistry.getInstrumentation().targetContext.assets.open(completeFileName)
        val bReader = BufferedReader(InputStreamReader(stream, "UTF-8") as Reader?)
        var line = bReader.readLine()

        while (line != null) {
            builder.append(line)
            line = bReader.readLine()
        }
    } catch (e: IOException) {
        Timber.e(e)
    }
    return builder.toString()
}
