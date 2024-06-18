package com.devscion.classy.data.datasource

import com.devscion.classy.db.Classy
import com.devscion.classy.db.Image
import com.devscion.classy.domain.datasource.DiffusionImagesDataSource
import com.devscion.classy.domain.model.DataResponse
import com.devscion.classy.utils.Creds
import com.devscion.classy.utils.PlatformStorableImage
import com.devscion.classy.utils.convertToByteArray
import com.devscion.classy.utils.isValidBase64Image
import com.devscion.classy.utils.logAll
import com.devscion.classy.utils.toAppDateFormat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class DiffusionImagesDataSourceImpl constructor(
    private val classyDb: Classy?,//SqlDelight doesn't support web as of now, so db will be null
    private val httpClient: HttpClient
) : DiffusionImagesDataSource {
    private val TAG = "DiffusionImagesDataSour"

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun generateImage(prompt: String): Flow<DataResponse<String, String>> = flow {
        try {

            httpClient.post("https://api-inference.huggingface.co/models/devscion/pakhistoricalplaces") {
                bearerAuth(Creds.BEARER_TOKEN)
                contentType(ContentType.Application.Json)
                setBody(mapOf("inputs" to prompt))
            }
                .body<ByteArray>()
                .let {
                    val base64Image = Base64.encode(it)
                    if (base64Image.isValidBase64Image()) {
                        try {
                            classyDb?.imageQueries?.insert(
                                Image(
                                    imageBase64 = base64Image,
                                    date = GMTDate().toAppDateFormat(),
                                    prompt = prompt,
                                    id = -1
                                )
                            )
                        }catch (e:Exception){
                            e logAll TAG
                        }
                    }
                    emit(DataResponse.Success(base64Image))
                }
        } catch (e: Exception) {
            e logAll TAG
            emit(DataResponse.Error("Network Error generating image"))
        } catch (e: kotlinx.serialization.SerializationException) {
            e logAll TAG
            emit(DataResponse.Error("Network Error generating image"))
        }
    }

    override suspend fun generateText(platformStorableImage: PlatformStorableImage): Flow<DataResponse<String, String>> =
        flow {
            try {
                httpClient.submitFormWithBinaryData(url = "http://192.168.8.102:8080/t2i/",
                    formData =
                        formData {
//                            header("Authorization", token)
                            convertToByteArray(platformStorableImage)!!.let {
                                append("image", it, Headers.build {
                                    println("Original size ${it.size} bytes")
                                    append(HttpHeaders.ContentType, "image/jpg")
                                    append(HttpHeaders.ContentDisposition, "filename=image.jpg")
                                })
                            }
                        }
                ) {

//                    bearerAuth(Creds.BEARER_TOKEN)
//                    contentType(ContentType.MultiPart.FormData)
//                    convertToByteArray(platformStorableImage)?.let {
////                        setBody(it)
//                        formData {
//                            this.append(FormPart("image", it))
//                            this.appendInput(
//                                key = "image",
//                                headers = Headers.build {
//                                    append(
//                                        HttpHeaders.ContentDisposition,
//                                        "filename=image.jpg"
//                                    )
//                                },
//                            ) { buildPacket { writeFully(it) } }
//                        }
//                    } ?: kotlin.run {
//                        emit(DataResponse.Error("System Error Occurred"))
//                        return@flow
//                    }
                }
                    .body<Map<String, String>>()
                    .let {
                        emit(DataResponse.Success(it["caption"]))
                    }
            } catch (e: Exception) {
                e logAll TAG
                emit(DataResponse.Error("Network Error annotating image"))
            } catch (e: kotlinx.serialization.SerializationException) {
                e logAll TAG
                emit(DataResponse.Error("Network Error annotating image"))
            }
        }

    override suspend fun getAllImages(): Flow<DataResponse<List<Image>, String>> = flow {
        try {
            emit(
                DataResponse.Success(
                    classyDb?.imageQueries?.getAllImages()?.executeAsList() ?: listOf()
                )
            )
        } catch (e: Exception) {
            e logAll TAG
            emit(DataResponse.Error("Some error occurred"))
        }
    }
}