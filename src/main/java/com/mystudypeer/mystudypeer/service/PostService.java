package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        // 10 Per page
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate")).subList(0, 4);
    }

    public Optional<Post> getPost(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new EntityNotFoundException("Post '" + id + "' not found");
        }
        return postRepository.findById(id);
    }
}
