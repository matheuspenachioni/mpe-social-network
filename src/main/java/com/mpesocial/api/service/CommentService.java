package com.mpesocial.api.service;

import com.mpesocial.api.model.Comment;

public interface CommentService {

    Comment save(Comment comment);

    void delete(Comment comment);

}
