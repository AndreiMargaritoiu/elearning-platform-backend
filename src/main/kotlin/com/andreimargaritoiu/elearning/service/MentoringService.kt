package com.andreimargaritoiu.elearning.service

import com.andreimargaritoiu.elearning.model.Mentorship
import com.andreimargaritoiu.elearning.repository.dataSource.MentoringDataSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class MentoringService(private val mentoringDataSource: MentoringDataSource) {

    fun getMentorship(mentorshipId: String): Mentorship = mentoringDataSource.getMentorship(mentorshipId)
    fun addMentorship(mentorship: Mentorship): Mentorship = mentoringDataSource.addMentorship(mentorship)
    fun updateMentorship(mentorshipId: String, mentorship: Mentorship): Mentorship =
            mentoringDataSource.updateMentorship(mentorshipId, mentorship)
    fun deleteMentorship(mentorshipId: String) = mentoringDataSource.deleteMentorship(mentorshipId)
    fun getMentorships(): Collection<Mentorship> = mentoringDataSource.getMentorships();
    
}