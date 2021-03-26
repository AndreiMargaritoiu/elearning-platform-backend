package com.andreimargaritoiu.elearning.repository.generic

import com.andreimargaritoiu.elearning.model.Mentorship

interface MentoringRepository {

    fun getMentorships(): Collection<Mentorship>
    fun getMentorship(mentorshipId: String): Mentorship
    fun addMentorship(mentorship: Mentorship): Mentorship
    fun updateMentorship(mentorshipId: String, mentorship: Mentorship): Mentorship
    fun deleteMentorship(mentorshipId: String)
    
}