package com.example.contactsviewer.data

data class Contact(
    val id: String,
    val name: String,
    val number: String?,
    val secondNumber: String?,
    val image: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (id != other.id) return false
        if (name != other.name) return false
        if (number != other.number) return false
        if (secondNumber != other.secondNumber) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + secondNumber.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}