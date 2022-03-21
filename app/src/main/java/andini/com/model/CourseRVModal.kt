package andini.com.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


class CourseRVModal : Parcelable {
    var courseName: String? = null
    var courseDescription: String? = null
    var coursePrice: String? = null
    var bestSuitedFor: String? = null
    var courseImg: String? = null
    var courseLink: String? = null
    var courseId: String? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        courseName = `in`.readString()
        courseId = `in`.readString()
        courseDescription = `in`.readString()
        coursePrice = `in`.readString()
        bestSuitedFor = `in`.readString()
        courseImg = `in`.readString()
        courseLink = `in`.readString()
    }

    constructor(
        courseId: String?,
        courseName: String?,
        courseDescription: String?,
        coursePrice: String?,
        bestSuitedFor: String?,
        courseImg: String?,
        courseLink: String?
    ) {
        this.courseName = courseName
        this.courseId = courseId
        this.courseDescription = courseDescription
        this.coursePrice = coursePrice
        this.bestSuitedFor = bestSuitedFor
        this.courseImg = courseImg
        this.courseLink = courseLink
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(courseName)
        dest.writeString(courseId)
        dest.writeString(courseDescription)
        dest.writeString(coursePrice)
        dest.writeString(bestSuitedFor)
        dest.writeString(courseImg)
        dest.writeString(courseLink)
    }

    companion object CREATOR : Creator<CourseRVModal> {
        override fun createFromParcel(parcel: Parcel): CourseRVModal {
            return CourseRVModal(parcel)
        }

        override fun newArray(size: Int): Array<CourseRVModal?> {
            return arrayOfNulls(size)
        }
    }

}
