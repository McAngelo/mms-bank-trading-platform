package com.mms.user.service.model.mappers;

import com.mms.user.service.dtos.FeedbackRequestDto;
import com.mms.user.service.dtos.FeedbackResponseDto;
import com.mms.user.service.model.Book;
import com.mms.user.service.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequestDto request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .shareable(false) // Not required and has no impact :: just to satisfy lombok
                        .archived(false) // Not required and has no impact :: just to satisfy lombok
                        .build()
                )
                .build();
    }

    public FeedbackResponseDto toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponseDto.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
