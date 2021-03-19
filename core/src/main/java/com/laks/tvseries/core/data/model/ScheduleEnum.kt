package com.laks.tvseries.core.data.model

import androidx.annotation.StringDef


@StringDef(Genre.Action, Genre.Adult, Genre.Adventure, Genre.Anime, Genre.Children, Genre.Children, Genre.Comedy, Genre.Crime, Genre.Diy, Genre.Drama, Genre.Espionage,
           Genre.Family, Genre.Fantasy, Genre.Food, Genre.History, Genre.Horror, Genre.Legal, Genre.Medical, Genre.Music, Genre.Mystery, Genre.Nature, Genre.Romance,
           Genre.ScienceFiction, Genre.Sports, Genre.Supernatural, Genre.Thriller, Genre.Travel, Genre.War, Genre.Western)
@Retention(AnnotationRetention.SOURCE)
annotation class Genre {
    companion object {
        const val Action = "Action"
        const val Adult = "Adult"
        const val Adventure = "Adventure"
        const val Anime = "Anime"
        const val Children= "Children"
        const val Comedy= "Comedy"
        const val Crime = "Crime"
        const val Diy = "DIY"
        const val Drama = "Drama"
        const val Espionage = "Espionage"
        const val Family = "Family"
        const val Fantasy = "Fantasy"
        const val Food = "Food"
        const val History = "History"
        const val Horror = "Horror"
        const val Legal = "Legal"
        const val Medical = "Medical"
        const val Music = "Music"
        const val Mystery = "Mystery"
        const val Nature = "Nature"
        const val Romance = "Romance"
        const val ScienceFiction = "Science-Fiction"
        const val Sports = "Sports"
        const val Supernatural = "Supernatural"
        const val Thriller = "Thriller"
        const val Travel = "Travel"
        const val War = "War"
        const val Western = "Western"
    }
}


@StringDef(Day.Friday, Day.Monday, Day.Saturday, Day.Sunday, Day.Wednesday, Day.Tuesday, Day.Thursday)
@Retention(AnnotationRetention.SOURCE)
annotation class Day {
    companion object {
        const val Friday = "Friday"
        const val Monday = "Monday"
        const val Saturday = "Saturday"
        const val Sunday = "Sunday"
        const val Wednesday= "Wednesday"
        const val Tuesday= "Tuesday"
        const val Thursday= "Thursday"
    }
}

@StringDef(Status.Ended, Status.InDevelopment, Status.Running, Status.ToBeDetermined)
@Retention(AnnotationRetention.SOURCE)
annotation class Status {
    companion object {
        const val Ended = "Ended"
        const val InDevelopment = "In Development"
        const val Running = "Running"
        const val ToBeDetermined = "To Be Determined"
    }
}

@StringDef(ShowType.Animation, ShowType.AwardShow, ShowType.Documentary, ShowType.GameShow, ShowType.News, ShowType.PanelShow,
        ShowType.Reality, ShowType.Scripted, ShowType.Sports, ShowType.TalkShow, ShowType.Variety)
@Retention(AnnotationRetention.SOURCE)
annotation class ShowType {
    companion object {
        const val Animation = "Animation"
        const val AwardShow = "Award Show"
        const val Documentary = "Documentary"
        const val GameShow = "Game Show"
        const val News = "News"
        const val PanelShow = "Panel Show"
        const val Reality = "Reality"
        const val Scripted = "Scripted"
        const val Sports = "Sports"
        const val TalkShow = "Talk Show"
        const val Variety = "Variety"
    }
}

@StringDef(ScheduleType.InsignificantSpecial, ScheduleType.Regular, ScheduleType.SignificantSpecial)
@Retention(AnnotationRetention.SOURCE)
annotation class ScheduleType{
    companion object {
        const val InsignificantSpecial = "insignificant_special"
        const val Regular = "regular"
        const val SignificantSpecial = "significant_special"
    }
}

