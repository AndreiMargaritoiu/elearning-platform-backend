package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.models.Mentorship
import com.andreimargaritoiu.elearning.model.builders.MentorshipBuilder
import com.andreimargaritoiu.elearning.model.updates.MentorshipUpdates

interface MentoringRepository {

    fun getMentorships(): Collection<Mentorship>
    fun getMentorship(mentorshipId: String): Mentorship
    fun addMentorship(mentorshipBuilder: MentorshipBuilder, userId: String): Mentorship
    fun updateMentorship(mentorshipId: String, mentorshipUpdates: MentorshipUpdates)
    fun deleteMentorship(mentorshipId: String)
    
}