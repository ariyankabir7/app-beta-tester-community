package app.test.xyz.modal

data class HomeData(
    val app_name: String,
    val c_id: String,
    val test_complete: String,
    val test_uncomplete: String,
    val totallimit: String,
    val uploader_name: String,
    val app_link: String,
    val instruction: String
)

var selectedHomeData: HomeData? = null
