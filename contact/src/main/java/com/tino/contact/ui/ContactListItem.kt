package com.tino.contact.ui

import com.tino.common.db.bean.Contact

/**
 * Common UI model between the [Cheese] data class and separators.
 */
sealed class ContactListItem(val name: String) {
    data class Item(val contact: Contact) : ContactListItem(contact.contactName)
    data class Separator(private val letter: Char) : ContactListItem(letter.toUpperCase().toString())
}