import com.gmail.eamosse.idbdata.data.AuthorDetails
import com.gmail.eamosse.idbdata.data.Review
import com.google.gson.annotations.SerializedName

internal data class ReviewResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val reviews: List<ReviewEntity>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int
) {
    data class ReviewEntity(
        @SerializedName("author")
        val author: String,

        @SerializedName("author_details")
        val authorDetails: AuthorDetailsEntity,

        @SerializedName("content")
        val content: String,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("id")
        val reviewId: String,

        @SerializedName("updated_at")
        val updatedAt: String,

        @SerializedName("url")
        val url: String
    ){
        internal fun toReviewResult() = Review(
             author =  author,
             authorDetails =  authorDetails.toAuthorDetails(),
             content =  content,
             createdAt =  createdAt,
             reviewId =  reviewId,
             updatedAt =  updatedAt,
             url = url
        )
    }

    data class AuthorDetailsEntity(
        @SerializedName("name")
        val name: String?,

        @SerializedName("username")
        val username: String,

        @SerializedName("avatar_path")
        val avatarPath: String?,

        @SerializedName("rating")
        val rating: Int?
    ) {
        internal fun toAuthorDetails() = AuthorDetails(
            name =  name,
            username = username,
            avatarPath = avatarPath,
            rating = rating
        )
    }
}
