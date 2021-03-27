package com.laks.tvseries.core.common.people

import com.laks.tvseries.core.data.model.PersonInfo

interface PeopleItemClickListener {
    fun personClickListener(person: PersonInfo)
}