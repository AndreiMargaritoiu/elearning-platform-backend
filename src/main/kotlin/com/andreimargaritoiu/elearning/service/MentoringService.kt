package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.models.Mentorship
import com.andreimargaritoiu.elearning.model.builders.MentorshipBuilder
import com.andreimargaritoiu.elearning.model.updates.MentorshipUpdates
import com.andreimargaritoiu.elearning.repository.dataSource.MentoringDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class MentoringService(private val mentoringDataSource: MentoringDataSource) {

    fun getMentorship(mentorshipId: String): Mentorship = mentoringDataSource.getMentorship(mentorshipId)
    fun addMentorship(mentorshipBuilder: MentorshipBuilder): Mentorship =
            mentoringDataSource.addMentorship(mentorshipBuilder)
    fun updateMentorship(mentorshipId: String, mentorshipUpdates: MentorshipUpdates): Mentorship =
            mentoringDataSource.updateMentorship(mentorshipId, mentorshipUpdates)
    fun deleteMentorship(mentorshipId: String) = mentoringDataSource.deleteMentorship(mentorshipId)
    fun getMentorships(uid: Optional<String>, category: Optional<Collection<String>>): Collection<Mentorship> {
        val mentorships: Collection<Mentorship> = mentoringDataSource.getMentorships()

        if (!uid.isEmpty) {
            return mentorships.filter { it.mentorId == uid.get() }
        }

        if (!category.isEmpty) {
            return mentorships.filter { category.get().contains(it.category) }
        }

        return mentorships
    }
}