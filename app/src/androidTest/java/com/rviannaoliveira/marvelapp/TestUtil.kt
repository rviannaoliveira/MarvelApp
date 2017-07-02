package com.rviannaoliveira.marvelapp

import android.content.Context
import okhttp3.mockwebserver.MockWebServer
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


/**
 * Criado por rodrigo on 25/06/17.
 */
object TestUtil {

    fun initMockServer(): MockWebServer {
        val server = MockWebServer()
        server.start()
        server.url("/v1/marvel/")
        return server
    }

    fun readFileFromAssets(cx: Context, fileName: String): String {
        val builder = StringBuilder()
        try {
            val stream = cx.assets.open(fileName)
            val bReader = BufferedReader(InputStreamReader(stream, "UTF-8"))
            var line = bReader.readLine()

            while (line != null) {
                builder.append(line)
                line = bReader.readLine()
            }
        } catch (e: IOException) {
            Timber.e(e)
        }

        return builder.toString().substring(0)
    }
}